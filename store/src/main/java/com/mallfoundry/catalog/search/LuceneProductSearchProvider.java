/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.catalog.search;

import com.mallfoundry.catalog.InternalProduct;
import com.mallfoundry.catalog.Product;
import com.mallfoundry.catalog.ProductQuery;
import com.mallfoundry.catalog.ProductStatus;
import com.mallfoundry.catalog.ProductVariant;
import com.mallfoundry.data.PageList;
import com.mallfoundry.data.SliceList;
import com.mallfoundry.inventory.InventoryStatus;
import com.mallfoundry.util.JsonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermInSetQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LuceneProductSearchProvider implements ProductSearchProvider {

    private static final String PRODUCT_FIELD_NAME = "product";

    private static final String STORE_ID_FIELD_NAME = "product.storeId";

    private static final String PRODUCT_ID_FIELD_NAME = "product.id";

    private static final String PRODUCT_NAME_FIELD_NAME = "product.name";

    private static final String PRODUCT_TYPE_FIELD_NAME = "product.type";

    private static final String PRODUCT_PRICE_FIELD_NAME = "product.price";

    private static final String PRODUCT_COLLECTIONS_FIELD_NAME = "product.collections";

    private static final String PRODUCT_STATUS_FIELD_NAME = "product.status";

    private static final String PRODUCT_INVENTORY_STATUS_FIELD_NAME = "product.inventoryStatus";

    private final String directoryPath;

    public LuceneProductSearchProvider(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    private Document getDocument(String productId) {
        try (Directory directory = FSDirectory.open(Path.of(this.directoryPath))) {
            if (ArrayUtils.isEmpty(directory.listAll())) {
                return null;
            }
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
                queryBuilder.add(new TermQuery(new Term(PRODUCT_ID_FIELD_NAME, productId)), BooleanClause.Occur.MUST);
                TopDocs topDocs = searcher.search(queryBuilder.build(), 1);
                ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                for (ScoreDoc scoreDoc : scoreDocs) {
                    int docId = scoreDoc.doc;
                    return reader.document(docId);
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Product getProduct(String productId) {
        var document = this.getDocument(productId);
        return Objects.isNull(document) ? null : JsonUtils.parse(document.get("product"), InternalProduct.class);
    }

    private void setDocument(Product product, Document document) {
        document.removeFields(PRODUCT_ID_FIELD_NAME);
        document.add(new StringField(PRODUCT_ID_FIELD_NAME, String.valueOf(product.getId()), Field.Store.YES));

        document.removeFields(STORE_ID_FIELD_NAME);
        document.add(new StringField(STORE_ID_FIELD_NAME, String.valueOf(product.getStoreId()), Field.Store.NO));

        document.removeFields(PRODUCT_NAME_FIELD_NAME);
        document.add(new TextField(PRODUCT_NAME_FIELD_NAME, product.getName(), Field.Store.YES));

        document.removeFields(PRODUCT_TYPE_FIELD_NAME);
        document.add(new StringField(PRODUCT_TYPE_FIELD_NAME, String.valueOf(product.getType()), Field.Store.NO));

        document.removeFields(PRODUCT_STATUS_FIELD_NAME);
        document.add(new StringField(PRODUCT_STATUS_FIELD_NAME, String.valueOf(product.getStatus()), Field.Store.NO));

        document.removeFields(PRODUCT_INVENTORY_STATUS_FIELD_NAME);
        document.add(new StringField(PRODUCT_INVENTORY_STATUS_FIELD_NAME, String.valueOf(product.getInventoryStatus()), Field.Store.NO));

        document.removeFields(PRODUCT_PRICE_FIELD_NAME);
        Stream.concat(Stream.ofNullable(product.getPrice()), product.getVariants().stream().map(ProductVariant::getPrice))
                .map(BigDecimal::toString).distinct()
                .forEach(price -> document.add(new StringField(PRODUCT_PRICE_FIELD_NAME, price, Field.Store.NO)));

        if (CollectionUtils.isNotEmpty(product.getCollections())) {
            document.removeFields(PRODUCT_COLLECTIONS_FIELD_NAME);
            product.getCollections().forEach(collection ->
                    document.add(new StringField(PRODUCT_COLLECTIONS_FIELD_NAME, collection, Field.Store.NO)));
        }

        document.removeFields(PRODUCT_FIELD_NAME);
        document.add(new StoredField(PRODUCT_FIELD_NAME, JsonUtils.stringify(product)));
    }

    public void addProduct(Product product) {
        try (Directory directory = FSDirectory.open(Path.of(this.directoryPath))) {
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            try (IndexWriter indexWriter = new IndexWriter(directory, config)) {
                Document document = new Document();
                setDocument(product, document);
                indexWriter.addDocument(document);
                indexWriter.commit();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(Product product) {
        try (Directory directory = FSDirectory.open(Path.of(this.directoryPath))) {
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            try (IndexWriter indexWriter = new IndexWriter(directory, config)) {
                Document document = new Document();
                setDocument(product, document);
                indexWriter.updateDocument(new Term(PRODUCT_ID_FIELD_NAME, String.valueOf(product.getId())), document);
                indexWriter.commit();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product) {
        if (Objects.isNull(getProduct(product.getId()))) {
            this.addProduct(product);
        } else {
            this.updateProduct(product);
        }
    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public SliceList<Product> search(ProductQuery search) {
        try (Directory directory = FSDirectory.open(Path.of(this.directoryPath))) {
            Analyzer analyzer = new StandardAnalyzer();
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
                if (Objects.nonNull(search.getName())) {
                    QueryParser parser = new QueryParser(PRODUCT_NAME_FIELD_NAME, analyzer);
                    Query queryName = parser.parse(search.getName());
                    queryBuilder.add(queryName, BooleanClause.Occur.MUST);
                }

                if (Objects.nonNull(search.getStoreId())) {
                    queryBuilder.add(new TermQuery(new Term(STORE_ID_FIELD_NAME, search.getStoreId())), BooleanClause.Occur.MUST);
                }

                if (CollectionUtils.isNotEmpty(search.getTypes())) {
                    var types = search.getTypes().stream().map(Enum::toString).map(BytesRef::new).collect(Collectors.toSet());
                    queryBuilder.add(new TermInSetQuery(PRODUCT_TYPE_FIELD_NAME, types), BooleanClause.Occur.MUST);
                }

                if (CollectionUtils.isNotEmpty(search.getStatuses())) {
                    var statuses = search.getStatuses().stream().map(ProductStatus::toString).map(BytesRef::new).collect(Collectors.toSet());
                    queryBuilder.add(new TermInSetQuery(PRODUCT_STATUS_FIELD_NAME, statuses), BooleanClause.Occur.MUST);
                }
                if (CollectionUtils.isNotEmpty(search.getInventoryStatuses())) {
                    var statuses = search.getInventoryStatuses().stream().map(InventoryStatus::toString).map(BytesRef::new).collect(Collectors.toSet());
                    queryBuilder.add(new TermInSetQuery(PRODUCT_INVENTORY_STATUS_FIELD_NAME, statuses), BooleanClause.Occur.MUST);
                }

                if (CollectionUtils.isNotEmpty(search.getCollections())) {
                    var collectionIds = search.getCollections().stream().map(BytesRef::new).collect(Collectors.toSet());
                    queryBuilder.add(new TermInSetQuery(PRODUCT_COLLECTIONS_FIELD_NAME, collectionIds), BooleanClause.Occur.MUST);
                }

                if (Objects.nonNull(search.getMinPrice()) || Objects.nonNull(search.getMaxPrice())) {
                    var minPrice = Objects.nonNull(search.getMinPrice()) ? new BytesRef(search.getMinPrice().toString()) : null;
                    var maxPrice = Objects.nonNull(search.getMaxPrice()) ? new BytesRef(search.getMaxPrice().toString()) : null;
                    queryBuilder.add(new TermRangeQuery(PRODUCT_PRICE_FIELD_NAME, minPrice, maxPrice, true, true), BooleanClause.Occur.MUST);
                }

                int totalSize = searcher.count(queryBuilder.build());
                TopDocs topDocs = searcher.search(queryBuilder.build(), 10);

                ScoreDoc[] scoreDocs = topDocs.scoreDocs;

                List<Product> products = new ArrayList<>();
                for (ScoreDoc scoreDoc : scoreDocs) {
                    int docId = scoreDoc.doc;
                    Document doc = reader.document(docId);
                    String product = doc.get("product");
                    products.add(JsonUtils.parse(product, InternalProduct.class));
                }
                return PageList.of(products).page(search.getPage()).limit(search.getLimit()).totalSize(totalSize);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

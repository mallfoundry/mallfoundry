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

package com.mallfoundry.store.product.search;

import com.mallfoundry.data.PageList;
import com.mallfoundry.data.SliceList;
import com.mallfoundry.store.product.InternalProduct;
import com.mallfoundry.util.JsonUtils;
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
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LuceneProductSearchProvider implements ProductSearchProvider {

    private static final String STORE_ID_FIELD_NAME = "store_id";

    private static final String PRODUCT_ID_FIELD_NAME = "product_id";

    private static final String PRODUCT_TITLE_FIELD_NAME = "product_title";

    private final String directoryPath;

    public LuceneProductSearchProvider(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    private InternalProduct getProduct(Long productId) {
        try {
            Directory directory = FSDirectory.open(Path.of(this.directoryPath));
            if (ArrayUtils.isEmpty(directory.listAll())) {
                return null;
            }
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
            queryBuilder.add(new TermQuery(new Term(PRODUCT_ID_FIELD_NAME, String.valueOf(productId))), BooleanClause.Occur.MUST);
            TopDocs topDocs = searcher.search(queryBuilder.build(), 1);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                Document doc = reader.document(docId);
                String product = doc.get("product");
                return JsonUtils.parse(product, InternalProduct.class);
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addProduct(InternalProduct product) {
        try {
            Directory directory = FSDirectory.open(Path.of(this.directoryPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, config);
            Document document = new Document();
            document.add(new StringField(PRODUCT_ID_FIELD_NAME, String.valueOf(product.getId()), Field.Store.YES));
            document.add(new StringField(STORE_ID_FIELD_NAME, String.valueOf(product.getStoreId()), Field.Store.YES));
            document.add(new TextField(PRODUCT_TITLE_FIELD_NAME, product.getTitle(), Field.Store.YES));
            document.add(new StoredField("product", JsonUtils.stringify(product)));
            indexWriter.addDocument(document);

            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateProduct(InternalProduct product) {
        try {
            Directory directory = FSDirectory.open(Path.of(this.directoryPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, config);
            Document document = new Document();
            document.add(new StringField(PRODUCT_ID_FIELD_NAME, String.valueOf(product.getId()), Field.Store.YES));
            document.add(new StringField(STORE_ID_FIELD_NAME, String.valueOf(product.getStoreId()), Field.Store.YES));
            document.add(new TextField(PRODUCT_TITLE_FIELD_NAME, product.getTitle(), Field.Store.YES));
            document.add(new StoredField("product", JsonUtils.stringify(product)));
            indexWriter.updateDocument(new Term(PRODUCT_ID_FIELD_NAME, String.valueOf(product.getId())), document);
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(InternalProduct product) {
        if (Objects.isNull(getProduct(product.getId()))) {
            this.addProduct(product);
        } else {
            this.updateProduct(product);
        }
    }

    @Override
    public void delete(InternalProduct product) {

    }

    @Override
    public SliceList<InternalProduct> search(ProductQuery search) {
        try {
            Directory directory = FSDirectory.open(Path.of(this.directoryPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();

            if (Objects.nonNull(search.getTitle())) {
                QueryParser parser = new QueryParser(PRODUCT_TITLE_FIELD_NAME, analyzer);
                Query queryName = parser.parse(search.getTitle());
                queryBuilder.add(queryName, BooleanClause.Occur.SHOULD);
            }

            if (Objects.nonNull(search.getStoreId())) {
                queryBuilder.add(new TermQuery(new Term(STORE_ID_FIELD_NAME, search.getStoreId())), BooleanClause.Occur.MUST);
            }

            int totalSize = searcher.count(queryBuilder.build());
            TopDocs topDocs = searcher.search(queryBuilder.build(), 10);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            List<InternalProduct> products = new ArrayList<>();
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                Document doc = reader.document(docId);
                String product = doc.get("product");
                products.add(JsonUtils.parse(product, InternalProduct.class));
            }
            return PageList.of(products).page(search.getPage()).limit(search.getLimit()).totalSize(totalSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

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

package com.mallfoundry.catalog.domain.product;

import com.mallfoundry.storefront.domain.product.Product;
import com.mallfoundry.util.JsonUtils;
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
import java.util.Collections;
import java.util.List;

public class LuceneProductRepository implements ProductRepository {

    private final String directoryPath;

    public LuceneProductRepository(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void add(Product product) {
        try {
            Directory directory = FSDirectory.open(Path.of(this.directoryPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, config);
            Document document = new Document();
            document.add(new StringField("id", String.valueOf(product.getId()), Field.Store.YES));
            document.add(new TextField("name", product.getName(), Field.Store.YES));
            document.add(new StoredField("product", JsonUtils.stringify(product)));
            indexWriter.addDocument(document);
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> search(ProductQuery search) {
        try {
            Directory directory = FSDirectory.open(Path.of(this.directoryPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("name", analyzer);
            Query query = parser.parse(search.getName());
            TopDocs topDocs = searcher.search(query, 10);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            List<Product> products = new ArrayList<>();
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                Document doc = reader.document(docId);
                String product = doc.get("product");
                products.add(JsonUtils.parse(product, Product.class));
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getProduct(String id) {
        try {
            Directory directory = FSDirectory.open(Path.of(this.directoryPath));
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = new TermQuery(new Term("id", id));
            TopDocs topDocs = searcher.search(query, 10);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                Document doc = reader.document(docId);
                String product = doc.get("product");
                return JsonUtils.parse(product, Product.class);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> getProducts(List<String> ids) {
        try {
            Directory directory = FSDirectory.open(Path.of(this.directoryPath));
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            BooleanQuery.Builder builder = new BooleanQuery.Builder();

            for (String id : ids) {
                builder.add(new TermQuery(new Term("id", id)), BooleanClause.Occur.SHOULD);
            }
            BooleanQuery query = builder.build();
            int count = searcher.count(query);
            if (count == 0) {
                return Collections.emptyList();
            }
            TopDocs topDocs = searcher.search(query, count);
            List<Product> products = new ArrayList<>();
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                products.add(JsonUtils.parse(reader.document(scoreDoc.doc).get("product"), Product.class));
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

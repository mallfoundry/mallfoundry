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

package com.github.shop.catalog.application.search;

import com.github.shop.catalog.FirstProduct;
import com.github.shop.catalog.ProductSearch;
import com.github.shop.catalog.search.ProductSearchService;
import com.github.shop.util.JsonUtils;
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
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneProductSearchService implements ProductSearchService {

    @Override
    public void create(FirstProduct product) {
        try {
            Directory directory = FSDirectory.open(Path.of("D:/shop/data/indexes"));
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
    public List<FirstProduct> search(ProductSearch search) {
        try {
            Directory directory = FSDirectory.open(Path.of("D:/shop/data/indexes"));
            Analyzer analyzer = new StandardAnalyzer();
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("name", analyzer);
            Query query = parser.parse(search.getName());
            TopDocs topDocs = searcher.search(query, 10);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            List<FirstProduct> firstProducts = new ArrayList<>();
            for (ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                Document doc = reader.document(docId);
                String product = doc.get("product");
                firstProducts.add(JsonUtils.parse(product, FirstProduct.class));
            }
            return firstProducts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

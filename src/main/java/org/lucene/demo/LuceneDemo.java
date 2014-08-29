package org.lucene.demo;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

/**
 * Lucene search demo
 * @author Guduru, Thirupathi (TG028792)
 */
public class LuceneDemo {

    /**
     * @param args
     * @throws IOException
     * @throws ParseException
     */
    public static void main(final String[] args) throws IOException, ParseException {

        // write index
        final StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
        final Directory index = new NIOFSDirectory(new File("/Users/tg028792/git/lucene/search.lucene"));
        final IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46, analyzer);

        // final IndexWriter writer = new IndexWriter(index, config);
        //
        // writeIndex(writer, "Lucene", "Lucene Search Library");
        // writeIndex(writer, "Lucene-Solr", "Solr Search Lucene Framework");
        // writeIndex(writer, "Lucene-ElasticSearch", "Elastic Search Lucene Framework");
        // writer.close(); // Committing all transaction so we can open the file for read
        // query
        final String query = args.length > 0 ? args[0] : "Lucene";
        final Query q = new QueryParser(Version.LUCENE_46, "title", analyzer).parse(query);

        // search
        final int hits = 10;
        final IndexReader reader = DirectoryReader.open(index);
        final IndexSearcher searcher = new IndexSearcher(reader);
        final TopScoreDocCollector collector = TopScoreDocCollector.create(hits, true);
        searcher.search(q, collector);

        final ScoreDoc[] items = collector.topDocs().scoreDocs;

        // print searched items
        System.out.println("Found :" + items.length);
        for (final ScoreDoc doc : items) {
            final Document document = searcher.doc(doc.doc);
            System.out.println(document.get("title") + " -- " + document.get("content"));
        }
    }

    /**
     * @param writer
     * @param content
     * @throws IOException
     */
    private static void writeIndex(final IndexWriter writer, final String title, final String content)
            throws IOException {
        final Document document = new Document();
        final TextField textField = new TextField("title", title, Field.Store.YES);
        final StringField stringField = new StringField("content", content, Field.Store.YES);
        document.add(textField);
        document.add(stringField);
        writer.addDocument(document);
    }

}

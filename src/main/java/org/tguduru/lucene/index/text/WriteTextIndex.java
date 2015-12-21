package org.tguduru.lucene.index.text;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import java.io.IOException;

/**
 * Writes an index using {@link org.apache.lucene.document.TextField} which will tokenize and index the document in the
 * inverted index.
 * @author Guduru, Thirupathi Reddy
 * @modified 12/14/15
 */
public class WriteTextIndex {
    private final String indexName;
    private final Directory directory;

    public WriteTextIndex(final String indexName, final Directory directory) {
        this.indexName = indexName;
        this.directory = directory;
    }

    public void writeIndex(final String content) throws IOException {
        final Analyzer standardAnalyzer = new StandardAnalyzer();
        final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        final IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        final Document document = new Document();
        final TextField textField = new TextField(indexName, content, Field.Store.YES);
        document.add(textField);
        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();

    }
}

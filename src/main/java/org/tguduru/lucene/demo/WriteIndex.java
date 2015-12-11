package org.tguduru.lucene.demo;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.io.IOException;

/**
 * Writes the index to a given a file name
 * @author Guduru, Thirupathi Reddy
 * @modified 12/10/15
 */
public class WriteIndex {
    private final Directory directory;
    private final String indexName;
    private final String contentIndex;

    public WriteIndex(final Directory directory, final String indexName, final String contentIndex) {
        this.directory = directory;
        this.indexName = indexName;
        this.contentIndex = contentIndex;
    }

    public Directory getDirectory() {
        return directory;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getContentIndex() {
        return contentIndex;
    }

    public void writeIndex(final String title, final String content) throws IOException {
        final StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
        final IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_9, analyzer);

        final IndexWriter writer = new IndexWriter(directory, config);
        final Document document = new Document();
        final TextField textField = new TextField(indexName, title, Field.Store.YES);
        final StringField stringField = new StringField(contentIndex, content, Field.Store.YES);
        document.add(textField);
        document.add(stringField);
        writer.addDocument(document);
        writer.commit();
        writer.close(); // this will write the index data
    }
}

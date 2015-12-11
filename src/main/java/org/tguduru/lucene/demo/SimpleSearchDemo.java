package org.tguduru.lucene.demo;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Lucene search demo
 * @author Guduru, Thirupathi Reddy
 */
public class SimpleSearchDemo {

    /**
     * @param args
     * @throws IOException
     * @throws ParseException
     */
    public static void main(final String[] args) throws IOException, ParseException {
        final String defaultIndexFileName = "lucene.index";
        final String indexName = "title";
        final String content = "content";
        final Directory index = new NIOFSDirectory(new File(defaultIndexFileName));
        // write index
        final WriteIndex writeIndex = new WriteIndex(index, indexName, content);
        writeIndex.writeIndex("Lucene", "Lucene Search Library");
        writeIndex.writeIndex("Lucene-Solr", "Solr Search Lucene Framework");
        writeIndex.writeIndex("Lucene-ElasticSearch", "Elastic Search Lucene Framework");

        // search
        final SearchIndex searchIndex = new SearchIndex(index, indexName);
        final List<String> foundItems = searchIndex.searchIndex("lucene", 3);

        // print searched items
        foundItems.forEach(System.out::println);
    }
}

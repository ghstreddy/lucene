package org.tguduru.lucene.demo;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the index provided by the searchIndex file name.
 * @author Guduru, Thirupathi Reddy
 * @modified 12/10/15
 */
public class SearchIndex {
    private final Directory directory;
    private final String searchIndex;

    public SearchIndex(final Directory directory, final String searchIndex) {
        this.directory = directory;
        this.searchIndex = searchIndex;
    }

    public Directory getDirectory() {
        return directory;
    }

    public String getSearchIndex() {
        return searchIndex;
    }

    public List<String> searchIndex(final String searchString, final int topN) throws ParseException, IOException {
        final StandardAnalyzer analyzer = new StandardAnalyzer();
        final Query q = new QueryParser(searchIndex, analyzer).parse(searchString);

        // search
        final IndexReader reader = DirectoryReader.open(directory);
        final IndexSearcher searcher = new IndexSearcher(reader);
        final TopScoreDocCollector collector = TopScoreDocCollector.create(topN);
        searcher.search(q, collector);
        final ScoreDoc[] items = collector.topDocs().scoreDocs;
        final List<String> strings = new ArrayList<>();
        for (final ScoreDoc doc : items) {
            final Document document = searcher.doc(doc.doc);
            strings.add(document.get("title") + " -- " + document.get("content"));
        }

        return strings;
    }
}

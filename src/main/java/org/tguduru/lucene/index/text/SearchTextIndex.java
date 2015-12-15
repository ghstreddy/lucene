package org.tguduru.lucene.index.text;

import com.google.common.collect.Lists;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.List;

/**
 * Searches through the index of the huge text created by using {@link org.apache.lucene.document.TextField}.
 * @author Guduru, Thirupathi Reddy
 * @modified 12/14/15
 */
public class SearchTextIndex {
    private final Directory directory;
    private final String indexName;

    public SearchTextIndex(final Directory directory, final String indexName) {
        this.directory = directory;
        this.indexName = indexName;
    }

    public List<String> searchIndex(final String searchString) throws ParseException, IOException {
        final List<String> resultStrings = Lists.newArrayList();
        final Analyzer standardAnalyzer = new StandardAnalyzer();
        final Query query = new QueryParser(indexName, standardAnalyzer).parse(searchString);

        final IndexReader indexReader = DirectoryReader.open(directory);
        final IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        final TopDocs topDocs = indexSearcher.search(query,5);
        final ScoreDoc[] resultDocs = topDocs.scoreDocs;

        for (final ScoreDoc scoreDoc : resultDocs) {
            final Document document = indexSearcher.doc(scoreDoc.doc);
            resultStrings.add(document.get(indexName));
        }

        return resultStrings;
    }
}

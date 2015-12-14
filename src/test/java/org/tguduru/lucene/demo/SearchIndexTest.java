package org.tguduru.lucene.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 12/10/15
 */
public class SearchIndexTest {

    @Test
    public void testSearchIndex() throws IOException, ParseException {
        final Directory directory = new RAMDirectory();
        final WriteIndex writeIndex = new WriteIndex(directory, "title", "content");
        writeIndex.writeIndex("test-title", "content1");
        writeIndex.writeIndex("test-title", "content2");
        writeIndex.writeIndex("test-title", "content3");

        // found items for given search string
        final SearchIndex searchIndex = new SearchIndex(directory, "title");
        final List<String> strings = searchIndex.searchIndex("title", 2);
        assertEquals(2, strings.size());

        // not found search string
        final List<String> notFoundList = searchIndex.searchIndex("noIndex", 2);
        assertTrue(notFoundList.isEmpty());

    }

}
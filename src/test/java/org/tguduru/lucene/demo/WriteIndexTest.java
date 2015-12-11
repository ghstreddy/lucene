package org.tguduru.lucene.demo;

import static org.junit.Assert.assertEquals;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 12/10/15
 */
public class WriteIndexTest {

    @Test
    public void testWriteIndex() throws IOException {
        final Directory directory = new RAMDirectory();
        final WriteIndex writeIndex = new WriteIndex(directory, "index", "content");
        writeIndex.writeIndex("index1", "indexContent1");
        writeIndex.writeIndex("index2", "indexContent2");
        writeIndex.writeIndex("index3", "indexContent3");

        final IndexReader indexReader = DirectoryReader.open(directory);
        assertEquals(3, indexReader.getContext().leaves().size());
    }

}
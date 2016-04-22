package org.tguduru.lucene.index.text;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

/**
 * Demonstrates the usage of {@link org.apache.lucene.document.TextField}.
 * @author Guduru, Thirupathi Reddy
 * @modified 12/14/15
 */
public class TextContentIndexDemo {
    public static void main(final String[] args) throws IOException, ParseException {
        final String indexName = "textIndex";

        final Path path = FileSystems.getDefault().getPath("/tmp","textIndex");
        final Directory directory = new NIOFSDirectory(path);

        //write
        final WriteTextIndex writeTextIndex = new WriteTextIndex(indexName,directory);
        writeTextIndex.writeIndex("This is the text Content that can be Searched with any of this text");

        //search
        final SearchTextIndex searchTextIndex = new SearchTextIndex(directory,indexName);
        final List<String> strings = searchTextIndex.searchIndex("content");
        strings.forEach(System.out::println);

    }
}

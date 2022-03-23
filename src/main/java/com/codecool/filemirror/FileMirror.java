package com.codecool.filemirror;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileMirror {

    public FileMirror(String inPath) {
    }

    void mirror(){
        try (final Stream<String> lines = Files.lines(Paths.get("xxx.txt"))) {
            lines.collect(Collectors.toCollection(LinkedList::new))
                    .descendingIterator()
                    .forEachRemaining(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

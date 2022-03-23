package com.codecool.filemirror;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestFileMirror {
    public static final String DATA_PATH = "src/main/resources/";
    public static final String TXT = ".txt";

    @Test
    @Order(1)
    void testMirrorCreatesOutputFileWithPath() throws IOException {
        String file = "only_one";
        String inPath = DATA_PATH + file + TXT;
        String outPath = DATA_PATH + file + ".mirror" + TXT;
        deleteIfExists(outPath);
        FileMirror fm = new FileMirror(inPath);
        fm.mirror();
        File out = new File(outPath);
        assertThat(out).exists();
    }

    @Test
    @Order(2)
    void testMirrorCopiesOnlyOneCharFile() throws IOException {
        String file = "only_one";
        String inPath = DATA_PATH + file + TXT;
        String outPath = DATA_PATH + file + ".mirror" + TXT;
        deleteIfExists(outPath);

        FileMirror fm = new FileMirror(inPath);
        fm.mirror();

        File out = new File(outPath);
        assertThat(out)
                .exists()
                .content()
                .hasLineCount(4)
                .contains("xxxx");
    }

    @Test
    @Order(3)
    void testMirrorCopiesSameCharsFile() throws IOException {
        String file = "same_chars";
        String inPath = DATA_PATH + file + TXT;
        String outPath = DATA_PATH + file + ".mirror" + TXT;
        deleteIfExists(outPath);

        FileMirror fm = new FileMirror(inPath);
        fm.mirror();

        File out = new File(outPath);
        assertThat(out)
                .exists()
                .content()
                .hasLineCount(3)
                .containsSubsequence("cccccccccccccccccccccccc", "bbbbbbbbbbbbbbbbbbbbbbbb", "aaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Test
    @Order(4)
    void testMirrorReversesSameCharPerRowsFile() throws IOException {
        String file = "same_char_rows";
        String inPath = DATA_PATH + file + TXT;
        String outPath = DATA_PATH + file + ".mirror" + TXT;
        deleteIfExists(outPath);

        FileMirror fm = new FileMirror(inPath);
        fm.mirror();

        File out = new File(outPath);
        assertThat(out)
                .exists()
                .content()
                .hasLineCount(3)
                .contains("abcdefg");
    }

    @Test
    @Order(5)
    void testMirrorReversesPoemRowsFile() throws IOException {
        String file = "poem";
        String inPath = DATA_PATH + file + TXT;
        String outPath = DATA_PATH + file + ".mirror" + TXT;
        deleteIfExists(outPath);

        FileMirror fm = new FileMirror(inPath);
        fm.mirror();

        File out = new File(outPath);
        assertThat(out)
                .exists()
                .content()
                .hasLineCount(12)
                .containsSubsequence("A HIT BOLDOGÍT", "És a mosoly egy sugarkája");
    }

    private void deleteIfExists(String path) throws IOException {
        Path p = Paths.get(path);
        if (Files.exists(p)) Files.delete(p);
    }
}

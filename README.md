# HTV - Horizontális Tükröző

## Bevezetés

Valaki tréfát akart űzni veled, és megfordította a fájljaidat!
Akár melyik szövegfájlodat nézed, az utolsó sor szerepel az első sorban, s az első az utolsóban.

Írj egy java programot, ami képes visszaállítani a megtükrözött fájljaidat!

## Feladatleírás

Írj egy osztályt, `FileMirror`, melynek van egy `mirror()` metódusa. A `FileMirror`-t úgy lehet létrehozni, hogy
konstruktorában megadjuk a tükrözendő fájl elérési útvonalát + nevét. A `mirror` hívás beolvassa a fájl tartalmát,
soronként megfordítja, és egy új fájlba menti, melynek neve: `<eredeti fájl név>.mirror.<kiterjesztes>`. Pl.: `dog.txt`
&rarr; `dog.mirror.txt`

Példák:

Bemenet:

```
ABCDEF
67890
12345
```

Kimenet:

```
12345
67890
ABCDEF
```

## Tesztek

```java
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
```

## Pontozás

Egy feladatra 0 pontot ér, ha:

- nem fordul le
- lefordul, de egy teszteset sem fut le sikeresen.
- ha a forráskód olvashatatlan, nem felel meg a konvencióknak, nem követi a clean code alapelveket.

0 pont adandó továbbá, ha:

- kielégíti a teszteseteket, de a szöveges követelményeknek nem felel meg

Pontokat a további működési funkciók megfelelősségének arányában kell adni a vizsgafeladatra:

- 5 pont: az adott projekt lefordul, néhány teszteset sikeresen lefut, és ezek funkcionálisan is helyesek. Azonban több
  teszteset nem fut le, és a kód is olvashatatlan.
- 10 pont: a projekt lefordul, a tesztesetek legtöbbje lefut, ezek funkcionálisan is helyesek, és a clean code elvek
  nagyrészt betartásra kerültek.
- 20 pont: ha a projekt lefordul, a tesztesetek lefutnak, funkcionálisan helyesek, és csak apróbb funkcionális vagy
  clean code hibák szerepelnek a megoldásban.

Gyakorlati pontozás a project feladatokhoz:

- Alap pontszám egy feladatra(max 20): lefutó egység tesztek száma / összes egység tesztek száma * 20, feltéve, hogy a
  megoldás a szövegben megfogalmazott feladatot valósítja meg
- Clean kód, programozási elvek, bevett gyakorlat, kód formázás megsértéséért - pontlevonás jár. Szintén pontlevonás
  jár, ha valaki a feladatot nem a leghatékonyabb módszerrel oldja meg - amennyiben ez értelmezhető.

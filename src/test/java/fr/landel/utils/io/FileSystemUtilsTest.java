/*
 * #%L
 * utils-io
 * %%
 * Copyright (C) 2016 - 2017 Gilandel
 * %%
 * Authors: Gilles Landel
 * URL: https://github.com/Gilandel
 * 
 * This file is under Apache License, version 2.0 (2004).
 * #L%
 */
package fr.landel.utils.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;

import fr.landel.utils.assertor.Assertor;

/**
 * Check utility class (files).
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public class FileSystemUtilsTest extends AbstractTest {

    private static final String XML_EXT = "xml";
    private static final String TXT_EXT = "txt";
    private static final String UNK_EXT = "unknown";
    private static final FilenameFilter XML_FILENAME_FILTER = FileSystemUtils.createFilenameFilter(XML_EXT);
    private static final FilenameFilter UNK_FILENAME_FILTER = FileSystemUtils.createFilenameFilter(UNK_EXT);
    private static final FileFilter XML_FILE_FILTER = file -> XML_EXT.equalsIgnoreCase(FileSystemUtils.getExtensionPart(file));
    private static final FileFilter TXT_FILTER = file -> TXT_EXT.equalsIgnoreCase(FileSystemUtils.getExtensionPart(file));

    private static final String CHECK_CRC32_PATH = "src/test/resources/io";
    private static final String CHECK_CRC32_TARGET_PATH = "target/io";
    private static final String CHECK_CRC32_FILE = CHECK_CRC32_PATH + "/checkCRC32.xml";

    private static final Long CHECK_CRC32_VALUE_WIN = 1_476_569_244L;
    private static final long CHECK_CRC32_PATH_SIZE_WIN = 1_160L;
    private static final long CHECK_CRC32_FILE_SIZE_WIN = 1_143L;

    private static final Long CHECK_CRC32_VALUE_UNIX = 3_893_630_386L;
    private static final long CHECK_CRC32_PATH_SIZE_UNIX = 1_119L;
    private static final long CHECK_CRC32_FILE_SIZE_UNIX = 1_102L;

    private static final String ERROR_PARAM_NULL = "At least one parameter is null";
    private static final String ERROR_PARAM_NULL_COMBINATION = "the combination 'false' and ' AND ' is invalid (message: At least one parameter is null)";

    /**
     * Remove test directory
     * 
     * @throws IOException
     *             on error
     */
    @After
    public void dispose() throws IOException {
        File target = new File(CHECK_CRC32_TARGET_PATH);

        if (target.isDirectory()) {
            assertTrue(FileSystemUtils.deleteDirectory(target));
        }
    }

    /**
     * Test constructor for {@link FileSystemUtils} .
     */
    @Test
    public void testConstructors() {
        assertTrue(checkPrivateConstructor(FileSystemUtils.class));
    }

    /**
     * Check if all special characters are correctly replaced
     */
    @Test
    public void testReplaceSpecialCharacters() {
        String result = FileSystemUtils.replaceSpecialCharacters("Je\\|:pars/-<en>?*vacances\"", "-");
        assertEquals("Je---pars---en---vacances-", result);

        result = FileSystemUtils.replaceSpecialCharacters("Je\\|:pars/-<en>?*vacances\"", null);
        assertEquals("Jepars-envacances", result);
    }

    /**
     * Check the extension getter
     */
    @Test
    public void testGetExtension() {
        assertEquals(XML_EXT, FileSystemUtils.getExtensionPart(CHECK_CRC32_FILE));
    }

    /**
     * Check {@link FileSystemUtils#copyDirectory}
     */
    @Test
    public void testCopyDirectory() {
        try {
            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH, XML_FILENAME_FILTER);
            if (SystemUtils.isWindows()) {
                assertEquals(CHECK_CRC32_VALUE_WIN, FileCRC32Utils.getCRC32(CHECK_CRC32_TARGET_PATH, XML_FILENAME_FILTER));
            } else {
                assertEquals(CHECK_CRC32_VALUE_UNIX, FileCRC32Utils.getCRC32(CHECK_CRC32_TARGET_PATH, XML_FILENAME_FILTER));
            }

            String dest = "target/dir" + UUID.randomUUID();
            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, dest, TXT_FILTER);
            assertTrue(new File(dest).isDirectory());

            FileSystemUtils.copyDirectory(new File(CHECK_CRC32_PATH), new File("target/dir" + UUID.randomUUID()));
            FileSystemUtils.copyDirectory(new File(CHECK_CRC32_PATH), new File("target/dir" + UUID.randomUUID()), XML_FILENAME_FILTER);
            FileSystemUtils.copyDirectory(new File(CHECK_CRC32_PATH), new File("target/dir" + UUID.randomUUID()), TXT_FILTER);
            FileSystemUtils.copyDirectory(new File(CHECK_CRC32_PATH).getParentFile(), new File("target/dir" + UUID.randomUUID()));

            FileSystemUtils.copyDirectory(new File(CHECK_CRC32_TARGET_PATH), new File("target/dir" + UUID.randomUUID()));

            FileSystemUtils.copyDirectory(CHECK_CRC32_FILE, "target/dir" + UUID.randomUUID(), TXT_FILTER);
            FileSystemUtils.copyDirectory(CHECK_CRC32_FILE, "target/dir" + UUID.randomUUID(), XML_FILENAME_FILTER);
            FileSystemUtils.copyDirectory(CHECK_CRC32_FILE, "target/dir" + UUID.randomUUID(), UNK_FILENAME_FILTER);

            FileSystemUtils.copyDirectory(new File(CHECK_CRC32_PATH), new File("target/dir" + UUID.randomUUID()), UNK_FILENAME_FILTER);

            String newDir = "target/dir" + UUID.randomUUID();
            if (FileSystemUtils.createDirectory(newDir)) {
                FileSystemUtils.copyDirectory(new File(newDir), new File("target/dir" + UUID.randomUUID()));
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }

        assertException(() -> {
            FileSystemUtils.copyDirectory(null, (String) null);
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            FileSystemUtils.copyDirectory(null, "");
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            FileSystemUtils.copyDirectory("", null);
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            FileSystemUtils.copyDirectory(null, (File) null);
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            FileSystemUtils.copyDirectory(null, new File(""));
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            FileSystemUtils.copyDirectory(new File(""), null);
            fail();
        }, IllegalArgumentException.class);
    }

    /**
     * Check {@link FileSystemUtils#moveDirectory}
     */
    @Test
    public void testMoveDirectory() {
        String dest = "target/dir" + UUID.randomUUID();
        final String dest3 = "target/dir" + UUID.randomUUID();
        try {
            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH, XML_FILENAME_FILTER);
            if (SystemUtils.isWindows()) {
                assertEquals(CHECK_CRC32_VALUE_WIN, FileCRC32Utils.getCRC32(CHECK_CRC32_TARGET_PATH, XML_FILENAME_FILTER));
            } else {
                assertEquals(CHECK_CRC32_VALUE_UNIX, FileCRC32Utils.getCRC32(CHECK_CRC32_TARGET_PATH, XML_FILENAME_FILTER));
            }
            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH + 2, TXT_FILTER);

            FileSystemUtils.moveDirectory(CHECK_CRC32_TARGET_PATH, dest);
            assertFalse(new File(CHECK_CRC32_TARGET_PATH).isDirectory());
            assertTrue(new File(dest).isDirectory());

            String dest2 = "target/dir" + UUID.randomUUID();
            FileSystemUtils.moveDirectory(new File(dest), new File(dest2));

            FileSystemUtils.moveDirectory(new File(dest2), new File(dest), XML_FILENAME_FILTER);
            FileSystemUtils.moveDirectory(dest, dest2, XML_FILENAME_FILTER);

            FileSystemUtils.moveDirectory(new File(CHECK_CRC32_TARGET_PATH + 2), new File(dest), TXT_FILTER);
            FileSystemUtils.moveDirectory(dest, dest2, TXT_FILTER);

            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH + 4, XML_FILENAME_FILTER);
            FileSystemUtils.copyDirectory(CHECK_CRC32_TARGET_PATH + 4 + "/checkCRC32.xml", CHECK_CRC32_PATH);

            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH + 4, XML_FILENAME_FILTER);
            FileSystemUtils.copyDirectory(CHECK_CRC32_TARGET_PATH + 4 + "/checkCRC32.xml", CHECK_CRC32_PATH, XML_FILENAME_FILTER);

            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH + 4, XML_FILENAME_FILTER);
            FileSystemUtils.copyDirectory(CHECK_CRC32_TARGET_PATH + 4 + "/checkCRC32.xml", CHECK_CRC32_PATH, XML_FILE_FILTER);

            // prepare next step
            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, dest3);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        assertException(() -> {
            FileSystemUtils.copyDirectory(CHECK_CRC32_TARGET_PATH, "target/dir" + UUID.randomUUID());
            fail();
        }, FileNotFoundException.class, "the source doesn't exist");

        assertException(() -> {
            FileSystemUtils.moveDirectory(CHECK_CRC32_TARGET_PATH, "target/dir" + UUID.randomUUID());
            fail();
        }, FileNotFoundException.class, "the source doesn't exist");

        if (SystemUtils.isWindows()) {
            assertException(() -> {
                FileSystemUtils.moveDirectory(new File(dest3), new File("file>zzz"));
                fail();
            }, IOException.class, "cannot access or create the destination directory");
        }
    }

    /**
     * Check {@link FileSystemUtils#deleteDirectory}
     */
    @Test
    public void testDeleteDirectory() {
        try {
            final File target = new File(CHECK_CRC32_TARGET_PATH);

            if (!target.exists()) {
                FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH, XML_FILENAME_FILTER);
            }
            assertTrue(target.exists());
            assertTrue(FileSystemUtils.deleteDirectory(CHECK_CRC32_TARGET_PATH));
            assertFalse(target.exists());

            if (!target.exists()) {
                FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH, XML_FILENAME_FILTER);
            }
            assertTrue(target.exists());
            assertTrue(FileSystemUtils.deleteDirectory(new File(CHECK_CRC32_TARGET_PATH)));
            assertFalse(target.exists());

            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH);
            assertTrue(FileSystemUtils.deleteDirectory(CHECK_CRC32_TARGET_PATH, XML_FILE_FILTER));

            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH);
            assertTrue(FileSystemUtils.deleteDirectory(new File(CHECK_CRC32_TARGET_PATH), XML_FILE_FILTER));

            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH);
            assertTrue(FileSystemUtils.deleteDirectory(CHECK_CRC32_TARGET_PATH, XML_FILENAME_FILTER));

            FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH);
            assertTrue(FileSystemUtils.deleteDirectory(new File(CHECK_CRC32_TARGET_PATH), XML_FILENAME_FILTER));

            assertException(() -> {
                assertFalse(FileSystemUtils.deleteDirectory((File) null));
                fail();
            }, IllegalArgumentException.class, ERROR_PARAM_NULL);

            assertException(() -> {
                assertFalse(FileSystemUtils.deleteDirectory(CHECK_CRC32_FILE));
                fail();
            }, IllegalArgumentException.class, "not a directory");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Check {@link FileSystemUtils#createDirectory}
     * 
     * @throws IOException
     *             on error
     */
    @Test
    public void testCreateDirectory() throws IOException {

        UUID uuid = UUID.randomUUID();
        // new directory
        assertTrue(FileSystemUtils.createDirectory("target/createa" + uuid));
        // already exists
        assertTrue(FileSystemUtils.createDirectory("target/createa" + uuid));
        // from file
        assertTrue(FileSystemUtils.createDirectory(new File("target/create" + UUID.randomUUID())));

        // path is a file
        FileSystemUtils.copyFile(CHECK_CRC32_FILE, CHECK_CRC32_TARGET_PATH + "output.file");
        assertFalse(FileSystemUtils.createDirectory(CHECK_CRC32_TARGET_PATH + "output.file"));

        // null
        assertFalse(FileSystemUtils.createDirectory((String) null));
        assertFalse(FileSystemUtils.createDirectory((File) null));
    }

    /**
     * Check {@link FileSystemUtils#moveFile}
     * 
     * @throws IOException
     *             on error
     */
    @Test
    public void testMoveFile() throws IOException {
        // prepare
        FileSystemUtils.copyFile(CHECK_CRC32_FILE, CHECK_CRC32_TARGET_PATH + "output2.file");

        FileSystemUtils.moveFile(CHECK_CRC32_TARGET_PATH + "output2.file", CHECK_CRC32_TARGET_PATH + "/output3.file");
        File file = new File(CHECK_CRC32_TARGET_PATH, "output4.file");
        FileSystemUtils.moveFile(new File(CHECK_CRC32_TARGET_PATH, "output3.file"), file);

        // same file
        FileSystemUtils.moveFile(file, file);

        assertException(() -> {
            FileSystemUtils.moveFile(new File(CHECK_CRC32_TARGET_PATH, "output2.file"), new File(CHECK_CRC32_TARGET_PATH, "output3.file"));
            fail();
        }, FileNotFoundException.class);

        assertException(() -> {
            FileSystemUtils.moveFile(null, CHECK_CRC32_TARGET_PATH + "output3.file");
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            FileSystemUtils.moveFile((String) null, null);
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            FileSystemUtils.moveFile(CHECK_CRC32_TARGET_PATH + "output3.file", null);
            fail();
        }, IllegalArgumentException.class);

        if (SystemUtils.isWindows()) {
            assertException(() -> {
                FileSystemUtils.moveFile(CHECK_CRC32_TARGET_PATH + "/output4.file", "file>zzz");
                fail();
            }, FileNotFoundException.class);

            assertException(() -> {
                FileSystemUtils.moveFile(file, new File("file>zzz"));
                fail();
            }, FileNotFoundException.class);
        }
    }

    /**
     * Check {@link FileSystemUtils#getExtensionPart}
     */
    @Test
    public void testGetExtensionPart() {
        assertEquals("log", FileSystemUtils.getExtensionPart(new File("target/file.log")));
        assertEquals("log", FileSystemUtils.getExtensionPart("target/sub\\file.log"));
        assertEquals("", FileSystemUtils.getExtensionPart("target/"));
        assertEquals("log", FileSystemUtils.getExtensionPart("file.log"));
        assertEquals("", FileSystemUtils.getExtensionPart("file."));
        assertEquals("", FileSystemUtils.getExtensionPart("file"));
        assertNull(FileSystemUtils.getExtensionPart((File) null));
        assertNull(FileSystemUtils.getExtensionPart((String) null));
    }

    /**
     * Check {@link FileSystemUtils#getExtensionPart}
     */
    @Test
    public void testHasExtensionPart() {
        assertTrue(FileSystemUtils.hasExtensionPart(CHECK_CRC32_FILE, XML_EXT));
        assertFalse(FileSystemUtils.hasExtensionPart(CHECK_CRC32_FILE));
        assertFalse(FileSystemUtils.hasExtensionPart((String) null));

        assertTrue(FileSystemUtils.hasExtensionPart(new File(CHECK_CRC32_FILE), XML_EXT));
        assertFalse(FileSystemUtils.hasExtensionPart(new File(CHECK_CRC32_FILE)));
        assertFalse(FileSystemUtils.hasExtensionPart((File) null));
    }

    /**
     * Check {@link FileSystemUtils#getFileNamePart}
     */
    @Test
    public void testGetFileNamePart() {
        assertEquals("file", FileSystemUtils.getFileNamePart(new File("target/file.log")));
        assertEquals("file", FileSystemUtils.getFileNamePart("target/sub\\file.log"));
        assertEquals("", FileSystemUtils.getFileNamePart("target/"));
        assertEquals("file", FileSystemUtils.getFileNamePart("file.log"));
        assertEquals("file", FileSystemUtils.getFileNamePart("file."));
        assertEquals("file", FileSystemUtils.getFileNamePart("file"));
        assertNull(FileSystemUtils.getFileNamePart((File) null));
        assertNull(FileSystemUtils.getFileNamePart((String) null));
    }

    /**
     * Check {@link FileSystemUtils#createFile}
     */
    @Test
    public void testCreateFile() {
        File file = FileSystemUtils.createFile("target", "classes", "fr", "landel", "utils", "io", "FileSystemUtils.class");

        String expected = StringUtils.join(Arrays.asList("target", "classes", "fr", "landel", "utils", "io", "FileSystemUtils.class"),
                File.separator);

        assertEquals(expected, file.getPath());

        assertNull(FileSystemUtils.createFile((File) null, "classes", "fr", "landel", "utils", "io", "FileSystemUtils.class"));
        assertNull(FileSystemUtils.createFile("target"));
    }

    /**
     * Check {@link FileSystemUtils#getSize}
     * 
     * @throws IOException
     *             on error
     */
    @Test
    public void testGetSize() throws IOException {
        if (SystemUtils.isWindows()) {
            assertEquals(CHECK_CRC32_PATH_SIZE_WIN, FileSystemUtils.getSize(CHECK_CRC32_PATH));
            assertEquals(CHECK_CRC32_FILE_SIZE_WIN, FileSystemUtils.getSize(CHECK_CRC32_PATH, XML_FILENAME_FILTER));
            assertEquals(CHECK_CRC32_FILE_SIZE_WIN, FileSystemUtils.getSize(CHECK_CRC32_PATH, XML_FILE_FILTER));
            assertEquals(CHECK_CRC32_PATH_SIZE_WIN, FileSystemUtils.getSize(new File(CHECK_CRC32_PATH)));
            assertEquals(CHECK_CRC32_FILE_SIZE_WIN, FileSystemUtils.getSize(new File(CHECK_CRC32_PATH), XML_FILENAME_FILTER));
            assertEquals(CHECK_CRC32_FILE_SIZE_WIN, FileSystemUtils.getSize(new File(CHECK_CRC32_PATH), XML_FILE_FILTER));

            assertEquals(CHECK_CRC32_PATH_SIZE_WIN, FileSystemUtils.getSize(new File(CHECK_CRC32_PATH).getParentFile()));

            assertEquals(CHECK_CRC32_FILE_SIZE_WIN, FileSystemUtils.getSize(new File(CHECK_CRC32_FILE)));
            assertEquals(CHECK_CRC32_FILE_SIZE_WIN, FileSystemUtils.getSize(new File(CHECK_CRC32_FILE), XML_FILENAME_FILTER));
            assertEquals(CHECK_CRC32_FILE_SIZE_WIN, FileSystemUtils.getSize(new File(CHECK_CRC32_FILE), XML_FILE_FILTER));
        } else {
            assertEquals(CHECK_CRC32_PATH_SIZE_UNIX, FileSystemUtils.getSize(CHECK_CRC32_PATH));
            assertEquals(CHECK_CRC32_FILE_SIZE_UNIX, FileSystemUtils.getSize(CHECK_CRC32_PATH, XML_FILENAME_FILTER));
            assertEquals(CHECK_CRC32_FILE_SIZE_UNIX, FileSystemUtils.getSize(CHECK_CRC32_PATH, XML_FILE_FILTER));
            assertEquals(CHECK_CRC32_PATH_SIZE_UNIX, FileSystemUtils.getSize(new File(CHECK_CRC32_PATH)));
            assertEquals(CHECK_CRC32_FILE_SIZE_UNIX, FileSystemUtils.getSize(new File(CHECK_CRC32_PATH), XML_FILENAME_FILTER));
            assertEquals(CHECK_CRC32_FILE_SIZE_UNIX, FileSystemUtils.getSize(new File(CHECK_CRC32_PATH), XML_FILE_FILTER));

            assertEquals(CHECK_CRC32_PATH_SIZE_UNIX, FileSystemUtils.getSize(new File(CHECK_CRC32_PATH).getParentFile()));

            assertEquals(CHECK_CRC32_FILE_SIZE_UNIX, FileSystemUtils.getSize(new File(CHECK_CRC32_FILE)));
            assertEquals(CHECK_CRC32_FILE_SIZE_UNIX, FileSystemUtils.getSize(new File(CHECK_CRC32_FILE), XML_FILENAME_FILTER));
            assertEquals(CHECK_CRC32_FILE_SIZE_UNIX, FileSystemUtils.getSize(new File(CHECK_CRC32_FILE), XML_FILE_FILTER));
        }

        assertException(() -> {
            FileSystemUtils.getSize((String) null);
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            FileSystemUtils.getSize((File) null);
            fail();
        }, IllegalArgumentException.class);

        String dir = "target/dirSize";
        assertTrue(FileSystemUtils.createDirectory(dir));
        assertEquals(0, FileSystemUtils.getSize(new File(dir)));

        FileSystemUtils.copyDirectory(CHECK_CRC32_PATH, CHECK_CRC32_TARGET_PATH, XML_FILE_FILTER);
        assertEquals(0, FileSystemUtils.getSize(new File(CHECK_CRC32_TARGET_PATH), TXT_FILTER));
    }

    /**
     * Check {@link FileSystemUtils#listFiles}
     * 
     * @throws IOException
     *             on error
     */
    @Test
    public void testListFiles() throws IOException {
        assertTrue(
                Assertor.that(FileSystemUtils.listFiles(CHECK_CRC32_PATH)).isNotEmpty().and().contains(new File(CHECK_CRC32_FILE)).isOK());

        assertEquals(2, FileSystemUtils.listFiles(CHECK_CRC32_PATH).size());
        assertEquals(1, FileSystemUtils.listFiles(CHECK_CRC32_PATH, XML_FILENAME_FILTER).size());
        assertEquals(1, FileSystemUtils.listFiles(CHECK_CRC32_PATH, XML_FILE_FILTER).size());

        assertEquals(1, FileSystemUtils.listFiles(CHECK_CRC32_FILE).size());
        assertEquals(1, FileSystemUtils.listFiles(CHECK_CRC32_FILE, XML_FILENAME_FILTER).size());
        assertEquals(0, FileSystemUtils.listFiles(CHECK_CRC32_FILE, TXT_FILTER).size());

        assertTrue(Assertor.that(FileSystemUtils.listFiles(new File(CHECK_CRC32_PATH))).isNotEmpty().and()
                .contains(new File(CHECK_CRC32_FILE)).isOK());

        assertEquals(2, FileSystemUtils.listFiles(new File(CHECK_CRC32_PATH)).size());
        assertEquals(1, FileSystemUtils.listFiles(new File(CHECK_CRC32_PATH), XML_FILENAME_FILTER).size());
        assertEquals(1, FileSystemUtils.listFiles(new File(CHECK_CRC32_PATH), XML_FILE_FILTER).size());

        assertEquals(1, FileSystemUtils.listFiles(new File(CHECK_CRC32_FILE)).size());
        assertEquals(1, FileSystemUtils.listFiles(new File(CHECK_CRC32_FILE), XML_FILENAME_FILTER).size());
        assertEquals(0, FileSystemUtils.listFiles(new File(CHECK_CRC32_FILE), TXT_FILTER).size());
    }

    /**
     * Check {@link FileSystemUtils#createFilenameFilter}
     * 
     * @throws IOException
     *             on error
     */
    @Test
    public void testCreateFilenameFilter() throws IOException {
        FilenameFilter filter = FileSystemUtils.createFilenameFilter(TXT_EXT, XML_EXT);

        assertEquals(2, FileSystemUtils.listFiles(new File(CHECK_CRC32_PATH), filter).size());

        assertFalse(filter.accept(null, null));

        assertException(() -> {
            FileSystemUtils.createFilenameFilter();
            fail();
        }, IllegalArgumentException.class, ERROR_PARAM_NULL);

        assertException(() -> {
            FileSystemUtils.createFilenameFilter((String[]) null);
            fail();
        }, IllegalArgumentException.class, ERROR_PARAM_NULL);

        assertException(() -> {
            FileSystemUtils.createFilenameFilter(new String[] {"log", null});
            fail();
        }, IllegalArgumentException.class, "extensions array cannot contains 'null'");
    }

    /**
     * Check {@link FileSystemUtils#getAbsolutePath}
     * 
     * @throws IOException
     *             on error
     */
    @Test
    public void testGetAbsolutePath() throws IOException {
        String relative = new File(CHECK_CRC32_FILE).getPath();
        String absolute = new File(CHECK_CRC32_FILE).getAbsolutePath();
        String base = absolute.substring(0, absolute.length() - relative.length());

        assertEquals(base + relative, FileSystemUtils.getAbsolutePath(base, relative));

        assertEquals(absolute, FileSystemUtils.getAbsolutePath("base", absolute));

        assertEquals(base + relative, FileSystemUtils.getAbsolutePath(new File(base), new File(relative)));

        assertEquals(absolute, FileSystemUtils.getAbsolutePath(new File("base"), new File(absolute)));
    }

    /**
     * Check {@link FileSystemUtils#isDirectoryEmpty}
     * 
     * @throws IOException
     *             on error
     */
    @Test
    public void testIsDirectoryEmpty() throws IOException {
        String dir = "target/empty";
        assertTrue(FileSystemUtils.createDirectory(dir));
        assertTrue(FileSystemUtils.isDirectoryEmpty(dir));
        assertTrue(FileSystemUtils.isDirectoryEmpty(new File(dir)));

        assertFalse(FileSystemUtils.isDirectoryEmpty(CHECK_CRC32_FILE));

        assertException(() -> {
            FileSystemUtils.isDirectoryEmpty((String) null);
            fail();
        }, IllegalArgumentException.class, ERROR_PARAM_NULL);

        assertException(() -> {
            FileSystemUtils.isDirectoryEmpty((File) null);
            fail();
        }, IllegalArgumentException.class, ERROR_PARAM_NULL);
    }
}

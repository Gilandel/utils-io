/*-
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;

import org.junit.Test;

/**
 * Check {@link FileCRC32Utils}
 *
 * @since Aug 11, 2016
 * @author Gilles
 *
 */
public class FileCRC32UtilsTest extends AbstractTest {

    private static final String XML_EXT = "xml";
    private static final FilenameFilter XML_FILENAME_FILTER = FileSystemUtils.createFilenameFilter(XML_EXT);
    private static final FileFilter XML_FILE_FILTER = (file) -> XML_EXT.equalsIgnoreCase(FileSystemUtils.getExtensionPart(file));

    private static final String CHECK_CRC32_PATH = "src/test/resources/io";
    private static final String CHECK_CRC32_FILE = CHECK_CRC32_PATH + "/checkCRC32.xml";

    private static final Long CHECK_CRC32_VALUE_WIN = 1_476_569_244L;
    private static final Long CHECK_CRC32_DIR_VALUE_WIN = 914_046_700L;

    private static final Long CHECK_CRC32_VALUE_UNIX = 3_893_630_386L;
    private static final Long CHECK_CRC32_DIR_VALUE_UNIX = 580_225_974L;

    /**
     * Test constructor for {@link FileCRC32Utils} .
     */
    @Test
    public void testConstructors() {
        assertTrue(checkPrivateConstructor(FileCRC32Utils.class));
    }

    /**
     * Test method for {@link FileCRC32Utils#getCRC32}.
     */
    @Test
    public void testGetCRC32() {
        try {
            if (SystemUtils.isWindows()) {
                assertEquals(CHECK_CRC32_VALUE_WIN, FileCRC32Utils.getCRC32(CHECK_CRC32_FILE));
                assertEquals(CHECK_CRC32_VALUE_WIN, FileCRC32Utils.getCRC32(new File(CHECK_CRC32_FILE)));
                assertEquals(CHECK_CRC32_VALUE_WIN, FileCRC32Utils.getCRC32(StreamUtils.createBufferedInputStream(CHECK_CRC32_FILE)));

                assertEquals(CHECK_CRC32_VALUE_WIN, FileCRC32Utils.getCRC32(CHECK_CRC32_PATH, XML_FILENAME_FILTER));
                assertEquals(CHECK_CRC32_VALUE_WIN, FileCRC32Utils.getCRC32(CHECK_CRC32_PATH, XML_FILE_FILTER));

                assertEquals(CHECK_CRC32_DIR_VALUE_WIN, FileCRC32Utils.getCRC32(CHECK_CRC32_PATH));
            } else {
                assertEquals(CHECK_CRC32_VALUE_UNIX, FileCRC32Utils.getCRC32(CHECK_CRC32_FILE));
                assertEquals(CHECK_CRC32_VALUE_UNIX, FileCRC32Utils.getCRC32(new File(CHECK_CRC32_FILE)));
                assertEquals(CHECK_CRC32_VALUE_UNIX, FileCRC32Utils.getCRC32(StreamUtils.createBufferedInputStream(CHECK_CRC32_FILE)));

                assertEquals(CHECK_CRC32_VALUE_UNIX, FileCRC32Utils.getCRC32(CHECK_CRC32_PATH, XML_FILENAME_FILTER));
                assertEquals(CHECK_CRC32_VALUE_UNIX, FileCRC32Utils.getCRC32(CHECK_CRC32_PATH, XML_FILE_FILTER));

                assertEquals(CHECK_CRC32_DIR_VALUE_UNIX, FileCRC32Utils.getCRC32(CHECK_CRC32_PATH));
            }

            final File emptyDir = new File("target/empty");
            assertTrue(FileSystemUtils.createDirectory(emptyDir));
            assertEquals(Long.valueOf(0L), FileCRC32Utils.getCRC32(emptyDir));

            final File unknownDir = new File("target/unknown");

            assertEquals(Long.valueOf(0L), FileCRC32Utils.getCRC32(unknownDir));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}

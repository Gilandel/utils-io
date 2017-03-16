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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

/**
 * Check utility class (streams).
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public class StreamUtilsTest {

    private static final String CHECK_CRC32_PATH = "src/test/resources/io";
    private static final String CHECK_CRC32_TARGET_PATH = "target";
    private static final String CHECK_CRC32_FILE_INPUT = CHECK_CRC32_PATH + "/checkCRC32.xml";
    private static final String CHECK_CRC32_FILE_OUTPUT = CHECK_CRC32_TARGET_PATH + "/checkCRC32.xml";

    /**
     * Test method for
     * {@link StreamUtils#createBufferedReader(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testCreateBufferedReaderStringString() {
        try {
            assertNotNull(StreamUtils.createBufferedReader(CHECK_CRC32_FILE_INPUT, EncodingUtils.ENCODING_UTF_8));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));

            CloseableManager.close(CHECK_CRC32_FILE_INPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedReader(java.io.File, java.lang.String)}
     * .
     */
    @Test
    public void testCreateBufferedReaderFileString() {
        try {
            File file = new File(CHECK_CRC32_FILE_INPUT);

            assertNotNull(StreamUtils.createBufferedReader(file, EncodingUtils.ENCODING_UTF_8));

            assertTrue(CloseableManager.isCloseable(file));

            CloseableManager.close(file);

            assertFalse(CloseableManager.isCloseable(file));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createInputStreamReader(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testCreateInputStreamReaderStringString() {
        try {
            assertNotNull(StreamUtils.createInputStreamReader(CHECK_CRC32_FILE_INPUT, EncodingUtils.ENCODING_UTF_8));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));

            CloseableManager.close(CHECK_CRC32_FILE_INPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createInputStreamReader(java.io.File, java.lang.String)}
     * .
     */
    @Test
    public void testCreateInputStreamReaderFileString() {
        try {
            File file = new File(CHECK_CRC32_FILE_INPUT);

            assertNotNull(StreamUtils.createInputStreamReader(file, EncodingUtils.ENCODING_UTF_8));
            assertNotNull(StreamUtils.createInputStreamReader(file, null));

            assertTrue(CloseableManager.isCloseable(file));

            CloseableManager.close(file);

            assertFalse(CloseableManager.isCloseable(file));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createInputStreamReader(java.net.URL, java.lang.String)}
     * .
     */
    @Test
    public void testCreateInputStreamReaderUrl() {
        try {
            File file = new File(CHECK_CRC32_FILE_INPUT);
            URL url = file.toURI().toURL();

            assertNotNull(StreamUtils.createInputStreamReader(url, EncodingUtils.ENCODING_UTF_8));
            assertNotNull(StreamUtils.createInputStreamReader(url, null));

            assertTrue(CloseableManager.isCloseable(url));

            CloseableManager.close(url);
            CloseableManager.close((URL) null);

            assertFalse(CloseableManager.isCloseable(url));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedWriter(java.lang.String)} .
     */
    @Test
    public void testCreateOutputStreamWriterString() {
        try {
            assertNotNull(StreamUtils.createBufferedWriter(CHECK_CRC32_FILE_OUTPUT));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));

            CloseableManager.close(CHECK_CRC32_FILE_OUTPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedWriter(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testCreateOutputStreamWriterStringString() {
        try {
            assertNotNull(StreamUtils.createBufferedWriter(CHECK_CRC32_FILE_OUTPUT, EncodingUtils.ENCODING_UTF_8));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));

            CloseableManager.close(CHECK_CRC32_FILE_OUTPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedWriter(java.lang.String, boolean)} .
     */
    @Test
    public void testCreateOutputStreamWriterStringBoolean() {
        try {
            assertNotNull(StreamUtils.createBufferedWriter(CHECK_CRC32_FILE_OUTPUT, true));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));

            CloseableManager.close(CHECK_CRC32_FILE_OUTPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link StreamUtils#createBufferedWriter(java.io.File)} .
     */
    @Test
    public void testCreateOutputStreamWriterFile() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(StreamUtils.createBufferedWriter(file));

            assertTrue(CloseableManager.isCloseable(file));

            CloseableManager.close(file);

            assertFalse(CloseableManager.isCloseable(file));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedWriter(java.io.File, java.lang.String)}
     * .
     */
    @Test
    public void testCreateOutputStreamWriterFileString() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(StreamUtils.createBufferedWriter(file, EncodingUtils.ENCODING_UTF_8));

            assertTrue(CloseableManager.isCloseable(file));

            CloseableManager.close(file);

            assertFalse(CloseableManager.isCloseable(file));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedWriter(java.io.File, boolean)} .
     */
    @Test
    public void testCreateOutputStreamWriterFileBoolean() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(StreamUtils.createBufferedWriter(file, true));

            assertTrue(CloseableManager.isCloseable(file));

            CloseableManager.close(file);

            assertFalse(CloseableManager.isCloseable(file));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedWriter(java.lang.String, java.lang.String, boolean)}
     * .
     */
    @Test
    public void testCreateOutputStreamWriterStringStringBoolean() {
        try {
            assertNotNull(StreamUtils.createBufferedWriter(CHECK_CRC32_FILE_OUTPUT, EncodingUtils.ENCODING_UTF_8, true));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));

            CloseableManager.close(CHECK_CRC32_FILE_OUTPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedWriter(java.io.File, java.lang.String, boolean)}
     * .
     */
    @Test
    public void testCreateOutputStreamWriterFileStringBoolean() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(StreamUtils.createBufferedWriter(file, EncodingUtils.ENCODING_UTF_8, true));

            assertTrue(CloseableManager.isCloseable(file));

            CloseableManager.close(file);

            assertFalse(CloseableManager.isCloseable(file));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createDataInputStream(java.lang.String)} .
     */
    @Test
    public void testCreateDataInputStream() {
        try {
            assertNotNull(StreamUtils.createDataInputStream(CHECK_CRC32_FILE_OUTPUT));
            assertNotNull(StreamUtils.createDataInputStream(new File(CHECK_CRC32_FILE_OUTPUT)));
            assertNotNull(StreamUtils.createDataInputStream(new File(CHECK_CRC32_FILE_OUTPUT).toURI().toURL()));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));

            CloseableManager.close(CHECK_CRC32_FILE_OUTPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedOutputStream(java.lang.String)} .
     */
    @Test
    public void testCreateDataOutputStream() {
        try {
            assertNotNull(StreamUtils.createDataOutputStream(CHECK_CRC32_FILE_OUTPUT));
            assertNotNull(StreamUtils.createDataOutputStream(new File(CHECK_CRC32_FILE_OUTPUT)));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));

            CloseableManager.close(CHECK_CRC32_FILE_OUTPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedOutputStream(java.lang.String)} .
     */
    @Test
    public void testCreateBufferedOutputStreamString() {
        try {
            assertNotNull(StreamUtils.createBufferedOutputStream(CHECK_CRC32_FILE_OUTPUT));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));

            CloseableManager.close(CHECK_CRC32_FILE_OUTPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedOutputStream(java.lang.String, boolean)}
     * .
     */
    @Test
    public void testCreateBufferedOutputStreamStringBoolean() {
        try {
            assertNotNull(StreamUtils.createBufferedOutputStream(CHECK_CRC32_FILE_OUTPUT, true));

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));

            CloseableManager.close(CHECK_CRC32_FILE_OUTPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_OUTPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedOutputStream(java.io.File)} .
     */
    @Test
    public void testCreateBufferedOutputStreamFile() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(StreamUtils.createBufferedOutputStream(file));

            assertTrue(CloseableManager.isCloseable(file));

            CloseableManager.close(file);

            assertFalse(CloseableManager.isCloseable(file));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link StreamUtils#createBufferedOutputStream(java.io.File, boolean)} .
     */
    @Test
    public void testCreateBufferedOutputStreamFileBoolean() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(StreamUtils.createBufferedOutputStream(file, true));

            assertTrue(CloseableManager.isCloseable(file));

            CloseableManager.close(file);

            assertFalse(CloseableManager.isCloseable(file));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}

/*
 * #%L
 * utils-io
 * %%
 * Copyright (C) 2016 - 2018 Gilles Landel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public class IOStreamUtilsTest extends AbstractTest {

    private static final String CHECK_CRC32_PATH = "src/test/resources/io";
    private static final String CHECK_CRC32_TARGET_PATH = "target";
    private static final String CHECK_CRC32_FILE_INPUT = CHECK_CRC32_PATH + "/checkCRC32.xml";
    private static final String CHECK_CRC32_FILE_OUTPUT = CHECK_CRC32_TARGET_PATH + "/checkCRC32.xml";

    /**
     * Test constructor for {@link IOStreamUtils} .
     */
    @Test
    public void testConstructors() {
        assertTrue(checkPrivateConstructor(IOStreamUtils.class));
    }

    /**
     * Test method for
     * {@link IOStreamUtils#createBufferedReader(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testCreateBufferedReaderStringString() {
        try {
            assertNotNull(IOStreamUtils.createBufferedReader(CHECK_CRC32_FILE_INPUT, EncodingUtils.ENCODING_UTF_8));

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
     * {@link IOStreamUtils#createBufferedReader(java.io.File, java.lang.String)}
     * .
     */
    @Test
    public void testCreateBufferedReaderFileString() {
        try {
            File file = new File(CHECK_CRC32_FILE_INPUT);

            assertNotNull(IOStreamUtils.createBufferedReader(file, EncodingUtils.ENCODING_UTF_8));

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
     * {@link IOStreamUtils#createInputStreamReader(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testCreateInputStreamReaderStringString() {
        try {
            assertNotNull(IOStreamUtils.createInputStreamReader(CHECK_CRC32_FILE_INPUT, EncodingUtils.ENCODING_UTF_8));

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
     * {@link IOStreamUtils#createInputStreamReader(java.io.File, java.lang.String)}
     * .
     */
    @Test
    public void testCreateInputStreamReaderFileString() {
        try {
            File file = new File(CHECK_CRC32_FILE_INPUT);

            assertNotNull(IOStreamUtils.createInputStreamReader(file, EncodingUtils.ENCODING_UTF_8));
            assertNotNull(IOStreamUtils.createInputStreamReader(file, null));

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
     * {@link IOStreamUtils#createInputStreamReader(java.net.URL, java.lang.String)}
     * .
     */
    @Test
    public void testCreateInputStreamReaderUrl() {
        try {
            File file = new File(CHECK_CRC32_FILE_INPUT);
            URL url = file.toURI().toURL();

            assertNotNull(IOStreamUtils.createInputStreamReader(url, EncodingUtils.ENCODING_UTF_8));
            assertNotNull(IOStreamUtils.createInputStreamReader(url, null));

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
     * {@link IOStreamUtils#createBufferedWriter(java.lang.String)} .
     */
    @Test
    public void testCreateOutputStreamWriterString() {
        try {
            assertNotNull(IOStreamUtils.createBufferedWriter(CHECK_CRC32_FILE_OUTPUT));

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
     * {@link IOStreamUtils#createBufferedWriter(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testCreateOutputStreamWriterStringString() {
        try {
            assertNotNull(IOStreamUtils.createBufferedWriter(CHECK_CRC32_FILE_OUTPUT, EncodingUtils.ENCODING_UTF_8));

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
     * {@link IOStreamUtils#createBufferedWriter(java.lang.String, boolean)} .
     */
    @Test
    public void testCreateOutputStreamWriterStringBoolean() {
        try {
            assertNotNull(IOStreamUtils.createBufferedWriter(CHECK_CRC32_FILE_OUTPUT, true));

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
     * Test method for {@link IOStreamUtils#createBufferedWriter(java.io.File)} .
     */
    @Test
    public void testCreateOutputStreamWriterFile() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(IOStreamUtils.createBufferedWriter(file));

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
     * {@link IOStreamUtils#createBufferedWriter(java.io.File, java.lang.String)}
     * .
     */
    @Test
    public void testCreateOutputStreamWriterFileString() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(IOStreamUtils.createBufferedWriter(file, EncodingUtils.ENCODING_UTF_8));

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
     * {@link IOStreamUtils#createBufferedWriter(java.io.File, boolean)} .
     */
    @Test
    public void testCreateOutputStreamWriterFileBoolean() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(IOStreamUtils.createBufferedWriter(file, true));

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
     * {@link IOStreamUtils#createBufferedWriter(java.lang.String, java.lang.String, boolean)}
     * .
     */
    @Test
    public void testCreateOutputStreamWriterStringStringBoolean() {
        try {
            assertNotNull(IOStreamUtils.createBufferedWriter(CHECK_CRC32_FILE_OUTPUT, EncodingUtils.ENCODING_UTF_8, true));

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
     * {@link IOStreamUtils#createBufferedWriter(java.io.File, java.lang.String, boolean)}
     * .
     */
    @Test
    public void testCreateOutputStreamWriterFileStringBoolean() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(IOStreamUtils.createBufferedWriter(file, EncodingUtils.ENCODING_UTF_8, true));

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
     * {@link IOStreamUtils#createDataInputStream(java.lang.String)} .
     */
    @Test
    public void testCreateDataInputStream() {
        try {
            assertNotNull(IOStreamUtils.createDataInputStream(CHECK_CRC32_FILE_OUTPUT));
            assertNotNull(IOStreamUtils.createDataInputStream(new File(CHECK_CRC32_FILE_OUTPUT)));
            assertNotNull(IOStreamUtils.createDataInputStream(new File(CHECK_CRC32_FILE_OUTPUT).toURI().toURL()));

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
     * {@link IOStreamUtils#createBufferedOutputStream(java.lang.String)} .
     */
    @Test
    public void testCreateDataOutputStream() {
        try {
            assertNotNull(IOStreamUtils.createDataOutputStream(CHECK_CRC32_FILE_OUTPUT));
            assertNotNull(IOStreamUtils.createDataOutputStream(new File(CHECK_CRC32_FILE_OUTPUT)));

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
     * {@link IOStreamUtils#createBufferedOutputStream(java.lang.String)} .
     */
    @Test
    public void testCreateBufferedOutputStreamString() {
        try {
            assertNotNull(IOStreamUtils.createBufferedOutputStream(CHECK_CRC32_FILE_OUTPUT));

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
     * {@link IOStreamUtils#createBufferedOutputStream(java.lang.String, boolean)}
     * .
     */
    @Test
    public void testCreateBufferedOutputStreamStringBoolean() {
        try {
            assertNotNull(IOStreamUtils.createBufferedOutputStream(CHECK_CRC32_FILE_OUTPUT, true));

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
     * {@link IOStreamUtils#createBufferedOutputStream(java.io.File)} .
     */
    @Test
    public void testCreateBufferedOutputStreamFile() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(IOStreamUtils.createBufferedOutputStream(file));

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
     * {@link IOStreamUtils#createBufferedOutputStream(java.io.File, boolean)} .
     */
    @Test
    public void testCreateBufferedOutputStreamFileBoolean() {
        try {
            File file = new File(CHECK_CRC32_FILE_OUTPUT);

            assertNotNull(IOStreamUtils.createBufferedOutputStream(file, true));

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

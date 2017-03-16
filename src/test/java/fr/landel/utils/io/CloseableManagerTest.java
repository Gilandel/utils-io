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

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Check utility class (streams).
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public class CloseableManagerTest extends AbstractTest {

    private static final String CHECK_CRC32_PATH = "src/test/resources/io";
    private static final String CHECK_CRC32_FILE_INPUT = CHECK_CRC32_PATH + "/checkCRC32.xml";

    private static final Logger LOGGER = LoggerFactory.getLogger(CloseableManager.class);

    /**
     * Test constructor for {@link CloseableManager} .
     */
    @Test
    public void testConstructors() {
        assertTrue(checkPrivateConstructor(CloseableManager.class));
    }

    /**
     * Test method for {@link CloseableManager#isCloseable(java.lang.Class)} .
     */
    @Test
    public void testStreamClass() {
        FileInputStream fis;
        try {
            CloseableManager.defineLogger(LOGGER);

            CloseableManager.isCloseable((Class<?>) null);
            CloseableManager.close(CloseableManagerTest.class);
            CloseableManager.close((Class<?>) null);

            fis = new FileInputStream(CHECK_CRC32_FILE_INPUT);
            CloseableManager.addCloseable(CloseableManagerTest.class, fis);
            assertTrue(CloseableManager.isCloseable(CloseableManagerTest.class));

            // general errors
            CloseableManager.addCloseable(CloseableManagerTest.class, null);
            CloseableManager.addCloseable((Class<?>) null, fis);

            CloseableManager.close(CloseableManagerTest.class);

            assertFalse(CloseableManager.isCloseable(CloseableManagerTest.class));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link CloseableManager#isCloseable(java.lang.String)} .
     */
    @Test
    public void testStreamString() {
        try {
            // general errors
            CloseableManager.close((String) null);
            CloseableManager.isCloseable((String) null);

            // specific errors
            StreamUtils.createBufferedInputStream(CHECK_CRC32_FILE_INPUT);
            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));
            CloseableManager.close(CHECK_CRC32_FILE_INPUT);

            // general errors
            CloseableManager.addCloseable(CHECK_CRC32_FILE_INPUT, null);
            CloseableManager.addCloseable((String) null, new FileInputStream(CHECK_CRC32_FILE_INPUT));

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link CloseableManager#isCloseable(java.io.File)} .
     */
    @Test
    public void testStreamFile() {
        try {
            // general errors
            CloseableManager.close((File) null);

            // specific errors
            File file = new File(CHECK_CRC32_FILE_INPUT);
            StreamUtils.createBufferedInputStream(file);
            assertTrue(CloseableManager.isCloseable(file));
            CloseableManager.close(file);

            // general errors
            CloseableManager.isCloseable((File) null);
            CloseableManager.addCloseable(file, null);
            CloseableManager.addCloseable((File) null, new FileInputStream(CHECK_CRC32_FILE_INPUT));

            assertFalse(CloseableManager.isCloseable(file));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link CloseableManager#isCloseable(java.net.URL)} .
     */
    @Test
    public void testStreamURL() {
        try {
            // general errors
            CloseableManager.close((URL) null);

            // specific errors
            URL url = new File(CHECK_CRC32_FILE_INPUT).toURI().toURL();
            StreamUtils.createBufferedInputStream(url);
            assertTrue(CloseableManager.isCloseable(url));
            CloseableManager.close(url);

            // general errors
            CloseableManager.isCloseable((URL) null);
            CloseableManager.addCloseable(url, null);
            CloseableManager.addCloseable((URL) null, new FileInputStream(CHECK_CRC32_FILE_INPUT));

            assertFalse(CloseableManager.isCloseable(url));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link CloseableManager#addCloseable(java.lang.String, java.io.Closeable)}
     * .
     */
    @Test
    public void testAddStreamStringCloseable() {
        FileInputStream fis;
        try {
            fis = new FileInputStream(CHECK_CRC32_FILE_INPUT);

            CloseableManager.addCloseable(CHECK_CRC32_FILE_INPUT, fis);

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));

            CloseableManager.close(CHECK_CRC32_FILE_INPUT);

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link CloseableManager#addCloseable(java.io.File, java.io.Closeable)} .
     */
    @Test
    public void testAddStreamFileCloseable() {
        FileInputStream fis;
        try {
            File file = new File(CHECK_CRC32_FILE_INPUT);

            fis = new FileInputStream(file);

            CloseableManager.addCloseable(file, fis);

            assertTrue(CloseableManager.isCloseable(file));

            CloseableManager.close(file);

            assertFalse(CloseableManager.isCloseable(file));
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link CloseableManager#closeAll()}.
     */
    @Test
    public void testCloseAll() {
        try {
            StreamUtils.createBufferedInputStream(CHECK_CRC32_FILE_INPUT);

            assertTrue(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));

            CloseableManager.closeAll();

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try {
            BufferedInputStream bis = StreamUtils.createBufferedInputStream(CHECK_CRC32_FILE_INPUT);

            bis.close();
            CloseableManager.closeAll();

            assertFalse(CloseableManager.isCloseable(CHECK_CRC32_FILE_INPUT));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link CloseableManager#close(Integer)} .
     */
    @Test
    public void testCloseCloseableInteger() {
        FileInputStream fis;
        try {
            // general errors
            CloseableManager.close((Integer) null);
            CloseableManager.close(1);
            CloseableManager.isCloseable((Integer) null);
            CloseableManager.isCloseable(1);

            // specific errors
            fis = new FileInputStream(CHECK_CRC32_FILE_INPUT);
            CloseableManager.addCloseable(1, fis);
            CloseableManager.close(1);

            // general errors
            CloseableManager.addCloseable((Integer) null, fis);
            CloseableManager.addCloseable(2, null);

            fis.available();

            fail("error has to be thrown");
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for {@link CloseableManager#close(java.io.Closeable)} .
     */
    @Test
    public void testCloseCloseable() {
        FileInputStream fis;
        try {
            CloseableManager.close((Closeable) null);
            CloseableManager.addCloseable((Closeable) null);
            CloseableManager.isCloseable((Closeable) null);

            fis = new FileInputStream(CHECK_CRC32_FILE_INPUT);
            CloseableManager.addCloseable(fis);
            CloseableManager.addCloseable(fis); // duplicate
            CloseableManager.isCloseable(fis);
            CloseableManager.close(fis);

            fis = new FileInputStream(CHECK_CRC32_FILE_INPUT);
            CloseableManager.close(fis);

            fis.available();

            fail("error has to be thrown");
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            assertNotNull(e);
        }
    }
}

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import fr.landel.utils.assertor.Assertor;

/**
 * Check file utils
 *
 * @since Dec 11, 2015
 * @author Gilles Landel
 *
 */
public class FileUtilsTest extends AbstractTest {

    private static final String CHECK_PROPERTIES_PATH = "src/test/resources";
    private static final String CHECK_PROPERTIES_FILE = "file.properties";

    private static final String CHECK_CRC32_PATH = "src/test/resources/io";
    private static final String CHECK_CRC32_TARGET_PATH = "target/io";
    private static final String CHECK_CRC32_FILE = "checkCRC32.xml";

    /**
     * Remove test directory
     * 
     * @throws IOException
     *             on error
     */
    @AfterEach
    public void dispose() throws IOException {
        File target = new File(CHECK_CRC32_TARGET_PATH);
        if (target.exists()) {
            assertTrue(FileSystemUtils.deleteDirectory(target));
        }
    }

    /**
     * Test constructor for {@link FileUtils} .
     */
    @Test
    public void testConstructors() {
        assertTrue(checkPrivateConstructor(FileUtils.class));
    }

    /**
     * Test method for {@link FileUtils#getFileContent(java.io.InputStream)} .
     */
    @Test
    public void testGetFileContentInputStream() {
        String test = "text";

        try (ByteArrayInputStream bais = new ByteArrayInputStream(test.getBytes(EncodingUtils.CHARSET_UTF_8))) {
            StringBuilder sb = FileUtils.getFileContent(bais);

            assertEquals(test, sb.toString());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link FileUtils#getFileContent(java.io.InputStream, java.nio.charset.Charset)}
     * .
     */
    @Test
    public void testGetFileContentInputStreamCharset() {
        final String test = "texte accentu\u00e9";

        try (ByteArrayInputStream bais = new ByteArrayInputStream(test.getBytes(EncodingUtils.CHARSET_UTF_8))) {
            StringBuilder sb = FileUtils.getFileContent(bais, EncodingUtils.CHARSET_UTF_8);

            assertEquals(test, sb.toString());
        } catch (IOException e) {
            fail(e.getMessage());
        }

        assertException(() -> FileUtils.getFileContent("unknown"), IOException.class, Pattern.compile(".*?unknown.*"));

        try (ByteArrayInputStream bais = new ByteArrayInputStream(test.getBytes(EncodingUtils.CHARSET_UTF_8))) {
            StringBuilder sb = FileUtils.getFileContent(bais, EncodingUtils.CHARSET_US_ASCII);

            assertNotEquals(test, sb.toString());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link FileUtils#getProperties} .
     */
    @Test
    public void testGetProperties() {

        Optional<Properties> properties = FileUtils.getProperties(CHECK_PROPERTIES_PATH);
        assertFalse(properties.isPresent());

        // String
        properties = FileUtils.getProperties(CHECK_PROPERTIES_PATH, CHECK_PROPERTIES_FILE);
        assertTrue(properties.isPresent());

        assertEquals(" AND ", properties.get().getProperty("operator.and"));
        assertEquals("the combination '{0}' and '{1}' is invalid", properties.get().getProperty("invalid.without.message"));

        // Path
        properties = FileUtils.getProperties(Paths.get(CHECK_PROPERTIES_PATH, CHECK_PROPERTIES_FILE));
        assertTrue(properties.isPresent());

        assertEquals(" AND ", properties.get().getProperty("operator.and"));
        assertEquals("the combination '{0}' and '{1}' is invalid", properties.get().getProperty("invalid.without.message"));

        // File
        properties = FileUtils.getProperties(new File(CHECK_PROPERTIES_PATH, CHECK_PROPERTIES_FILE));
        assertTrue(properties.isPresent());

        assertEquals(" AND ", properties.get().getProperty("operator.and"));
        assertEquals("the combination '{0}' and '{1}' is invalid", properties.get().getProperty("invalid.without.message"));

        // Supplier
        properties = FileUtils.getProperties(() -> FileUtilsTest.class.getClassLoader().getResourceAsStream(CHECK_PROPERTIES_FILE));
        assertTrue(properties.isPresent());

        assertEquals(" AND ", properties.get().getProperty("operator.and"));
        assertEquals("the combination '{0}' and '{1}' is invalid", properties.get().getProperty("invalid.without.message"));
    }

    /**
     * Test method for
     * {@link FileUtils#writeFileContent(java.lang.StringBuilder, java.io.File, java.nio.charset.Charset)}
     * .
     */
    @Test
    public void testWriteFileContentStringBuilderFileCharset() {
        StringBuilder sb = new StringBuilder();

        sb.append("toto");
        sb.append("est pr\u00eat pour partir en vacances.");
        sb.append("\n");
        sb.append("cool!");

        FileSystemUtils.createDirectory(CHECK_CRC32_TARGET_PATH);

        final File outputFile = new File(CHECK_CRC32_TARGET_PATH, "output.txt");

        try {
            FileUtils.writeFileContent(sb, outputFile, EncodingUtils.CHARSET_UTF_8);

            StringBuilder outputSb = FileUtils.getFileContent(outputFile, EncodingUtils.CHARSET_US_ASCII);

            assertNotEquals(sb.toString(), outputSb.toString());

            outputSb = FileUtils.getFileContent(outputFile, EncodingUtils.CHARSET_UTF_8);

            assertEquals(sb.toString(), outputSb.toString());

            // Do nothing
            FileUtils.writeFileContent(null, outputFile, EncodingUtils.CHARSET_UTF_8);
            FileUtils.writeFileContent(sb, (File) null, EncodingUtils.CHARSET_UTF_8);
            FileUtils.writeFileContent(sb, new File("file>zzz"), EncodingUtils.CHARSET_UTF_8);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link FileUtils#writeFileContent(java.io.InputStream, java.lang.String)}
     * {@link FileUtils#getFileContent(java.lang.String)} .
     */
    @Test
    public void testWriteFileContentInputStreamString() {
        File referenceFile = new File(CHECK_CRC32_PATH, CHECK_CRC32_FILE);

        FileSystemUtils.createDirectory(CHECK_CRC32_TARGET_PATH);

        final String outputPath = CHECK_CRC32_TARGET_PATH + "/output.txt";

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(referenceFile))) {
            FileUtils.writeFileContent(bis, outputPath);

            StringBuilder inputSb = FileUtils.getFileContent(referenceFile.getAbsolutePath());
            StringBuilder outputSb = FileUtils.getFileContent(outputPath);

            assertEquals(inputSb.toString(), outputSb.toString());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link FileUtils#writeFileContent(java.lang.StringBuilder, java.lang.String, java.nio.charset.Charset)}
     * {@link FileUtils#getFileContent(java.lang.String, java.nio.charset.Charset)}
     * .
     */
    @Test
    public void testWriteFileContentAndReadContentCharset() {
        StringBuilder sb = new StringBuilder();

        sb.append("toto");
        sb.append("est pr\u00eat pour partir en vacances.");
        sb.append("\n");
        sb.append("cool!");

        FileSystemUtils.createDirectory(CHECK_CRC32_TARGET_PATH);

        final String outputPath = CHECK_CRC32_TARGET_PATH + "/output.txt";

        try {
            FileUtils.writeFileContent(sb, outputPath, EncodingUtils.CHARSET_UTF_8);

            StringBuilder outputSb = FileUtils.getFileContent(outputPath, EncodingUtils.CHARSET_US_ASCII);

            assertNotEquals(sb.toString(), outputSb.toString());

            outputSb = FileUtils.getFileContent(outputPath, EncodingUtils.CHARSET_UTF_8);

            assertEquals(sb.toString(), outputSb.toString());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link FileUtils#writeStream(java.io.InputStream, java.io.OutputStream)}
     * .
     */
    @Test
    public void testWriteStream() {
        String test = "text";

        try (ByteArrayInputStream bais = new ByteArrayInputStream(test.getBytes(EncodingUtils.CHARSET_UTF_8))) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                FileUtils.writeStream(bais, baos);

                assertEquals(test, baos.toString(EncodingUtils.ENCODING_UTF_8));
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link FileUtils#isEqual(java.io.File, java.io.File)} .
     * 
     * @throws IOException
     *             On copy failed
     */
    @Test
    public void testIsEqual() throws IOException {
        File referenceFile = new File(CHECK_CRC32_PATH, CHECK_CRC32_FILE);
        File copiedFile = new File(CHECK_CRC32_TARGET_PATH, CHECK_CRC32_FILE);

        FileSystemUtils.createDirectory(CHECK_CRC32_TARGET_PATH);
        FileSystemUtils.copyFile(referenceFile, copiedFile);

        assertTrue(FileUtils.isEqual(referenceFile, copiedFile));
        assertTrue(FileUtils.isEqual(referenceFile.getAbsolutePath(), copiedFile.getAbsolutePath()));

        File file2 = new File("target/test2.txt");
        FileUtils.writeFileContent(new StringBuilder(), file2, StandardCharsets.UTF_8);
        assertFalse(FileUtils.isEqual(referenceFile, file2));
        assertFalse(FileUtils.isEqual(file2, copiedFile));

        File file1 = new File("target/test1.txt");
        FileUtils.writeFileContent(new StringBuilder(), file1, StandardCharsets.UTF_8);
        assertTrue(FileUtils.isEqual(file1, file2));

        assertException(() -> {
            FileUtils.isEqual(null, copiedFile);
        }, IllegalArgumentException.class, "The first file isn't valid");

        assertException(() -> {
            FileUtils.isEqual(new File("./"), copiedFile);
        }, IllegalArgumentException.class, "The first file isn't valid");

        assertException(() -> {
            FileUtils.isEqual(referenceFile, null);
        }, IllegalArgumentException.class, "The second file isn't valid");

        assertException(() -> {
            FileUtils.isEqual(referenceFile, new File("./"));
        }, IllegalArgumentException.class, "The second file isn't valid");
    }

    /**
     * Test method for
     * {@link FileUtils#getFileContent(String, java.nio.charset.Charset, ClassLoader)}
     * .
     * 
     * @throws IOException
     *             On copy failed
     */
    @Test
    public void testGetFileContent() throws IOException {
        File referenceFile = new File(CHECK_CRC32_PATH, CHECK_CRC32_FILE);

        StringBuilder referenceContent = FileUtils.getFileContent(referenceFile, StandardCharsets.UTF_8);
        StringBuilder content = FileUtils.getFileContent("io/" + CHECK_CRC32_FILE, StandardCharsets.UTF_8, null);
        content = FileUtils.getFileContent("io/" + CHECK_CRC32_FILE, StandardCharsets.UTF_8, FileUtils.class.getClassLoader());

        assertNotNull(referenceContent);
        assertNotNull(content);

        assertTrue(Assertor.that(content).isEqual(referenceContent).isOK());

        content = FileUtils.getFileContent(new File("src/test/resources/io/", CHECK_CRC32_FILE));

        assertNotNull(content);
    }

    /**
     * Test method for {@link FileUtils#convertToWindows(StringBuilder)} .
     */
    @Test
    public void testConvertToWindows() {
        final StringBuilder input = new StringBuilder("\ntest\r\nNew line\r\n\n\r\nend\r");
        final StringBuilder expected = new StringBuilder("\r\ntest\r\nNew line\r\n\r\n\r\nend\r\n");

        assertEquals(expected.toString(), FileUtils.convertToWindows(input).toString());
    }

    /**
     * Test method for {@link FileUtils#convertToUnix(StringBuilder)} .
     */
    @Test
    public void testConvertToUnix() {
        final StringBuilder input = new StringBuilder("\ntest\r\nNew line\r\n\n\r\nend\r");
        final StringBuilder expected = new StringBuilder("\ntest\nNew line\n\n\nend\n");

        assertEquals(expected.toString(), FileUtils.convertToUnix(input).toString());
    }

    /**
     * Test method for {@link FileUtils#convertToMacOS(StringBuilder)} .
     */
    @Test
    public void testConvertToMacOS() {
        final StringBuilder input = new StringBuilder("\ntest\r\nNew line\r\n\n\r\nend\r");
        final StringBuilder expected = new StringBuilder("\n\rtest\n\rNew line\n\r\n\r\n\rend\n\r");

        assertEquals(expected.toString(), FileUtils.convertToMacOS(input).toString());
    }
}

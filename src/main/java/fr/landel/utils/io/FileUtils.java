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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import fr.landel.utils.assertor.Assertor;

/**
 * This class is used to read and write files.
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public final class FileUtils {

    /**
     * Character to create a chariot return
     */
    public static final char CR = '\r';

    /**
     * Character to create a newline under Unix
     */
    public static final char LF = '\n';

    /**
     * Characters to create a newline under Windows
     */
    public static final String CRLF = "\r\n";

    /**
     * Characters to create a newline under MacOS
     */
    public static final String LFCR = "\n\r";

    /**
     * Characters to create a newline under Windows
     */
    public static final String NEWLINE_WINDOWS = CRLF;

    /**
     * Characters to create a newline under Unix
     */
    public static final String NEWLINE_UNIX = String.valueOf(LF);

    /**
     * Characters to create a newline under MacOS
     */
    public static final String NEWLINE_MACOS = LFCR;

    private static final String C = String.valueOf(CR);
    private static final String L = String.valueOf(LF);
    private static final int BUFFER_SIZE = 10240;

    /**
     * Constructor.
     *
     */
    private FileUtils() {
    }

    /**
     * Get the content of a file (charset used: UTF-8).
     * 
     * @param path
     *            The path of the file
     * @return The buffered content
     * @throws IOException
     *             Exception thrown if problems occurs during reading
     */
    public static StringBuilder getFileContent(final String path) throws IOException {
        return getFileContent(path, EncodingUtils.CHARSET_UTF_8);
    }

    /**
     * Get the content of a file (charset used: UTF-8).
     * 
     * @param file
     *            The file
     * @return The buffered content
     * @throws IOException
     *             Exception thrown if problems occurs during reading
     */
    public static StringBuilder getFileContent(final File file) throws IOException {
        return getFileContent(file, EncodingUtils.CHARSET_UTF_8);
    }

    /**
     * Get the content of a file.
     * 
     * @param path
     *            The path of the file
     * @param charset
     *            The file charset
     * @return The buffered content
     * @throws IOException
     *             Exception thrown if problems occurs during reading
     */
    public static StringBuilder getFileContent(final String path, final Charset charset) throws IOException {
        Assertor.that(path).isNotNull().orElseThrow("The 'path' parameter cannot be null");
        Assertor.that(charset).isNotNull().orElseThrow("The 'charset' parameter cannot be null");

        return FileUtils.getFileContent(new File(path), charset);
    }

    /**
     * Get the content of a file.
     * 
     * @param file
     *            The file
     * @param charset
     *            The file charset
     * @return The buffered content
     * @throws IOException
     *             Exception thrown if problems occurs during reading
     */
    public static StringBuilder getFileContent(final File file, final Charset charset) throws IOException {
        Assertor.that(file).isNotNull().orElseThrow("The 'file' parameter cannot be null");
        Assertor.that(charset).isNotNull().orElseThrow("The 'charset' parameter cannot be null");

        final StringBuilder buffer;

        final BufferedInputStream bis = StreamUtils.createBufferedInputStream(file);

        buffer = getFileContent(bis, charset);

        CloseableManager.close(file);

        return buffer;
    }

    /**
     * Get the content of a input stream (charset used: UTF-8).
     * 
     * @param inputStream
     *            The input stream
     * @return The buffered content
     * @throws IOException
     *             Exception thrown if problems occurs during reading
     */
    public static StringBuilder getFileContent(final InputStream inputStream) throws IOException {
        return FileUtils.getFileContent(inputStream, EncodingUtils.CHARSET_UTF_8);
    }

    /**
     * Get the content of a input stream.
     * 
     * @param inputStream
     *            The input stream
     * @param charset
     *            The file charset
     * @return The buffered content
     * @throws IOException
     *             Exception thrown if problems occurs during reading
     */
    public static StringBuilder getFileContent(final InputStream inputStream, final Charset charset) throws IOException {
        Assertor.that(inputStream).isNotNull().orElseThrow("The 'inputStream' parameter cannot be null");
        Assertor.that(charset).isNotNull().orElseThrow("The 'charset' parameter cannot be null");

        final StringBuilder content = new StringBuilder();

        loadContent(content, inputStream, charset);

        return content;
    }

    /**
     * Get the content of a file from class loader (from classpath root).
     * 
     * @param path
     *            The path
     * @param charset
     *            The file charset
     * @param classLoader
     *            The class loader (advice: use a Class in the same JAR of the
     *            file to load), if null use the class loader of the current
     *            thread
     * @return The buffered content
     * @throws IOException
     *             Exception thrown if problems occurs during reading
     */
    public static StringBuilder getFileContent(final String path, final Charset charset, final ClassLoader classLoader) throws IOException {
        Assertor.that(path).isNotBlank().orElseThrow("The 'path' parameter cannot be null or blank");
        Assertor.that(charset).isNotNull().orElseThrow("The 'charset' parameter cannot be null");

        ClassLoader loader = classLoader;

        final StringBuilder content = new StringBuilder();

        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
        }

        try (InputStream inputStream = loader.getResourceAsStream(path)) {
            loadContent(content, inputStream, charset);
        }

        return content;
    }

    private static void loadContent(final StringBuilder content, final InputStream inputStream, final Charset charset) throws IOException {
        Assertor.that(inputStream).isNotNull().orElseThrow("The 'inputStream' from the classpath cannot be null");

        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int bufferReadSize;
            final byte[] buffer = new byte[BUFFER_SIZE];

            while ((bufferReadSize = inputStream.read(buffer, 0, BUFFER_SIZE)) >= 0) {
                baos.write(buffer, 0, bufferReadSize);
            }

            // convert into the specified charset after loading all data to
            // avoid the cutting of encoded character at the end of the buffer
            // size
            content.append(new String(baos.toByteArray(), 0, baos.size(), charset));
        }
    }

    /**
     * Write the content of the buffer into a file and create intermediate
     * directories if necessary.
     * 
     * @param buffer
     *            the buffer
     * @param file
     *            The file
     * @param charset
     *            The charset
     * @throws IOException
     *             Exception thrown if problems occurs during writing
     */
    public static void writeFileContent(final StringBuilder buffer, final File file, final Charset charset) throws IOException {
        if (buffer != null && file != null && FileSystemUtils.createDirectory(file.getParentFile())) {

            final BufferedOutputStream bos = StreamUtils.createBufferedOutputStream(file);

            bos.write(buffer.toString().getBytes(ObjectUtils.defaultIfNull(charset, StandardCharsets.UTF_8)));

            CloseableManager.close(file);
        }
    }

    /**
     * Write the content of the buffer into a file and create intermediate
     * directories if necessary.
     * 
     * @param buffer
     *            the buffer
     * @param path
     *            The path of the file
     * @param charset
     *            The charset
     * @throws IOException
     *             Exception thrown if problems occurs during writing
     */
    public static void writeFileContent(final StringBuilder buffer, final String path, final Charset charset) throws IOException {
        Assertor.that(path).isNotNull().orElseThrow("The 'path' parameter cannot be null");
        Assertor.that(charset).isNotNull().orElseThrow("The 'charset' parameter cannot be null");

        writeFileContent(buffer, new File(path), charset);
    }

    /**
     * Write the content of the input stream into a file and create intermediate
     * directories if necessary.
     * 
     * @param inputStream
     *            The input stream
     * @param path
     *            The path of the file
     * @throws IOException
     *             Exception thrown if problems occurs during reading
     */
    public static void writeFileContent(final InputStream inputStream, final String path) throws IOException {
        Assertor.that(path).isNotNull().orElseThrow("The 'path' parameter cannot be null");

        writeFileContent(inputStream, new File(path));
    }

    /**
     * Write the content of the input stream into a file and create intermediate
     * directories if necessary.
     * 
     * @param inputStream
     *            The input stream
     * @param file
     *            The file
     * @throws IOException
     *             Exception thrown if problems occurs during reading
     */
    public static void writeFileContent(final InputStream inputStream, final File file) throws IOException {
        Assertor.that(inputStream).isNotNull().orElseThrow("The 'inpuStream' parameter cannot be null");
        Assertor.that(file).isNotNull().orElseThrow("The 'file' parameter cannot be null");

        final BufferedOutputStream bos = StreamUtils.createBufferedOutputStream(file);

        writeStream(inputStream, bos);

        CloseableManager.close(file);
    }

    /**
     * Write a stream content into another.
     * 
     * @param inputStream
     *            The input stream
     * @param outputStream
     *            The output stream
     * @throws IOException
     *             thrown if problems occurs during reading
     */
    public static void writeStream(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        Assertor.that(inputStream).isNotNull().orElseThrow("The 'inpuStream' parameter cannot be null");
        Assertor.that(outputStream).isNotNull().orElseThrow("The 'outputStream' parameter cannot be null");

        int bufferReadSize;
        final byte[] buffer = new byte[BUFFER_SIZE];

        while ((bufferReadSize = inputStream.read(buffer, 0, BUFFER_SIZE)) >= 0) {
            outputStream.write(buffer, 0, bufferReadSize);
        }

        outputStream.flush();
    }

    /**
     * Compare two files
     * 
     * @param path1
     *            The path of the first file
     * @param path2
     *            The path of the second file
     * @return true, if files match
     * @throws IllegalArgumentException
     *             If parameters are null or not files
     */
    public static boolean isEqual(final String path1, final String path2) {
        Assertor.that(path1).isNotNull().and(path2).isNotNull().orElseThrow("The 'path1' or 'path2' parameters cannot be null");

        return FileUtils.isEqual(new File(path1), new File(path2));
    }

    /**
     * Compare two files
     * 
     * @param file1
     *            The first file
     * @param file2
     *            The second file
     * @return true, if files match
     * @throws IllegalArgumentException
     *             If parameters are null or not files
     */
    public static boolean isEqual(final File file1, final File file2) {
        if (file1 == null || !file1.isFile()) {
            throw new IllegalArgumentException("The first file isn't valid");
        } else if (file2 == null || !file2.isFile()) {
            throw new IllegalArgumentException("The second file isn't valid");
        }

        if (file1.length() != file2.length()) {
            return false;
        } else if (file1.length() == 0) {
            return true;
        }

        return FileUtils.isIdentical(file1, file2);
    }

    private static boolean isIdentical(final File file1, final File file2) {
        boolean result = true;

        try (BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(file1));
                BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(file2))) {

            final byte[] buffer1 = new byte[BUFFER_SIZE];
            final byte[] buffer2 = new byte[BUFFER_SIZE];

            while (result && bis1.read(buffer1, 0, BUFFER_SIZE) > 0 && bis2.read(buffer2, 0, BUFFER_SIZE) > 0) {
                if (!ArrayUtils.isSameLength(buffer1, buffer2) || !Arrays.equals(buffer1, buffer2)) {
                    result = false;
                }
            }
        } catch (IOException e) {
            result = false;
        }

        return result;
    }

    /**
     * Convert all newline characters into Windows newlines.
     * 
     * @param input
     *            The text to convert
     * @return The text converted
     */
    public static StringBuilder convertToWindows(final StringBuilder input) {
        final StringBuilder output = new StringBuilder(input);

        int pos = 0;
        while ((pos = output.indexOf(LFCR, pos)) > -1) {
            output.replace(pos, pos + 2, CRLF);
            pos++;
        }
        pos = 0;
        while ((pos = output.indexOf(L, pos)) > -1) {
            if (pos == 0 || output.charAt(pos - 1) != CR) {
                output.replace(pos, pos + 1, CRLF);
            }
            pos++;
        }
        pos = 0;
        final int len = output.length();
        while ((pos = output.indexOf(C, pos)) > -1) {
            if (pos == len - 1 || output.charAt(pos + 1) != LF) {
                output.replace(pos, pos + 1, CRLF);
            }
            pos++;
        }

        return output;
    }

    /**
     * Convert all newline characters into Unix newlines.
     * 
     * @param input
     *            The text to convert
     * @return The text converted
     */
    public static StringBuilder convertToUnix(final StringBuilder input) {
        final StringBuilder output = new StringBuilder(input);

        int pos = 0;
        while ((pos = output.indexOf(LFCR, pos)) > -1) {
            output.replace(pos, pos + 2, L);
            pos++;
        }
        pos = 0;
        while ((pos = output.indexOf(CRLF, pos)) > -1) {
            output.replace(pos, pos + 2, L);
            pos++;
        }
        pos = 0;
        while ((pos = output.indexOf(C, pos)) > -1) {
            output.replace(pos, pos + 1, L);
            pos++;
        }

        return output;
    }

    /**
     * Convert all newline characters into Mac OS newlines.
     * 
     * @param input
     *            The text to convert
     * @return The text converted
     */
    public static StringBuilder convertToMacOS(final StringBuilder input) {
        final StringBuilder output = new StringBuilder(input);

        int pos = 0;
        while ((pos = output.indexOf(CRLF, pos)) > -1) {
            output.replace(pos, pos + 2, LFCR);
            pos++;
        }
        pos = 0;
        while ((pos = output.indexOf(C, pos)) > -1) {
            if (pos == 0 || output.charAt(pos - 1) != LF) {
                output.replace(pos, pos + 1, LFCR);
            }
            pos++;
        }
        pos = 0;
        final int len = output.length();
        while ((pos = output.indexOf(L, pos)) > -1) {
            if (pos == len - 1 || output.charAt(pos + 1) != CR) {
                output.replace(pos, pos + 1, LFCR);
            }
            pos++;
        }

        return output;
    }
}

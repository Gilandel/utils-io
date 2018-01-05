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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;

import fr.landel.utils.assertor.Assertor;

/**
 * Utility class to manage streams.
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public final class IOStreamUtils {

    /**
     * Default encoding
     */
    private static final Charset DEFAULT_CHARSET = EncodingUtils.CHARSET_UTF_8;

    /**
     * Error : file null
     */
    private static final String ERROR_FILE_PARAM_NULL = "The file parameter cannot be null";
    /**
     * Error : url null
     */
    private static final String ERROR_URL_PARAM_NULL = "The url parameter cannot be null";
    /**
     * Error : filename null
     */
    private static final String ERROR_FILE_NAME_PARAM_NULL = "The fileName parameter cannot be null";

    /**
     * Constructor.
     * 
     */
    private IOStreamUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Create a buffered reader from the specified path
     * 
     * @param fileName
     *            The input path
     * @param encoding
     *            The encoding, if null: UTF-8 is used
     * @return The buffered reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized BufferedReader createBufferedReader(final String fileName, final String encoding)
            throws IOException, UnsupportedEncodingException {
        Assertor.that(fileName).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_NAME_PARAM_NULL), false);
        return createBufferedReader(new File(fileName), encoding);
    }

    /**
     * Create a buffered reader from the specified file
     * 
     * @param file
     *            The input file
     * @param encoding
     *            The encoding, if null: UTF-8 is used
     * @return The buffered reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized BufferedReader createBufferedReader(final File file, final String encoding)
            throws IOException, UnsupportedEncodingException {
        Assertor.that(file).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_NAME_PARAM_NULL), false);
        final BufferedReader br = new BufferedReader(createInputStreamReader(file, encoding));
        CloseableManager.addCloseable(file, br);
        return br;
    }

    /**
     * Create an input stream reader from the specified path
     * 
     * @param fileName
     *            The input path
     * @param encoding
     *            The encoding, if null: UTF-8 is used
     * @return The input stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized InputStreamReader createInputStreamReader(final String fileName, final String encoding)
            throws IOException, UnsupportedEncodingException {
        Assertor.that(fileName).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_NAME_PARAM_NULL), false);
        return createInputStreamReader(new File(fileName), encoding);
    }

    /**
     * Create an input stream reader from the specified file
     * 
     * @param file
     *            The input file
     * @param encoding
     *            The encoding, if null: UTF-8 is used
     * @return The input stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized InputStreamReader createInputStreamReader(final File file, final String encoding)
            throws IOException, UnsupportedEncodingException {
        final InputStreamReader isr;

        if (encoding != null) {
            isr = new InputStreamReader(createBufferedInputStream(file), encoding);
        } else {
            isr = new InputStreamReader(createBufferedInputStream(file), DEFAULT_CHARSET);
        }

        CloseableManager.addCloseable(file, isr);
        return isr;
    }

    /**
     * Create an input stream reader from the specified URL
     * 
     * @param url
     *            The input URL
     * @param encoding
     *            The encoding, if null: UTF-8 is used
     * @return The input stream reader
     * @throws IOException
     *             Thrown if file wasn't found or in case of opening URL stream
     */
    public static synchronized InputStreamReader createInputStreamReader(final URL url, final String encoding) throws IOException {
        final InputStreamReader isr;

        if (encoding != null) {
            isr = new InputStreamReader(createBufferedInputStream(url), encoding);
        } else {
            isr = new InputStreamReader(createBufferedInputStream(url), DEFAULT_CHARSET);
        }

        CloseableManager.addCloseable(url, isr);
        return isr;
    }

    /**
     * Create an buffered data input stream from the specified file
     * 
     * @param fileName
     *            The input file name
     * @return The data input stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized DataInputStream createDataInputStream(final String fileName) throws IOException {
        Assertor.that(fileName).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_NAME_PARAM_NULL), false);
        return createDataInputStream(new File(fileName));
    }

    /**
     * Create an buffered data input stream from the specified file
     * 
     * @param file
     *            The input file
     * @return The data input stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized DataInputStream createDataInputStream(final File file) throws IOException {
        Assertor.that(file).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_PARAM_NULL), false);
        return CloseableManager.addCloseable(file, new DataInputStream(createBufferedInputStream(file)));
    }

    /**
     * Create an buffered data input stream from the specified url
     * 
     * @param url
     *            The input URL
     * @return The data input stream
     * @throws IOException
     *             Error thrown on creating stream
     */
    public static synchronized DataInputStream createDataInputStream(final URL url) throws IOException {
        Assertor.that(url).isNotNull().orElseThrow(new FileNotFoundException(ERROR_URL_PARAM_NULL), false);
        return CloseableManager.addCloseable(url, new DataInputStream(createBufferedInputStream(url)));
    }

    /**
     * Create a buffered input stream from the specified file name
     * 
     * @param fileName
     *            The input file name
     * @return The buffered input stream
     * @throws FileNotFoundException
     *             Error thrown on creating stream
     */
    public static synchronized BufferedInputStream createBufferedInputStream(final String fileName) throws IOException {
        Assertor.that(fileName).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_NAME_PARAM_NULL), false);
        return createBufferedInputStream(new File(fileName));
    }

    /**
     * Create a buffered input stream from the specified file
     * 
     * @param file
     *            The input file
     * @return The buffered input stream
     * @throws IOException
     *             Error thrown if file wasn't found
     */
    public static synchronized BufferedInputStream createBufferedInputStream(final File file) throws IOException {
        Assertor.that(file).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_NAME_PARAM_NULL), false);

        final FileInputStream fis = new FileInputStream(file);
        CloseableManager.addCloseable(file, fis);

        final BufferedInputStream bis = new BufferedInputStream(fis);
        CloseableManager.addCloseable(file, bis);

        return bis;
    }

    /**
     * Create a buffered input stream from the specified URL
     * 
     * @param url
     *            The input URL
     * @return The buffered input stream
     * @throws IOException
     *             Thrown if file wasn't found or in case of opening URL stream
     */
    public static synchronized BufferedInputStream createBufferedInputStream(final URL url) throws IOException {
        Assertor.that(url).isNotNull().orElseThrow(new FileNotFoundException(ERROR_URL_PARAM_NULL), false);

        final InputStream is = url.openStream();
        CloseableManager.addCloseable(url, is);

        final BufferedInputStream bis = new BufferedInputStream(is);
        CloseableManager.addCloseable(url, bis);

        return bis;
    }

    /**
     * Create an output stream writer from the specified path
     * 
     * @param fileName
     *            The output path
     * @return The output stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized OutputStreamWriter createBufferedWriter(final String fileName)
            throws IOException, UnsupportedEncodingException {
        return createBufferedWriter(fileName, null, false);
    }

    /**
     * Create an output stream writer from the specified path
     * 
     * @param fileName
     *            The output path
     * @param encoding
     *            The encoding, if null: UTF-8 is used
     * @return The output stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized OutputStreamWriter createBufferedWriter(final String fileName, final String encoding)
            throws IOException, UnsupportedEncodingException {
        return createBufferedWriter(fileName, encoding, false);
    }

    /**
     * Create an output stream writer from the specified path
     * 
     * @param fileName
     *            The output path
     * @param append
     *            if true, then bytes will be written to the end of the file
     *            rather than the beginning
     * @return The output stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized OutputStreamWriter createBufferedWriter(final String fileName, final boolean append)
            throws IOException, UnsupportedEncodingException {
        return createBufferedWriter(fileName, null, append);
    }

    /**
     * Create an output stream writer from the specified file
     * 
     * @param file
     *            The output file
     * @return The output stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized OutputStreamWriter createBufferedWriter(final File file) throws IOException, UnsupportedEncodingException {
        return createBufferedWriter(file, null, false);
    }

    /**
     * Create an output stream writer from the specified file
     * 
     * @param file
     *            The output file
     * @param encoding
     *            The encoding, if null: UTF-8 is used
     * @return The output stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized OutputStreamWriter createBufferedWriter(final File file, final String encoding)
            throws IOException, UnsupportedEncodingException {
        return createBufferedWriter(file, encoding, false);
    }

    /**
     * Create an output stream writer from the specified file
     * 
     * @param file
     *            The output file
     * @param append
     *            if true, then bytes will be written to the end of the file
     *            rather than the beginning
     * @return The output stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized OutputStreamWriter createBufferedWriter(final File file, final boolean append)
            throws IOException, UnsupportedEncodingException {
        return createBufferedWriter(file, null, append);
    }

    /**
     * Create an output stream writer from the specified path
     * 
     * @param fileName
     *            The output path
     * @param encoding
     *            The encoding, if null: UTF-8 is used
     * @param append
     *            if true, then bytes will be written to the end of the file
     *            rather than the beginning
     * @return The output stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized OutputStreamWriter createBufferedWriter(final String fileName, final String encoding, final boolean append)
            throws IOException, UnsupportedEncodingException {
        Assertor.that(fileName).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_NAME_PARAM_NULL), false);
        return createBufferedWriter(new File(fileName), encoding, append);
    }

    /**
     * Create an output stream writer from the specified file
     * 
     * @param file
     *            The output file
     * @param encoding
     *            The encoding, if null: UTF-8 is used
     * @param append
     *            if true, then bytes will be written to the end of the file
     *            rather than the beginning
     * @return The output stream reader
     * @throws IOException
     *             Error thrown if wasn't found
     * @throws UnsupportedEncodingException
     *             Error thrown if encoding doesn't match
     */
    public static synchronized OutputStreamWriter createBufferedWriter(final File file, final String encoding, final boolean append)
            throws IOException, UnsupportedEncodingException {

        final OutputStreamWriter osw;

        if (encoding != null) {
            osw = new OutputStreamWriter(createBufferedOutputStream(file, append), encoding);
        } else {
            osw = new OutputStreamWriter(createBufferedOutputStream(file, append), DEFAULT_CHARSET);
        }

        CloseableManager.addCloseable(file, osw);

        return osw;
    }

    /**
     * Create a buffered output stream from the specified path
     * 
     * @param fileName
     *            The output file name
     * @return The buffered output stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized BufferedOutputStream createBufferedOutputStream(final String fileName) throws IOException {
        return createBufferedOutputStream(fileName, false);
    }

    /**
     * Create a buffered output stream from the specified path
     * 
     * @param fileName
     *            The output file name
     * @param append
     *            if true, then bytes will be written to the end of the file
     *            rather than the beginning
     * @return The buffered output stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized BufferedOutputStream createBufferedOutputStream(final String fileName, final boolean append)
            throws IOException {
        Assertor.that(fileName).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_NAME_PARAM_NULL), false);
        return createBufferedOutputStream(new File(fileName), append);
    }

    /**
     * Create a buffered output stream from the specified file
     * 
     * @param file
     *            The output file
     * @return The buffered output stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized BufferedOutputStream createBufferedOutputStream(final File file) throws IOException {
        return createBufferedOutputStream(file, false);
    }

    /**
     * Create a buffered output stream from the specified file
     * 
     * @param file
     *            The output file
     * @param append
     *            if true, then bytes will be written to the end of the file
     *            rather than the beginning
     * @return The buffered output stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized BufferedOutputStream createBufferedOutputStream(final File file, final boolean append) throws IOException {
        Assertor.that(file).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_PARAM_NULL), false);

        final FileOutputStream fos = new FileOutputStream(file, append);
        CloseableManager.addCloseable(file, fos);

        final BufferedOutputStream bos = new BufferedOutputStream(fos);
        CloseableManager.addCloseable(file, bos);

        return bos;
    }

    /**
     * Create a buffered data output stream from the specified path
     * 
     * @param fileName
     *            The output file name
     * @return The buffered output stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized DataOutputStream createDataOutputStream(final String fileName) throws IOException {
        return createDataOutputStream(fileName, false);
    }

    /**
     * Create a data buffered output stream from the specified path
     * 
     * @param fileName
     *            The output file name
     * @param append
     *            if true, then bytes will be written to the end of the file
     *            rather than the beginning
     * @return The buffered output stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized DataOutputStream createDataOutputStream(final String fileName, final boolean append) throws IOException {
        Assertor.that(fileName).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_NAME_PARAM_NULL), false);
        return createDataOutputStream(new File(fileName), append);
    }

    /**
     * Create a data buffered output stream from the specified file
     * 
     * @param file
     *            The output file
     * @return The buffered output stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized DataOutputStream createDataOutputStream(final File file) throws IOException {
        return createDataOutputStream(file, false);
    }

    /**
     * Create a data buffered output stream from the specified file
     * 
     * @param file
     *            The output file
     * @param append
     *            if true, then bytes will be written to the end of the file
     *            rather than the beginning
     * @return The buffered output stream
     * @throws IOException
     *             Error thrown if wasn't found
     */
    public static synchronized DataOutputStream createDataOutputStream(final File file, final boolean append) throws IOException {
        Assertor.that(file).isNotNull().orElseThrow(new FileNotFoundException(ERROR_FILE_PARAM_NULL), false);

        final FileOutputStream fos = new FileOutputStream(file, append);
        CloseableManager.addCloseable(file, fos);

        final BufferedOutputStream bos = new BufferedOutputStream(fos);
        CloseableManager.addCloseable(file, bos);

        final DataOutputStream dos = new DataOutputStream(bos);
        CloseableManager.addCloseable(file, dos);

        return dos;
    }
}

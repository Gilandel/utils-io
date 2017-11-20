/*
 * #%L
 * utils-io
 * %%
 * Copyright (C) 2016 - 2017 Gilles Landel
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
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.commons.tuple.MutableSingle;
import fr.landel.utils.commons.tuple.Single;

/**
 * This class is used to:<br>
 * - move or copy a file or a directory,<br>
 * - delete a directory from disk
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
class InternalFileSystemUtils {

    /**
     * The line separator string (windows = \r\n, unix = \n, macOS = \r)
     */
    public static final String LINE_SEPARATOR = SystemProperties.LINE_SEPARATOR.getValue();

    /**
     * Message if parameters are null
     */
    protected static final String ERROR_PARAM_NULL = "At least one parameter is null";

    private static final int BUFFER_SIZE = 10240;

    /**
     * Constructor.
     *
     */
    protected InternalFileSystemUtils() {
    }

    /**
     * Create a directory and any intermediate directories.
     * 
     * @param directory
     *            The directory to create
     * @return true, if already exists or successfully created
     * @throws SecurityException
     *             Exception thrown if problems occurs during coping
     */
    public static boolean createDirectory(final File directory) {
        if (directory != null) {
            return directory.isDirectory() || directory.mkdirs();
        }
        return false;
    }

    /**
     * Copy a file.
     * 
     * @param src
     *            The source file name
     * @param dest
     *            The destination file name
     * @param removeSource
     *            Remove the source after copy
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    protected static void copyFile(final File src, final File dest, final boolean removeSource) throws IOException {
        int bufferReadSize;
        final byte[] buffer = new byte[BUFFER_SIZE];

        final File target;
        if (dest.isDirectory()) {
            target = new File(dest, src.getName());
        } else {
            target = dest;
        }

        if (InternalFileSystemUtils.createDirectory(target.getParentFile())) {
            if (!src.getAbsolutePath().equals(dest.getAbsolutePath())) {
                final BufferedInputStream bis = IOStreamUtils.createBufferedInputStream(src);
                final BufferedOutputStream bos = IOStreamUtils.createBufferedOutputStream(target);

                while ((bufferReadSize = bis.read(buffer, 0, BUFFER_SIZE)) >= 0) {
                    bos.write(buffer, 0, bufferReadSize);
                }

                CloseableManager.close(target);
                CloseableManager.close(src);

                if (removeSource && !src.delete()) {
                    throw new IOException("Cannot remove the source file");
                }
            }
        } else {
            throw new FileNotFoundException("destination directory doesn't exist and cannot be created");
        }
    }

    /**
     * Copy a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @param fileFilter
     *            The filter to limit file to be copied
     * @param filenameFilter
     *            The filename filter to limit file to be copied
     * @param removeSource
     *            Remove the source after copy
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    protected static void copyDirectory(final File src, final File dest, final FileFilter fileFilter, final FilenameFilter filenameFilter,
            final boolean removeSource) throws IOException {
        Assertor.that(src).validates((file) -> file.exists()).orElseThrow(() -> new FileNotFoundException("the source doesn't exist"));

        if (src.isDirectory()) {
            // creation du repertoire si necessaire
            if (InternalFileSystemUtils.createDirectory(dest)) {
                // creation de la liste des fichiers et repertoires
                File[] filesToCopy;
                if (fileFilter != null) {
                    filesToCopy = src.listFiles(fileFilter);
                } else if (filenameFilter != null) {
                    filesToCopy = src.listFiles(filenameFilter);
                } else {
                    filesToCopy = src.listFiles();
                }

                if (ArrayUtils.isNotEmpty(filesToCopy)) {
                    copy(filesToCopy, dest, fileFilter, filenameFilter, removeSource);
                }
                if (removeSource && !src.delete()) {
                    throw new IOException("Cannot delete the directory" + src.getAbsolutePath());
                }
            } else {
                throw new IOException("cannot access or create the destination directory");
            }
        } else if (matchFilter(src, fileFilter, filenameFilter)) {
            copyFile(src, dest, removeSource);
        }
    }

    /**
     * Get if file matches the filter
     * 
     * @param file
     *            the file (required, if null returns false)
     * @param fileFilter
     *            the file filter (optional)
     * @param filenameFilter
     *            the filename filter (optional)
     * @return if filter matches
     */
    protected static boolean matchFilter(final File file, final FileFilter fileFilter, final FilenameFilter filenameFilter) {
        boolean ok = false;
        if (file.isFile()) {
            if (fileFilter != null && fileFilter.accept(file)) {
                ok = true;
            } else if (filenameFilter != null && filenameFilter.accept(null, file.getName())) {
                ok = true;
            } else if (fileFilter == null && filenameFilter == null) {
                ok = true;
            }
        }
        return ok;
    }

    /**
     * Get the size of a directory
     * 
     * @param src
     *            the directory to analyze
     * @param fileFilter
     *            the file filter (optional)
     * @param filenameFilter
     *            the filename filter (optional)
     * @return the size or zero
     * @throws IOException
     *             on IO errors
     */
    protected static long getSize(final File src, final FileFilter fileFilter, final FilenameFilter filenameFilter) throws IOException {
        final MutableSingle<Long> size = Single.ofMutable(0L);
        InternalFileSystemUtils.listFiles(Optional.ofNullable(null), src, fileFilter, filenameFilter, (file) -> {
            if (file.isFile()) {
                size.set(size.get() + file.length());
            }
        });
        return size.get();
    }

    /**
     * List the files in a directory
     * 
     * @param output
     *            the output list (optional)
     * @param src
     *            the directory to analyze
     * @param fileFilter
     *            the file filter (optional)
     * @param filenameFilter
     *            the filename filter (optional)
     * @param actionOnEachFile
     *            the action applied on each file (optional)
     * @return the list of files
     * @throws IOException
     *             on IO errors
     */
    protected static List<File> listFiles(final Optional<List<File>> output, final File src, final FileFilter fileFilter,
            final FilenameFilter filenameFilter, Consumer<File> actionOnEachFile) throws IOException {

        final List<File> list = output.orElse(new ArrayList<>());

        if (src.isDirectory()) {
            // creation du repertoire si necessaire
            // creation de la liste des fichiers et repertoires
            File[] files = listFiles(src, fileFilter, filenameFilter);

            if (files != null) {
                list.addAll(Arrays.asList(files));

                for (File entry : files) {
                    if (actionOnEachFile != null) {
                        actionOnEachFile.accept(entry);
                    }

                    if (entry.isDirectory()) {
                        InternalFileSystemUtils.listFiles(Optional.of(list), entry, fileFilter, filenameFilter, actionOnEachFile);
                    }
                }
            }
        } else if (matchFilter(src, fileFilter, filenameFilter)) {
            list.add(src);
            if (actionOnEachFile != null) {
                actionOnEachFile.accept(src);
            }
        }
        return list;
    }

    /**
     * Gets the list of files following filters
     * 
     * @param dir
     *            the directory (required, not null)
     * @param fileFilter
     *            the file filter (optional)
     * @param filenameFilter
     *            the filename filter (optional)
     * @return the list of files
     */
    protected static File[] listFiles(final File dir, final FileFilter fileFilter, final FilenameFilter filenameFilter) {
        File[] files;
        if (fileFilter != null) {
            files = dir.listFiles(fileFilter);
        } else if (filenameFilter != null) {
            files = dir.listFiles(filenameFilter);
        } else {
            files = dir.listFiles();
        }
        return files;
    }

    /**
     * Copy files.
     * 
     * @param filesToCopy
     *            files to copy
     * @param dest
     *            destination file
     * @param fileFilter
     *            file filter
     * @param filenameFilter
     *            filename filter
     * @param removeSource
     *            Remove the source after copy
     * @throws IOException
     *             IOexception
     */
    protected static void copy(final File[] filesToCopy, final File dest, final FileFilter fileFilter, final FilenameFilter filenameFilter,
            final boolean removeSource) throws IOException {
        for (int i = 0; i < filesToCopy.length; i++) {
            // Check if element is a file or a directory
            File current = filesToCopy[i];
            if (current.isDirectory()) {
                // Create the directory
                copyDirectory(filesToCopy[i], createFile(dest, filesToCopy[i].getName()), fileFilter, filenameFilter, removeSource);
            } else {
                // Copy the file
                copyFile(filesToCopy[i], new File(dest + File.separator + filesToCopy[i].getName()), removeSource);
            }
        }
    }

    /**
     * Deletes a directory
     * 
     * @param dir
     *            the directory to delete (required)
     * @param fileFilter
     *            the file filter (optional)
     * @param filenameFilter
     *            the filename filter (optional)
     * @return true, if deleted
     * @throws IOException
     *             on IO errors
     */
    protected static boolean deleteDirectory(final File dir, final FileFilter fileFilter, final FilenameFilter filenameFilter)
            throws IOException {
        Assertor.that(dir).isNotNull().and().validates((file) -> file.isDirectory(), "not a directory").orElseThrow();

        boolean notDeleted = false;

        // Create the list of files and directories
        File[] files = listFiles(dir, fileFilter, filenameFilter);

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // Check if the element is a directory or a file
                File current = files[i];
                if (current.isDirectory()) {
                    notDeleted |= !deleteDirectory(current, fileFilter, filenameFilter);
                } else {
                    // Delete the file
                    notDeleted |= !current.delete();
                }
            }
        }

        if (isDirectoryEmpty(dir)) {
            // Delete the empty directory following filters
            notDeleted |= listFiles(dir, fileFilter, filenameFilter).length == 0 && !dir.delete();
        }

        return !notDeleted;
    }

    /**
     * Check if the specified directory is empty.
     * 
     * @param dir
     *            The directory to check
     * @return true if directory is empty
     */
    public static boolean isDirectoryEmpty(final File dir) {
        Assertor.that(dir).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extract the extension part.
     * 
     * @param fileName
     *            The file name
     * @return The extension part or null
     */
    public static String getExtensionPart(final String fileName) {
        if (fileName != null) {
            final int sep = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
            String temp = fileName;
            if (sep > -1) {
                if (sep < temp.length() - 1) {
                    temp = temp.substring(sep + 1);
                } else {
                    temp = "";
                }
            }

            final int index = temp.lastIndexOf('.');
            if (index > -1) {
                temp = temp.substring(index + 1);
            } else {
                temp = "";
            }
            return temp;
        }
        return null;
    }

    /**
     * Checks if the file has an extension including in the specified list.
     * 
     * @param fileName
     *            The file name
     * @param extensions
     *            The extensions list to check
     * @return true, if one extension matchs
     */
    public static boolean hasExtensionPart(final String fileName, final String... extensions) {
        if (fileName != null && ArrayUtils.isNotEmpty(extensions)) {
            String ext = getExtensionPart(fileName);
            if (ext != null) {
                List<String> exts = Arrays.asList(StringUtils.join(extensions, ",").toLowerCase().split(","));
                return exts.contains(ext.toLowerCase());
            }
        }
        return false;
    }

    /**
     * Extract the file name part without extension.
     * 
     * @param fileName
     *            The file name
     * @return The file name part or null
     */
    public static String getFileNamePart(final String fileName) {
        if (fileName != null) {
            final int sep = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
            String temp = fileName;
            if (sep > -1) {
                if (sep < temp.length() - 1) {
                    temp = fileName.substring(sep + 1);
                } else {
                    temp = "";
                }
            }
            final int index = temp.lastIndexOf('.');
            if (index > -1) {
                return temp.substring(0, index);
            }
            return temp;
        }
        return null;
    }

    /**
     * Create filename filter for directory tree.
     * 
     * @param exts
     *            The allowed extensions
     * @return The filter
     */
    public static FilenameFilter createFilenameFilter(final String... exts) {
        Assertor.that(exts).isNotEmpty(ERROR_PARAM_NULL).and().not().contains(null, "extensions array cannot contains 'null'")
                .orElseThrow();

        final Set<String> allowedExts = new HashSet<>();
        for (String ext : exts) {
            allowedExts.add(ext.toLowerCase());
        }
        return new FilenameFilter() {
            @Override
            public boolean accept(final File file, final String name) {
                final String ext = InternalFileSystemUtils.getExtensionPart(name);
                if (ext != null) {
                    return allowedExts.contains(ext.toLowerCase());
                }
                return false;
            }
        };
    }

    /**
     * Create {@link File} from base and add sub directories/file.
     * 
     * @param base
     *            The base directory (required, not null)
     * @param subFiles
     *            The list of sub directories/file (required, not null)
     * @return The concatenation of the arguments
     */
    public static File createFile(final File base, final String... subFiles) {
        File generatedFile = null;

        if (base != null && subFiles.length > 0) {
            generatedFile = base;
            for (String subFile : subFiles) {
                generatedFile = new File(generatedFile, subFile);
            }
        }

        return generatedFile;
    }
}

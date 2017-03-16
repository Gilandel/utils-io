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

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import fr.landel.utils.assertor.Assertor;

/**
 * This class is used to:<br>
 * - move or copy a file or a directory,<br>
 * - delete a directory from disk
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public final class FileSystemUtils extends InternalFileSystemUtils {

    private static final Pattern PATTERN_SPECIAL_CHARACTERS = Pattern.compile("[\\\\/:*?\"<>|]");

    /**
     * Constructor.
     *
     */
    private FileSystemUtils() {
    }

    /**
     * Replace all special characters in filename by the replacement string.
     * 
     * @param filename
     *            The input filename
     * @param replacement
     *            The replacement string
     * @return The filename processed
     */
    public static String replaceSpecialCharacters(final String filename, final String replacement) {
        if (replacement != null) {
            return PATTERN_SPECIAL_CHARACTERS.matcher(filename).replaceAll(replacement);
        }
        return PATTERN_SPECIAL_CHARACTERS.matcher(filename).replaceAll("");
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
    public static boolean createDirectory(final String directory) {
        if (directory != null) {
            return createDirectory(new File(directory));
        }
        return false;
    }

    /**
     * Move a file.
     * 
     * @param src
     *            The source file name
     * @param dest
     *            The destination file name
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void moveFile(final String src, final String dest) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyFile(new File(src), new File(dest), true);
    }

    /**
     * Move a file.
     * 
     * @param src
     *            The source file name
     * @param dest
     *            The destination file name
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void moveFile(final File src, final File dest) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyFile(src, dest, true);
    }

    /**
     * Copy a file.
     * 
     * @param src
     *            The source file name
     * @param dest
     *            The destination file name
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void copyFile(final String src, final String dest) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyFile(new File(src), new File(dest), false);
    }

    /**
     * Copy a file.
     * 
     * @param src
     *            The source file name
     * @param dest
     *            The destination file name
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void copyFile(final File src, final File dest) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyFile(src, dest, false);
    }

    /**
     * Move a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void moveDirectory(final String src, final String dest) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(new File(src), new File(dest), null, null, true);
    }

    /**
     * Move a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @param fileFilter
     *            The filter to limit file to be copied
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void moveDirectory(final String src, final String dest, final FileFilter fileFilter) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(new File(src), new File(dest), fileFilter, null, true);
    }

    /**
     * Move a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @param filenameFilter
     *            The filename filter to limit file to be copied
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void moveDirectory(final String src, final String dest, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(new File(src), new File(dest), null, filenameFilter, true);
    }

    /**
     * Move a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void moveDirectory(final File src, final File dest) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(src, dest, null, null, true);
    }

    /**
     * Move a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @param fileFilter
     *            The filter to limit file to be copied
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void moveDirectory(final File src, final File dest, final FileFilter fileFilter) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(src, dest, fileFilter, null, true);
    }

    /**
     * Move a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @param filenameFilter
     *            The filename filter to limit file to be copied
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void moveDirectory(final File src, final File dest, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(src, dest, null, filenameFilter, true);
    }

    /**
     * Copy a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void copyDirectory(final String src, final String dest) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(new File(src), new File(dest), null, null, false);
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
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void copyDirectory(final String src, final String dest, final FileFilter fileFilter) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(new File(src), new File(dest), fileFilter, null, false);
    }

    /**
     * Copy a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @param filenameFilter
     *            The filename filter to limit file to be copied
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void copyDirectory(final String src, final String dest, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(new File(src), new File(dest), null, filenameFilter, false);
    }

    /**
     * Copy a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void copyDirectory(final File src, final File dest) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(src, dest, null, null, false);
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
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void copyDirectory(final File src, final File dest, final FileFilter fileFilter) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(src, dest, fileFilter, null, false);
    }

    /**
     * Copy a directory recursively.
     * 
     * @param src
     *            The source directory name
     * @param dest
     *            The destination directory name, the directory is created if it
     *            not exists
     * @param filenameFilter
     *            The filename filter to limit file to be copied
     * @throws IOException
     *             Exception thrown if problems occurs during coping
     */
    public static void copyDirectory(final File src, final File dest, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(src).isNotNull().and(dest).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        copyDirectory(src, dest, null, filenameFilter, false);
    }

    public static long getSize(final String src) throws IOException {
        Assertor.that(src).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.getSize(new File(src), null, null);
    }

    public static long getSize(final String src, final FileFilter fileFilter) throws IOException {
        Assertor.that(src).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.getSize(new File(src), fileFilter, null);
    }

    public static long getSize(final String src, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(src).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.getSize(new File(src), null, filenameFilter);
    }

    public static long getSize(final File src) throws IOException {
        Assertor.that(src).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.getSize(src, null, null);
    }

    public static long getSize(final File src, final FileFilter fileFilter) throws IOException {
        Assertor.that(src).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.getSize(src, fileFilter, null);
    }

    public static long getSize(final File src, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(src).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.getSize(src, null, filenameFilter);
    }

    public static List<File> listFiles(final String src) throws IOException {
        Assertor.that(src).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.listFiles(Optional.empty(), new File(src), null, null, null);
    }

    public static List<File> listFiles(final String src, final FileFilter fileFilter) throws IOException {
        Assertor.that(src).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.listFiles(Optional.empty(), new File(src), fileFilter, null, null);
    }

    public static List<File> listFiles(final String src, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(src).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.listFiles(Optional.empty(), new File(src), null, filenameFilter, null);
    }

    public static List<File> listFiles(final File src) throws IOException {
        Assertor.that(src).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.listFiles(Optional.empty(), src, null, null, null);
    }

    public static List<File> listFiles(final File src, final FileFilter fileFilter) throws IOException {
        Assertor.that(src).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.listFiles(Optional.empty(), src, fileFilter, null, null);
    }

    public static List<File> listFiles(final File src, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(src).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return FileSystemUtils.listFiles(Optional.empty(), src, null, filenameFilter, null);
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
     * Remove a directory.
     * 
     * @param dir
     *            The directory name
     * @return true, if full deleted
     * @throws IOException
     *             IOexception
     */
    public static boolean deleteDirectory(final String dir) throws IOException {
        Assertor.that(dir).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return deleteDirectory(new File(dir), null, null);
    }

    /**
     * Remove a directory.
     * 
     * @param dir
     *            The directory name
     * @param fileFilter
     *            The file filter to apply (not used if null)
     * @return true, if full deleted
     * @throws IOException
     *             IOexception
     */
    public static boolean deleteDirectory(final String dir, final FileFilter fileFilter) throws IOException {
        Assertor.that(dir).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return deleteDirectory(new File(dir), fileFilter, null);
    }

    /**
     * Remove a directory.
     * 
     * @param dir
     *            The directory name
     * @param filenameFilter
     *            The file filter to apply (not used if null)
     * @return true, if full deleted
     * @throws IOException
     *             IOexception
     */
    public static boolean deleteDirectory(final String dir, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(dir).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return deleteDirectory(new File(dir), null, filenameFilter);
    }

    /**
     * Remove a directory.
     * 
     * @param dir
     *            The directory name
     * @return true, if full deleted
     * @throws IOException
     *             IOexception
     */
    public static boolean deleteDirectory(final File dir) throws IOException {
        Assertor.that(dir).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return deleteDirectory(dir, null, null);
    }

    /**
     * Remove a directory.
     * 
     * @param dir
     *            The directory name (required, not null and a directory)
     * @param fileFilter
     *            The file filter to apply (not used if null)
     * @return true, if full deleted
     * @throws IOException
     *             IOexception
     */
    public static boolean deleteDirectory(final File dir, final FileFilter fileFilter) throws IOException {
        Assertor.that(dir).isNotNull().and(fileFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return deleteDirectory(dir, fileFilter, null);
    }

    /**
     * Remove a directory.
     * 
     * @param dir
     *            The directory name (required, not null and a directory)
     * @param filenameFilter
     *            The file filter to apply (not used if null)
     * @return true, if full deleted
     * @throws IOException
     *             IOexception
     */
    public static boolean deleteDirectory(final File dir, final FilenameFilter filenameFilter) throws IOException {
        Assertor.that(dir).isNotNull().and(filenameFilter).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return deleteDirectory(dir, null, filenameFilter);
    }

    /**
     * Check if the specified directory is empty.
     * 
     * @param dir
     *            The directory to check
     * @return true if directory is empty
     * @throws IOException
     *             IOException
     */
    public static boolean isDirectoryEmpty(final String dir) throws IOException {
        Assertor.that(dir).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return isDirectoryEmpty(new File(dir));
    }

    /**
     * To get the absolute path.
     * 
     * @param base
     *            The base directory
     * @param filePath
     *            The file to check
     * @return filePath, if it's absolute otherwise return 'base + filePath'
     */
    public static String getAbsolutePath(final File base, final File filePath) {
        Assertor.that(base).isNotNull().and(filePath).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        File file = filePath;
        if (!file.isAbsolute()) {
            file = new File(base, filePath.getPath());
        }
        return file.getAbsolutePath();
    }

    /**
     * To get the absolute path.
     * 
     * @param base
     *            The base directory
     * @param filePath
     *            The file to check
     * @return filePath, if it's absolute otherwise return 'base + filePath'
     */
    public static String getAbsolutePath(final String base, final String filePath) {
        Assertor.that(base).isNotNull().and(filePath).isNotNull().orElseThrow(ERROR_PARAM_NULL);

        return getAbsolutePath(new File(base), new File(filePath));
    }

    /**
     * Extract the extension part.
     * 
     * @param file
     *            The file
     * @return The extension part or null
     */
    public static String getExtensionPart(final File file) {
        if (file != null) {
            return getExtensionPart(file.getName());
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
     * Checks if the file has an extension including in the specified list.
     * 
     * @param file
     *            The file
     * @param extensions
     *            The extensions list to check
     * @return true, if one extension matchs
     */
    public static boolean hasExtensionPart(final File file, final String... extensions) {
        if (file != null) {
            return hasExtensionPart(file.getName(), extensions);
        }
        return false;
    }

    /**
     * Extract the file name part without extension.
     * 
     * @param file
     *            The file
     * @return The file name part or null
     */
    public static String getFileNamePart(final File file) {
        if (file != null) {
            return getFileNamePart(file.getName());
        }
        return null;
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
    public static File createFile(final String base, final String... subFiles) {
        return createFile(new File(base), subFiles);
    }
}

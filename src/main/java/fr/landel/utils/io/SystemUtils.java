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

/**
 * System utility class
 *
 * @since Dec 21, 2016
 * @author Gilles
 *
 */
public class SystemUtils {

    /**
     * The operating system name
     */
    public static final String OS = SystemProperties.OS_NAME.getValue();

    private static final String OS_LC = OS.toLowerCase();

    private static final boolean IS_WINDOWS = OS_LC.indexOf("win") > -1;
    private static final boolean IS_MAC = OS_LC.indexOf("mac") > -1;
    // Unix, Linux and AIX (IBM)
    private static final boolean IS_UNIX = OS_LC.indexOf("nix") > -1 || OS_LC.indexOf("nux") > -1 || OS_LC.indexOf("aix") > -1;
    private static final boolean IS_SOLARIS = OS_LC.indexOf("sunos") > -1;

    /**
     * Check if the current OS is Windows
     * 
     * @return true, if Windows
     */
    public static boolean isWindows() {
        return IS_WINDOWS;
    }

    /**
     * Check if the current OS is Mac
     * 
     * @return true, if Mac
     */
    public static boolean isMac() {
        return IS_MAC;
    }

    /**
     * Check if the current OS is Unix
     * 
     * @return true, if Unix
     */
    public static boolean isUnix() {
        return IS_UNIX;
    }

    /**
     * Check if the current OS is Solaris
     * 
     * @return true, if Solaris
     */
    public static boolean isSolaris() {
        return IS_SOLARIS;
    }
}

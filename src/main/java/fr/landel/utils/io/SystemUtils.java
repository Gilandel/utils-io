/*-
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

import java.util.Arrays;

/**
 * System utility class
 *
 * @since Dec 21, 2016
 * @author Gilles
 *
 */
public final class SystemUtils {

    /**
     * The operating system name
     */
    public static final String OS = SystemProperties.OS_NAME.getValue();

    private static final String OS_LC = OS.toLowerCase();

    private static final boolean IS_WINDOWS = OS_LC.indexOf("win") > -1;
    private static final boolean IS_MAC = OS_LC.indexOf("mac") > -1;
    // Unix, Linux, AIX (IBM) and FreeBSD
    private static final boolean IS_UNIX = Arrays.asList("nix", "nux", "aix", "freebsd").stream().anyMatch(os -> OS_LC.indexOf(os) > -1);
    private static final boolean IS_SOLARIS = OS_LC.indexOf("sunos") > -1;

    /**
     * Hidden Constructor
     */
    private SystemUtils() {
        throw new UnsupportedOperationException();
    }

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

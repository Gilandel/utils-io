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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Check utility class (system).
 *
 * @since Dec 21, 2016
 * @author Gilles
 *
 */
public class SystemUtilsTest extends AbstractTest {

    private static final String OS = System.getProperty("os.name");
    private static final String OS_LC = OS.toLowerCase();

    /**
     * Test constructor for {@link SystemUtils} .
     */
    @Test
    public void testConstructors() {
        assertTrue(checkPrivateConstructor(SystemUtils.class));
    }

    /**
     * Test method for {@link SystemUtils#OS_NAME}.
     */
    @Test
    public void testOS() {
        assertEquals(OS, SystemUtils.OS);
    }

    /**
     * Test method for {@link SystemUtils#isWindows()}.
     * 
     * @throws IllegalAccessException
     *             on loading new instance error
     * @throws InstantiationException
     *             on loading new instance error
     */
    @Test
    public void testIsWindows() throws InstantiationException, IllegalAccessException {
        assertEquals(OS_LC.indexOf("win") > -1, SystemUtils.isWindows());
    }

    /**
     * Test method for {@link SystemUtils#isMac()}.
     */
    @Test
    public void testIsMac() {
        assertEquals(OS_LC.indexOf("mac") > -1, SystemUtils.isMac());
    }

    /**
     * Test method for {@link SystemUtils#isUnix()}.
     */
    @Test
    public void testIsUnix() {
        assertEquals(OS_LC.indexOf("nix") > -1 || OS_LC.indexOf("nux") > -1 || OS_LC.indexOf("aix") > -1 || OS_LC.indexOf("freebsd") > -1,
                SystemUtils.isUnix());
    }

    /**
     * Test method for {@link SystemUtils#isSolaris()}.
     */
    @Test
    public void testIsSolaris() {
        assertEquals(OS_LC.indexOf("sunos") > -1, SystemUtils.isSolaris());
    }
}

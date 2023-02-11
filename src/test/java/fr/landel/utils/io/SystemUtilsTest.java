/*-
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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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

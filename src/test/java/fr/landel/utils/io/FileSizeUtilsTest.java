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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

/**
 * Check utility class (files).
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public class FileSizeUtilsTest extends AbstractTest {

    /**
     * Test constructor for {@link FileSizeUtils} .
     */
    @Test
    public void testConstructors() {
        assertTrue(checkPrivateConstructor(FileSizeUtils.class));
    }

    /**
     * Check size formatter
     */
    @Test
    public void testFormatSize() {
        assertEquals("1 Octet", FileSizeUtils.formatSize(1L));
        assertEquals("23 Octets", FileSizeUtils.formatSize(23L));
        assertEquals("23.017 Kio", FileSizeUtils.formatSize(23569L, Locale.US));
        assertEquals("22.478 Mio", FileSizeUtils.formatSize(23569896L, Locale.US));
        assertEquals("21.951 Gio", FileSizeUtils.formatSize(23569896548L, Locale.US));
        assertEquals("21.437 Tio", FileSizeUtils.formatSize(23569896548855L, Locale.US));
        assertEquals("20.934 Pio", FileSizeUtils.formatSize(23569896548855142L, Locale.US));
        assertEquals("20,934 Pio", FileSizeUtils.formatSize(23569896548855142L, Locale.FRANCE));
    }
}

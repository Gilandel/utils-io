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

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

/**
 * Check utility class (files).
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public class FileSizeUtilsTest {

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

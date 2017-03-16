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

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import fr.landel.utils.commons.EnumChar;

/**
 * This class contains the list of file sizes
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public final class FileSizeUtils {

    /**
     * Octets in kibioctets (kilo)
     */
    public static final long KIO = 1024;

    /**
     * Octets in mebioctets (mega)
     */
    public static final long MIO = KIO * KIO;

    /**
     * Octets in gibioctets (giba)
     */
    public static final long GIO = KIO * MIO;

    /**
     * Octets in tebioctets (tera)
     */
    public static final long TIO = KIO * GIO;

    /**
     * Octets in pebioctets (peta)
     */
    public static final long PIO = KIO * TIO;

    /**
     * Octets in exabioctets (exa)
     */
    public static final long EIO = KIO * PIO;

    /**
     * The map of sizes
     */
    public static final Map<Long, String> SIZE_UNITIES;

    static {
        Map<Long, String> map = new TreeMap<>();

        map.put(0L, "Octet");
        map.put(KIO, "Kio");
        map.put(MIO, "Mio");
        map.put(GIO, "Gio");
        map.put(TIO, "Tio");
        map.put(PIO, "Pio");
        map.put(EIO, "Eio");

        SIZE_UNITIES = Collections.unmodifiableMap(map);
    }

    /**
     * Constructor.
     *
     */
    private FileSizeUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Format a file size (use current locale).
     * 
     * @param size
     *            The file size
     * @return The size formatted
     */
    public static String formatSize(final long size) {
        return formatSize(size, null);
    }

    /**
     * Format a file size.
     * 
     * @param size
     *            The file size
     * @param locale
     *            The locale (can be null)
     * @return The size formatted
     */
    public static String formatSize(final long size, final Locale locale) {
        double unitySize;
        final NumberFormat numberFormatter;
        if (locale != null) {
            numberFormatter = NumberFormat.getNumberInstance(locale);
        } else {
            numberFormatter = NumberFormat.getNumberInstance();
        }

        final Map<Long, String> mapReversed = new TreeMap<>(Collections.reverseOrder());
        mapReversed.putAll(SIZE_UNITIES);

        for (Entry<Long, String> entry : mapReversed.entrySet()) {
            if (size >= entry.getKey() && entry.getKey() > 0) {
                unitySize = (double) size / entry.getKey();
                return numberFormatter.format(unitySize) + EnumChar.SPACE.getUnicode() + entry.getValue();
            }
        }
        if (size > 1) {
            return new StringBuilder(String.valueOf(size)).append(EnumChar.SPACE.getUnicode()).append(mapReversed.get(0L)).append("s")
                    .toString();
        }
        return new StringBuilder(String.valueOf(size)).append(EnumChar.SPACE.getUnicode()).append(mapReversed.get(0L)).toString();
    }
}

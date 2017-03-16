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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The list of encoding, BOM and charset
 *
 * @since Nov 27, 2015
 * @author Gilles Landel
 *
 */
public final class EncodingUtils {

    /**
     * UTF-1 BOM, the UTF-1 byte order mark = F7 64 4C
     */
    public static final String BOM_UTF_1 = String.valueOf(new char[] {0xF7, 0x64, 0x4C});

    /**
     * UTF-7 BOM, the UTF-7 byte order mark = 2B 2F 76 (38 | 39 | 2B | 2F | 38
     * 2D)
     */
    public static final String BOM_UTF_7_V1 = String.valueOf(new char[] {0x2B, 0x2F, 0x76, 0x38});

    /**
     * UTF-7 BOM, the UTF-7 byte order mark = 2B 2F 76 (38 | 39 | 2B | 2F | 38
     * 2D)
     */
    public static final String BOM_UTF_7_V2 = String.valueOf(new char[] {0x2B, 0x2F, 0x76, 0x39});

    /**
     * UTF-7 BOM, the UTF-7 byte order mark = 2B 2F 76 (38 | 39 | 2B | 2F | 38
     * 2D)
     */
    public static final String BOM_UTF_7_V3 = String.valueOf(new char[] {0x2B, 0x2F, 0x76, 0x2B});

    /**
     * UTF-7 BOM, the UTF-7 byte order mark = 2B 2F 76 (38 | 39 | 2B | 2F | 38
     * 2D)
     */
    public static final String BOM_UTF_7_V4 = String.valueOf(new char[] {0x2B, 0x2F, 0x76, 0x2F});

    /**
     * UTF-7 BOM, the UTF-7 byte order mark = 2B 2F 76 (38 | 39 | 2B | 2F | 38
     * 2D)
     */
    public static final String BOM_UTF_7_V5 = String.valueOf(new char[] {0x2B, 0x2F, 0x76, 0x38, 0x2D});

    /**
     * UTF-8 BOM, the UTF-8 byte order mark = EF BB BF
     */
    public static final String BOM_UTF_8 = String.valueOf(new char[] {0xEF, 0xBB, 0xBF});

    /**
     * UTF-16 BOM, the UTF-16 byte order mark = FE FF (Big Endian)
     */
    public static final String BOM_UTF_16_BE = String.valueOf(new char[] {0xFE, 0xFF});

    /**
     * UTF-16 BOM, the UTF-16 byte order mark = FF FE (Little Endian)
     */
    public static final String BOM_UTF_16_LE = String.valueOf(new char[] {0xFF, 0xFE});

    /**
     * UTF-32 BOM, the UTF-32 byte order mark = 00 00 FE FF (Big Endian)
     */
    public static final String BOM_UTF_32_BE = String.valueOf(new char[] {0, 0, 0xFE, 0xFF});

    /**
     * UTF-32 BOM, the UTF-32 byte order mark = FF FE 00 00 (Little Endian)
     */
    public static final String BOM_UTF_32_LE = String.valueOf(new char[] {0xFF, 0xFE, 0, 0});

    /**
     * UTF-EBCDIC BOM, the UTF-EBCDIC byte order mark = DD 73 66 73
     */
    public static final String BOM_UTF_EBCDIC = String.valueOf(new char[] {0xDD, 0x73, 0x66, 0x73});

    /**
     * SCSU BOM, the Standard Compression Scheme for Unicode byte order mark =
     * 0E FE FF
     */
    public static final String BOM_SCSU = String.valueOf(new char[] {0x0E, 0xFE, 0xFF});

    /**
     * BOCU-1 BOM, the BOCU-1 byte order mark = FB EE 28
     */
    public static final String BOM_BOCU_1 = String.valueOf(new char[] {0xFB, 0xEE, 0x28});

    /**
     * GB-18030 BOM, the GB-18030 byte order mark = 84 31 95 33
     */
    public static final String BOM_GB_18030 = String.valueOf(new char[] {0x84, 0x31, 0x95, 0x33});

    /**
     * US ASCII encoding
     */
    public static final String ENCODING_US_ASCII = "US-ASCII";

    /**
     * UTF-8 encoding
     */
    public static final String ENCODING_UTF_8 = "UTF-8";

    /**
     * ISO-8859-1 encoding
     */
    public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";

    /**
     * UTF-16 encoding
     */
    public static final String ENCODING_UTF_16 = "UTF-16";

    /**
     * UTF-16 Little Endian encoding
     */
    public static final String ENCODING_UTF_16_LE = "UTF-16LE";

    /**
     * UTF-16 Big Endian encoding
     */
    public static final String ENCODING_UTF_16_BE = "UTF-16BE";

    /**
     * US ASCII charset
     */
    public static final Charset CHARSET_US_ASCII = Charset.forName(ENCODING_US_ASCII);

    /**
     * UTF-8 charset
     */
    public static final Charset CHARSET_UTF_8 = Charset.forName(ENCODING_UTF_8);

    /**
     * ISO-8859-1 charset
     */
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ENCODING_ISO_8859_1);

    /**
     * UTF-16 charset
     */
    public static final Charset CHARSET_UTF_16 = Charset.forName(ENCODING_UTF_16);

    /**
     * UTF-16 Little Endian charset
     */
    public static final Charset CHARSET_UTF_16_LE = Charset.forName(ENCODING_UTF_16_LE);

    /**
     * UTF-16 Big Endian charset
     */
    public static final Charset CHARSET_UTF_16_BE = Charset.forName(ENCODING_UTF_16_BE);

    /**
     * The Byte Order Mark map (key = encoding name, value = the BOM)
     */
    public static final Map<String, String> BOM_LIST;

    /**
     * The encoding list (supported by JAVA)
     */
    public static final List<String> ENCODING_LIST;

    /**
     * The charset map (supported by JAVA) (key = encoding name, value = the
     * charset)
     */
    public static final Map<String, Charset> CHARSET_LIST;

    static {
        Map<String, String> bom = new LinkedHashMap<>();

        bom.put("UTF-1", BOM_UTF_1);
        bom.put("UTF-7_v1", BOM_UTF_7_V1);
        bom.put("UTF-7_v2", BOM_UTF_7_V2);
        bom.put("UTF-7_v3", BOM_UTF_7_V3);
        bom.put("UTF-7_v4", BOM_UTF_7_V4);
        bom.put("UTF-7_v5", BOM_UTF_7_V5);
        bom.put(ENCODING_UTF_8, BOM_UTF_8);
        bom.put(ENCODING_UTF_16_BE, BOM_UTF_16_BE);
        bom.put(ENCODING_UTF_16_LE, BOM_UTF_16_LE);
        bom.put("UTF-32BE", BOM_UTF_32_BE);
        bom.put("UTF-32LE", BOM_UTF_32_LE);
        bom.put("UTF-EBCDIC", BOM_UTF_EBCDIC);
        bom.put("SCSU", BOM_SCSU);
        bom.put("BOCU-1", BOM_BOCU_1);
        bom.put("GB-18030", BOM_GB_18030);

        BOM_LIST = Collections.unmodifiableMap(bom);

        List<String> encoding = new ArrayList<>();

        encoding.add(ENCODING_US_ASCII);
        encoding.add(ENCODING_ISO_8859_1);
        encoding.add(ENCODING_UTF_8);
        encoding.add(ENCODING_UTF_16_BE);
        encoding.add(ENCODING_UTF_16_LE);
        encoding.add(ENCODING_UTF_16);

        ENCODING_LIST = Collections.unmodifiableList(encoding);

        Map<String, Charset> charset = new LinkedHashMap<>();

        charset.put(ENCODING_US_ASCII, CHARSET_US_ASCII);
        charset.put(ENCODING_ISO_8859_1, CHARSET_ISO_8859_1);
        charset.put(ENCODING_UTF_8, CHARSET_UTF_8);
        charset.put(ENCODING_UTF_16_BE, CHARSET_UTF_16_BE);
        charset.put(ENCODING_UTF_16_LE, CHARSET_UTF_16_LE);
        charset.put(ENCODING_UTF_16, CHARSET_UTF_16);

        CHARSET_LIST = Collections.unmodifiableMap(charset);
    }

    /**
     * Constructor.
     *
     */
    private EncodingUtils() {
        throw new UnsupportedOperationException();
    }
}

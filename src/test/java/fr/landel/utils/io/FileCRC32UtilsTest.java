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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.landel.utils.commons.StringUtils;

/**
 * Check {@link FileCRC32Utils}
 *
 * @since Aug 11, 2016
 * @author Gilles
 *
 */
public class FileCRC32UtilsTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileCRC32UtilsTest.class);

    private static final String XML_EXT = "xml";
    private static final FilenameFilter XML_FILENAME_FILTER = FileSystemUtils.createFilenameFilter(XML_EXT);
    private static final FileFilter XML_FILE_FILTER = (file) -> XML_EXT.equalsIgnoreCase(FileSystemUtils.getExtensionPart(file));

    private static final String CHECK_CRC32_PATH = "src/test/resources/io";
    private static final String CHECK_CRC32_FILE = CHECK_CRC32_PATH + "/checkCRC32.xml";

    private static final Long CHECK_CRC32_VALUE = 1_476_569_244L;
    private static final Long CHECK_CRC32_DIR_VALUE1 = 914_046_700L;
    private static final Long CHECK_CRC32_DIR_VALUE2 = 1_208_031_330L;
    private static final List<Long> CHECK_CRC32_DIR_VALUES = Arrays.asList(CHECK_CRC32_DIR_VALUE1, CHECK_CRC32_DIR_VALUE2);

    /**
     * Test constructor for {@link FileCRC32Utils} .
     */
    @Test
    public void testConstructors() {
        assertTrue(checkPrivateConstructor(FileCRC32Utils.class));
    }

    /**
     * Test method for {@link FileCRC32Utils#getCRC32}.
     */
    @Test
    public void testGetCRC32() {
        try {
            assertEquals(CHECK_CRC32_VALUE, FileCRC32Utils.getCRC32(CHECK_CRC32_FILE));
            assertEquals(CHECK_CRC32_VALUE, FileCRC32Utils.getCRC32(new File(CHECK_CRC32_FILE)));
            assertEquals(CHECK_CRC32_VALUE, FileCRC32Utils.getCRC32(IOStreamUtils.createBufferedInputStream(CHECK_CRC32_FILE)));

            assertEquals(CHECK_CRC32_VALUE, FileCRC32Utils.getCRC32(CHECK_CRC32_PATH, XML_FILENAME_FILTER));
            assertEquals(CHECK_CRC32_VALUE, FileCRC32Utils.getCRC32(CHECK_CRC32_PATH, XML_FILE_FILTER));

            final Long crc32 = FileCRC32Utils.getCRC32(CHECK_CRC32_PATH);
            final String expected = StringUtils.joinComma(CHECK_CRC32_DIR_VALUES);
            
            LOGGER.info("Expected CRC: {}, actual: {}", expected, crc32);
            
            assertTrue(StringUtils.inject("FileCRC32Utils.getCRC32 result {} doesn't match expected values: {}", crc32, expected),
            		CHECK_CRC32_DIR_VALUES.contains(crc32));

            final File emptyDir = new File("target/empty");
            assertTrue(FileSystemUtils.createDirectory(emptyDir));
            assertEquals(Long.valueOf(0L), FileCRC32Utils.getCRC32(emptyDir));

            final File unknownDir = new File("target/unknown");

            assertEquals(Long.valueOf(0L), FileCRC32Utils.getCRC32(unknownDir));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}

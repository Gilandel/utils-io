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

import static fr.landel.utils.io.SystemProperties.AWT_TOOLKIT;
import static fr.landel.utils.io.SystemProperties.CDRAMS_DECORATIONS;
import static fr.landel.utils.io.SystemProperties.CDRAMS_PRESENTATION;
import static fr.landel.utils.io.SystemProperties.CDRAMS_REPOSITORY;
import static fr.landel.utils.io.SystemProperties.CDRAMS_VERBOSE;
import static fr.landel.utils.io.SystemProperties.COM_SUN_MIDP_IMPLEMENTATION;
import static fr.landel.utils.io.SystemProperties.COM_SUN_PACKAGE_SPEC_VERSION;
import static fr.landel.utils.io.SystemProperties.FILE_ENCODING_PKG;
import static fr.landel.utils.io.SystemProperties.JAVA_AWT_GRAPHICS_ENV;
import static fr.landel.utils.io.SystemProperties.JAVA_AWT_PRINTERJOB;
import static fr.landel.utils.io.SystemProperties.JAVA_COMPILER;
import static fr.landel.utils.io.SystemProperties.JAVA_ENDORSED_DIR;
import static fr.landel.utils.io.SystemProperties.JAVA_EXT_DIR;
import static fr.landel.utils.io.SystemProperties.MICROEDITION_COMMPORTS;
import static fr.landel.utils.io.SystemProperties.MICROEDITION_CONFIGURATION;
import static fr.landel.utils.io.SystemProperties.MICROEDITION_ENCODING;
import static fr.landel.utils.io.SystemProperties.MICROEDITION_HOSTNAME;
import static fr.landel.utils.io.SystemProperties.MICROEDITION_LOCALE;
import static fr.landel.utils.io.SystemProperties.MICROEDITION_PLATFORM;
import static fr.landel.utils.io.SystemProperties.MICROEDITION_PROFILES;
import static fr.landel.utils.io.SystemProperties.MICROEDITION_SECURERANDOM_NOFALLBACK;
import static fr.landel.utils.io.SystemProperties.SUN_BOOT_CLASS_PATH;
import static fr.landel.utils.io.SystemProperties.SUN_CPU_ISALIST;
import static fr.landel.utils.io.SystemProperties.SUN_DESKTOP;
import static fr.landel.utils.io.SystemProperties.SUN_JAVA2D_FONTPATH;
import static fr.landel.utils.io.SystemProperties.SUN_MISC_PRODUCT;
import static fr.landel.utils.io.SystemProperties.SUN_OS_PATCH_LEVEL;
import static fr.landel.utils.io.SystemProperties.USER_REGION;
import static fr.landel.utils.io.SystemProperties.USER_SCRIPT;
import static fr.landel.utils.io.SystemProperties.USER_TIMEZONE;
import static fr.landel.utils.io.SystemProperties.USER_VARIANT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.landel.utils.commons.StringUtils;

/**
 * Check {@link SystemProperties}
 *
 * @since Mar 4, 2017
 * @author Gilles
 *
 */
public class SystemPropertiesTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemPropertiesTest.class);

	/**
	 * Check {@link SystemProperties}
	 */
	@Test
	public void test() {
		assertEquals(SystemProperties.OS_NAME, SystemProperties.valueOf("OS_NAME"));
		assertEquals("OS_NAME", SystemProperties.OS_NAME.name());
		assertEquals(0, SystemProperties.JAVA_RUNTIME_NAME.ordinal());
	}

	/**
	 * Check {@link SystemProperties#getKey()}
	 */
	@Test
	public void testGetKey() {
		assertEquals("os.name", SystemProperties.OS_NAME.getKey());
	}

	/**
	 * Check {@link SystemProperties#getValue()}
	 */
	@Test
	public void testGetValue() {
		final List<SystemProperties> excludedProperties = Arrays.asList(JAVA_COMPILER, SUN_OS_PATCH_LEVEL, USER_VARIANT,
				USER_SCRIPT, USER_REGION, USER_TIMEZONE, SUN_DESKTOP, SUN_CPU_ISALIST, SUN_JAVA2D_FONTPATH,
				SUN_MISC_PRODUCT, CDRAMS_DECORATIONS, CDRAMS_PRESENTATION, CDRAMS_REPOSITORY, CDRAMS_VERBOSE,
				COM_SUN_MIDP_IMPLEMENTATION, COM_SUN_PACKAGE_SPEC_VERSION, MICROEDITION_COMMPORTS,
				MICROEDITION_CONFIGURATION, MICROEDITION_ENCODING, MICROEDITION_HOSTNAME, MICROEDITION_LOCALE,
				MICROEDITION_PLATFORM, MICROEDITION_PROFILES, MICROEDITION_SECURERANDOM_NOFALLBACK, JAVA_ENDORSED_DIR,
				JAVA_EXT_DIR, SUN_BOOT_CLASS_PATH, JAVA_AWT_GRAPHICS_ENV, JAVA_AWT_PRINTERJOB, AWT_TOOLKIT,
				FILE_ENCODING_PKG);

		LOGGER.info("----- FOUND PROPS");
		Arrays.asList(SystemProperties.values()).stream().filter(p -> StringUtils.isNotEmpty(p.getValue()))
				.forEach(SystemPropertiesTest::log);

		LOGGER.info("----- NOT FOUND PROPS");
		Arrays.asList(SystemProperties.values()).stream().filter(p -> StringUtils.isEmpty(p.getValue()))
				.forEach(SystemPropertiesTest::log);

		LOGGER.info("----- NEW PROPS");
		final List<String> keys = Arrays.asList(SystemProperties.values()).stream().map(p -> p.getKey())
				.collect(Collectors.toList());
		System.getProperties().entrySet().stream().filter(p -> !keys.contains(p.getKey()))
				.forEach(SystemPropertiesTest::log);

		LOGGER.info("-----");

		for (SystemProperties property : SystemProperties.values()) {
			if (!excludedProperties.contains(property)) {
				assertTrue(StringUtils.isNotEmpty(property.getValue()),
						StringUtils.inject("Property '{}' is not correct: {}", property.getKey(), property.getValue()));
			}
		}
	}

	private static void log(final Object object) {
		LOGGER.info(Objects.toString(object));
	}
}

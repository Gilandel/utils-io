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

/**
 * An enumeration of system properties. Values can be {@code null}.
 * 
 * @since Mar 4, 2017
 * @author Gilles
 *
 */
public enum SystemProperties {

    /**
     * The Java runtime name (ex: Java(TM) SE Runtime Environment)
     */
    JAVA_RUNTIME_NAME("java.runtime.name"),

    /**
     * The Java runtime version (ex: 1.8.0_121-b13)
     */
    JAVA_RUNTIME_VERSION("java.runtime.version"),

    /**
     * The Java home (ex: C:\Program Files\Java\jdk1.8.0_121\jre)
     */
    JAVA_HOME("java.home"),

    /**
     * The Java version (ex: 1.8.0_121)
     */
    JAVA_VERSION("java.version"),

    /**
     * The Java compiler
     */
    JAVA_COMPILER("java.compiler"),

    /**
     * The Java vendor (ex: Oracle Corporation)
     */
    JAVA_VENDOR("java.vendor"),

    /**
     * The Java vendor URL to report a bug (ex:
     * http://bugreport.sun.com/bugreport/ or
     * http://bugreport.java.com/bugreport/)
     */
    JAVA_VENDOR_URL_BUG("java.vendor.url.bug"),

    /**
     * The JVM name (ex: Java HotSpot(TM) 64-Bit Server VM)
     */
    JAVA_VM_NAME("java.vm.name"),

    /**
     * The JVM version (ex: 25.121-b13)
     */
    JAVA_VM_VERSION("java.vm.version"),

    /**
     * The Java info (ex: mixed mode)
     */
    JAVA_VM_INFO("java.vm.info"),

    /**
     * The JVM vendor (ex: Oracle Corporation)
     */
    JAVA_VM_VENDOR_NAME("java.vm.vendor"),

    /**
     * The JVM vendor URL (ex: http://java.oracle.com/)
     */
    JAVA_VM_VENDOR_URL("java.vendor.url"),

    /**
     * The JVM specification name (ex: Java Virtual Machine Specification)
     */
    JAVA_VM_SPEC_NAME("java.vm.specification.name"),

    /**
     * The JVM specification vendor (ex: Oracle Corporation)
     */
    JAVA_VM_SPEC_VENDOR("java.vm.specification.vendor"),

    /**
     * The JVM specification version (ex: 1.8)
     */
    JAVA_VM_SPEC_VERSION("java.vm.specification.version"),

    /**
     * The JVM specification name (ex: Java Platform API Specification)
     */
    JAVA_SPEC_NAME("java.specification.name"),

    /**
     * The JVM specification version (ex: 1.8)
     */
    JAVA_SPEC_VERSION("java.specification.version"),

    /**
     * The JVM specification vendor (ex: Oracle Corporation)
     */
    JAVA_SPEC_VENDOR("java.specification.vendor"),

    /**
     * The Java libraries path (ex: C:\Program
     * Files\Java\jdk1.8.0_121\bin;C:\WINDOWS\Sun\Java\bin;...)
     */
    JAVA_LIBRARY_PATH("java.library.path"),

    /**
     * The Java class path (ex:
     * /home/gilles/WORKSPACES/utils/utils-commons/target/classes;...)
     */
    JAVA_CLASS_PATH("java.class.path"),

    /**
     * The Java class version (ex for Java 8: 52.0)
     */
    JAVA_CLASS_VERSION("java.class.version"),

    /**
     * The Java endorsed directory (ex: C:\Program
     * Files\Java\jdk1.8.0_121\jre\lib\endorsed)
     */
    JAVA_ENDORSED_DIR("java.endorsed.dirs"),

    /**
     * The external directory (ex: C:\Program
     * Files\Java\jdk1.8.0_121\jre\lib\ext;C:\WINDOWS\Sun\Java\lib\ext)
     */
    JAVA_EXT_DIR("java.ext.dirs"),

    /**
     * The current architecture data model (ex: 64)
     */
    SUN_ARCH_DATA_MODEL("sun.arch.data.model"),

    /**
     * The Sun compiler (HotSpot 64-Bit Tiered Compilers)
     */
    SUN_MANAGEMENT_COMPILER("sun.management.compiler"),

    /**
     * The Sun boot class path (ex: C:\Program
     * Files\Java\jdk1.8.0_121\jre\lib\resources.jar;...)
     */
    SUN_BOOT_CLASS_PATH("sun.boot.class.path"),

    /**
     * The path of the current JVM (ex: C:\Program
     * Files\Java\jdk1.8.0_121\jre\bin)
     */
    SUN_BOOT_LIBRARY_PATH("sun.boot.library.path"),

    /**
     * The type of Java launcher (ex: SUN_STANDARD)
     */
    SUN_JAVA_LAUNCHER("sun.java.launcher"),

    /**
     * The current Java command (ex:
     * org.eclipse.jdt.internal.junit.runner.RemoteTestRunner -version 3 -port
     * 53045 -testLoaderClass
     * org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader -loaderpluginname
     * org.eclipse.jdt.junit4.runtime -classNames
     * fr.landel.utils.assertor.expect.ExpectTest)
     */
    SUN_JAVA_COMMAND("sun.java.command"),

    /**
     * The operating system patch level
     */
    SUN_OS_PATCH_LEVEL("sun.os.patch.level"),

    /**
     * Platform-specific, follows sun.cpu.endian (ex: UnicodeLittle)
     */
    SUN_IO_UNICODE_ENCODING("sun.io.unicode.encoding"),

    /**
     * Platform-specific (ex: little),
     */
    SUN_CPU_ENDIAN("sun.cpu.endian"),

    /**
     * (ex: windows)
     */
    SUN_DESKTOP("sun.desktop"),

    /**
     * (ex: amd64)
     */
    SUN_CPU_ISALIST("sun.cpu.isalist"),

    /**
     * User-defined path to fonts. Prefix the path value with "append:" or
     * "prepend:" to specify if it should be searched before or after the
     * JRE-defined font directories. Read-only property.
     */
    SUN_JAVA2D_FONTPATH("sun.java2d.fontpath"),

    /**
     * (ex: Oracle Java Micro Edition Embedded Client)
     */
    SUN_MISC_PRODUCT("sun.misc.product"),

    /**
     * The filename encoding (ex: Cp1252)
     */
    SUN_JNU_ENCODING("sun.jnu.encoding"),

    /**
     * The file content encoding (ex: UTF-8)
     */
    FILE_ENCODING("file.encoding"),

    /**
     * The operating system name (ex: Windows 10)
     */
    OS_NAME("os.name"),

    /**
     * The operating system version (ex: 10.0)
     */
    OS_VERSION("os.version"),

    /**
     * The operating system architecture (ex: amd64)
     */
    OS_ARCH("os.arch"),

    /**
     * The current Java graphics environment implementation (ex:
     * sun.awt.Win32GraphicsEnvironment)
     */
    JAVA_AWT_GRAPHICS_ENV("java.awt.graphicsenv"),

    /**
     * The current Java printerjob implementation (ex:
     * sun.awt.windows.WPrinterJob)
     */
    JAVA_AWT_PRINTERJOB("java.awt.printerjob"),

    /**
     * The current Java Toolkit implementation (ex: sun.awt.windows.WToolkit)
     */
    AWT_TOOLKIT("awt.toolkit"),

    /**
     * The path separator (ex: ';' on Windows or ':' on Unix)
     */
    PATH_SEPARATOR("path.separator"),

    /**
     * The line separator (ex: '\n' on Unix, '\r\n' on Windows and '\n\r' under
     * MacOS)
     */
    LINE_SEPARATOR("line.separator"),

    /**
     * The file separator (ex: '\' on Windows, '/' on Unix)
     */
    FILE_SEPARATOR("file.separator"),

    /**
     * The file encoding package (ex: sun.io)
     */
    FILE_ENCODING_PKG("file.encoding.pkg"),

    /**
     * The Java temporary directory (ex: C:\Users\Gilles\AppData\Local\Temp\)
     */
    JAVA_IO_TEMP_DIR("java.io.tmpdir"),

    /**
     * The user name (ex: Gilles)
     */
    USER_NAME("user.name"),

    /**
     * The user home (ex: C:\Users\Gilles)
     */
    USER_HOME("user.home"),

    /**
     * The user time zone (ex: Europe/Paris)
     */
    USER_TIMEZONE("user.timezone"),

    /**
     * The current user country (ex: FR)
     */
    USER_COUNTRY("user.country"),

    /**
     * The current user region (ex: US)
     */
    USER_REGION("user.region"),

    /**
     * The current user language (ex: fr)
     */
    USER_LANGUAGE("user.language"),

    /**
     * The user variant (more specific that country and language)
     */
    USER_VARIANT("user.variant"),

    /**
     * The current user script
     */
    USER_SCRIPT("user.script"),

    /**
     * The current user directory (ex:
     * /home/gilles/WORKSPACES/utils/utils-commons)
     */
    USER_DIR("user.dir"),

    /**
     * Display native window decorations (ex: false)
     */
    CDRAMS_DECORATIONS("cdcams.decorations"),

    /**
     * Top-level presentation mode class (No default)
     */
    CDRAMS_PRESENTATION("cdcams.presentation"),

    /**
     * Location of application repository (ex: CVMHOME/repository)
     */
    CDRAMS_REPOSITORY("cdcams.repository"),

    /**
     * Display extra diagnostic information (ex: false)
     */
    CDRAMS_VERBOSE("cdcams.verbose"),

    /**
     * For dual-stack builds only, a space-separated list of jar files that
     * provide a MIDP implementation
     */
    COM_SUN_MIDP_IMPLEMENTATION("com.sun.midp.implementation"),

    /**
     * Indicates the Java SE equivalent version for core class interfaces (ex:
     * 1.4.2)
     */
    COM_SUN_PACKAGE_SPEC_VERSION("com.sun.package.spec.version"),

    /**
     * Comma-delimited list of available communications ports
     */
    MICROEDITION_COMMPORTS("microedition.commports"),

    /**
     * Java ME configuration (ex: cdc)
     */
    MICROEDITION_CONFIGURATION("microedition.configuration"),

    /**
     * Unicode character encoding (ex: ISO_LATIN_1)
     */
    MICROEDITION_ENCODING("microedition.encoding"),

    /**
     * Host platform
     */
    MICROEDITION_HOSTNAME("microedition.hostname"),

    /**
     * System locale (ex: en-US)
     */
    MICROEDITION_LOCALE("microedition.locale"),

    /**
     * Java platform (ex: j2me)
     */
    MICROEDITION_PLATFORM("microedition.platform"),

    /**
     * Java ME profile
     */
    MICROEDITION_PROFILES("microedition.profiles"),

    /**
     * Disable the mechanism that allows the CDC Java run-time environment to
     * fallback to using /dev/urandom if /dev/random does not have enough
     * entropy to work properly. See the Oracle Java Micro Edition Embedded
     * Client Customization Guide for more information
     */
    MICROEDITION_SECURERANDOM_NOFALLBACK("microedition.securerandom.nofallback");

    private String key;
    private String value;

    SystemProperties(final String key) {
        this.key = key;
        this.value = System.getProperty(key, null);
    }

    /**
     * Get the key value
     * 
     * @return the key value
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get the property value
     * 
     * @return the property value
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return new StringBuilder(this.name()).append(":{\"").append(this.key).append("\":\"").append(this.value).append("\"}").toString();
    }
}

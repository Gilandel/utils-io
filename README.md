# utils-io
[![Build Status](https://api.travis-ci.org/Gilandel/utils-io.svg?branch=master)](https://travis-ci.org/Gilandel/utils-io/builds)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/8b9cbba23a594f95bdc50db076c7ea4d)](https://www.codacy.com/app/gilles/utils-io)
[![codecov.io](https://codecov.io/github/Gilandel/utils-io/coverage.svg?branch=master)](https://codecov.io/github/Gilandel/utils-io?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.landel.utils/utils-io/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.landel.utils/utils-io)
[![Javadocs](http://www.javadoc.io/badge/fr.landel.utils/utils-io.svg)](http://www.javadoc.io/doc/fr.landel.utils/utils-io)

[![Tokei LoC](https://tokei.rs/b1/github/Gilandel/utils-io)](https://github.com/Aaronepower/tokei)
[![Tokei NoFiles](https://tokei.rs/b1/github/Gilandel/utils-io?category=files)](https://github.com/Aaronepower/tokei)
[![Tokei LoComments](https://tokei.rs/b1/github/Gilandel/utils-io?category=comments)](https://github.com/Aaronepower/tokei)

[![codecov.io tree](https://codecov.io/gh/Gilandel/utils-io/branch/master/graphs/tree.svg)](https://codecov.io/gh/Gilandel/utils-io/branch/master)
[![codecov.io sunburst](https://codecov.io/gh/Gilandel/utils-io/branch/master/graphs/sunburst.svg)](https://codecov.io/gh/Gilandel/utils-io/branch/master)

Work progress:
![Code status](http://vbc3.com/script/progressbar.php?text=Code&progress=100)
![Test status](http://vbc3.com/script/progressbar.php?text=Test&progress=100)
![JavaDoc status](http://vbc3.com/script/progressbar.php?text=JavaDoc&progress=100)

```xml
<dependency>
	<groupId>fr.landel.utils</groupId>
	<artifactId>utils-io</artifactId>
	<version>1.0.5</version>
</dependency>
```

## Features:
- CloseableManager: A manager for closeable, to open X closeable and to close them (written before Java7 autocloseable)
- EncodingUtils: All encoding (BOM, String and Charset)
- FileCRC32Utils: To get CRC32 from a file
- FileSizeUtils: List all file sizes from octects to exabioctets
- FileSystemUtils: All methods to manage files and directories
- FileUtils: To read, write and compare files
- IOStreamUtils: To manage stream files (related to Input/OutputStream)
- SystemProperties: A list of system properties (like os.name, java.home,...)
- SystemUtils: To get operating system info

## Changelog
### 1.0.5 - 2018-07-02
- Misc: update dependencies
- Misc: remove classpath definition from JAR (Wildfly warning when some dependencies are in multiple versions and defined provided)

### 1.0.4 - 2018-02-15
- Fix: correct BOM values (EncodingUtils)

### 1.0.3 - 2017-12-17
- New: add properties loader functions (FileUtils#getProperties)

## License
Apache License, version 2.0
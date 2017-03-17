# utils-io
[![Build Status](https://api.travis-ci.org/Gilandel/utils-io.svg?branch=master)](https://travis-ci.org/Gilandel/utils-io)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/8b9cbba23a594f95bdc50db076c7ea4d)](https://www.codacy.com/app/gilles/utils-io)
[![Dependency Status](https://www.versioneye.com/user/projects/58b29b6f7b9e15003a17e544/badge.svg?style=flat)](https://www.versioneye.com/user/projects/58b29b6f7b9e15003a17e544)
[![codecov.io](https://codecov.io/github/Gilandel/utils-io/coverage.svg?branch=master)](https://codecov.io/github/Gilandel/utils-io?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.landel.utils/utils-io/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.landel.utils/utils-io)

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
	<version>1.0.0</version>
</dependency>
```

Features:
- CloseableManager: A manager for closeable, to open X closeable and to close them (written before Java7 autocloseable)
- EncodingUtils: All encoding (BOM, String and Charset)
- FileCRC32Utils: To get CRC32 from a file
- FileSizeUtils: List all file sizes from octects to exabioctets
- FileSystemUtils: All methods to manage files and directories
- FileUtils: To read, write and compare files
- StreamUtils: To manage stream files (related to Input/OutputStream)
- SystemProperties: A list of system properties (like os.name, java.home,...)
- SystemUtils: To get operating system info
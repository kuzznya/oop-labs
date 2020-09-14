# INI file parser

[Source code](src/main/java/com/github/kuzznya/iniparser)

## Usage

Parser usage is very simple - just create a new instance of parser &
call parse(File) method

```java
IniParser parser = new IniParser();
Configuration config = parser.parse(new File(filename));
```

INI file format can be checked by using `checkFileFormat(file)` method:

```java
parser.checkFileFormat(new File(filename));
```

After parsing configuration, the sections can be accessed by 
`section(name)` method  
The properties are accessed by 
`getInt(key)`, `getDouble(key)`, `getString(key)` methods, by
`tryGetInt(key)`, `tryGetDouble(key)`, `tryGetString(key)`, or by
`getProperty(key)` methods

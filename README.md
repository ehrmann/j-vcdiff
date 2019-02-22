[![CircleCI](https://circleci.com/gh/ehrmann/vcdiff-java/tree/master.svg?style=svg)](https://circleci.com/gh/ehrmann/vcdiff-java/tree/master)
[![Coverage Status](https://coveralls.io/repos/github/ehrmann/vcdiff-java/badge.svg?branch=master)](https://coveralls.io/github/ehrmann/vcdiff-java?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.davidehrmann.vcdiff/vcdiff-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.davidehrmann.vcdiff/vcdiff-parent)

# VCDiff-java

A Java port of Google's [open-vcdiff](https://github.com/google/open-vcdiff) vcdiff (RFC3284) implementation.
It's currently synced with [open-vcdiff 0.8.4](https://github.com/google/open-vcdiff/releases/tag/openvcdiff-0.8.4).

## Download
Maven:
```xml
<dependency>
    <groupId>com.davidehrmann.vcdiff</groupId>
    <artifactId>vcdiff-core</artifactId>
    <version>0.1.1</version>
</dependency>
```

Gradle:
```
compile 'com.davidehrmann.vcdiff:vcdiff-core:0.1.1'
```

## Usage
### Encoding (compressing)
```java
byte[] dictionary = ...;
byte[] uncompressedData = ...;
OutputStream compressedData = ...;

// OutputStream (like GZIPOutputStream) and stream-based encoders are
// also available from the builder.
VCDiffEncoder<OutputStream> encoder = VCDiffEncoderBuilder.builder()
    .withDictionary(dictionary)
    .buildSimple();

encoder.encode(uncompressedData, compressedData);
```
### Decoding (decompressing)
```java
byte[] dictionary = ...;
byte[] compressedData = ...;
OutputStream uncompressedData = ...;

// InputStream (like GZIPInputStream) and stream-based decoders are
// also available from the builder.
VCDiffDecoder decoder = VCDiffDecoderBuilder.builder().buildSimple();
decoder.decode(dictionary, compressedData, uncompressedData);
```

## Command line usage

The command line wrapper for java-vcdiff is generally compatble with the open-vcdiff implementation:

```sh
# Compress original with dictionary dict to compressed.vcdiff
java com.davidehrmann.vcdiff.VCDiffFileBasedCoder encode -dictionary dict -delta compressed.vcdiff -target original

# Decompress compressed.vcdiff with dictionary dict to decompressed
java com.davidehrmann.vcdiff.VCDiffFileBasedCoder decode -dictionary dict -delta compressed.vcdiff -target decompressed

# Usage details
java com.davidehrmann.vcdiff.VCDiffFileBasedCoder help
```

The command line tool is available in the Central Repository

Maven:
```xml
<dependency>
    <groupId>com.davidehrmann.vcdiff</groupId>
    <artifactId>vcdiff-cli</artifactId>
    <version>0.1.1</version>
</dependency>
```

Gradle:
```
compile 'com.davidehrmann.vcdiff:vcdiff-cli:0.1.1'
```

## Compatability
### xdelta3
xdelta3 has extensions that aren't currently supported by vcdiff-java: the application header, adler32 checksum,
and secondary compression. When encoding, passing `-S -A -n` to `xdelta3` will disable these features. 

## See also
* [Femtozip](https://github.com/gtoubassi/femtozip) (includes dictionary generator)
* [Diffable](https://web.archive.org/web/20120301201412/http://code.google.com/p/diffable/)
* [xdelta](http://xdelta.org/)

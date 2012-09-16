CEREBRA
=======

Build
-----

Dependencies: GNU Make, ANTLR v3 and Apache Commons CLI.

1. Get the "Complete ANTLR v3 Java binaries jar" from
<http://www.antlr.org/download.html> . *Do not* get the
ANTLRWorks+ANTLR distribution. Put the jar file in the
jars/ subdirectory.

2. Get the Apache Commons CLI library from
<http://commons.apache.org/cli/download_cli.cgi> . Get the
binaries (a .zip or .tar.gz archive), unpack them and
move ONLY the commons-cli-X.Y.jar to the jars/ sub-
directory. You may delete the remaining unpacked files.

3. Run `make`

4. Run `make dist` to create a standalone jar in the
distrib/ subdirectory

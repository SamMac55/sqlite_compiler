# Scripted Language for SQLite + Compiler

## Overview
In v0 of the scripted language + compiler, the goal was to create a high-level grammar that could be translated into SQL statements that are compatible specifically with SQLite. The grammar, liteQL.g4 allows for SQL select statements including single joins and the different clauses (group, order, where, having, limit). Further, it allows for the creation/deletion of tables and inserting/updating/deleting rows from tables.  v0 **does not** handle semantic processing, but includes some code that prepares to allow for semantics. This program only uses syntax-driven translation by taking in scripted statements and "pretty printing" them into SQL statements.

## How to build and run
- Ensure you download antlr4...
- get the class path to antlr4 and initialize it into CP using...
- generate the antlr-related files using antlr4 -no-listener -visitor liteQL.g4
- compile the java files related to Driver using javac -cp $CP *.java
- run java -cp $CP Driver < io/INPUTFILENAME.txt > OUTPUTFILENAME.txt
- see the results!

## The grammar

## The code 

## Next steps (v1 semantic processing)


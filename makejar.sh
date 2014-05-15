#! /bin/bash

echo -n "Give jar name (without .jar): "
read jname
jname="$jname.jar"
echo "$jname"
jar -cfm $jname manifest.txt CalendarProgram.class Res/*.class Res/*/*.class Res/*/*/*.class Res/*/*/*/*.class Res/*/*/*/*/*.class

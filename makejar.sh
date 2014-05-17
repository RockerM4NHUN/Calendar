#! /bin/bash

if [ -z $1 ]
then
	echo -n "Give jar name (without .jar): "
	read jname
	jname="$jname.jar"
else
	jname="$1.jar"
fi
jar -cfm $jname manifest.txt CalendarProgram.class Res/*.class Res/*/*.class Res/*/*/*.class Res/*/*/*/*.class Res/*/*/*/*/*.class

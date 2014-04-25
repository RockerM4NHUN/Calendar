#!/bin/bash

echo -n "Compile? "
read isrun
if [ -z "$isrun" ] || [ "$isrun" == "all" ]; then
	echo "Compiling..."
	echo "----------"

	if [ "$isrun" == "all" ]; then
		echo -e "\e[0;35mViews"
		javac Res/GUI/Views/*.java
		echo ""
		echo -e "\e[0;31mGUI"
		javac Res/GUI/*.java
		echo ""
		echo -e "\e[0;32mBin"
		javac Res/Bin/*.java
		echo ""
		echo -e "\e[0;33mData"
		javac Res/Data/*.java
		echo ""
		echo -e "\e[0;36mRes"
		javac Res/*.java
		echo ""
		echo -e "\e[0;34mMain"
	fi
	javac CalendarProgram.java
	echo -e "\e[0m"
else
	echo "Not compiling..."
fi

echo -n "Run? "
read isrun
if [ -z "$isrun" ]; then
	echo "Not running..."
else
	echo "Running..."
	echo "----------"
	java CalendarProgram $isrun
fi
exit 0

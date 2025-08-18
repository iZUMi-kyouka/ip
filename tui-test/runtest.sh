#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.txt" ]
then
    rm ACTUAL.txt
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -source 17 -target 17 -cp ../src/main/java -Xlint:none -d ../bin src/main/java/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.txt
java -classpath ../bin Rumi < ./tui-test/input.txt > ./tui-test/ACTUAL.txt

# compare the output to the expected output
diff ./tui-test/ACTUAL.txt ./tui-test/EXPECTED.txt
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
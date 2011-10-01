#!/bin/sh

for file in $*
do
    cat ${file} | grep -v ^\ \ 7 > temp
    mv temp ${file}
done

#!/bin/bash

PORT=$1

for i in `seq 1 5` ; do
    echo "start $i client"
    bin/./client $PORT 4000000000  15 >> output
    wait
done

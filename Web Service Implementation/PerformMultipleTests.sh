#!/bin/bash

PORT=$1

MB=8000000

for amount in `seq 50 50 500`
do
    filename="output${amount}MB"
    for i in `seq 1 20`
    do
        ./client  $PORT $MB $amount >> $filename
        wait
    done
done

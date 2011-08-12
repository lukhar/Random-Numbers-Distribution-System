#!/bin/bash

ADDRESS=$1
PORT=$2

MB=8000000

for amount in `seq 50 50 500`
do
    echo   "bin/./client $ADDRESS $PORT $MB $amount >> ../Output\ Data/unix\ socket/$filename"
    # echo "bin/./client $ADDRESS $PORT $MB $amount >> $filename"
    filename="output${amount}MB"
    for i in `seq 1 20`
    do
        bin/./client $ADDRESS $PORT $MB $amount >> ../Output\ Data/unix\ socket/$filename
        #bin/./client $ADDRESS $PORT $MB $amount >> $filename
        wait
    done
done

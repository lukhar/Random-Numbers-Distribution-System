#!/bin/bash

ADDRESS=$1

MB=8000000

for amount in `seq 50 50 500`
do
    filename="output${amount}MB"
    for i in `seq 1 20` 
    do
        echo $amount $i
        java -jar "target/rndsClient-1.0-SNAPSHOT-jar-with-dependencies.jar" $ADDRESS $MB $amount >> ../../Output\ Data/servlet\ implementation/$filename 
        wait
    done
done

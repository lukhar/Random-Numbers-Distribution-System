#!/bin/bash

ADDRESS=$1
PORT=$2

TSHARK="tshark -i eth0 -R '((tcp.flags == 0x02) || (tcp.flags == 0x12) ) || ((tcp.flags == 0x10) && (tcp.ack==1) && (tcp.len==0)) || (tcp.flags==0x11 || ((tcp.flags == 0x10) && (tcp.ack==10) && (tcp.len==0)))'"

MB=8000000

for amount in `seq 500 50 500`
do
    filename="log${amount}MB"
    path="../Tshark\ Logs/unix\ socket/"
    echo $path 
    echo $TSHARK 
    eval ${TSHARK} >> ../Tshark\ Logs/unix\ socket/$filename &
    echo tshark pid : $!
    echo wait for tshark intialize
    sleep 2
    for i in `seq 1 5`
    do
        bin/./client $ADDRESS $PORT $MB $amount &
        echo client pid : $!
        wait $!
        echo wait for closing socket
        sleep 3
        echo clean after tshark
        rm -rf /tmp/wireshark*
    done
    pkill tshark
done

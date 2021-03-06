#!/bin/sh

ADDRESS=$1
PORT=$2

TMP=tmpRawDataFile
MB=8000000

DUMP="tshark -i eth0 -f 'port ${PORT}' -w ${TMP}"
#DUMP="sudo tcpdump -i eth0 'tcp[tcpflags] & (tcp-syn|tcp-fin|tcp-ack) != 0' -w ${TMP}"
echo $DUMP

FILTER="tshark -r ${TMP} -B 50  -R '((tcp.flags == 0x02) || (tcp.flags == 0x12) ) || ((tcp.flags == 0x10) && (tcp.ack==1) && (tcp.len==0)) || (tcp.flags==0x11 || ((tcp.flags == 0x10) && (tcp.ack==10) && (tcp.len==0)))' "

for amount in `seq 50 50 500`
do
    filename="log_x${amount}MB"
    path="../Tshark\ Logs/unix\ socket/"
    
    for i in `seq 1 20`
    do
        eval ${DUMP} &
        echo wait for dump to initialize
        sleep 2

        echo start client
        bin/./client $ADDRESS $PORT $MB $amount &
        echo client pid : $!
        wait $!
        echo wait for closing socket
        sleep 2
        
        pkill tshark
        echo clean after dump
        rm -rf /tmp/wireshark*

        echo start filtering
        echo $FILTER 
        eval ${FILTER} >> ../Tshark\ Logs/unix\ socket/$filename &
        echo filter pid : $!
        wait $!

        pkill tshark
        echo clean after filtering 
        rm -rf /tmp/wireshark*
        rm $TMP
    done
done

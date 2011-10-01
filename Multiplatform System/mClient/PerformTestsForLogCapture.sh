#!/bin/sh

ADDRESS=$1
PORT=$2

TMP=tmpRawDataFile
MB=8000000

#DUMP="tshark -i eth0 -f 'port ${PORT}' -w ${TMP}"
DUMP="sudo tcpdump -i eth0 port ${PORT} -w ${TMP}"
echo $DUMP

# waiting for closing socket ACK field with value 13 not 10 why ????
FILTER="tshark -r ${TMP} -R '((tcp.flags == 0x02) || (tcp.flags == 0x12) ) || ((tcp.flags == 0x10) && (tcp.ack==1) && (tcp.len==0)) || (tcp.flags==0x11 || ((tcp.flags == 0x10) && (tcp.ack==10 || tcp.ack ==14 || tcp.ack==13) && (tcp.len==0)))' -z conv,ip -p"

for amount in `seq 500 50 500`
do
    log="log${amount}MB"
    filename="output${amount}MB"
    path="../../Tshark\ Logs/multiplatform\ system/"
    
    for i in `seq 1 20`
    do
        eval ${DUMP} &
        echo wait for dump to initialize
        sleep 2

        echo start client $ADDRESS $PORT $MB $amount
        java -jar "target/mClient-1.0-SNAPSHOT-jar-with-dependencies.jar" $ADDRESS $PORT $MB $amount >> ../../Output\ Data/multiplatform\ system/$filename &
        echo client pid : $!
        wait $!
        echo wait for closing socket
        sleep 2
        
        sudo pkill tcpdump
        echo clean after dump
        rm -rf /tmp/wireshark*

        echo start filtering
        echo $FILTER 
        eval ${FILTER} >> ../../Tshark\ Logs/multiplatform\ system/$log &
        echo filter pid : $!
        wait $!

        pkill tshark
        echo clean after filtering 
        rm -rf /tmp/wireshark*
        rm -f $TMP
    done
done

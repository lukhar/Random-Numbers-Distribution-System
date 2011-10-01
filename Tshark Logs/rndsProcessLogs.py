#!/usr/bin/env python
# encoding: utf-8
import sys
import os

class Packet:
    """ Abstarction for packet"""

    def __init__(self, number=0, time=0.0, info=""):
        """ constructs empty packet
        
        :number: TODO
        :time: TODO
        :direction: TODO
        :info: TODO
        """
        self.number = number
        self.time = time
        self.info = info

    def __str__(self):
        """ display contents """ 
        return "[ " + str(self.number) + " " + str(self.time) + " " + self.info + " ]"


class MeasureSample:
    """ Contains one row of measure data"""

    def __init__(self, data_size = 0.0, open_connection_time = 0.0,
            transfer_data_time = 0.0, close_data_time = 0.0):
        """ creates default instance based on parameters

        :open_connection_time: TODO
        :transfer_data_time: TODO
        :close_data_time: TODO
        """
        self.data_size = data_size
        self.open_connection_time = open_connection_time
        self.transfer_data_time = transfer_data_time
        self.close_data_time = close_data_time

    def __str__(self):
        """ display contents"""
        return str(self.data_size) + " " + str(self.open_connection_time) + " " + str(self.transfer_data_time) + " " + str(self.close_data_time)

    def __add__(self, measure_sample):
        return MeasureSample(self.data_size + measure_sample.data_size,
                self.open_connection_time + measure_sample.open_connection_time,
                self.transfer_data_time + measure_sample.transfer_data_time,
                self.close_data_time + measure_sample.close_data_time)    


def read_packets(file_name):
    """ read packets to list
    
    """
    packets = list()
    with open(file_name) as a_file:
        for a_line in a_file:
            if not a_line.isspace():
                (number, time, info) = a_line.split(None, 2)
                packets.append(
                        Packet(int(number), float(time), info.strip()))

    return packets

def calc_measure_samples(packets, data_size):
    """ calculate times for data transfer based on package data
    
    :packtes: TODO list of packtes
    
    :returns: list of calculated measure samples"""

    measure_samples = list()
    index = 0
    while index < len(packets):
        if index + 4 < len(packets):
            if packets[index + 3].number > 1:
                measure_sample = MeasureSample()
                measure_sample.data_size = data_size
                measure_sample.open_connection_time = packets[index + 2].time - packets[index].time 
                measure_sample.transfer_data_time = packets[index + 3].time - packets[index + 2].time
                measure_sample.close_data_time = packets[index + 4].time - packets[index + 3].time

                measure_samples.append(measure_sample)
            else:
                index -=2
        index += 5

    return measure_samples

def write_measurements(measure_samples, file_name):
    """ writes measuremnts to specified file
    
    :measure_samples: TODO
    :file_name: TODO 

    : returns nothing"""
    with open(file_name, mode='w') as a_file:
        for measure_sample in measure_samples:
            a_file.write(measure_sample.__str__() + "\n")
    

if __name__ == '__main__':
    for path in sys.argv[1:]:
         data_size = ''.join(i for i in path if i.isdigit())
         packets = read_packets(path)
         measure_samples = calc_measure_samples(packets, data_size)
         (dir_name, file_name)=os.path.split(path)
         write_measurements(measure_samples, dir_name + "/output" + str(data_size) + "MB")

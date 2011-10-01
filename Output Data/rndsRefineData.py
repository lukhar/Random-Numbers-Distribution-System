#!/usr/bin/python
# encoding: utf-8

import sys
import os


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
        return '{0} {1:.8f} {2:.8f} {3:.8f}'.format(self.data_size / 1000000, self.open_connection_time, self.transfer_data_time, self.close_data_time)

    def __add__(self, measure_sample):
        return MeasureSample(measure_sample.data_size,
                self.open_connection_time + measure_sample.open_connection_time,
                self.transfer_data_time + measure_sample.transfer_data_time,
                self.close_data_time + measure_sample.close_data_time)    


def read_data(file_name):
    """ read data from single file into list of
    MeasureSample objects

    """
    measure_samples = list()
    with open(file_name) as a_file:
        for a_line in a_file:
            if not a_line.isspace():
                (data_size, open_connection_time,
                        transfer_data_time, close_data_time) = a_line.split();
                
                measure_samples.append(MeasureSample(float(data_size), float(open_connection_time), 
                    float(transfer_data_time), float(close_data_time)))

    return measure_samples


def calculate_avreage(measure_samples):
    """ calculates avreage from list
    
    :measure_samples: TODO

    :returns: TODO
    """
    measure_sample = MeasureSample()
    for measure in measure_samples:
        measure_sample = measure_sample + measure
    
    return MeasureSample(measure_sample.data_size, 
            measure_sample.open_connection_time / len(measure_samples),
            measure_sample.transfer_data_time / len(measure_samples),
            measure_sample.close_data_time / len(measure_samples))

def write_measurements(measure_samples, file_name):
    """ writes measuremnts to specified file
    
    :measure_samples: TODO
    :file_name: TODO 

    : returns nothing"""
    with open(file_name, mode='w') as a_file:
        for measure_sample in measure_samples:
            a_file.write(measure_sample.__str__() + "\n")

if __name__ == '__main__':
    output = list()
    for path in sys.argv[1:]:
        (dir_name, file_name) = os.path.split(path)
        measure_samples = read_data(path)
        avreage = calculate_avreage(measure_samples)
        print(avreage)
        output.append(avreage)
        write_measurements(output, dir_name + "/avreageOutput") 

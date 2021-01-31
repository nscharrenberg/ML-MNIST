package com.nscharrenberg.algorithms;

public interface IAlgorithm {
    void train(String dataset, String configuration);
    void test(String model, String dataset);
    void predict(String model, String image);
}

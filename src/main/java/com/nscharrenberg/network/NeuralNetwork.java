package com.nscharrenberg.network;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

public class NeuralNetwork extends MultiLayerNetwork
{
    public NeuralNetwork(MultiLayerConfiguration conf) {
        super(conf);
    }

    public NeuralNetwork(String conf, INDArray params) {
        super(conf, params);
    }

    public NeuralNetwork(MultiLayerConfiguration conf, INDArray params) {
        super(conf, params);
    }

    static NeuralNetwork getModelByType (ModelType type)
    {
        switch (type)
        {
            case V1:
                return new NeuralNetwork(v1());
        }
        return null;
    }
    private static MultiLayerConfiguration v1()
    {
        return new MultiLayerConfiguration();
    }

    enum ModelType
    {
        V1,
        V2,
        V3
    }
}

package com.nscharrenberg.algorithms.mlp;

import com.nscharrenberg.algorithms.IAlgorithm;
import com.nscharrenberg.enums.FileType;
import com.nscharrenberg.enums.NetworkConfiguration;
import com.nscharrenberg.pipelines.FileParser;
import com.nscharrenberg.pipelines.Testing;
import com.nscharrenberg.pipelines.Training;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.IOException;

public class MultiLayerPerceptron implements IAlgorithm
{

    private MultiLayerNetwork network;
    private final NetworkConfiguration version = NetworkConfiguration.v4;


    @Override
    public void train(String dataset, String configuration)
    {


        //if loading
        new FileParser(configuration, FileType.ZIP);


        //else if creating ill need to check on the enum
        switch (version)
        {
            case v1 :
                this.network = ImageClassificationConfiguration.getModelV1();
                break;
            case v2:
                this.network = ImageClassificationConfiguration.getModelV2();
                break;
            case v3:
                this.network = ImageClassificationConfiguration.getModelV3();
                break;
            case v4:
                this.network = ImageClassificationConfiguration.getModelV4();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + version);
        }

        DataSetIterator iter =null ; //Training.getData();

        this.network.addListeners(new ScoreIterationListener(10));
        this.network.fit(iter);

    }

    @Override
    public void test(String directory, String dataset)
    {
        new FileParser(directory,FileType.ZIP);
        DataSetIterator t = null;
        Evaluation e = new Evaluation(1);
        while (t.hasNext())
        {
            INDArray d = this.network.output(t.next().getLabels());
            e.eval(t.next().getLabels(), d);
        }

    }

    @Override
    public void predict(String model, String image)
    {
        new FileParser(model,FileType.ZIP);
        DataSetIterator t = null;
        DataSet r = t.next();
        INDArray d = this.network.output(t);

        Evaluation e = new Evaluation(1);
        e.eval(r.getLabels(),d);

    }

    @Override
    public void saveModel(String directory) {
        try {
            ModelSerializer.writeModel( this.network, directory,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNetwork(MultiLayerNetwork model)
    {
        this.network = model;
    }

    public MultiLayerNetwork getNetwork()
    {
        return this.network;
    }




}

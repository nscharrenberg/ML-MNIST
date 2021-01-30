package com.nscharrenberg.repositories;

import com.nscharrenberg.interfaces.ITrainingRepository;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.autodiff.listeners.Listener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.slf4j.Logger;

public class TrainingRepository implements ITrainingRepository
{
    private static int batchSizeTraining;
    private static int nEpochs;
    private static int inputSize;
    private static int numClasses;
    private static int labelIndex;
    private static DataSet trainingData;
    private static DataSetIterator trainingIter;


    public static void initialiseDataSet()
    {

    }


    @Override
    public DataSet getDataset() {
        return null;
    }

    @Override
    public void setDataset(DataSet set) {

    }

    @Override
    public DataSetIterator getIterator() {
        return null;
    }

    @Override
    public void setIterator(DataSetIterator iter) {

    }

    @Override
    public Evaluation getEvaluation() {
        return null;
    }

    @Override
    public void newEvaluation(int numClasses) {

    }

    @Override
    public INDArray getOutput() {
        return null;
    }

    @Override
    public void evaluate(INDArray labels, INDArray targets) {

    }

    @Override
    public String getStats() {
        return null;
    }

    @Override
    public void saveModel() {

    }

    @Override
    public void setListener(Listener listener) {

    }

    @Override
    public Logger getFactoryLogger() {
        return null;
    }
}

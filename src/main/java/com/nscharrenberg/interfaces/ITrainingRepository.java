package com.nscharrenberg.interfaces;

import org.nd4j.autodiff.listeners.Listener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.slf4j.Logger;

import javax.xml.crypto.Data;
import java.net.http.WebSocket;

public interface ITrainingRepository
{

    DataSet getDataset();
    void setDataset(DataSet set);
    DataSetIterator getIterator();
    void setIterator(DataSetIterator iter);
    Evaluation getEvaluation();
    void newEvaluation(int numClasses);
    INDArray getOutput();
    void evaluate(INDArray labels,INDArray targets);
    String getStats();
    void saveModel();
    void setListener(Listener listener);
    Logger getFactoryLogger();


}

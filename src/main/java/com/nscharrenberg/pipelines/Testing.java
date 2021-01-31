package com.nscharrenberg.pipelines;

import com.nscharrenberg.algorithms.IAlgorithm;
import com.nscharrenberg.pipelines.utils.ImageML;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public class Testing implements Pipeline
{
    private IAlgorithm algorithm;
    private DataSet dataSet;
    private DataSetIterator dataIter;
    private Evaluation evaluation;

    public Testing(IAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public IAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(IAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void setDataIterator(DataSetIterator iter) {
        this.dataIter = iter;
    }

    @Override
    public DataSetIterator getIter() {
        return this.dataIter;
    }

    // TODO: Select Testing Dataset

    @Override
    public DataSet getData() {
        return this.dataSet;
    }

    @Override
    public void setData(DataSet set) {
        this.dataSet = set;
    }


    // TODO: Convert Test Dataset to their respective Gray Scale Binary file (ImageML method call)
    public void convert()
    {
        //if predicting from drawing
        new ImageML().decodeScreen();
        //if testing dataset

    }

    // TODO: Start Testing procedure (algorithm.test)
    public void test()
    {

    }

    // TODO: Output whatever useful data should be outputted.
}

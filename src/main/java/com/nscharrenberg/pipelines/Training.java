package com.nscharrenberg.pipelines;

import com.nscharrenberg.algorithms.IAlgorithm;
import com.nscharrenberg.enums.FileType;
import com.nscharrenberg.pipelines.utils.ImageML;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public class Training implements Pipeline
{
    private IAlgorithm algorithm;
    private int batchSize;
    private int numClasses;
    private long seed;
    private DataSet dataSet;
    private DataSetIterator dataIter;
    private Evaluation evaluation;


    public Training(IAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public IAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(IAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    // TODO: Select Training Dataset

    @Override
    public void setDataIterator(DataSetIterator iter)
    {
        this.dataIter = iter;
    }

    @Override
    public DataSetIterator getIter() {
        return this.dataIter;
    }

    @Override
    public DataSet getData() {
        return this.dataSet;
    }

    @Override
    public void setData(DataSet set) {
        this.dataSet = set;
    }

    // TODO: Convert Training Dataset to their respective Gray Scale Binary file (ImageML method call)

    public void convert()
    {
        new FileParser("directory", FileType.ZIP);
    }

    // TODO: Start Training procedure (algorithm.train)

    public void train()
    {
        algorithm.train("this.dataIter","model.configuration");
    }

    // TODO: Output whatever useful data should be outputted. (Probably a universal model)

    public void printEvaluation()
    {
        this.evaluation = new Evaluation(numClasses);
        System.out.println(this.evaluation.stats());
        System.out.println(this.evaluation.getConfusionMatrix());
    }


    // TODO: Maybe let the user store the model somewhere (or store it ourselves in the same directory).


}

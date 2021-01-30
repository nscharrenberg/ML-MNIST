package com.nscharrenberg.features;

import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public class DataSetMaker
{
    private static DataSetIterator iterator;
    private static DataSet dataset;

    public DataSetMaker()
    {

    }
    public static DataSetIterator getTrainingSet()
    {
        return iterator;
    }

    public static DataSet getDataset()
    {
        return dataset;
    }
}

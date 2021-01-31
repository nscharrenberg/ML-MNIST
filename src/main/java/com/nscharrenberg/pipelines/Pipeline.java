package com.nscharrenberg.pipelines;

import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public interface Pipeline
{
    void setDataIterator(DataSetIterator iter);

    DataSetIterator getIter ();

    DataSet getData();

    void setData(DataSet set);
}

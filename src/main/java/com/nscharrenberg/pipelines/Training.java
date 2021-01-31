package com.nscharrenberg.pipelines;

import com.nscharrenberg.algorithms.IAlgorithm;

public class Training {
    private IAlgorithm algorithm;

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
    public void setDataSet(String directory){}

    // TODO: Convert Training Dataset to their respective Gray Scale Binary file (ImageML method call)

    // TODO: Start Training procedure (algorithm.train)

    // TODO: Output whatever useful data should be outputted. (Probably a universal model)

    // TODO: Maybe let the user store the model somewhere (or store it ourselves in the same directory).
}

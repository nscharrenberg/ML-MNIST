package com.nscharrenberg.pipelines;

import com.nscharrenberg.algorithms.IAlgorithm;

public class Testing {
    private IAlgorithm algorithm;

    public Testing(IAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public IAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(IAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    // TODO: Select Test Dataset

    // TODO: Convert Test Dataset to their respective Gray Scale Binary file (ImageML method call)

    // TODO: Start Testing procedure (algorithm.test)

    // TODO: Output whatever useful data should be outputted.
}

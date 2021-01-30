package com.nscharrenberg.utils;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import java.io.File;
import java.io.IOException;

public class SavingModel
{
    public static void saveModel(File file, boolean saveUpdater, MultiLayerNetwork model)
    {
        try {
            ModelSerializer.writeModel(model,file, saveUpdater);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

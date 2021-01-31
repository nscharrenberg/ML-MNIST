package com.nscharrenberg.pipelines;

import com.nscharrenberg.enums.FileType;
import com.nscharrenberg.enums.NetworkConfiguration;
import com.nscharrenberg.pipelines.utils.ImageML;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;

public class FileParser
{

    private FileType type;
    private int batchSize;
    private int numClasses;

    public FileParser(String directory, FileType type)
    {
        if (type.equals(FileType.ZIP))
            new ModelLoader(directory);
        else if (type.equals(FileType.PNG))
            new ImageML().start();
    }

    public FileParser(String directory, FileType type, int batchSize, int numClasses)
    {
        this.batchSize = batchSize;
        this.numClasses = numClasses;

        if (type.equals(FileType.CSV))
            new CSVParser(directory);

    }



    class CSVParser
    {
        public CSVParser(String directory)
        {
            RecordReader recordReader = new CSVRecordReader();
            try {
                recordReader.initialize(new FileSplit(new File(directory)));
                DataSetIterator dataIter = new RecordReaderDataSetIterator(recordReader,batchSize,0,numClasses);
                //trainingInstance.setDataIter(dataIter);
                //testingIntance.setDataIter(dataIter);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    static class ModelLoader
    {
        File toLoad;
        public ModelLoader(String directory)
        {
            //TODO: concat to directory string the selected model

            toLoad = new File(directory);

        }
        MultiLayerNetwork getModel()
        {
            try {
                MultiLayerNetwork fromFile = ModelSerializer.restoreMultiLayerNetwork(toLoad);
                //trainingInstance.setNetwork(fromFile);
                //testingIntance.setNetwork(fromFile);
                return fromFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}

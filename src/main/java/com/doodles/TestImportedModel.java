package com.doodles;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.IOException;

public class TestImportedModel
{
    public static void main(String [] args) throws IOException, InterruptedException {
        File locationToSave = new File("trained_classifier_four_pics.zip");


        MultiLayerNetwork network = ModelSerializer.restoreMultiLayerNetwork(locationToSave);

        int labelIndex = 0;
        int numClasses = 4;
        int batchSizeTraining = 3596;
        DataSet testData ;//= readCSVDataset("animals_data_train.csv",batchSizeTraining,labelIndex,numClasses);
        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new File( "animals_data_train.csv")));
        DataSetIterator iterator = new RecordReaderDataSetIterator.Builder(rr,batchSizeTraining).classification(labelIndex,numClasses).build();
        testData = iterator.next();

        Evaluation eval = new Evaluation(4);
        INDArray output = network.output(testData.getFeatures());
        eval.eval(testData.getLabels(),output);
        System.out.println(eval.stats());
    }
}

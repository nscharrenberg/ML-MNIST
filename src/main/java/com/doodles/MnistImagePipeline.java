package com.doodles;

import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.split.FileSplit;
import org.datavec.api.split.InputStreamInputSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.datasets.iterator.impl.EmnistDataSetIterator;
import org.deeplearning4j.nn.conf.inputs.InvalidInputTypeException;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class MnistImagePipeline
{
    private static Logger log = LoggerFactory.getLogger(MnistImagePipeline.class);
    private static String modelType = "LeNet";


    public static void main(String [] args) throws Exception
    {
        int height = 28;
        int width = 28;

        int channels = 1;
        int seed = 123;
        Random randNumGen = new Random(seed);

        int batchSize= 10; //change

        int outputNum = 10;

        MnistImagePipeline app = new MnistImagePipeline();
        ClassLoader classLoader = app.getClass().getClassLoader();

        //file paths
        File trainData = new File (classLoader.getResource("training").getFile());
        File testData = new File (classLoader.getResource("testing").getFile());

        //define filesplit
        FileSplit trainSplit = new FileSplit(trainData, NativeImageLoader.ALLOWED_FORMATS,randNumGen);
        FileSplit testSplit = new FileSplit(testData,NativeImageLoader.ALLOWED_FORMATS,randNumGen);

        //make lables from directories
        ParentPathLabelGenerator labelMaker = new ParentPathLabelGenerator();
        ImageRecordReader reader = new ImageRecordReader(height,width,channels,labelMaker);

        //initialise reader and listener
        reader.initialize(trainSplit);
        //reader.setListeners(new LogRecordListener());

        DataSetIterator dataIter = new RecordReaderDataSetIterator(reader,batchSize,1,outputNum);

        //Scale pixel values to 0-1

        DataNormalization scaler = new ImagePreProcessingScaler(0,1);

        scaler.fit(dataIter);
        dataIter.setPreProcessor(scaler);

        /*
        MultiLayerNetwork network;
        switch (modelType) {
            case "LeNet":
                network = ImageClassificationConfiguration.LeNet();
                break;
            case "AlexNet":
                network = ImageClassificationConfiguration.AlexNet();
                break;
            case "VGGNet":
                network = ImageClassificationConfiguration.VGGNet(); //TODO: fix VGGNet implementation
                break;
            case "mnist":
                network = ImageClassificationConfiguration.mnistConfig();
                break;
            default:
                network = ImageClassificationConfiguration.build();
        }
        network.init();


        network.init();
        network.setListeners(new ScoreIterationListener(20));


        System.out.println("************Training Model*************");
        for ( int n = 0; n < 2; n++) {
            network.fit(dataIter);
        }

         */

        ComputationGraph network = ImageClassificationConfiguration.getModel();
        network.init();
        System.out.println(network.conf().toJson());
        network.addListeners(new ScoreIterationListener());
        //create evaluation with 10 classes
        Evaluation eval = new Evaluation(outputNum);
        reader.reset();
        reader.initialize(new FileSplit(testData));
        DataSetIterator testIter = new RecordReaderDataSetIterator(reader,batchSize,1, outputNum);
        scaler.fit(testIter);
        testIter.setPreProcessor(scaler);

        testIter = new EmnistDataSetIterator(EmnistDataSetIterator.Set.DIGITS, 1024, false, 12345);
        int i = 0;
        //evaluate the model on the test set
        System.out.println("************Evaluating Model*************");

        while(testIter.hasNext())
        {
            DataSet t = testIter.next();
            System.out.println(t);

            System.out.println(Arrays.toString(network.doEvaluation(dataIter, eval)));
           // System.out.println(Arrays.toString(features));
           // eval.eval(t.getLabels(), features[0]);
            if (i%100 == 0)
                System.out.printf("Evaluation on test data - [Epoch %d] [Accuracy: %.3f, Precision: %.3f, Recall: %.3f, F1: %.3f] %n",
                    i, eval.accuracy(), eval.precision(), eval.recall(), eval.f1());
            i++;
        }


        System.out.println(eval.stats());


        System.out.println("Finished...");
        //SAVE THE MODEL

        File locationToSave = new File("trained_digits_classifier_vLeNet_uNestrovs.zip");

       // SavingModel.saveModel(locationToSave, false, network);
      //  System.out.println("************Model Saved*************");

    }
}

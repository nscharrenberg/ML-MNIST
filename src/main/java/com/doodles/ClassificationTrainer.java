package com.doodles;
import com.sun.scenario.effect.Blend;
import org.datavec.api.conf.Configuration;
import org.datavec.api.io.WritableComparator;
import org.datavec.api.io.WritableConverter;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.split.InputSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.*;
import org.deeplearning4j.nn.conf.distribution.Distribution;
import org.deeplearning4j.nn.conf.distribution.Distributions;
import org.deeplearning4j.nn.conf.distribution.NormalDistribution;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.memory.enums.LearningPolicy;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.random.impl.GaussianDistribution;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.MultipleEpochsIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.learning.config.RmsProp;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.learning.regularization.Regularization;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.schedule.CycleSchedule;
import org.nd4j.linalg.schedule.ScheduleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassificationTrainer
{
    private static Logger log  = LoggerFactory.getLogger(ClassificationTrainer.class);


    public static void main (String[] args) throws Exception
    {


        int labelIndex = 0;
        int numClasses = 4;
        int batchSizeTraining = 3500;
        DataSet trainingData ;//= readCSVDataset("animals_data_train.csv",batchSizeTraining,labelIndex,numClasses);
        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new File( "animals_data_train.csv")));
        DataSetIterator iterator = new RecordReaderDataSetIterator.Builder(rr,batchSizeTraining).classification(labelIndex,numClasses).build();
        trainingData = iterator.next();




        MultiLayerNetwork network ;
        network = configure();

        System.out.println(network.conf().toJson());

        network.init();
        network.setListeners(new ScoreIterationListener(10));
        network.setEpochCount(1500);
        for (int i = 0; i< network.getEpochCount(); i++)
            network.fit(trainingData);

        log.info("Evaluate model::::");
        rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new File("animals_data_train.csv")));
        iterator = new RecordReaderDataSetIterator(rr,batchSizeTraining+50,0,4);
        DataSet testData = iterator.next();
        rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new File("animals_data_train.csv")));
        iterator = new RecordReaderDataSetIterator(rr,batchSizeTraining+50,0,4);
        trainingData = iterator.next();
        Evaluation eval = new Evaluation(4);
        INDArray output = network.output(testData.getFeatures());
        eval.eval(trainingData.getLabels(),output);
        log.info(eval.stats());
        System.out.println(eval.stats());


        //time to save it


        File locationToSave = new File("trained_classifier_four_pics.zip");

        new SavingModel(locationToSave, false, network);

    }
    static class SavingModel
    {
        public SavingModel(File file, boolean updateSaver, MultiLayerNetwork model)
        {
            try
            {
                ModelSerializer.writeModel(model,file,updateSaver);

            }catch (Exception e )
            {
                e.printStackTrace();
            }
        }
    }
    public static MultiLayerNetwork configure()
    {
        double nonZeroBias = 1;
        double dropOut = 0.5;


        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(1234)
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.TANH)
                .updater(new Nesterovs.Builder().momentum(0.9).learningRate(1e-2).build())
                .gradientNormalization(GradientNormalization.RenormalizeL2PerLayer) // normalize to prevent vanishing or exploding gradients
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .miniBatch(false)
                .list()
                .layer(0,new DenseLayer.Builder().nIn(784).nOut(256).name("input").activation(Activation.TANH).build())
                .layer(1,new DenseLayer.Builder().nIn(256).nOut(156).name("first hidden").activation(Activation.SIGMOID).build())
                .layer(2,new DenseLayer.Builder().nIn(156).nOut(64).name("second hidden").activation(Activation.HARDSIGMOID).build())
                .layer(3,new DenseLayer.Builder().nIn(64).nOut(16).name("third hidden").activation(Activation.SOFTMAX).build())
                .layer(4, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .name("output")
                        .nOut(4)
                        .activation(Activation.SOFTMAX)
                        .build())
                .backpropType(BackpropType.Standard)
                .setInputType(InputType.feedForward(784))
                .build();
        return new MultiLayerNetwork(conf);
    }

    public static MultiLayerNetwork configure2 ()
    {

        log.info("Build model....");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(1234)
                .activation(Activation.TANH)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-6)
                .list()
                .layer(new DenseLayer.Builder().nIn(784).nOut(500).build())
                .layer(new DenseLayer.Builder().nIn(500).nOut(500).build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX).nIn(500).nOut(4).build())
                .build();

        return new MultiLayerNetwork(conf);

    }



}

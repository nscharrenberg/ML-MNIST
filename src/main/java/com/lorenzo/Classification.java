package com.lorenzo;
import org.bytedeco.javacpp.tools.Logger;
import org.bytedeco.javacpp.tools.Slf4jLogger;
import org.slf4j.LoggerFactory;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.records.writer.RecordWriter;
import org.datavec.api.records.writer.impl.csv.CSVRecordWriter;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.datasets.iterator.ReconstructionDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.schedule.InverseSchedule;


import java.io.File;

public class Classification
{
    Logger logger = new Slf4jLogger(getClass());
    public static void main (String [] args) throws Exception
    {
        int seed = 123;
        double learningRate = 0.1;
        int batchSize = 50;
        int nEpochs = 200;
        int numInputs = 2;
        int numOutputs = 2;
        int numHiddenNodes = 20;

        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new File("linear_data_train.csv")));
        DataSetIterator trainIter = new RecordReaderDataSetIterator(rr, batchSize, 0, 2);
        //load the test


        RecordReader rrTest = new CSVRecordReader();
        rrTest.initialize(new FileSplit(new File("linear_data_eval.csv")));
        DataSetIterator testIter = new RecordReaderDataSetIterator(rrTest, batchSize, 0, 2);

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Nesterovs.Builder().learningRate(learningRate).momentum(0.9).build())
                .list()
                .layer(0, new DenseLayer
                        .Builder()
                        .nIn(numInputs)
                        .nOut(numHiddenNodes)
                        .weightInit(WeightInit.XAVIER)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new OutputLayer
                        .Builder()
                        .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .weightInit(WeightInit.XAVIER)
                        .activation(Activation.SOFTMAX)
                        .weightInit(WeightInit.XAVIER)
                        .nIn(numHiddenNodes)
                        .nOut(numOutputs)
                        .build())

                .backpropType(BackpropType.Standard)
                .build();


        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));
        for (int i = 0; i < nEpochs; i++) {
            model.fit(trainIter);
        }

        System.out.println("Evaluate model .......... >>");
        Evaluation eval = new Evaluation(numOutputs);
        while (testIter.hasNext()) {
            DataSet t = testIter.next();
            INDArray features = t.getFeatures();
            features.toDoubleMatrix();
            INDArray lables = t.getLabels();
            INDArray predicted = model.output(features, false);
            eval.eval(lables, predicted);
        }
        System.out.println(conf.toJson());

        System.out.println(eval.stats());
    }

}

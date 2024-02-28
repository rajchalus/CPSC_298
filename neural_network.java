import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.datasets.iterator.DataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;

public class SimpleNeuralNetwork {
    public static void main(String[] args) {
        int numInputNodes = 2;
        int numHiddenNodes = 3;
        int numOutputNodes = 1;
        int numEpochs = 1000;
        double learningRate = 0.1;

        // Create a neural network configuration
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(123)
            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            .updater(org.deeplearning4j.nn.conf.Updater.SGD)
            .learningRate(learningRate)
            .list()
            .layer(0, new DenseLayer.Builder()
                .nIn(numInputNodes)
                .nOut(numHiddenNodes)
                .activation(Activation.RELU)
                .build())
            .layer(1, new DenseLayer.Builder()
                .nIn(numHiddenNodes)
                .nOut(numOutputNodes)
                .activation(Activation.SIGMOID)
                .build())
            .backpropType(BackpropType.Standard)
            .build();

        // Create the neural network
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(100));

        // Create some training data
        INDArray input = Nd4j.create(new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}});
        INDArray output = Nd4j.create(new double[][]{{0}, {1}, {1}, {0}});
        DataSetIterator dataSetIterator = new ListDataSetIterator<>(new org.nd4j.linalg.dataset.DataSet(input, output).asList(), 1);

        // Train the neural network
        for (int i = 0; i < numEpochs; i++) {
            dataSetIterator.reset();
            model.fit(dataSetIterator);
        }

        // Test the neural network
        INDArray testInput = Nd4j.create(new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}});
        INDArray predictedOutput = model.output(testInput, false);
        System.out.println("Predicted output:");
        System.out.println(predictedOutput);
    }
}

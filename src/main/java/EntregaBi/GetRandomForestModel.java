package EntregaBi;

import java.io.FileWriter;
import java.util.Random;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;


    /*
        Train eta Dev fitxategiak sartuta, OneR modeloarekin kalitatearen estimazioa egiten du

     */
/**
 * A class to generate and evaluate a RandomForest classifier and store both model and evaluation metrics.  <br>
 *
 * This class reads an '.arff' and uses it to build a RandomForest classifier.
 * The classifier is evaluated using 3 different models: Overfitting, Hold Out and CrossValidation.
 * <br><br>
 * Arguments needed for main method: <br>
 * 1 - Path to the existing '.arff' file <br>
 * 2 - Path to the output file where the model ('.model' format) will be saved.
 * 3 - Path to the output '.txt' file where the evaluation metrics will be written.
 * <br> <br>
 * */

public class GetRandomForestModel {

    public static void main(String[] args) throws Exception {
        /**
         *
         * @param args list of arguments as an array of strings
         * @throws Exception
         */

        if(args.length != 3) {
            System.out.println("Ez duzu arguments atala behar bezala bete!");
            System.out.println("Erabilera:");
            System.out.println("java -jar GetRandomForestModel.jar train.arff modeloa.model emaitzak.txt ");            //Programa honek 3 parametro ezberdin beharko ditu
            //1. parametroa train multzorako erabiliko den .arff fitxategia
            //2. parametroa modeloa gordetzeko erabiliko dugun helbidea, .model izan behar da
            //3. parametroa .txt fitxategi bat izango da, hemen gure modeloaren kalitatearen estimazioa gordeko dugu

        }
        else{

            DataSource source=null;
            try {
                source = new DataSource(args[0]);
            } catch (Exception e) {
                System.out.println("train multzoa sortzeko sartu duzun arff-aren helbidea okerra da.");
            }
            Instances train= source.getDataSet();
            train.setClassIndex(train.numAttributes()-1); //tipico de cargar el .arff


            RandomForest randomF= new RandomForest();
            randomF.setNumExecutionSlots(Runtime.getRuntime().availableProcessors()); //parametro ekorketatik ateratako datuak
            randomF.setNumFeatures(200);                                              //RandomForest_ekorketa.txt fitxategiak daude emaitzak
            randomF.setNumIterations(26);
            randomF.setBagSizePercent(16);
            randomF.setMaxDepth(50);
            randomF.buildClassifier(train);

            weka.core.SerializationHelper.write(args[1], randomF);


            FileWriter fw = new FileWriter(args[2]);


            //1- Ebaluazioa normala train eta dev fitxategiekin
            Evaluation evalTrainDev = new Evaluation(train);
            evalTrainDev.evaluateModel(randomF, train);
            //Fitxategian gorde kalitatearen estimazioa
            fw.write("\n=============================================================\n");
            fw.write("EBALUAZIO EZ ZINTZOA:\n");
            fw.write(evalTrainDev.toSummaryString());
            fw.write("\n");
            fw.write(evalTrainDev.toClassDetailsString());
            fw.write("\n");
            fw.write(evalTrainDev.toMatrixString());
            fw.write("\n");

            //2- Cross Validation
            Evaluation evaluatorCross = new Evaluation(train);
            randomF = new RandomForest();
            randomF.setNumExecutionSlots(Runtime.getRuntime().availableProcessors()); //parametro ekorketatik ateratako datuak
            randomF.setNumFeatures(200);                                              //RandomForest_ekorketa.txt fitxategiak daude emaitzak
            randomF.setNumIterations(26);
            randomF.setBagSizePercent(16);
            randomF.setMaxDepth(50);
            randomF.buildClassifier(train);
            evaluatorCross.crossValidateModel(randomF, train, 10, new Random(1));
            //Fitxategian gorde kalitatearen estimazioa
            fw.write("\n=============================================================\n");
            fw.write("KFCV-REKIN EBALUATUZ (TRAIN MULTZOAN SOILIK):\n");
            fw.write(evaluatorCross.toSummaryString());
            fw.write("\n");
            fw.write(evaluatorCross.toClassDetailsString());
            fw.write("\n");
            fw.write(evaluatorCross.toMatrixString());
            fw.write("\n");

            //3- HoldOut



            Evaluation evaluatorSplit = new Evaluation(train);


            for(int i = 0; i<100; i++){
                Randomize filter = new Randomize();
                filter.setRandomSeed(0);
                filter.setInputFormat(train);
                train = Filter.useFilter(train, filter);

                //train multzoa
                RemovePercentage rmpct = new RemovePercentage();
                rmpct.setInputFormat(train);
                rmpct.setInvertSelection(false);
                rmpct.setPercentage(30);
                Instances train1 = Filter.useFilter(train, rmpct);

                randomF= new RandomForest();
                randomF.setNumExecutionSlots(Runtime.getRuntime().availableProcessors()); //parametro ekorketatik ateratako datuak
                randomF.setNumFeatures(200);                                              //RandomForest_ekorketa.txt fitxategiak daude emaitzak
                randomF.setNumIterations(26);
                randomF.setBagSizePercent(16);
                randomF.setMaxDepth(50);
                randomF.buildClassifier(train);

                //test multzoa
                RemovePercentage rmpct2 = new RemovePercentage();
                rmpct2.setInputFormat(train);
                rmpct2.setInvertSelection(true);
                rmpct2.setPercentage(30);
                Instances test1 = Filter.useFilter(train, rmpct2);

                //evaluation
                evaluatorSplit.evaluateModel(randomF, test1);
            }

            //Fitxategian gorde kalitatearen estimazioa
            fw.write("\n=============================================================\n");
            fw.write("HOLD OUT-EKIN (%70) EBALUATUZ:\n");
            fw.write(evaluatorSplit.toSummaryString());
            fw.write("\n");
            fw.write(evaluatorSplit.toClassDetailsString());
            fw.write("\n");
            fw.write(evaluatorSplit.toMatrixString());
            fw.write("\n");


            fw.flush();
            fw.close();
        }

    }

}

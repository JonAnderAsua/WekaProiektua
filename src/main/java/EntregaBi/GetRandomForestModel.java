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

public class GetRandomForestModel {

    public static void main(String[] args) throws Exception {

        if(args.length != 4) {
            System.out.println("Ez duzu arguments atala behar bezala bete!");
            //Programa honek 3 parametro ezberdin beharko ditu
            //1. parametroa train multzorako erabiliko den .arff fitxategia
            //2. parametroa dev multzorako erabiliko den .arff fitxategia
            //3. parametroa modeloa gordetzeko erabiliko dugun helbidea, .model izan behar da
            //4. parametroa .txt fitxategi bat izango da, hemen gure modeloaren kalitatearen estimazioa gordeko dugu

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

            weka.core.SerializationHelper.write(args[2], randomF);


            FileWriter fw = new FileWriter(args[3]);

            DataSource devSource=null;
            try {
                devSource = new DataSource(args[1]);
            } catch (Exception e) {
                System.out.println("dev multzoa sortzeko sartu duzun arff-aren helbidea okerra da.");
            }
            Instances dev= devSource.getDataSet();
            dev.setClassIndex(dev.numAttributes()-1);


            //1- Ebaluazioa normala train eta dev fitxategiekin
            Evaluation evalTrainDev = new Evaluation(dev);
            evalTrainDev.evaluateModel(randomF, dev);
            //Fitxategian gorde kalitatearen estimazioa
            fw.write("\n=============================================================\n");
            fw.write("EBALUAZIO NORMALA, TRAIN ENTRENAMENDU MULTZOA ETA DEV PROBA MULTZOA:\n");
            fw.write(evalTrainDev.toSummaryString());
            fw.write("\n");
            fw.write(evalTrainDev.toClassDetailsString());
            fw.write("\n");
            fw.write(evalTrainDev.toMatrixString());
            fw.write("\n");
            System.out.println("Ebaluazio normala eginda");

            //2- Cross Validation
            Evaluation evaluatorCross = new Evaluation(dev);
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
            System.out.println("10fcv eginda");

            //3- HoldOut
            Randomize filter = new Randomize();
            filter.setRandomSeed(0);
            filter.setInputFormat(train);
            train = Filter.useFilter(train, filter);
            System.out.println("randomize eginda");

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
            Evaluation evaluatorSplit = new Evaluation(train1);
            evaluatorSplit.evaluateModel(randomF, test1);
            //Fitxategian gorde kalitatearen estimazioa
            fw.write("\n=============================================================\n");
            fw.write("HOLD OUT-EKIN (%70) EBALUATUZ (TRAIN MULTZOKO INSTANTZIEKIN SOILIK):\n");
            fw.write(evaluatorSplit.toSummaryString());
            fw.write("\n");
            fw.write(evaluatorSplit.toClassDetailsString());
            fw.write("\n");
            fw.write(evaluatorSplit.toMatrixString());
            fw.write("\n");
            System.out.println("hold out eginda");


            fw.flush();
            fw.close();
        }

    }

}

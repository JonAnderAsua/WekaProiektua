package EntregaBi;

import java.io.FileWriter;
import java.util.Random;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.rules.OneR;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;


    /*
        Train eta Dev fitxategiak sartuta, OneR modeloarekin kalitatearen estimazioa egiten du

     */

public class GetOneRModel {

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

            //1- Fitxategia
            DataSource source=null;
            try {
                source = new DataSource(args[0]);
            } catch (Exception e) {
                System.out.println("train multzoa sortzeko sartu duzun arff-aren helbidea okerra da.");
            }
            Instances train= source.getDataSet();

            //dev multzoaren klasea definitu
            train.setClassIndex(0);

            //2-Baseline modeloa sortu - OneR
            //Classifier
            OneR oner= new OneR();
            oner.buildClassifier(train);

            //3- Modeloa gorde
            weka.core.SerializationHelper.write(args[2], oner);


            //4- fitxategian datuak idatzi
            FileWriter fw = new FileWriter(args[3]);

            DataSource devSource=null;
            try {
                devSource = new DataSource(args[1]);
            } catch (Exception e) {
                System.out.println("dev multzoa sortzeko sartu duzun arff-aren helbidea okerra da.");
            }
            Instances dev= devSource.getDataSet();
            dev.setClassIndex(0);
            //1- Ez zintzoa
            Evaluation evaluatorEzZintzoa = new Evaluation(dev);
            evaluatorEzZintzoa.evaluateModel(oner, dev);
            //Fitxategian gorde kalitatearen estimazioa
            fw.write("\n");
            fw.write("=============================================================");
            fw.write("\n");
            fw.write("EBALUAZIO NORMALA, TRAIN ENTRENAMENDU MULTZOA ETA DEV PROBA MULTZOA:");
            fw.write("\n");
            fw.write(evaluatorEzZintzoa.toSummaryString());
            fw.write("\n");
            fw.write(evaluatorEzZintzoa.toClassDetailsString());
            fw.write("\n");
            fw.write(evaluatorEzZintzoa.toMatrixString());
            fw.write("\n");

            //2- Cross Validation
            Evaluation evaluatorCross = new Evaluation(dev);
            oner = new OneR();
            evaluatorCross.crossValidateModel(oner, train, 10, new Random(1));
            //Fitxategian gorde kalitatearen estimazioa
            fw.write("\n");
            fw.write("=============================================================");
            fw.write("\n");
            fw.write("KFCV-REKIN EBALUATUZ:");
            fw.write("\n");
            fw.write(evaluatorCross.toSummaryString());
            fw.write("\n");
            fw.write(evaluatorCross.toClassDetailsString());
            fw.write("\n");
            fw.write(evaluatorCross.toMatrixString());
            fw.write("\n");

            //3- HoldOut

            //datuak nahastu
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

            oner= new OneR();
            oner.buildClassifier(train1);

            //test multzoa
            RemovePercentage rmpct2 = new RemovePercentage();
            rmpct2.setInputFormat(train);
            rmpct2.setInvertSelection(true);
            rmpct2.setPercentage(30);
            Instances test1 = Filter.useFilter(train, rmpct2);

            //evaluation
            Evaluation evaluatorSplit = new Evaluation(train1);
            evaluatorSplit.evaluateModel(oner, test1);
            //Fitxategian gorde kalitatearen estimazioa
            fw.write("\n");
            fw.write("=============================================================");
            fw.write("\n");
            fw.write("HOLD OUT-EKIN (%70) EBALUATUZ (TRAIN MULTZOKO INSTANTZIEKIN SOILIK):");
            fw.write("\n");
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

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
            System.out.println("Erabilera:");
            System.out.println("java -jar GetOneRModel.jar train.arff modeloa.model emaitzak.txt ");            //Programa honek 3 parametro ezberdin beharko ditu
            //1. parametroa train multzorako erabiliko den .arff fitxategia
            //2. parametroa dev multzorako erabiliko den .arff fitxategia
            //3. parametroa modeloa gordetzeko erabiliko dugun helbidea, .model izan behar da
            //4. parametroa .txt fitxategi bat izango da, hemen gure modeloaren kalitatearen estimazioa gordeko dugu

        }
        else{ //el metodo es practicamente una copia de GetRandomForestModel pero mas simple


            DataSource source=null;
            try {
                source = new DataSource(args[0]);
            } catch (Exception e) {
                System.out.println("train multzoa sortzeko sartu duzun arff-aren helbidea okerra da.");
            }
            Instances train= source.getDataSet();


            train.setClassIndex(train.numAttributes()-1);
            OneR oner= new OneR();
            oner.buildClassifier(train);

            weka.core.SerializationHelper.write(args[1], oner);
            FileWriter fw = new FileWriter(args[2]);



            //1- Ebaluazio normala
            Evaluation evaluatorEzZintzoa = new Evaluation(train);
            evaluatorEzZintzoa.evaluateModel(oner, train);
            //Fitxategian gorde kalitatearen estimazioa
            fw.write("\n=============================================================\n");
            fw.write("EBALUAZIO EZ ZINTZOA:\n");
            fw.write(evaluatorEzZintzoa.toSummaryString());
            fw.write("\n");
            fw.write(evaluatorEzZintzoa.toClassDetailsString());
            fw.write("\n");
            fw.write(evaluatorEzZintzoa.toMatrixString());
            fw.write("\n");

            //2- Cross Validation
            Evaluation evaluatorCross = new Evaluation(train);
            oner = new OneR();
            evaluatorCross.crossValidateModel(oner, train, 10, new Random(1));
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

                oner= new OneR();
                oner.buildClassifier(train1);

                //test multzoa
                RemovePercentage rmpct2 = new RemovePercentage();
                rmpct2.setInputFormat(train);
                rmpct2.setInvertSelection(true);
                rmpct2.setPercentage(30);
                Instances test1 = Filter.useFilter(train, rmpct2);

                evaluatorSplit = new Evaluation(train1);
                evaluatorSplit.evaluateModel(oner, test1);
            }
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

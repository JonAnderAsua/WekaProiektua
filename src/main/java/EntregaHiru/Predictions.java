package EntregaHiru;


import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.RandomForest;
import weka.core.*;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;
import weka.filters.unsupervised.attribute.Reorder;

import java.io.File;
import java.io.FileWriter;

/**
 * Class for predicting a raw text or a set of texts inside a '.arff' file using a RandomForest classifier.  <br>
 *
 * This class loads a RandomForest classifier and it uses it to predict the class of a simple raw text or a set of texts in a '.arff' file. <br>
 * <br>
 * Arguments needed for main method: <br>
 * 1 - RandomForest classifier '.model' file directory. <br>
 * 2 - Raw text or path to a set of texts in a '.arff' file. <br>
 * 3 - Path desired to store the output file (where predictions are written). <br>
 * @version 2021/4/16 <br> <br>
 * */




public class Predictions {


    /**
     *
     * @param args list of arguments as an array of strings
     * @throws Exception
     */

    public static void main (String[] args) throws Exception {
        /*
            arg 1 modeloa
            arg 2 .arff fitxategia edo esaldi bat
            arg 3 = irteera fitxategia

         */


        if (args.length== 4){
            RandomForest randomF = (RandomForest) weka.core.SerializationHelper.read(args[0]);
            File file;
            FileWriter fw = new FileWriter(new File(args[2]));
            Instances data;
            Instances dataClear;
            if(args[1].contains(".arff")){ //bateragarria ez den .arff bat kargatu, bateragarria den fitxategi bat kargatu ahal dugu, baina orduan ez genuke jakingo zein zen hasierako mezua
                ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(args[1]);
                data = dataSource.getDataSet();
                data.setClassIndex(data.numAttributes() - 1);
                dataClear = data;
                FixedDictionaryStringToWordVector filtroa = new FixedDictionaryStringToWordVector();
                filtroa.setDictionaryFile(new File(args[3]));//esto habria que cambiarlo por un argumento
                filtroa.setInputFormat(data);


                data= Filter.useFilter(data, filtroa);
                Reorder reorder = new Reorder();
                reorder.setAttributeIndices("2-" + data.numAttributes() + ",1");
                reorder.setInputFormat(data);
                data = Filter.useFilter(data, reorder);
                data.setClassIndex(data.numAttributes()-1);
                Evaluation eval = new Evaluation(data);
                eval.evaluateModel(randomF, data);
                int i = 0;
                for (Prediction p: eval.predictions() ){
                    System.out.println(dataClear.instance(i).attribute(1).value((int) dataClear.instance(i).value(1))+ " " + data.attribute(data.classIndex()).value((int) p.predicted()));
                    fw.write(dataClear.instance(i).attribute(1).value((int) dataClear.instance(i).value(1))+ " " + data.attribute(data.classIndex()).value((int) p.predicted())+"\n");
                    i++;
                }




            }else{ //esaldi bat kargatu
                ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource("spam_clean.arff"); //hutsi dagoen .arff behar dugu
                data = dataSource.getDataSet();
                data.setClassIndex(0);
                System.out.println(data.numInstances());
                Instance algo = new DenseInstance(data.numAttributes());
                algo.setDataset(data);
                algo.setValue(1, args[1]);
                algo.setMissing(0);
                data.add(algo); //esaldia duen instantzia sortu eta .arff-ra gehitu
                dataClear=data;
                FixedDictionaryStringToWordVector filtroa = new FixedDictionaryStringToWordVector();
                filtroa.setDictionaryFile(new File(args[3])); //esto habria que cambiarlo por un argumento
                filtroa.setInputFormat(data);
                data= Filter.useFilter(data, filtroa);
                Evaluation eval = new Evaluation(data);
                eval.evaluateModel(randomF, data);
                int i = 0;
                for (Prediction p: eval.predictions() ){   //ez da beharrezkoa for loop hau, baina agian esaldi bat baino gehiagorekin funtzionatzeko inplementatuko dugu
                    System.out.println(dataClear.instance(i).attribute(1).value(0)+ " " + data.attribute(0).value((int) p.predicted()));
                    fw.write(dataClear.instance(i).attribute(1).value(0)+ ", " + data.attribute(0).value((int) p.predicted()));
                    i++;
                }
            }
        fw.close();

        }else {
            System.out.println("Ez duzu arguments atala behar bezala bete!");
            System.out.println("Erabilerak:");
            System.out.println("Lehenengoa: java -jar Predictions.jar modeloa.model test.arff emaitzak.txt hiztegia");
            System.out.println("Bigarrena: java -jar Predictions.jar modeloa.model \"Ebaluatu nahi den esaldia\" emaitzak.txt hiztegia");

        }


    }

    public String iragarpenakAtera(String s) throws Exception {
        String iragarpena = "SPAM";
        RandomForest randomF = (RandomForest) weka.core.SerializationHelper.read("randomForestUI.model");
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource("spam_clean.arff"); //hutsi dagoen .arff behar dugu
        Instances data = dataSource.getDataSet();
        data.setClassIndex(0);
        System.out.println(data.numInstances());
        Instance algo = new DenseInstance(data.numAttributes());
        algo.setDataset(data);
        algo.setValue(1, s);
        algo.setMissing(0);
        data.add(algo); //esaldia duen instantzia sortu eta .arff-ra gehitu
        Instances dataClear=data;
        FixedDictionaryStringToWordVector filtroa = new FixedDictionaryStringToWordVector();
        filtroa.setDictionaryFile(new File("hiztegiaUI.txt")); //esto habria que cambiarlo por un argumento
        filtroa.setInputFormat(data);
        data= Filter.useFilter(data, filtroa);
        Evaluation eval = new Evaluation(data);
        eval.evaluateModel(randomF, data);
        for(Prediction p: eval.predictions() ){
            if(p.predicted() == 1.0){
                iragarpena = "Ez SPAM";
            }
        }
        return iragarpena;
    }
}

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
 * <h1>Class for predicting a raw text or a set of texts inside a '.arff' file using a RandomForest classifier</h1>
 * @version 2021/4/16
 *
 * <b>This class loads a RandomForest classifier and it uses it to predict the class of a simple raw text or a set of texts in a '.arff' file.</b>
 * @see "test"
 *
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


        if (args.length== 3){
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
                filtroa.setDictionaryFile(new File("hiztegia.txt"));//esto habria que cambiarlo por un argumento
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
                filtroa.setDictionaryFile(new File("hiztegia.txt")); //esto habria que cambiarlo por un argumento
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

        }


    }

}

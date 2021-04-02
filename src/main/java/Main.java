import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Random;

public class Main {

    //Instantziak
    static Instances data ;
    static Instances train ;
    static Instances test ;

    //Klasifikatzaileak
    static OneR oneR = new OneR();
    static RandomForest randomF = new RandomForest();

    public static void main(String[] args) throws Exception {
        rawToArff();
        instantziakKargatu();
        stringToWordVector();
    }

    //Testua .arff bihurtu
    private static void rawToArff() {
        //TODO
    }

    //Instantziak kargatzeko metodoa
    private static void instantziakKargatu() throws Exception {
        ClassLoader cl = Main.class.getClassLoader();
        InputStream is = cl.getResourceAsStream("dataFiles/spam_dev.arff");
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(is);
        data = dataSource.getDataSet();
        //Gure kasuan klasea lehenengo instantzian dago
        data.setClassIndex(0);

        //Instantziak nahastu
        Randomize random = new Randomize();
        random.setInputFormat(data);
        data = random.useFilter(data,random);

        //Instantziak bi laginetan banatu (train eta test)
        RemovePercentage rp = new RemovePercentage();
        rp.setInputFormat(data);
        rp.setPercentage(30);

        train = Filter.useFilter(data,rp);
        train.setClassIndex(0);

        rp = new RemovePercentage();
        rp.setInputFormat(data);
        rp.setPercentage(30);
        rp.setInvertSelection(true);

        test = Filter.useFilter(data,rp);
        test.setClassIndex(0);
        System.out.println(train.numInstances() + " " + test.numInstances());

    }

    //String to word vector filtroa
    private static void stringToWordVector() throws Exception {
        //https://stackoverflow.com/questions/41877413/how-to-use-stringtowordvector-weka-in-java
        //Filtroa


        ClassLoader cl = Main.class.getClassLoader();
        InputStream is = cl.getResourceAsStream("dataFiles/spam_test.arff"); //he cambiado a que se lea el de test asi que la matrix va a dar 0 en todo
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(is);
        test = dataSource.getDataSet();
        test.setClassIndex(0);
        Instances testOriginal = test;
        testOriginal.setClassIndex(0);
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(train);
        File fitxategia = new File("dictionary.txt");
        filter.setDictionaryFileToSaveTo(fitxategia);
        train = Filter.useFilter(train,filter);
        FixedDictionaryStringToWordVector fdstwvFilter = new FixedDictionaryStringToWordVector();
        fdstwvFilter.setDictionaryFile(fitxategia);
        fdstwvFilter.setInputFormat(test);
        test = Filter.useFilter(test,fdstwvFilter);
        Evaluation eval = new Evaluation(test);
        randomF.buildClassifier(train);
        eval.evaluateModel(randomF, test);
        System.out.println(eval.toMatrixString());  //lo dicho en la linea 85

        System.out.println("Atributos antes:" + train.numAttributes()); //esto de abajo lo he copiado de lo que pasó Paul
        AttributeSelection attSelect = new AttributeSelection();
        InfoGainAttributeEval infoGainEval = new InfoGainAttributeEval();
        Ranker search = new Ranker();
        search.setOptions(new String[] { "-T", "0.001" });	// estos valores nose cuales hay que poner
        attSelect.setInputFormat(train);
        attSelect.setEvaluator(infoGainEval);
        attSelect.setSearch(search);
        train = Filter.useFilter(train,attSelect);

        System.out.println("Atributos ahora:"+ train.numAttributes());

        //train-aren hiztegia lortu. atributu guztiak txt batean gordeko ditugu
        BufferedWriter bw= new BufferedWriter(new FileWriter("hiztegia.txt"));

        for(int i=0;i<train.numAttributes()-1;i++){
            Attribute a=train.attribute(i);
            bw.newLine();
            bw.write(a.name());
        }
        bw.flush();
        bw.close();
        FixedDictionaryStringToWordVector hiztegia= new FixedDictionaryStringToWordVector();
        hiztegia.setDictionaryFile(new File("hiztegia.txt"));
        hiztegia.setInputFormat(test);
        test=Filter.useFilter(test, hiztegia);

        Evaluation newEval = new Evaluation(test);
        OneR newOneR = new OneR();
        newOneR.buildClassifier(train);
        double[] algo = newEval.evaluateModel(newOneR, test);
        System.out.println(test.numInstances());
        int i = 0;
        for(Prediction p:  eval.predictions()
        ) {
            if(data.attribute(0).value((int) p.predicted()).equals("spam")){
                System.out.println(testOriginal.instance(i).stringValue(1));
                System.out.println("\n " + i +" Iragarpena: "+data.attribute(0).value((int) p.predicted()));

            }
            i++;
        }
    }
}

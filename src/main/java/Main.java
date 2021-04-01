import weka.classifiers.Evaluation;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;

import java.io.File;
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
        InputStream is = cl.getResourceAsStream("dataFiles/dev/spam_dev.arff");
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
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(train);
        File fitxategia = new File("dictionary.txt");
        filter.setDictionaryFileToSaveTo(fitxategia);
        FixedDictionaryStringToWordVector fdstwvFilter = new FixedDictionaryStringToWordVector();
        fdstwvFilter.setDictionaryFile(fitxategia);
        fdstwvFilter.setInputFormat(train);

        train = Filter.useFilter(train,filter);
        test = Filter.useFilter(test,fdstwvFilter);

        oneR.buildClassifier(train);
        Evaluation eval = new Evaluation(test);

        eval.crossValidateModel(oneR,test,10,new Random());
        System.out.println(eval.toMatrixString());

    }
}

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.stemmers.LovinsStemmer;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;

import java.util.Random;

public class Main {

    //Instantziak
    static Instances data;
    static Instances train;
    static Instances test;

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
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource("");
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

        Instances train = rp.useFilter(data,rp);
        train.setClassIndex(0);
        rp.setInvertSelection(true);

        Instances test = rp.useFilter(data,rp);
        test.setClassIndex(0);
    }

    //String to word vector filtroa
    private static void stringToWordVector() throws Exception {
        //https://stackoverflow.com/questions/41877413/how-to-use-stringtowordvector-weka-in-java

        //Filtroa
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(data);
        //filter.setIDFTransform(true);
        LovinsStemmer stemmer = new LovinsStemmer();
        filter.setStemmer(stemmer);
        filter.setLowerCaseTokens(true);

        //FilteredClassifier-a sortu eta konfiguratu
        FilteredClassifier fc = new FilteredClassifier();
        //Filtroa zehaztu
        fc.setFilter(filter);
        //Base-line klasifikatzailea zehaztu
        fc.setClassifier(oneR);
        //Klasifikatzailea eraiki
        fc.buildClassifier(train);
    }
}

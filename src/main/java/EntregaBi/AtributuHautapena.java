package EntregaBi;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;
import weka.filters.unsupervised.attribute.Reorder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
/**
 * A class to select the optimal attributes of an '.arff' file and store it's new dictionary.  <br>
 *
 * This class reads the train '.arff' file obtained with class "TransformRaw" and uses the dev/test '.arff' file (Raw text, NOT vectorized)
 * to select the best attributes for the data.
 * <br>
 * Arguments needed for main method: <br>
 * 1 - Path to the existing train '.arff' file obtained with class "TransformRaw". <br>
 * 2 - Path to the file where the dictionary will be written.
 * 3 - Path to the test/dev '.arff' file with raw text.
 * <br> <br>
 * For more information about the input train '.arff' file:
 * @see EntregaBat.TransformRaw
 * */

public class AtributuHautapena {



    //Ahora la clase va a estar en el ultimo atributo
    public static void main(String[] args) throws Exception {
        /**
         *
         * @param args list of arguments as an array of strings
         * @throws Exception
         */

        System.out.println(args.length);
        if(args.length  !=3) {
            System.out.println("Ez duzu arguments atala behar bezala bete!");
            System.out.println("Erabilera:");
            System.out.println("java -jar AtributuHautapena.jar train.arff hiztegia dev/test.arff ");            /*
                1. Parametroa BoW motako train arff fitxategia
                2. Parametroa Hiztegia gordetzeko fitxategia
                3. Dev/Test .arff fitxategia
             */
        }
        else {
            System.out.println(args[0]);

            DataSource dataSource = new DataSource(args[0]);
            Instances train = dataSource.getDataSet();
            train.setClassIndex(0);
            AttributeSelection attSelect = new AttributeSelection();
            InfoGainAttributeEval infoGainEval = new InfoGainAttributeEval();
            Ranker search = new Ranker();
            search.setOptions(new String[]{"-T", "0.001"});
            attSelect.setInputFormat(train);
            attSelect.setEvaluator(infoGainEval);
            attSelect.setSearch(search);
            train = Filter.useFilter(train, attSelect);

            ArffSaver arffSaver = new ArffSaver();
            arffSaver.setInstances(train);
            arffSaver.setDestination(new File("AtributuHautapena_" + args[0]));
            arffSaver.setFile(new File("AtributuHautapena_" + args[0]));
            arffSaver.writeBatch();


            BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));

            for (int i = 0; i < train.numAttributes() - 1; i++) {
                Attribute a = train.attribute(i);
                bw.newLine();
                bw.write(a.name());
            }
            bw.flush();
            bw.close();


            DataSource devSource = new DataSource(args[2]);
            Instances dev = devSource.getDataSet();
            dev.setClassIndex(0);

            FixedDictionaryStringToWordVector hiztegia = new FixedDictionaryStringToWordVector();
            hiztegia.setDictionaryFile(new File(args[1]));
            hiztegia.setInputFormat(dev);
            dev = Filter.useFilter(dev, hiztegia);

            // Clasea azken atributu bezala ezarri
            Reorder reorder = new Reorder();
            reorder.setAttributeIndices("2-" + dev.numAttributes() + ",1");
            reorder.setInputFormat(dev);
            dev = Filter.useFilter(dev, reorder);


            arffSaver = new ArffSaver();
            arffSaver.setInstances(dev);
            arffSaver.setDestination(new File("AtributuHautapena_" + args[2].toString()));
            arffSaver.setFile(new File("AtributuHautapena_" + args[2].toString()));
            arffSaver.writeBatch();

        }

    }




}

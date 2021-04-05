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

public class AtributuHautapena {


    //Ahora la clase va a estar en el ultimo atributo
    public static void main(String[] args) throws Exception {
        if(args.length  !=3) {
            System.out.println("Ez duzu arguments atala behar bezala bete!");
            /*
                1. Parametroa BoW motako train arff fitxategia
                2. Parametroa Hiztegia gordetzeko fitxategia
                3. Dev/Test .arff fitxategia
             */
        }
        else {

            DataSource dataSource = new DataSource(args[0]);
            Instances train = dataSource.getDataSet();
            train.setClassIndex(0);
            AttributeSelection attSelect = new AttributeSelection();
            InfoGainAttributeEval infoGainEval = new InfoGainAttributeEval();
            Ranker search = new Ranker();
            search.setOptions(new String[]{"-T", "0.001"});    // estos valores nose cuales hay que poner
            attSelect.setInputFormat(train);
            attSelect.setEvaluator(infoGainEval);
            attSelect.setSearch(search);
            train = Filter.useFilter(train, attSelect);

            ArffSaver arffSaver = new ArffSaver();
            arffSaver.setInstances(train);
            arffSaver.setDestination(new File("ParamEkorketa_" + args[0]));
            arffSaver.setFile(new File("ParamEkorketa_" + args[0]));
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
            hiztegia.setDictionaryFile(new File(args[1].toString()));
            hiztegia.setInputFormat(dev);
            dev = Filter.useFilter(dev, hiztegia);

            // 3- FILTROA - Reorder. Reorder filtroa erabiliko dugu hau egiteko
            Reorder reorder = new Reorder();
            reorder.setAttributeIndices("2-" + dev.numAttributes() + ",1");   //2-119, 1.  2-119 bitarteko atributuak goian jarriko dira eta 1 atributua (klasea dena) amaieran.
            reorder.setInputFormat(dev);
            dev = Filter.useFilter(dev, reorder);


            arffSaver = new ArffSaver();
            arffSaver.setInstances(dev);
            arffSaver.setDestination(new File("ParamEkorketa_" + args[2].toString()));
            arffSaver.setFile(new File("ParamEkorketa_" + args[2].toString()));
            arffSaver.writeBatch();

        }

    }




}

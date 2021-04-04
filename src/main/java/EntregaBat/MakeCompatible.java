package EntregaBat;

import java.io.File;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;
import weka.filters.unsupervised.attribute.Reorder;

public class MakeCompatible {

    public static void main(String[] args) throws Exception {
        if(args.length  !=3) {
            System.out.println("Ez duzu arguments atala behar bezala bete!");
            /*
            for(String a : args){

                System.out.println(a);
            }

             */
            //Arguments atalean 3 parametro ezberdin behar ditu programa honek
            //1.parametroa jada exisistitzen den .arff fitxategia izango da (dev multzoa lortzeko)
            //2. parametroa TransformRaw-etik lortzen dugun hiztegia izango da
            //3. parametroa programa honek sortuko duen .arff fitxategia gordetzeko helbidea izango da


        }
        else{
            //dev multzoa lortu (arguments atalean sartzen den lehenengo parametroa args[0])
            DataSource source=null;
            try {
                source = new DataSource(args[0]);
            } catch (Exception e) {
                System.out.println("ERROREA - Sarrerako fitxategiaren helbidea okerra da");
            }
            Instances dev= source.getDataSet();

            //dev multzoaren klasea definitu
            dev.setClassIndex(0);


            //Orain parametro bezala (args[1]) lortu dugun hiztegia dev ean sartuko dugu FixedDictionaryStringToWordVector erabiliz
            FixedDictionaryStringToWordVector hiztegia= new FixedDictionaryStringToWordVector();
            hiztegia.setDictionaryFile(new File(args[1]));
            hiztegia.setInputFormat(dev);
            dev=Filter.useFilter(dev, hiztegia);


            //Atributuak reordenartu, klasea amaieran agertu dadin, horretarako reorder filtroa erabiliko dugu


            //arff fitxategia berria sortu arguments atalean sartu dugun helbidean (args[2])
            ArffSaver arffSaver = new ArffSaver();
            arffSaver.setInstances(dev);
            arffSaver.setDestination(new File(args[2]));
            arffSaver.setFile(new File(args[2].toString()+".arff"));
            arffSaver.writeBatch();

        }


    }
}




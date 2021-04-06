package EntregaBi;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class ParametroEkorketa {
    //TODO


    public static void main(String[] args) throws Exception {


        DataSource dataSource = new DataSource("AtributuHautapena_BoWspam_train.arff");
        Instances data = dataSource.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);
        System.out.println(data.numAttributes() +" "+ data.numInstances());

        ArrayList<Integer> bagSizePercent = new ArrayList<Integer>();
        for (int i = 1; i < 300; i = i + 100) {
            bagSizePercent.add(i);
        }

        ArrayList<Integer> numExecutionSlots = new ArrayList<Integer>();
        for (int l = 0; l < 60; l = l + 20) {
            numExecutionSlots.add(l);
        }

        ArrayList<Integer> numFeatures = new ArrayList<Integer>();

        for (int m = 0; m < 60; m = m + 20) {
            numFeatures.add(m);
        }
        ArrayList<Integer> numIterations = new ArrayList<Integer>();
        for (int n = 0; n < 300; n = n + 100) {
            numIterations.add(n);
        }

        double max = 0.0;
        BufferedWriter bw = new BufferedWriter(new FileWriter("RandomForest_ekorketa.txt"));
        bw.newLine();


        bw.write("bagSizePercent numExecutionSlots numFeatures numIterations CORRECT");
        System.out.println();

        RandomForest randomF= new RandomForest();
        for (int bagSizePercentBat : bagSizePercent) {
            randomF.setBagSizePercent(bagSizePercentBat);
                        for (Integer numExecutionSlotsBat : numExecutionSlots) {
                            randomF.setNumExecutionSlots(numExecutionSlotsBat);
                            for (Integer numFeaturesBat : numFeatures) {
                                randomF.setNumFeatures(numFeaturesBat);
                                for (Integer numIterationsBat : numIterations) {
                                    randomF.setNumIterations(numIterationsBat);
                                            randomF.buildClassifier(data);
                                            Evaluation evaluator = new Evaluation(data);
                                            evaluator.crossValidateModel(randomF, data, 10, new Random(1));

                                            System.out.println();

                                            System.out.format("%10s %10s %10s %10s %30s", bagSizePercentBat, numExecutionSlotsBat, numFeaturesBat, numIterationsBat, evaluator.pctCorrect());

                                            if (evaluator.pctCorrect() > max) {
                                                max = evaluator.pctCorrect();
                                            }
                                            //voy a guardar en un doc para despues analizar y si podemos quitar algun param o asi en la ekorketa
                                            bw.newLine();
                                            bw.write(bagSizePercentBat + "       "  + numExecutionSlotsBat + "      " + numFeaturesBat + "      " + numIterationsBat + "      "  + evaluator.pctCorrect());

                                        }

                                }
                            }


        }

        bw.flush();
        bw.close();






    }

    /*
        No está en Main, pero me hago a la idea de como va

        mi idea seria cargar un modelo (el que creamos con GetRandomForestModel) y hacer parametro ekorketa

        Nueva idea (y creo que mejor), hacer parametro ekorketa y luego con los resultados confijugar los parametros en GetRandomForestModel

     */

}

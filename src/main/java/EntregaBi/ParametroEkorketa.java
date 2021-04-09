package EntregaBi;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class ParametroEkorketa {
    //TODO


    public static void main(String[] args) throws Exception {


        DataSource dataSource = new DataSource(args[0]);
        Instances data = dataSource.getDataSet();
        data.setClassIndex(data.numAttributes()-1);
        double max = 0.0;
        BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));
        bw.newLine();
        bw.write("bagSizePercent numExecutionSlots numFeatures numIterations CORRECT");
        System.out.println();

        RandomForest randomF= new RandomForest();
        for (int bspb = 1; bspb<300;bspb+=100) {
            randomF.setBagSizePercent(bspb);
                        for (int nesb=0;nesb<60;nesb+=20) {
                            randomF.setNumExecutionSlots(nesb);
                            for (int nf=0;nf<60;nf+=20) {
                                randomF.setNumFeatures(nf);
                                for (int ni = 0; ni<300;ni+=100) {
                                    randomF.setNumIterations(ni);
                                            randomF.buildClassifier(data);
                                            Evaluation evaluator = new Evaluation(data);
                                            evaluator.crossValidateModel(randomF, data, 10, new Random(1));

                                            System.out.println();

                                            System.out.format("%10s %10s %10s %10s %30s", bspb, nesb, nf, ni, evaluator.pctCorrect());

                                            if (evaluator.pctCorrect() > max) {
                                                max = evaluator.pctCorrect();
                                            }
                                            //voy a guardar en un doc para despues analizar y si podemos quitar algun param o asi en la ekorketa
                                            bw.newLine();
                                            bw.write(bspb + "       "  + nesb + "      " + nf + "      " + ni + "      "  + evaluator.pctCorrect());

                                        }

                                }
                            }


        }

        bw.flush();
        bw.close();






    }


}

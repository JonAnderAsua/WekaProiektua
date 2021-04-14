package EntregaBi;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class ParametroEkorketaIragarpenDenbora {
    //TODO


    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        DataSource dataSource = new DataSource(args[0]);
        Instances data = dataSource.getDataSet();
        data.setClassIndex(data.numAttributes()-1);


        DataSource devSource = new DataSource(args[2]);
        Instances dev = devSource.getDataSet();
        dev.setClassIndex(dev.numAttributes()-1);


        int klaseMinoMaiz = Integer.MAX_VALUE;
        int klaseMino = 0;
        int unekoKlase = 0;
        for (int i : data.attributeStats(data.classIndex()).nominalCounts){
            if(i < klaseMinoMaiz){
                klaseMino= unekoKlase;
                klaseMinoMaiz = i;
            }
            unekoKlase-=-1;
        }

        double max = 0.0;
        double maxTime = Double.MAX_VALUE;
        BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));
        bw.newLine();
        bw.write("bagSizePercent numExecutionSlots numFeatures numIterations CORRECT DENBORA");
        System.out.println("bagSizePercent numExecutionSlots numFeatures numIterations \t\tCORRECT \t DENBORA(s)");
        RandomForest randomF= new RandomForest();
        int maxBSPB = 0;
        int maxNESB = 0;
        int maxNF = 0;
        int maxNI = 0;
        for (int bspb = 1; bspb<10;bspb+=2) { //10etik gora badoa exekuzio denbora asko handitzen da
            randomF.setBagSizePercent(bspb);
            //for (int nesb=1;nesb<=Runtime.getRuntime().availableProcessors();nesb+=1) {
            //randomF.setNumExecutionSlots(nesb); Probetan beti procesadore maximo kopurua atera da parametro optimoetan
            for (int nf=0;nf<data.numAttributes()-1;nf+=200) { //-1 agian klasea kontuan har dezakelako
                randomF.setNumFeatures(nf);
                for (int ni = 1; ni<50;ni+=5) {
                    long konbinazioHasieraDenbora = System.nanoTime();
                    randomF.setNumIterations(ni);
                    randomF.buildClassifier(data);

                    Evaluation evaluator = new Evaluation(data);
                    evaluator.evaluateModel(randomF, dev);
                    long konbinazioAmaieraDenbora = System.nanoTime();
                    double denbora = ((double)konbinazioAmaieraDenbora- konbinazioHasieraDenbora)/1000000000;
                    System.out.println();
                    System.out.format("%10s \t %10s \t%10s \t%10s %20s \t%10s", bspb, Runtime.getRuntime().availableProcessors(), nf, ni, evaluator.fMeasure(klaseMino), denbora);
                    if (evaluator.fMeasure(klaseMino) > max) { //si la puntuacion es mejor, pasa a ser el mejor

                        max = evaluator.fMeasure(klaseMino);
                        maxTime = denbora;
                        maxBSPB = bspb;
                        maxNESB = Runtime.getRuntime().availableProcessors();
                        maxNF = nf;
                        maxNI = ni;

                    }else if (evaluator.fMeasure(klaseMino)  == max && denbora<maxTime ){ //si la puntuacion es igual, y el tiempo menor, pasa a ser el mejor
                        max = evaluator.fMeasure(klaseMino);
                        maxTime = denbora;
                        maxBSPB = bspb;
                        maxNESB = Runtime.getRuntime().availableProcessors();
                        maxNF = nf;
                        maxNI = ni;
                    }
                    bw.newLine();
                    bw.write("\t"  + bspb + "\t \t"  + Runtime.getRuntime().availableProcessors() + "\t \t " + nf + "\t  " + ni + "\t   "  + evaluator.fMeasure(klaseMino)+ "\t "+ denbora);

                }

            }
            //}


        }
        long stopTime = System.nanoTime();
        bw.write("\n Balio hoberenak: \n");
        bw.write("BSPB = " + maxBSPB +  " NESB = " + maxNESB + " NF = " + maxNF + " NI = " +maxNI + " hurrengo puntuazioarekin " + max + " eta " + maxTime + " segundu behar izan ditu\n");
        bw.write("Exekuzio denbora: " + ((double)stopTime-startTime)/1000000000 + " segundu");
        System.out.println("Exekuzio denbora: " + ((double)stopTime-startTime)/1000000000);
        bw.flush();
        bw.close();







    }


}

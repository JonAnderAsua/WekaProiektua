echo -e 'label \t text' > main.txt
sed -i '/&lt;#&gt;/d' SMS_SpamCollection.dev.txt 
sed -i "s/'/ /g" SMS_SpamCollection.dev.txt 
cat SMS_SpamCollection.dev.txt >> main.txt

tr '"' ' ' < main.txt | tr ',' '/' | tr '\t' ',' | tr '/' ' ' > spam.csv 
Rscript newFix.r

echo "Sartu weka.jar fitxategiaren helbidea (adibidez weka-3-8-5/weka.jar)"
read nirejar
java -cp $nirejar weka.core.converters.CSVLoader spam_new.csv -B 10000 > spam.dev.arff
rm main.txt
rm spam.csv
rm spam_new.csv


cat spam.dev.arff | while read line 
do
	if [[ $line == *"attribute text"* ]]; then
  		echo "@attribute text string" >> spam_dev.arff
  	else
  		#echo $line | sed "s/'/ /2" >>spam_dev.arff
  		echo $line >> spam_dev.arff
fi
done

rm spam.dev.arff

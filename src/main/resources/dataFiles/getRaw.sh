#!/bin/bash

if [[ "$1" != "" ]]; then
    TRAIN="$1"
    if [[ "$2" != "" ]]; then
    	DEV="$2"
    	if [[ "$3" != "" ]]; then
    		TEST="$3"
    		if [[ "$4" != "" ]]; then
    			WEKA="$4"
    		
    		fi
    	fi
    fi
fi


nothing=""
if [ "$WEKA" = "$nothing" ]; then
    echo "Beharrezko argumentuak: train fitxategia, dev fitxategia, train fitxategia eta weka.jar fitxategia"
    exit 1
fi


echo "Converting dev file"

echo -e 'label \t text' > main.txt

 
sed -i '/&lt;#&gt;/d' $DEV 
sed -i "s/'//g" $DEV
sed -i "s/*//g" $DEV
cat $DEV >> main.txt


tr '"' ' ' < main.txt | tr ',' '/' | tr '\t' ',' | tr '/' ' ' > spam.csv 
Rscript newFix.r 2> /dev/null


java -cp $WEKA weka.core.converters.CSVLoader spam_new.csv -B 10000 > spam.dev.arff
rm main.txt
rm spam.csv
rm spam_new.csv


cat spam.dev.arff | while read line 
do
	if [[ $line == *"attribute text"* ]]; then
  		echo "@attribute text string" >> spam_dev.arff
  	else
  		echo $line >> spam_dev.arff
fi
done

rm spam.dev.arff
sed -i "s/ham,spam/spam,ham/g" spam_dev.arff


echo "Converting train file"

echo -e 'label \t text' > main.txt

sed -i '/&lt;#&gt;/d' $TRAIN 
sed -i "s/'//g" $TRAIN 
sed -i "s/*//g" $TRAIN


cat $TRAIN >> main.txt

tr '"' ' ' < main.txt | tr ',' '/' | tr '\t' ',' | tr '/' ' ' > spam.csv 
Rscript newFix.r

java -cp $WEKA weka.core.converters.CSVLoader spam_new.csv -B 10000 > spam.train.arff

rm main.txt
rm spam.csv
rm spam_new.csv


cat spam.train.arff | while read line 
do
	if [[ $line == *"attribute text"* ]]; then
  		echo "@attribute text string" >> spam_train.arff
  	else
  		echo $line >> spam_train.arff
fi
done

rm spam.train.arff

echo "Converting test file"

cat spam_train.arff | while read line 
do
	echo $line >> spam_test.arff
	if [[ $line == *"@data"* ]]; then
  		break
fi
done

sed -i '/&lt;#&gt;/d' $TEST
sed -i "s/'//g" $TEST
sed -i "s/*//g" $TEST
cat $TEST | while read line 
do
	echo "?,'${line}'" >> spam_test.arff
done


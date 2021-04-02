cat spam_train.arff | while read line 
do
	echo $line >> test.arff
	if [[ $line == *"@data"* ]]; then
  		break
fi
done

sed -i '/&lt;#&gt;/d' SMS_SpamCollection.test_blind.txt
sed -i "s/'//g" SMS_SpamCollection.test_blind.txt
sed -i "s/*//g" SMS_SpamCollection.test_blind.txt
cat SMS_SpamCollection.test_blind.txt | while read line 
do
	echo "?,'${line}'" >> test.arff
done
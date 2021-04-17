 Programak honek lau parametro behar ditu ondo lan egiteko: train fitxategia, dev fitxategia, test fitxategia eta weka.jar fitxagia.
 
Programak karpeta honetan dagoen R script-a exekutzatzen du, beraz, beharrezkoa da R instalatuta egotea.

 R script honek "data.table", "foreign" eta "tibble" liburutegiak erabiltzen ditu, hauek instalatuta ez badaude script-aren lehenengo 6 lineak deskomentatu behar dira.

Programaren erabileraren adibidea:
```bash
./getRaw.sh SMS_SpamCollection.train.txt SMS_SpamCollection.dev.txt SMS_SpamCollection.test_blind.txt ~/weka-3-8-5-azul-zulu-linux/weka-3-8-5/weka.jar
```


Hori egitean hurrengo hiru fitxategiak sortuko dira:

spam_dev.arff  spam_test.arff  spam_train.arff


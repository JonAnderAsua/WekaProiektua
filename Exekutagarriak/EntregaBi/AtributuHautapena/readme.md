Programa honek TransformRaw-etik ateratako train arff fitxategia, sortuko den hiztegia eta honekin bateragarria egingo den dev edo test arff fixategiak hartuko ditu (garrantzitsua, fitxategi hau ez da BoW eran egon behar), eta atributu hautapena egingo du train fitxategiaren gainean.

Programaren erabileraren adibidea:
```bash
java -jar AtributuHautapena.jar BoWspam_train.arff hiztegia.txt spam_dev.arff 
```


Hori egitean hurrengo hiru fitxategiak sortuko dira:

AtributuHautapena_BoWspam_train.arff  hiztegia.txt AtributuHautapena_spam_dev.arff


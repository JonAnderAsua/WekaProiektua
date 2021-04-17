Programa honek .arff fitxategi bat hartuko du eta StringToWordVector filtroa aplikatuko dio. Horretarako 5 parametro behar dira:

.arff fitxategia, sortutako den hiztegia gordetzeko helbidea,  TF transformazioa nahi den ala ez (YES/NO), IDF transformazioa nahi den ala ez (YES/NO) eta Sparse ala NonSparse nahi den ala ez (YES/NO)

Programaren erabileraren adibidea:

```bash
java -jar TransformRaw.jar spam_train.arff hiztegia.txt NO NO YES 
```

Hori egitean hurrengo bi fitxategiak sortuko dira:

BoWspam_train.arff hiztegia.txt
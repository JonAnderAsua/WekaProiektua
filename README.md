# WekaProiektua

Orden de ejecución: 
GetRaw > TransformRaw (NO,NO,YES) > MakeCompatible > AtributuHautapena > Get___Model

Haciendolo así yo he conseguido los resultados de emaitzak.txt, no aseguro que funcione si no se hace asi.

- [WekaProiektua](#wekaproiektua)
  * [Testuen errepresentazio bektoriala](#testuen-errepresentazio-bektoriala)
    + [Datu gordinak arff formatura bihurtu](#datu-gordinak-arff-formatura-bihurtu)
      - [Requerimentos](#requerimentos)
    + [Datu horien errepresentazio bektoriala lortu](#datu-horien-errepresentazio-bektoriala-lortu)
    + [Test multzoa errepresentazio-espaziora egokitu](#test-multzoa-errepresentazio-espaziora-egokitu)
    + [Dokumentazio eta atal teorikoa](#dokumentazio-eta-atal-teorikoa)
  * [Sailkatzailea](#sailkatzailea)
    + [Informazio irabazian oinarritutako atributuen hautapena](#informazio-irabazian-oinarritutako-atributuen-hautapena)
    + [Ereduaren inferentzia eta itxarondako kalitatearen estimazioa](#ereduaren-inferentzia-eta-itxarondako-kalitatearen-estimazioa)
    + [Eredu iragarlea](#eredu-iragarlea)
      - [OneR](#oner)
      - [RandomForest](#randomforest)
    + [Dokumentazio eta atal teorikoa](#dokumentazio-eta-atal-teorikoa-1)
  * [Iragarpenak](#iragarpenak)
    + [Cosas](#cosas)
    + [Dokumentazio eta atal teorikoa](#dokumentazio-eta-atal-teorikoa-2)



## Testuen errepresentazio bektoriala

### Datu gordinak arff formatura bihurtu
3.1 del primer documento

#### Requerimentos
Para ejecutar el script que arregla los fitxategis train y dev, hay que tener instalado R en el ordenador, ya que ejecuta el otro script de R (train y dev). Ademas de eso, hace falta instalar varias librerias de R, asi que si es la primera vez que lo ejecutais tendreis que descomentar las primeras lineas en el script de R.

Ejecutar el script: 

```bash
./getRaw.sh SMS_SpamCollection.train.txt SMS_SpamCollection.dev.txt SMS_SpamCollection.test_blind.txt ~/weka-3-8-5-azul-zulu-linux/weka-3-8-5/weka.jar 
```

### Datu horien errepresentazio bektoriala lortu
3.2 del primer documento, va de Bag of Words/Sparse/NonSparse...
Uso:
```bash
java -jar TransformRaw.jar train.arff hiztegia IDFTF(YES/NO) TFTF(YES/NO) SPARSE(YES/NO) 
```

### Test multzoa errepresentazio-espaziora egokitu
3.3 del primer documento.

### Dokumentazio eta atal teorikoa

---

## Sailkatzailea
### Informazio irabazian oinarritutako atributuen hautapena 
3.1 del segundo documento. Preguntar: Atributuen hautapena (erredundantzia kendu)
  
### Ereduaren inferentzia eta itxarondako kalitatearen estimazioa 
3.2 del segundo documento. Parametro ekorketa (depth, k value, numtrees?)
  
### Eredu iragarlea
#### OneR
```bash
java -jar GetOneRModel.jar BoWspam_train.arff spam_devBoW.arff oneR.model emaitzak.txt
```

#### RandomForest
```bash
java -jar GetRandomForestModel.jar BoWspam_train.arff spam_devBoW.arff RandomForest.model emaitzak.txt
```

### Dokumentazio eta atal teorikoa
  
---

## Iragarpenak

Para hacer las prediciones de un arff (no compatible)
```bash
"randomF.model" "spam_test.arff" "predictionsIrteeraARFFfitxategia.txt"
```


Para hacer la prediccion de una frase
```bash
"randomF.model" "you won cash prize, TXT 61020203 for free" "predictionsIrteeraEsaldia.txt"
```

### Cosas
  
### Dokumentazio eta atal teorikoa





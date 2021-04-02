# WekaProiektua

- [WekaProiektua](#wekaproiektua)
  * [Testuen errepresentazio bektoriala](#testuen-errepresentazio-bektoriala)
    + [Datu gordinak arff formatura bihurtu](#datu-gordinak-arff-formatura-bihurtu)
      - [Requerimentos](#requerimentos)
      - [Cosas a tener en cuenta](#cosas-a-tener-en-cuenta)
    + [Datu horien errepresentazio bektoriala lortu](#datu-horien-errepresentazio-bektoriala-lortu)
    + [Test multzoa errepresentazio-espaziora egokitu](#test-multzoa-errepresentazio-espaziora-egokitu)
    + [Dokumentazio eta atal teorikoa](#dokumentazio-eta-atal-teorikoa)
  * [Sailkatzailea](#sailkatzailea)
    + [Informazio irabazian oinarritutako atributuen hautapena](#informazio-irabazian-oinarritutako-atributuen-hautapena)
    + [Ereduaren inferentzia eta itxarondako kalitatearen estimazioa](#ereduaren-inferentzia-eta-itxarondako-kalitatearen-estimazioa)
    + [Eredu iragarlea](#eredu-iragarlea)
    + [Dokumentazio eta atal teorikoa](#dokumentazio-eta-atal-teorikoa-1)
  * [Iragarpenak](#iragarpenak)
    + [Cosas](#cosas)
    + [Dokumentazio eta atal teorikoa](#dokumentazio-eta-atal-teorikoa-2)


## Testuen errepresentazio bektoriala

### Datu gordinak arff formatura bihurtu
3.1 del primer documento

#### Requerimentos
Para ejecutar el script que arregla los fitxategis [train](https://github.com/JonAnderAsua/WekaProiektua/blob/master/src/main/resources/dataFiles/train/Script.sh) y [dev](https://github.com/JonAnderAsua/WekaProiektua/blob/master/src/main/resources/dataFiles/dev/Script.sh), hay que tener instalado R en el ordenador, ya que ejecuta el otro script de R ([train](https://github.com/JonAnderAsua/WekaProiektua/blob/master/src/main/resources/dataFiles/train/newFix.r) y [dev](https://github.com/JonAnderAsua/WekaProiektua/blob/master/src/main/resources/dataFiles/dev/newFix.r)).
Ademas de eso, hace falta instalar varias librerias de R, asi que si es la primera vez que lo ejecutais tendreis que descomentar las primeras lineas en el script de R.


#### Cosas a tener en cuenta 
El script que arregla los fitxategis [train](https://github.com/JonAnderAsua/WekaProiektua/blob/master/src/main/resources/dataFiles/train/Script.sh) y [dev](https://github.com/JonAnderAsua/WekaProiektua/blob/master/src/main/resources/dataFiles/dev/Script.sh) te va a pedir en un momento que le digas donde esta el archivo weka.jar, si pones el path como ~/weka-3-8-5/weka.jar falla. En cambio si lo pones como /home/ander/weka-3-8-5/weka.jar no.

El [script](https://github.com/JonAnderAsua/WekaProiektua/blob/master/src/main/resources/dataFiles/test/testScript.sh) para pasar todo el text a [test.arff](https://github.com/JonAnderAsua/WekaProiektua/blob/master/src/main/resources/dataFiles/test/test.arff) requiere que [spam_train.arff](https://github.com/JonAnderAsua/WekaProiektua/blob/master/src/main/resources/dataFiles/train/spam_train.arff) esté en la misma carpeta para funcionar. 

### Datu horien errepresentazio bektoriala lortu
3.2 del primer documento, va de Bag of Words/Sparse/NonSparse...
  
### Test multzoa errepresentazio-espaziora egokitu
3.3 del primer documento.

### Dokumentazio eta atal teorikoa

---

## Sailkatzailea
### Informazio irabazian oinarritutako atributuen hautapena 
3.1 del segundo documento. Preguntar: Atributuen hautapena con BOW?
  
### Ereduaren inferentzia eta itxarondako kalitatearen estimazioa 
3.2 del segundo documento. Parametro ekorketa
  
### Eredu iragarlea
3.2.3 del segundo documento.
  
### Dokumentazio eta atal teorikoa
  
---

## Iragarpenak

### Cosas
  
### Dokumentazio eta atal teorikoa





Programa honek bi erabilera ditu:

Lehenengoa, modeloa, test/dev arff fitxategia (GetRaw-en sortutakoa, horrela hasierako esaldia erraztasunez lor dezakegu), emaitzak gordetzeko erabiliko den fitxategia eta erabili nahi den hiztegia sarturik, arff fitxategiko esaldi guztiak sailkatutko ditu.


Programaren erabileraren adibidea:
```bash
java -jar Predictions.jar RandomForest.model spam_test.arff emaitzak.txt hiztegia.txt
```

Hori egitean hurrengo fitxategia sortuko da:

emaitzak.txt

Bigarren erabilera, modeloa, kakotxen artean sartutako esaldi bat, emaitzak gordetzeko erabiliko den fitxategia eta erabili nahi den hiztegia sarturik, arff fitxategiko esaldi guztiak sailkatutko ditu.
Erabilera honek spam_clean.arff fitxategia karpeta beran egotea behar du.

Programaren erabileraren adibidea:
```bash
java -jar Predictions.jar RandomForest.model "you won cash prize, TXT 61020203 for free" emaitzak.txt hiztegia.txt
```

Hori egitean hurrengo fitxategia sortuko da:

emaitzak.txt
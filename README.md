# WekaProiektua

- [WekaProiektua](#wekaproiektua)
  * [Programaren exekuzioa](#programaren-exekuzioa)
    + [Erabilera](#erabilera)
  * [GitHub repositorioari buruzko informazioa](#github-repositorioari-buruzko-informazioa)
    + [Exekutagarriak](#exekutagarriak)
    + [Hasierako Fitxategiak](#hasierako-fitxategiak)
    + [JavaDoc](#javadoc)
    + [Lortutako emaitzak](#lortutako-emaitzak)
    + [src](#src)

## Programaren exekuzioa

Soilik programa exekutatu nahi bada, [Exekutagarriak](https://github.com/JonAnderAsua/WekaProiektua/tree/master/Exekutagarriak) karpetan daude jar fitxategi guztiak. 

Mesedez irakurri karpeta bakoitzaren barnean daude README fitxategiak.

### Erabilera

1. [GetRaw](https://github.com/JonAnderAsua/WekaProiektua/tree/master/Exekutagarriak/EntregaBat/GetRaw) programa erabili behar da, hemen hasierako txt fitxategiak arff formatura aldatuko dira.

2. [TransformRaw](https://github.com/JonAnderAsua/WekaProiektua/tree/master/Exekutagarriak/EntregaBat/TransformRaw) erabiliz train fitxategia BoW formatura aldatu behar da.

3. [MakeCompatible](https://github.com/JonAnderAsua/WekaProiektua/tree/master/Exekutagarriak/EntregaBat/MakeCompatible) erabiliz test eta dev fitxategiak BoW formatura aldatu daitezke lehen lortutako hiztegia erabiliz.
Fitxategi honen erabilera ez da beharrezkoa, izan ere iragarpenak egiteko momentuan GetRaw-en sortutako arff fitxategia sartuko dugu (horrela hasierako esaldia berriz lortzea oso erraza egingo zaigu).

4. [AtributuHautapena](https://github.com/JonAnderAsua/WekaProiektua/tree/master/Exekutagarriak/EntregaBi/AtributuHautapena) erabiliz hiztegia txikitu erabiliko dugu.

5. [GetOneRModel](https://github.com/JonAnderAsua/WekaProiektua/tree/master/Exekutagarriak/EntregaBi/GetOneRModel)/[GetRandomForestModel](https://github.com/JonAnderAsua/WekaProiektua/tree/master/Exekutagarriak/EntregaBi/GetRandomForestModel) exekutatu eta gure modeloa lortuko dugu.

6. [Predictions](https://github.com/JonAnderAsua/WekaProiektua/tree/master/Exekutagarriak/EntregaHiru/Predictions) erabiltzeko prest gaude orain. Erabilera bakoitzaren espezifikazioak karpeta horren barnean dagoen README-an daude.

## GitHub repositorioari buruzko informazioa

Repositorio honetan aurkitu daitezke proiektuaren zehar egin eta erabili ditugun fitxategi guztiak.

### Exekutagarriak

Karpeta honen barruan aurreko sekzioan azaldutako exekutagarriak eta beste batzuk ere bai aurkitu daitezke.

### Hasierako Fitxategiak

Karpeta honetan hasiera batean geneuzkan eraldatu gabeko testu fitxategiak daude.

### JavaDoc

Bere izenak esaten duen bezala, JavaDoc guztiak karpeta honen barnean daude.

### Lortutako emaitzak

Hemen exekutagarrien bidez lortutako modeloak, hiztegiak, testu fitxategiak eta bestelakoak daude.

### src 

Java fitxategi guztiak, baita ere aurkezpenerako erabilitako programa eta UI-a.

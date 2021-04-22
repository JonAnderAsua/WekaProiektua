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

Exekutagarriak Java 15 erabiliz sortu dira eta beraz, beharrezkoa izan daiteke Java 15 erabiltzea hauek exekutatzeko.


Java 15 instalatzeko SDKMAN-en erabilera gomendatzen dugu (Windows eko erabiltzaileak hurrats berdinak jarrai dezakete Git Bash erabiliz):

```bash
$ curl -s "https://get.sdkman.io" | bash
```

Ondoren Java bertsioa aukeratu behar da:

```bash
$ sdk list java
================================================================================
Available Java Versions
================================================================================
 Vendor        | Use | Version      | Dist    | Status     | Identifier
--------------------------------------------------------------------------------
 AdoptOpenJDK  |     | 16.0.0.j9    | adpt    |            | 16.0.0.j9-adpt     
               |     | 16.0.0.hs    | adpt    |            | 16.0.0.hs-adpt     
               |     | 11.0.11.hs   | adpt    |            | 11.0.11.hs-adpt    
               |     | 11.0.10.j9   | adpt    |            | 11.0.10.j9-adpt    
               |     | 11.0.10.hs   | adpt    |            | 11.0.10.hs-adpt    
               |     | 8.0.292.hs   | adpt    |            | 8.0.292.hs-adpt    
               |     | 8.0.282.j9   | adpt    |            | 8.0.282.j9-adpt    
               |     | 8.0.282.hs   | adpt    |            | 8.0.282.hs-adpt    
 Alibaba       |     | 11.0.9.4     | albba   |            | 11.0.9.4-albba     
 Amazon        |     | 16.0.0.36.1  | amzn    |            | 16.0.0.36.1-amzn   
               |     | 15.0.2.7.1   | amzn    |            | 15.0.2.7.1-amzn    
               |     | 11.0.11.9.1  | amzn    |            | 11.0.11.9.1-amzn   
               |     | 11.0.10.9.1  | amzn    |            | 11.0.10.9.1-amzn   
               |     | 8.292.10.1   | amzn    |            | 8.292.10.1-amzn    
               |     | 8.282.08.1   | amzn    |            | 8.282.08.1-amzn    
 Azul Zulu     |     | 16.0.0       | zulu    |            | 16.0.0-zulu        
               |     | 16.0.0.fx    | zulu    |            | 16.0.0.fx-zulu     
               |     | 15.0.2.fx    | zulu    |            | 15.0.2.fx-zulu     
               |     | 11.0.11      | zulu    |            | 11.0.11-zulu       
               |     | 11.0.10      | zulu    |            | 11.0.10-zulu       
               |     | 11.0.10.fx   | zulu    |            | 11.0.10.fx-zulu    
               |     | 8.0.292      | zulu    |            | 8.0.292-zulu       
               |     | 8.0.282      | zulu    |            | 8.0.282-zulu       
               |     | 8.0.282.fx   | zulu    |            | 8.0.282.fx-zulu    
               |     | 7.0.302      | zulu    |            | 7.0.302-zulu       
               |     | 7.0.292      | zulu    |            | 7.0.292-zulu       
               |     | 6.0.119      | zulu    |            | 6.0.119-zulu       
 BellSoft      |     | 16.0.1.fx    | librca  |            | 16.0.1.fx-librca   
               |     | 16.0.1       | librca  |            | 16.0.1-librca      
               |     | 16.0.0.fx    | librca  |            | 16.0.0.fx-librca   
               |     | 16.0.0       | librca  |            | 16.0.0-librca      
               |     | 11.0.11.fx   | librca  |            | 11.0.11.fx-librca  
               |     | 11.0.11      | librca  |            | 11.0.11-librca     
               |     | 11.0.10.fx   | librca  |            | 11.0.10.fx-librca  
               |     | 11.0.10      | librca  |            | 11.0.10-librca     
               |     | 8.0.292.fx   | librca  |            | 8.0.292.fx-librca  
               |     | 8.0.292      | librca  |            | 8.0.292-librca     
               |     | 8.0.282.fx   | librca  |            | 8.0.282.fx-librca  
               |     | 8.0.282      | librca  |            | 8.0.282-librca     
 GraalVM       |     | 21.1.0.r16   | grl     |            | 21.1.0.r16-grl     
               |     | 21.1.0.r11   | grl     |            | 21.1.0.r11-grl     
               |     | 21.1.0.r8    | grl     |            | 21.1.0.r8-grl      
               |     | 21.0.0.2.r11 | grl     |            | 21.0.0.2.r11-grl   
               |     | 21.0.0.2.r8  | grl     |            | 21.0.0.2.r8-grl    
               |     | 20.3.2.r11   | grl     |            | 20.3.2.r11-grl     
               |     | 20.3.2.r8    | grl     |            | 20.3.2.r8-grl      
               |     | 20.3.1.2.r11 | grl     |            | 20.3.1.2.r11-grl   
               |     | 20.3.1.2.r8  | grl     |            | 20.3.1.2.r8-grl    
               |     | 19.3.6.r11   | grl     |            | 19.3.6.r11-grl     
               |     | 19.3.6.r8    | grl     |            | 19.3.6.r8-grl      
               |     | 19.3.5.r11   | grl     |            | 19.3.5.r11-grl     
               |     | 19.3.5.r8    | grl     |            | 19.3.5.r8-grl      
               |     | 19.1.0       | grl     |            | 19.1.0-grl         
 Java.net      |     | 17.ea.18     | open    |            | 17.ea.18-open      
               |     | 17.ea.6.lm   | open    |            | 17.ea.6.lm-open    
               |     | 17.ea.2.pma  | open    |            | 17.ea.2.pma-open   
               |     | 16           | open    |            | 16-open            
               |     | 16.0.1       | open    |            | 16.0.1-open        
               |     | 11.0.11      | open    |            | 11.0.11-open       
               |     | 11.0.10      | open    |            | 11.0.10-open       
               |     | 11.0.2       | open    |            | 11.0.2-open        
               |     | 8.0.292      | open    |            | 8.0.292-open       
               |     | 8.0.282      | open    |            | 8.0.282-open       
               |     | 8.0.265      | open    |            | 8.0.265-open       
 Mandrel       |     | 21.0.0.0     | mandrel |            | 21.0.0.0-mandrel   
               |     | 20.3.1.2     | mandrel |            | 20.3.1.2-mandrel   
 Microsoft     |     | 11.0.10.9    | ms      |            | 11.0.10.9-ms       
 SAP           |     | 16           | sapmchn |            | 16-sapmchn         
               |     | 16.0.1       | sapmchn |            | 16.0.1-sapmchn     
               |     | 15.0.2       | sapmchn |            | 15.0.2-sapmchn     
               |     | 11.0.11      | sapmchn |            | 11.0.11-sapmchn    
               |     | 11.0.10      | sapmchn |            | 11.0.10-sapmchn    
 TravaOpenJDK  |     | 11.0.9       | trava   |            | 11.0.9-trava       
================================================================================
Use the Identifier for installation:

    $ sdk install java 11.0.3.hs-adpt
================================================================================

```

Nahi dugun bertsioa haukeratu eta instalatu, gure kasuan 15.0.2.fx-zulu:

```bash
$ sdk install java 15.0.2.fx-zulu
```

Eta amaitzeko:
```bash
$ sdk use java 15.0.2.fx-zulu

Using java version 15.0.2.fx-zulu in this shell.
```
Ondoren exekutagarriak dauden direktorioa aldatu eta hauek erabiltzeko prest egongo gara.

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

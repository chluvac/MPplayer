 # MPplayer

Jednoduchý hudební přehrávač s možností seřazovat skladby do fronty.

- **Download:** [Release](https://github.com/chluvac/MPplayer/releases/tag/1.01)
- **Dokumentace:** [Stažení](https://github.com/chluvac/MPplayer/releases/tag/Dokumentace)
- **Repozitář:** [chluvac/MPplayer](https://github.com/chluvac/MPplayer) + [fork](http://github.com/gjkcz/MPplayer) v archivu maturitních prací
- **Autor:** Václav Chlupatý
- **Maturitní práce 2014/15** na [GJK](https://github.com/gjkcz/gjkcz)

##Dokumentace pro uživatele
###Instalace
K instalaci aplikace je potřeba pouze stažený release. Před jeho otevřením v zařízení je třeba dočasně povolit instalaci z neznámých zdrojů v Nastavení - Osobní - Zabezpečení.
###Spuštění
Aplikace má přístup pouze k soborům ve složce 'hudba' na SD kartě. Po prvním spuštění tedy musíme tyto soubory načíst tlačítkem 'aktualizovat knihovnu' a poté tlačítkem 'vše' zobrazit načtené skladby. Správně se budou zobrazovat pouze skladby s vyplněnými metadaty.
##Ovládání
Jednoduchým kliknutím na položku ji přiřadíme do fronty přehrávání, jejím podržením frontu smažeme a přehrávání začne hned. Při zobrazení fronty je možné mazat položky dlouhým stiskem.
##Dokumentace pro programátory
###Příprava
V programu [Android studio](https://developer.android.com/sdk/index.html) importujte projekt ze složky stažené z repozitáře. je potřeba projektu přiřadit SDK - ze seznamu vyberte 'android sdk' minimálně verze 15.
###Struktura kódu
Třída MainActicity spravuje uživatelské prostředí a vstup volá odpovídající metody ostatních tříd. PlaybackManager spravuje frontu a objekt MediaPlayer, který zajišťuje přehrávání souborů. DatabaseHepler vytváří databázi, se kterou poté DatabaseHandler pracuje.
AdapterCreator vytváří adaptér, který vytvoří vyžádaný náhled v uživatelském prostředí.

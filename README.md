jIECMapper
==========

Überprüft xls Daten gegen IEC Uml

0 Programmbeschreibung 

0.1 Ablauf

- Einlesen der Excel-Dateien
-- Laden der Excel-Dateien aus den Einträgen der Konfigurationsdatei
-- Einlesealgorithmus befüllt die Datenstruktur
--- Mittels jexcelapi
- Einlesen der UML-Datei
-- Einlesealgorithmus befüllt die Datenstruktur
--- Mittels eclipse.emf und eclipse.uml2
- Ausführen des Report Algorithmus

0.2 Report

Der Report wird als CSV-Datei ausgegeben. Er beinhaltet eine Tabellenstruktur,
welche in zwei Bereiche geteilt ist. Zum einen ein Auswertungsbereich im oberen Teil
der CSV-Datei und anschließend den eigentlichen Report-Bereich mit folgendem Aufbau:

Classname;Attributname;Attributtyp;inUML;inEXCEL;isEquals

Die Angabe inUML bzw. inEXCEL beziehen sich jeweils auf den entsprechenden Eintrag in Bezug
auf den jeweiligen Klassennamen oder das dazugehörige Attribut.

1 Vor dem Kompilieren

1.1 Ersetzen der Dummy Dateien in den Ordnern excel und uml

1.2 Bibliotheken herunterladen und in den entsprechenden Ordner kopieren

Benötigt werden folgende Pakte:
- org.eclipse.emf (http://www.eclipse.org/modeling/emf/ )
- org.eclipse.uml2 (http://www.eclipse.org/modeling/mdt/?project=uml2 )
- jxl (http://jexcelapi.sourceforge.net/ )

- Die Excel-Dateien müssen im Format 97-2003 vorliegen!

2 Projekt in Eclips Importieren

3 Kompilieren

4 Ausführen
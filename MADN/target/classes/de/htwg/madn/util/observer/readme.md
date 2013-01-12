Komponente Observer
=======

Die Komponente **Observer** beinhaltet die Utility-Klassen für das 
*Observer-Entwurfsmuster*. Diese Komponente sollte sich auf keine 
anderen Komponenten verlassen und alleine lauffähig sein.

###Schnittstellen

Die Komponente muss die Schnittstelle *IObserverable* umsetzen. 
Die Schnittstelle *IObserver* wird von der Klasse implementiert, 
die eine andere Klasse mit Hilfe des Observers beobachten möchte.
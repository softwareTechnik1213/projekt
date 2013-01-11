Komponente Model
=======

Die Komponente **Model** beinhaltet die Daten. Da es sich um eine
MVC-Architektur handelt greift diese Komponente auf keine weiteren Schichten
zu und befindet sich somit - bildlich gesprochen - am Boden.

###Schnittstellen

Die Komponente muss die Schnittstelle *IModelPort* umsetzen. 
Andere Komponenten verlassen sich auf diese Schnittstelle und greifen *nur*
auf diese Schnittstelle zu. 
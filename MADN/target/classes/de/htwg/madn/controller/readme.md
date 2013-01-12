Komponente Controller
=======

Die Komponente **Controller** beinhaltet die Intelligenz. Da es sich um eine
MVC-Architektur handelt greift diese Komponente nur auf die Model-Schicht zu,
niemals jedoch auf die View-Schicht.

###Schnittstellen

Die Komponente muss die Schnittstelle *IBoardControllerPort* umsetzen. 
Andere Komponenten verlassen sich auf diese Schnittstelle und greifen *nur*
auf diese Schnittstelle zu. 
Der Controller-Komponente muss die Schnittstelle IModelPort angeboten werden. 
Mit Hilfe dieser Schnittstelle wird auf die Model-Schicht zugegriffen.

###Anmerkungen

Die Controller Komponente sollte bei Änderungen am Zustand stets die *updateNotifyers*
Methode aufrufen, um die Beobachter auf die Änderungen aufmerksam zu machen.
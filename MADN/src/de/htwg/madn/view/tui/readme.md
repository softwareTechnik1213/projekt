Komponente TUI
=======

Die Komponente **TUI** beinhaltet die Darstellung in Textform. Da es sich um eine
MVC-Architektur handelt greift diese Komponente nur auf die Controller-Schicht zu.

###Schnittstellen

Auf diese Komponente wird von aussen nicht zugegriffen. Somit wird kein Interface 
benötigt.

###Anmerkungen

Die TUI kann auch als Logger verwendet werden, in dem die Datei *log4j.properties* 
angepasst wird und der TUI keine Eingabe-Strings in der main-Methode übergeben werden.
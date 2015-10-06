1. Übungsaufgabe
Entwickeln Sie in einer stark typisierten objektorientierten Sprache Ihrer Wahl (z.B. C++, Java oder C#) ein an Pac-Man angelehntes Spiel, das von bis zu drei Spieler(inne)n gleichzeitig von unterschiedlichen Computern aus spielbar ist:

Das rechteckige Spielfeld setzt sich aus aneinandergrenzenden annähernd quadratischen Feldern zusammen, die in mehreren Reihen und Spalten angeordnet sind. Spielfiguren (= Pac-Man-Figuren) können sich entlang der Reihen und Spalten von einem Feld auf ein benachbartes Feld bewegen. Manche Felder sind jedoch durch klar sichtbare Mauern, die sich zwischen den Feldern befinden, voneinander getrennt, und das gesamte Spielfeld ist ebenfalls von Mauern umgeben. Die Mauern schränken die Bewegungsmöglichkeiten der Spielfiguren ein.

Auf dem Spielfeld befinden sich drei Pac-Man-Figuren in unterschiedlichen Farben, die anfangs auf unterschiedlichen Feldern still stehen. Jede(r) Spieler(in) steuert eine Figur. Die Bewegung wird über eine Richtungstaste (unten, oben, links, rechts) gestartet, und die Figur bewegt sich mit jedem Takt des Spiels um ein Feld in diese Richtung, solange bis es an einer Mauer nicht mehr weiter geht oder über eine andere Taste die Richtung geändert wird. Bei weniger als drei Spieler(inne)n bleiben die restlichen Figuren auf dem Anfangsfeld stehen.

Auf einigen (oder allen) Feldern befinden sich anfangs Punkte. Figuren, die sich auf solche Felder bewegen, sammeln die Punkte auf, die daraufhin von den Feldern verschwinden. Ziel des Spiel ist es, in jeder Runde so viele Punkte wie möglich zu sammeln.

Wenn sich zwei Pac-Man-Figuren treffen (auf demselben Feld befinden), wird einer von ihnen gefressen. Die übrige Figur übernimmt alle in dieser Runde aufgesammelten Punkte der gefressenen Figur, und die Runde endet. Dabei spielen die Farben eine Rolle:

> Der rote Pac-Man frisst den blauen.
> Der blaue Pac-Man frisst den grünen.
> Der grüne Pac-Man frisst den roten.

Um das Spiel interessanter zu gestalten, tauschen die Figuren in regelmäßigen Zyklen ihre Farben.

Ein Spiel besteht aus mehreren Runden, jeweils mit unterschiedlichem Spielfeld, also unterschiedlicher Größe, unterschiedlichen Mauern, unterschiedlichen Punkten, unterschiedlichen Anfangspositionen der Figuren, etc.

Die Spieler(innen) können sich vor Beginn des Spiels remote mit dem Server, auf dem das Spiel läuft, verbinden und bekommen alle lokal das gleiche Fenster angezeigt. Vor allem zur Fehlersuche ist es sinnvoll, wenn auf nur einem Rechner mehrere Fenster zum selben Spiel vorhanden sein können und mehrere Pac-Man-Figuren über unterschiedliche Tasten bedienbar sind.

Änderungen und Erweiterungen der Aufgabenstellung mit dem Ziel, die Spielbarkeit oder grafische Darstellung zu verbessern, werden gerne akzeptiert.
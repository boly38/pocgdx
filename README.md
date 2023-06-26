# POCGdx

Projet LibGDX (mini jeu type PoC) capable de générer une application pour : 

- ✅ Android
- ✅ Desktop
- ✅ Html

## Specifications

- Un menu très simple accueille le joueur pour démarrer une partie ou quitter (quitter absent en mode html).
- 
- un tableau de x x y cases est présenté. Le joueur débute avec un score de tableau de `200`.

- un joueur est présent sur un bord d’un case du 1/4 haut-gauche du tableau

- une sortie existe sur un bord d’un case du 1/4 droit-bas du tableau

- le joueur peut se déplacer dans toutes les directions aux cases suivantes dans la limite du tableau.

- le joueur peut se déplacer au clavier avec les flèches ou avec des glissés (swipes du doigt ou à la souris).

- à chaque déplacement le joeur perd 1 au score.

- le jeu est terminé quand le joueur atteint la sortie. Il retourne au menu.

- le menu propose un score global de la somme des niveaux.


## Resources

Déclinaison des tutoriels de LibGDX :
- le coeur de l’application : [A Simple Game](https://libgdx.com/wiki/start/a-simple-game)
- la détection de swipe : [Gesture detection](https://libgdx.com/wiki/input/gesture-detection)

## Comment lancer le PoC pour contribuer ?

- cloner le code
- l'ouvrir avec Android Studio et gradlew 7.5

Puis ouvrir l'application [doc](https://libgdx.com/wiki/deployment/deploying-your-application#deploy-web)
- Lancer le mode `desktop`: gradle > desktop > other > `run` target
- Lancer l'application `android`: clic droid sur `android/src/com/olity/pocgdx/AndroidLauncher.java` puis `Run`
- Lancer l'application `html`:gradlew > other > `dist` (`gradlew html:dist`)
puis

````bash
# si absent installation de http-server
npm install http-server -g
# serveur local NodeJs pour héberger la version web
http-server html/build/dist
````
- iOS : non testé
# POCGdx

Projet LibGDX (mini jeu type PoC) capable de générer une application pour : 

- [OK] Android
- [OK] Desktop (gradle > desktop > other > `run` target)

## Specifications

un tableau de X x Y cases est présenté :
✅ un joueur est présent sur un bord d’un case du 1/4 haut-gauche du tableau
✅ une sortie existe sur un bord d’un case du 1/4 droit-bas du tableau
✅ le joueur peut se déplacer (au clavier ou avec des swipes) dans toutes les directions aux cases suivantes dans la limite du tableau.

## Resources

Déclinaison des tutoriels de LibGDX :
- le coeur de l’application : [A Simple Game](https://libgdx.com/wiki/start/a-simple-game)
- la détection de swipe : [Gesture detection](https://libgdx.com/wiki/input/gesture-detection)

## Limitations
- html:superDev : ne fonctionne pas correctement

Tester [src](https://libgdx.com/wiki/deployment/deploying-your-application#deploy-web)
````
gradlew html:dist
npm install http-server -g
http-server html/build/dist
````

- iOS : non testé
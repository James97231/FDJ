# BasicTest

## Description
Ce projet est un test technique pour un poste de senior developpeur. 
Il a été pensé mon une base pour une vraie application android.
Il permet de lister les équipes d'une ligue parmi une liste de ligues selectionnable par un champs text autocompleté.
Quand une ligue est selectionnée, la liste des équipes de cette ligue est affichée dans une liste comprenant un logo et un texte(nom de l'équipe).

## Choix technique
- Utilisation de JetPack compose pour le developpement
- Utilisation de "Clean architecture" avec "MVVM" et la partie Presenter inspiré de "MVI"
- Utilisation de retrofit pour les appels reseaux
- injection de dependance dagger-hilt
- Utilisation de Result pour remonter informations(Données ou exeptions)
- Il y a des fichiers ou methodes qui ne sont pas necessairent/peu utiles notamment le "NavGraph" et "TeamListEvent"
- J'ai testé en priorité la logique métier et le viewModel.

## Autres
Concernant UI afin de faciliter la verification des résultats à obtenir j'ai ajouté le nom des équipes sous leur logo.

Affichage de la liste des équipes du championnat triées par ordre anti-alphabétique en n’affichant qu’1 équipe sur 2.
-> il n'est pas indiqué par rapport a quoi on doit faire le tri du coup j'ai choisi de le faire surle champs : "strTeam"
-> 1 equipe sur 2: j'ai choisi les équipes d'index pair

## A Ameliorer
- Optimisation des recompositions

## Utilisation
- Pour lancer l'application il suffit de lancer le projet avec android studio# FDJ
Ce repo contient le test technique pour la FDJ (clean archi, MVVM, MVI, dagger-hilt, Compose, Retrofit)

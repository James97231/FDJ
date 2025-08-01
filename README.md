# BasicTest

## Description
Ce projet est un test technique pour un poste de senior developpeur.
Il a été pensé comme une base pour une vraie application android.
Il permet de lister les équipes d'une ligue parmi une liste de ligues selectionnable par un champs texte autocompleté.
Quand une ligue est selectionnée, la liste des équipes de cette ligue est affichée dans une liste comprenant un logo et un texte(nom de l'équipe).

## Choix techniques
- Utilisation de JetPack compose pour le développement
- Utilisation de "Clean architecture" avec "MVVM" et la partie Presenter inspiré de "MVI" (unidirectionnel)
- Utilisation de retrofit pour les appels reseaux
- injection de dépendance dagger-hilt
- Utilisation de Result pour remonter informations(Données ou exeptions)
- Il y a des fichiers ou methodes qui ne sont pas necessairent/peu utiles notamment le "NavGraph"
- J'ai testé en priorité la logique métier et le viewModel.

## Autres
Concernant UI afin de faciliter la vérification des résultats à obtenir j'ai ajouté le nom des équipes sous leur logo.

Affichage de la liste des équipes du championnat triées par ordre anti-alphabétique en n’affichant qu’1 équipe sur 2.
-> il n'est pas indiqué par rapport a quoi on doit faire le tri du coup j'ai choisi de le faire sur le champs : "strTeam"
-> 1 équipe sur 2: j'ai choisi les équipes d'index pair

## A Améliorer
- Des tests instrusmentés peuvent etre ajouté (à la convenance des choix de l'équipe)

## Points Forts
- Optimisation des recompositions
- Mise en place d'une version élégante et simplifiée du MVI -> Utilisation direct des fonctions du viewModel: voir commit précédent pour la comparaison

## Utilisation
- Pour lancer l'application, il suffit de lancer le projet avec android studio
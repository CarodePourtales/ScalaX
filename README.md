# Caroline de Pourtales

# ScalaX

## Pour Tester

J'ai préféré créer un objet Test avec une fonction Main pour y mettre tous mes tests car il y en a beaucoup et je préfère séparer les tests du code.

Pour les deux versions (deux packages), il faut marquer le dossier scala du mainen tant de Sources Route puis créer une Application et run.

srcMinus est la version sans classe, src est la meilleur version.

## Première implémentation 

### Classes et object compagnion

Je crée une classes Chilltime et son objet compagnion où se situent les constantes : BASE_URL, API_KEY ainsi que les caches.

### Schéma requête

Pour une recherche :

```
val query = URLEncoder.encode("TEXTE_DE_RECHERCHE", "UTF-8")
val contents = Source.fromURL(s"${ChilltimePlus.BASE_URL}/search/person?api_key=${ChilltimePlus.API_KEY}&query=${query}").mkString
val json4 = parse(contents)
val total_results = (json4 \ "total_results" \\ classOf[JInt]) (0)
```

Sinon pour plus d'informations sur un objet on utilise discover ou lieu de search.

```
contents = Source.fromURL(s"${ChilltimePlus.BASE_URL}/discover/movie?api_key=${ChilltimePlus.API_KEY}&with_cast=${actor.id}").mkString
```

Et pour en apprendre plus sur un film, on utilise movie.

```
contents = Source.fromURL(s"${ChilltimePlus.BASE_URL}/movie/${movie.id}/credits?api_key=${ChilltimePlus.API_KEY}").mkString
```

### Utilisation du cache

On crée des instances de caches dans l'objet compagnion. 

Le cache est actualisé à chaque requête si l'item n'y est pas déjà.

Il y a 4 caches : 
- private var ACTORS_NAMES    = Map\[Int, (String, String)\]() pour associer un id d'actor à son nom complet (utile pour afficher son nom à la fin lorsuq'on regarde qui a joué le plus avec qui)
- private var ACTORS_IDS      = Map\[(String, String), Int\]() pour stocker les requêtes lorsqu'on recherche l'id d'un acteur
- private var ACTORS_MOVIES   = Map\[Int, Set\[(Int, String)\]\]() pour stocker les movies associé à un acteur
- private var MOVIES_DIRECTOR = Map\[Int, (Int, String)\]() pour stocker le directeur associer à un movie
    
    
### Utilisation de fichier 

À chaque requête un fichier contenant le résultat est crée. 

L'avantage du fichier sur le cache c'est qu'il reste dans le projet même quand on relance l'app alors que le cache se réactualise à zéro.

On utilise donc les données des fichiers quand on veut retrouver les données sur un movie ou sur les films d'un acteur. 

Je n'utilise pas de fichier contenant les données sur un acteur dans la fonction findActor car le seul objectif de cette fonction est de trouver l'id de l'acteur, or pour enregistrer le fichier, il est mieux d'y mettre l'id dans le nom. Donc impossible d'enregistrer le fichier sans connaître l'id. 

## Deuxième implémentation 

### De nouvelles classes

- Actor (name, surname, id)
- Movie (id, name, director)
- Director (id, name) 

Ces nouvelles classes impliquent des caches différents, qui storent plus d'informations : 

- private var ACTORS          = List\[ActorPlus\]() pour stocker la liste d'acteurs  (utile pour afficher son nom à la fin lorsuq'on regarde qui a joué le plus avec qui)
- private var ACTORS_IDS      = Map\[(String, String), Int\]() pour stocker les requêtes lorsqu'on recherche l'id d'un acteur
- private var ACTORS_MOVIES   = Map[ActorPlus, Set\[MoviePlus\]\]() pour stocker les movies associé à un acteur
- private var MOVIES_DIRECTOR = Map\[MoviePlus, DirectorPlus\]() pour stocker le directeur associer à un movie
    
J'aurais aussi pu fusionner ACTORS et ACTORS_IDS en Map\[(String, String), ActorPlus\](), comme cela on retrouve toujours l'id d'un acteur si on a déjà cherché et en plus la liste des valeurs est la liste des acteurs mais j'ai préféré séparer les deux comme je ne les utilise pas ensemble.
    
### Avantages

- Configuration du toString => affichage des noms des acteurs, id ...
- Propreté
- Objets clairement définis
- Factorisation de code

### Inconvénients

- Plus de classes, plus gros projet pour une tâche peu compliquée 
- Il a fallu redéfinir les equals et hashcodes pour l'intersection des Set

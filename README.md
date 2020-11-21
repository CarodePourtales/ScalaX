# ScalaX

## Première implémentation 

### Classes et object compagnion

Je crée une classes Chilltime et son objet compagnion où se situent les constantes : BASE_URL, API_KEY ainsi que les caches.

Il y a 4 caches : 
- private var ACTORS_NAMES    = Map\[Int, (String, String)\]()
- private var ACTORS_IDS      = Map\[(String, String), Int\]()
- private var ACTORS_MOVIES   = Map\[Int, Set\[(Int, String)\]\]()
- private var MOVIES_DIRECTOR = Map\[Int, (Int, String)\]()
    
### Schéma requête

If we try to search  :

```
val contents = Source.fromURL(s"${ChilltimePlus.BASE_URL}/search/person?api_key=${ChilltimePlus.API_KEY}&query=${query}").mkString
val json4 = parse(contents)
val total_results = (json4 \ "total_results" \\ classOf[JInt]) (0)
```
       
If we try to learn more about an item, we use "discover"

If we try to find a movie, we use "movie"

### Utilisation de fichier 

After each request, a file is generated to store the response of the request.

To retrieve the movies where a specific actor played, we can look if the specific file exist, if not we will do a new request

### Utilisation du cache

On crée des instances de caches dans l'objet comapagnion. 

Le cache est actualisé à chaque requête si l'item n'y est pas déjà.

## Deuxième implémentation 

### De nouvelles classes

- Actor (name, surname, id)
- Movie (id, name, director)
- Director (id, name) 

- private var ACTORS          = List\[ActorPlus\]()
- private var ACTORS_IDS      = Map\[(String, String), Int\]()
- private var ACTORS_MOVIES   = Map[ActorPlus, Set\[MoviePlus\]\]()
- private var MOVIES_DIRECTOR = Map\[MoviePlus, DirectorPlus\]()
    
### Avantages

- Propreté
- Objets clairement définis
- Factorisation de code

### Inconvénients

- Plus de classes, plus gros projet pour une tâche peu compliquée 

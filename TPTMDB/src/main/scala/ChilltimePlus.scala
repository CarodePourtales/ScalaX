import java.net.URLEncoder

import scala.io.Source
import org.json4s._
import org.json4s.native.JsonMethods._
import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths}

import scala.collection.immutable.ListMap


class ChilltimePlus extends App {

    def findActor(name: String, surname: String): Option[ActorPlus] = {
        if (ChilltimePlus.ACTORS_IDS.contains((name,surname))) {
            return Some(new ActorPlus(name, surname, ChilltimePlus.ACTORS_IDS((name,surname))))
        }
        else {
            val query = URLEncoder.encode(name + ' ' + surname, "UTF-8")
            val contents = Source.fromURL(s"${ChilltimePlus.BASE_URL}/search/person?api_key=${ChilltimePlus.API_KEY}&query=${query}").mkString
            val json4 = parse(contents)
            val total_results = (json4 \ "total_results" \\ classOf[JInt]) (0)
            if (total_results > 0) {
                val id = ((json4 \ "results") (0) \ "id" \\ classOf[JInt]) (0).toInt

                //file
                val writer = new PrintWriter(new File(s"data/actor${id}.txt"))
                writer.write(contents)
                writer.close()

                //cache
                ChilltimePlus.ACTORS.::(new ActorPlus(name, surname, id))
                ChilltimePlus.ACTORS_IDS   += (name, surname) -> id

                return Some(new ActorPlus(name, surname, id))
            }
            return None
        }
    }

    def findActorMovies(actor: ActorPlus): Set[MoviePlus] = {
        if (ChilltimePlus.ACTORS_MOVIES.contains(actor)) {
            return ChilltimePlus.ACTORS_MOVIES(actor)
        }
        else {
            var contents = new String()
            if (Files.exists(Paths.get(s"data/actormovies${actor.id_}.txt"))) {
                //use data from file if file exist
                contents = Source.fromFile(s"data/actormovies${actor.id_}.txt").mkString
            } else {
                contents = Source.fromURL(s"${ChilltimePlus.BASE_URL}/discover/movie?api_key=${ChilltimePlus.API_KEY}&with_cast=${actor.id_}").mkString
                //file
                val writer = new PrintWriter(new File(s"data/actormovies${actor.id_}.txt"))
                writer.print(contents)
                writer.close()
            }
            val json4 = parse(contents)
            val actorMoviesQuery = (json4 \ "results").children
            var actorMovies = Set[MoviePlus]()
            for (movie <- actorMoviesQuery) {
                var idMovie = (movie \ "id" \\ classOf[JInt]) (0).toInt
                var nameMovie = (movie \ "title" \\ classOf[JString]) (0).toString
                actorMovies += new MoviePlus(idMovie, nameMovie)
            }

            //cache
            ChilltimePlus.ACTORS_MOVIES += actor -> actorMovies
            return actorMovies
        }
    }

    def findMovieDirector(movie: MoviePlus): Option[DirectorPlus] = {
        if (ChilltimePlus.MOVIES_DIRECTOR.contains(movie)) {
            return Some(ChilltimePlus.MOVIES_DIRECTOR(movie))
        }
        else {
            val contents = Source.fromURL(s"${ChilltimePlus.BASE_URL}/movie/${movie.id}/credits?api_key=${ChilltimePlus.API_KEY}").mkString

            //file
            val writer = new PrintWriter(new File(s"data/movie${movie.id}.txt"))
            writer.print(contents)
            writer.close()

            val json4 = parse(contents)
            val crewTeam = (json4 \ "crew").children
            for (crew <- crewTeam) {
                if ((crew \ "job" \\ classOf[JString]) (0).toString == "Director") {
                    val name = (crew \ "name" \\ classOf[JString]) (0).toString
                    val idDirector = (crew \ "id" \\ classOf[JInt]) (0).toInt
                    val director = new DirectorPlus(idDirector, name)
                    movie.director = director

                    //cache
                    ChilltimePlus.MOVIES_DIRECTOR += movie -> director

                    return Some(director)
                }
            }
            return None
        }
    }

    def request(actor1: ActorPlus, actor2: ActorPlus): Set[(DirectorPlus, MoviePlus)] = {
        var directorMovies = Set[(DirectorPlus, MoviePlus)]()
        val movieActor1 = findActorMovies(findActor(actor1.name, actor1.surname).getOrElse(-1))
        val movieActor2 = findActorMovies(findActor(actor2.name, actor2.surname).getOrElse(-1))
        //Set of movies where both actors played
        val commonActorsMovies = movieActor1 & movieActor2
        for (movie <- commonActorsMovies) {
            var director:DirectorPlus = findMovieDirector(movie).getOrElse(-1)
            directorMovies += ((director,movie))
        }

        return directorMovies
    }

    def actorsMostPlayingTogether (): Map[(ActorPlus, ActorPlus), Int] = {
        var pairs = Map[(ActorPlus, ActorPlus), Int]()
        //key is the pairs of actors ids, value is the number of common movies
        for (actor1 <- ChilltimePlus.ACTORS_MOVIES.keys.slice(0,ChilltimePlus.ACTORS_MOVIES.keys.size/2)) {
            for (actor2 <- ChilltimePlus.ACTORS_MOVIES.keys.slice(ChilltimePlus.ACTORS_MOVIES.keys.size/2,ChilltimePlus.ACTORS_MOVIES.keys.size)) {
                val nbCommonMovies = (ChilltimePlus.ACTORS_MOVIES(actor1) & ChilltimePlus.ACTORS_MOVIES(actor2)).size
                pairs += (actor1, actor2) -> nbCommonMovies
            }
        }
        //5 best couples of actors
        pairs = ListMap(pairs.toSeq.sortWith(_._2 > _._2):_*).slice(0,5)
        return pairs
    }

}

object ChilltimePlus  {

    val BASE_URL  = "https://api.themoviedb.org/3"
    val API_KEY    = "834e37e0f21b3d8979fc83710a14097f"

    //cache
    private var ACTORS          = List[ActorPlus]()
    private var ACTORS_IDS      = Map[(String, String), Int]()
    private var ACTORS_MOVIES   = Map[ActorPlus, Set[MoviePlus]]()
    private var MOVIES_DIRECTOR = Map[MoviePlus, DirectorPlus]()

}

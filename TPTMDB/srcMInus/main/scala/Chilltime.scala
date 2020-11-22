import java.net.URLEncoder

import scala.io.Source
import org.json4s._
import org.json4s.native.JsonMethods._
import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths}

import scala.collection.immutable.ListMap


class Chilltime extends App{

    def findActorId(name: String, surname: String): Option[Int] = {
        if (Chilltime.ACTORS_IDS.contains((name,surname))) {
            return Some(Chilltime.ACTORS_IDS((name,surname)))
        }
        else {
            val query = URLEncoder.encode(name + ' ' + surname, "UTF-8")
            val contents = Source.fromURL(s"${Chilltime.BASE_URL}/search/person?api_key=${Chilltime.API_KEY}&query=${query}").mkString
            val json4 = parse(contents)
            val total_results = (json4 \ "total_results" \\ classOf[JInt]) (0)
            if (total_results > 0) {
                val id = ((json4 \ "results") (0) \ "id" \\ classOf[JInt]) (0).toInt

                //cache
                Chilltime.ACTORS_NAMES += id -> (name, surname)
                Chilltime.ACTORS_IDS   += (name, surname) -> id

                return Some(id)
            }
            return None
        }
    }

    def findActorMovies(id: Int): Set[(Int, String)] = {
        if (Chilltime.ACTORS_MOVIES.contains(id)) {
            return Chilltime.ACTORS_MOVIES(id)
        }
        else {
            var contents = new String()
            if (Files.exists(Paths.get(s"data/actormovies${id}.txt"))) {
                //use data from file if file exist
                contents = Source.fromFile(s"data/actormovies${id}.txt").mkString
            } else {
                contents = Source.fromURL(s"${Chilltime.BASE_URL}/discover/movie?api_key=${Chilltime.API_KEY}&with_cast=${id}").mkString
                //file
                val writer = new PrintWriter(new File(s"data/actormovies${id}.txt"))
                writer.print(contents)
                writer.close()
            }
            val json4 = parse(contents)
            val actorMoviesQuery = (json4 \ "results").children
            var actorMovies = Set[(Int, String)]()
            for (movie <- actorMoviesQuery) {
                var idMovie = (movie \ "id" \\ classOf[JInt]) (0).toInt
                var nameMovie = (movie \ "title" \\ classOf[JString]) (0).toString
                actorMovies += ((idMovie, nameMovie))
            }

            //cache
            Chilltime.ACTORS_MOVIES += id -> actorMovies
            return actorMovies
        }
    }

    def findMovieDirector(id: Int): Option[(Int, String)] = {
        if (Chilltime.MOVIES_DIRECTOR.contains(id)) {
            return Some(Chilltime.MOVIES_DIRECTOR(id))
        }
        else {
            var contents = new String()
            if (Files.exists(Paths.get(s"data/movie${id}.txt"))) {
                //use data from file if file exist
                contents = Source.fromFile(s"data/movie${id}.txt").mkString
            } else {
                contents = Source.fromURL(s"${Chilltime.BASE_URL}/movie/${id}/credits?api_key=${Chilltime.API_KEY}").mkString
                //file
                val writer = new PrintWriter(new File(s"data/movie${id}.txt"))
                writer.print(contents)
                writer.close()
            }
            val json4 = parse(contents)
            val crewTeam = (json4 \ "crew").children
            for (crew <- crewTeam) {
                if ((crew \ "job" \\ classOf[JString]) (0).toString == "Director") {
                    val name = (crew \ "name" \\ classOf[JString]) (0).toString
                    val idDirector = (crew \ "id" \\ classOf[JInt]) (0).toInt

                    //cache
                    Chilltime.MOVIES_DIRECTOR += id -> (idDirector, name)

                    return Some((idDirector, name))
                }
            }
            return None
        }
    }

    def request(actor1: Int, actor2: Int): Set[(String, String)] = {
        var directorMovies = Set[(String, String)]()
        val movieActor1 = findActorMovies(actor1)
        val movieActor2 = findActorMovies(actor2)
        //Set of movies where both actors played
        val commonActorsMovies = movieActor1 & movieActor2
        for ((idMovie,movie) <- commonActorsMovies) {
            var director = findMovieDirector(idMovie).getOrElse((-1,""))
            var nameDirector = director._2
            directorMovies += ((nameDirector,movie))
        }

        return directorMovies
    }

    def actorsMostPlayingTogether (): Map[((String, String), (String, String)), Int] = {
        var pairs = Map[((String, String), (String, String)), Int]()
        //key is the pairs of actors ids, value is the number of common movies
        for (actor1 <- Chilltime.ACTORS_MOVIES.keys.slice(0,Chilltime.ACTORS_MOVIES.keys.size/2)) {
            for (actor2 <- Chilltime.ACTORS_MOVIES.keys.slice(Chilltime.ACTORS_MOVIES.keys.size/2,Chilltime.ACTORS_MOVIES.keys.size)) {
                val nbCommonMovies = (findActorMovies(actor1) & findActorMovies(actor2)).size
                pairs += (Chilltime.ACTORS_NAMES(actor1) , Chilltime.ACTORS_NAMES(actor2)) -> nbCommonMovies
            }
        }
        //5 best couples of actors
        pairs = ListMap(pairs.toSeq.sortWith(_._2 > _._2):_*).slice(0,5)
        return pairs
    }

}

object Chilltime  {

    val BASE_URL  = "https://api.themoviedb.org/3"
    val API_KEY    = "834e37e0f21b3d8979fc83710a14097f"

    //cache
    private var ACTORS_NAMES    = Map[Int, (String, String)]()
    private var ACTORS_IDS      = Map[(String, String), Int]()
    private var ACTORS_MOVIES   = Map[Int, Set[(Int, String)]]()
    private var MOVIES_DIRECTOR = Map[Int, (Int, String)]()

}

import java.io.PrintWriter
import scala.io.Source

object Test {

    def main(args: Array[String]): Unit = {

        print("Version avec peu de classes \n")

        var chilltime = new Chilltime()

        //some actors
        print("Some actors \n")
        print("Meryl Streep : " + chilltime.findActorId("Streep", "Meryl")   + "\n")
        print("Orlando Bloom: " + chilltime.findActorId("Bloom", "Orlando")  + "\n")
        print("Nicole Kidman : " + chilltime.findActorId("Kidman", "Nicole")  + "\n")
        print("Johnny Depp : " + chilltime.findActorId("Depp", "Johnny")    + "\n")
        print("Tom Cruise : " + chilltime.findActorId("Cruise", "Tom")     + "\n")
        print("Julia Roberts : " + chilltime.findActorId("Roberts", "Julia")  + "\n")
        print("Amanda Seyfried : " + chilltime.findActorId("Seyfried", "Amanda")+ "\n \n")

        //movies with actors
        print("Movies of some actors \n")
        print(chilltime.findActorMovies(114) + "\n")
        print(chilltime.findActorMovies(5064)+ "\n")
        print(chilltime.findActorMovies(2227)+ "\n \n")

        //Common movies with Meryl Streep #best actress
        print("Common movies between Meryl Streep and Amanda Seyfried \n")
        print(chilltime.request(71070, 5064) + "\n \n")

        //A sorted Map linking pairs of actors and the number of movies they played in together
        print("A sorted Map linking pairs of actors and the number of movies they played in together \n")
        print(chilltime.actorsMostPlayingTogether() + "\n \n")
    }

}

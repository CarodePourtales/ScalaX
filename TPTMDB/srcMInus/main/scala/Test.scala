import java.io.PrintWriter
import scala.io.Source

object Test extends App {

    var chilltime = new Chilltime()

    print(chilltime.findActorId("Streep", "Meryl"))
    print(chilltime.findActorId("Bloom", "Orlando"))
    print(chilltime.findActorId("Kidman", "Nicole"))
    print(chilltime.findActorId("Depp", "Johnny"))
    print(chilltime.findActorId("Cruise", "Tom"))
    print(chilltime.findActorId("Roberts", "Julia"))
    print(chilltime.findActorId("Seyfried", "Amanda"))

    //Movies with Orlando Bloom
    print(chilltime.findActorMovies(114))
    //Meryl Streep
    print(chilltime.findActorMovies(5064))
    //Nicole Kidman
    print(chilltime.findActorMovies(2227))

    val actor1 = new ActorPlus("Streep", "Meryl")
    val actor2 = new ActorPlus("Seyfried", "Amanda")
    val actor3 = new ActorPlus("Kidman", "Nicole")
    val actor4 = new ActorPlus("Kidman", "Nicole")

    print(chilltime.request(actor1, actor2))

}

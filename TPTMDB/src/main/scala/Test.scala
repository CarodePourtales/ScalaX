object Test extends App {

    var chilltimePlus = new ChilltimePlus()

    val actor1:ActorPlus = chilltimePlus.findActor("Streep", "Meryl").getOrElse(new ActorPlus ("","",-1))
    val actor2:ActorPlus = chilltimePlus.findActor("Bloom", "Orlando").getOrElse(new ActorPlus ("","",-1))
    val actor3:ActorPlus = chilltimePlus.findActor("Kidman", "Nicole").getOrElse(new ActorPlus ("","",-1))
    val actor4:ActorPlus = chilltimePlus.findActor("Depp", "Johnny").getOrElse(new ActorPlus ("","",-1))
    val actor5:ActorPlus = chilltimePlus.findActor("Cruise", "Tom").getOrElse(new ActorPlus ("","",-1))
    val actor6:ActorPlus = chilltimePlus.findActor("Roberts", "Julia").getOrElse(new ActorPlus ("","",-1))
    val actor7:ActorPlus = chilltimePlus.findActor("Seyfried", "Amanda").getOrElse(new ActorPlus ("","",-1))

    //Movies with Orlando Bloom
    print(chilltimePlus.findActorMovies(actor1))
    print(chilltimePlus.findActorMovies(actor7))
    print(chilltimePlus.findActorMovies(actor3))

    print(chilltimePlus.request(actor1, actor7))
    print(chilltimePlus.actorsMostPlayingTogether ())
}
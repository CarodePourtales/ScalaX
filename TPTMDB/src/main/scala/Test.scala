object Test {

  def main(args: Array[String]) {

    var chilltimePlus = new ChilltimePlus()

    val actor1:ActorPlus = chilltimePlus.findActor("Streep", "Meryl").getOrElse(-1)
    val actor2:ActorPlus = chilltimePlus.findActor("Bloom", "Orlando").getOrElse(-1)
    val actor3:ActorPlus = chilltimePlus.findActor("Kidman", "Nicole").getOrElse(-1)
    val actor4:ActorPlus = chilltimePlus.findActor("Depp", "Johnny").getOrElse(-1)
    val actor5:ActorPlus = chilltimePlus.findActor("Cruise", "Tom").getOrElse(-1)
    val actor6:ActorPlus = chilltimePlus.findActor("Roberts", "Julia").getOrElse(-1)
    val actor7:ActorPlus = chilltimePlus.findActor("Seyfried", "Amanda").getOrElse(-1)

    //Movies with Orlando Bloom
    print(chilltimePlus.findActorMovies(actor1))
    //Meryl Streep
    print(chilltimePlus.findActorMovies(actor2))
    //Nicole Kidman
    print(chilltimePlus.findActorMovies(actor3))

    print(chilltimePlus.request(actor1, actor2))
  }
}
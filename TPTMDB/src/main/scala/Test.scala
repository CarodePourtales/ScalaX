object Test  {

    def main(args: Array[String]): Unit = {

        print("Version améliorée \n")

        var chilltimePlus = new ChilltimePlus()

        val actor1: ActorPlus = chilltimePlus.findActor("Streep", "Meryl").getOrElse(new ActorPlus("", "", -1))
        val actor2: ActorPlus = chilltimePlus.findActor("Bloom", "Orlando").getOrElse(new ActorPlus("", "", -1))
        val actor3: ActorPlus = chilltimePlus.findActor("Kidman", "Nicole").getOrElse(new ActorPlus("", "", -1))
        val actor4: ActorPlus = chilltimePlus.findActor("Depp", "Johnny").getOrElse(new ActorPlus("", "", -1))
        val actor5: ActorPlus = chilltimePlus.findActor("Cruise", "Tom").getOrElse(new ActorPlus("", "", -1))
        val actor6: ActorPlus = chilltimePlus.findActor("Roberts", "Julia").getOrElse(new ActorPlus("", "", -1))
        val actor7: ActorPlus = chilltimePlus.findActor("Seyfried", "Amanda").getOrElse(new ActorPlus("", "", -1))

        //some actors
        print("Some actors \n")
        print(actor1 + "\n")
        print(actor7 + "\n")
        print(actor2 + "\n \n" )

        //movies with actors
        print("Movies of some actors \n")
        print(chilltimePlus.findActorMovies(actor1) + "\n")
        print(chilltimePlus.findActorMovies(actor2) + "\n")
        print(chilltimePlus.findActorMovies(actor3) + "\n")
        print(chilltimePlus.findActorMovies(actor4) + "\n")
        print(chilltimePlus.findActorMovies(actor5) + "\n")
        print(chilltimePlus.findActorMovies(actor6) + "\n")
        print(chilltimePlus.findActorMovies(actor7) + "\n \n")

        //Common movies with Meryl Streep #best actress
        print("Common movies between Meryl Streep and Amanda Seyfried \n")
        print(chilltimePlus.request(actor1, actor7) + "\n \n")

        //A sorted Map linking pairs of actors and the number of movies they played in together
        print("A sorted Map linking pairs of actors and the number of movies they played in together \n")
        print(chilltimePlus.actorsMostPlayingTogether() + "\n \n")
    }
}
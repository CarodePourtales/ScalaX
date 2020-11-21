
class ActorPlus(val name :String, val surname :String, var id : Int) {

  override def toString() : String = {
    return "id : " + this.id + " , name : " + this.name + ", surname = " + this.surname ;
  }
  
}

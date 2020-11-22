
class ActorPlus(val name :String, val surname :String, var id : Int) {

  override def equals(that: Any): Boolean = {
    that match {
      case that: ActorPlus => {
        this.id == that.id
      }
      case _ =>
        false
    }
  }

  override def hashCode: Int = {
    val prime = 17
    var result = 1
    result = prime * result + id;
    result = prime * result + (if (name == null) 0 else name.hashCode)
    result
  }

  override def toString() : String = {
    return "id : " + this.id + " , name : " + this.name + ", surname = " + this.surname ;
  }
  
}

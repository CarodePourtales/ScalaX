class ActorPlus(val name :String, val surname :String, var id_ : Int) {

  def name = name

  def surname = surname

  def id = id_

  override def toString() : String = {
    return "[id : " + id + " , name : " + name + ", surname = " + surname + "]" ;
  }
  
}

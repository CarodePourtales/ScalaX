class MoviePlus(val id: Int, val name: String, var director_ : DirectorPlus= new DirectorPlus()){

  def id = id

  def name = name

  def director_=(d: DirectorPlus) = director_ = d;

  def director = director_

  override def toString = "[id : " + id + ", Movie name = " + name +"]";

}

class MoviePlus(val id: Int, val name: String, var director_ : DirectorPlus= new DirectorPlus()){

  def director_=(d: DirectorPlus) = director_ = d;

  def director = director_

  override def equals(that: Any): Boolean = {
    that match {
      case that: MoviePlus => {
          this.id == that.id
      }
      case _ =>
        false
    }
  }

  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + id;
    result = prime * result + (if (name == null) 0 else name.hashCode)
    result
  }

  override def toString = "id : " + this.id + ", Movie name = " + this.name;

}

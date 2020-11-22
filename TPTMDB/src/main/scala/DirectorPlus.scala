class DirectorPlus(val id: Int=0, val name: String=""){

  override def equals(that: Any): Boolean = {
    that match {
      case that: DirectorPlus => {
        this.id == that.id
      }
      case _ =>
        false
    }
  }

  override def hashCode: Int = {
    val prime = 13
    var result = 1
    result = prime * result + id;
    result = prime * result + (if (name == null) 0 else name.hashCode)
    result
  }

  override def toString = "id : " + this.id + ", name = " + this.name;

}

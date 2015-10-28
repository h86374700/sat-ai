import org.apache.spark.mllib.linalg.{SparseVector, Vectors}

/**
 * Created by hyang on 10/28/15.
 */
case class Basket(items: Set[String]) {

  def ++(right: Basket): Basket = {
    Basket(this.items ++ right.items)
  }

  def toSparse(vocabulary: Map[String, Int]): SparseVector = {
    val values = items.collect{case x if (vocabulary.contains(x)) => (vocabulary(x), 1.0)}
    Vectors.sparse(vocabulary.size, values.toSeq).toSparse
  }
}

object Basket {
  def apply(s: String) = new Basket(s.toLowerCase.replaceAll("[-+?_,.():\"']", " ").split(" ").map(x => Stemmer.stem(x.trim)).filter(_.size > 0).toSet)
}

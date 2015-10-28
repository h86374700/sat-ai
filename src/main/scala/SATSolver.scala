import org.apache.spark.mllib.linalg.SparseVector
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by hyang on 10/28/15.
 */
object SATSolver {

  def main (args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("SAT Solver").setMaster("local[*]"))

    val dataset = Loader.tsv(sc, "input/training_set.tsv")

    val toBaskets = dataset.map(x => Basket(x.question))

    val count = toBaskets.count

    val stopwords = toBaskets.flatMap(x => x.items.map((_, 1))).reduceByKey(_ + _).filter(x => x._2 > count * 0.1).keys.collect.toSet

    val vocabulary = toBaskets.reduce(_ ++ _).items.diff(stopwords).zipWithIndex.toMap

    val vectors = toBaskets.map(_.toSparse(vocabulary))

    toBaskets.take(5).foreach{ x =>
      println(x)
      println(support(x.toSparse(vocabulary), vectors))
    }
  }

  def support(item: SparseVector, market: RDD[SparseVector]): Long = {
    market.filter(x => item.indices.diff(x.indices).size == 0).count
  }

}

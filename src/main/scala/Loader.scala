import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * Created by hyang on 10/28/15.
 */
object Loader {

  def tsv(sc: SparkContext, path: String): RDD[Question] = {
    sc.textFile(path)
      .filter(!_.startsWith("id"))
      .map(x => x.split("\t"))
      .map(x => Question(x))
  }
}

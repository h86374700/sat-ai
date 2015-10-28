/**
 * Created by hyang on 10/28/15.
 */
case class Question(id: Long, question: String, correctAnswer: String, answerA: String, answerB: String, answerC: String, answerD: String)

object Question {

  def apply(list: Array[String]): Question = {
    new Question(list(0).toLong, list(1), list(2), list(3), list(4), list(5), list(6))
  }
}

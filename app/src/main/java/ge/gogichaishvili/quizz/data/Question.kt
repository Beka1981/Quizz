package ge.gogichaishvili.quizz.data

data class Question(
    val id: Int,
    val question: String,
    val answers: List<Answer>,
    val rightAnswer: Int
)

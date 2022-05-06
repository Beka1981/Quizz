package ge.gogichaishvili.quizz.data

data class Answer(
    val id: Int,
    val answer: String,
    var hasAlreadyAnswered: Boolean = false,
    var shouldUpdateUi: Boolean = false
)

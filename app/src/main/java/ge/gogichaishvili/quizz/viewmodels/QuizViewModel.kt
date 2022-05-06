package ge.gogichaishvili.quizz.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.gogichaishvili.quizz.data.AnswerStatistic
import ge.gogichaishvili.quizz.data.FinalQuizResult
import ge.gogichaishvili.quizz.data.Question
import ge.gogichaishvili.quizz.data.QuizData.getQuizData
import kotlinx.coroutines.*

class QuizViewModel : ViewModel() {

    private var WRONG_ANSWERS_LIMIT = 3
    private var EXAM_TIME_LIMIT_IN_SECONDS = 30L

    private val _currentQuestionLiveData = MutableLiveData<Question?>()
    val currentQuestionLiveData: LiveData<Question?> get() = _currentQuestionLiveData

    private val _answerStatisticLiveData = MutableLiveData<AnswerStatistic>()
    val answerStatisticLiveData: LiveData<AnswerStatistic> get() = _answerStatisticLiveData

    private var currentQuestionIndex = -1
    private var answerStatistic = AnswerStatistic()

    private lateinit var quizData: MutableList<Question>

    fun startQuiz() {
        quizData = getQuizData()
        getNextQuestion()
        startTimer()
    }

    fun getNextQuestion() {
        if (currentQuestionIndex < quizData.size - 1) {
            currentQuestionIndex++
        }
        _currentQuestionLiveData.postValue(getQuizDataByIndex(currentQuestionIndex))
    }

    fun getPreviousQuestion() {
        if (currentQuestionIndex != 0) {
            currentQuestionIndex--
        }
        _currentQuestionLiveData.postValue(getQuizDataByIndex(currentQuestionIndex))
    }

    private fun getQuizDataByIndex(index: Int): Question? {
        if (quizData.size > currentQuestionIndex) {
            return quizData[index]
        }
        return null
    }

    fun getTotalQuizNumber(): Int {
        return quizData.size
    }

    fun onRightAnswerClicked() {
        answerStatistic.correctAnswerCount++
        _answerStatisticLiveData.postValue(answerStatistic)
    }

    fun onWrongAnswerClicked() {
        answerStatistic.wrongAnswerCount++
        _answerStatisticLiveData.postValue(answerStatistic)
    }

    private val _finalExamResult = MutableLiveData<FinalQuizResult>()
    val finalExamResult: LiveData<FinalQuizResult> get() = _finalExamResult

    private val _timeProgressLiveData = MutableLiveData<String>()
    val timeProgressLiveData: LiveData<String> get() = _timeProgressLiveData

    private fun Long.secondsAsTime(): String {
        val m: Int = (this % 3600 / 60).toInt()
        val s: Int = (this % 60).toInt()
        val mf = if (m == 0) "00" else if (m < 10) "0$m" else "$m"
        val sf = if (s == 0) "00" else if (s < 10) "0$s" else "$s"
        return "$mf:$sf"
    }

    private var timerJob: Job? = null
    private fun startTimer() {
        timerJob = viewModelScope.launch(Dispatchers.Main) {
            var currentTimeProgress = EXAM_TIME_LIMIT_IN_SECONDS
            withContext(Dispatchers.Default) {
                while (currentTimeProgress >= 0 && isActive) {
                    currentTimeProgress--
                    delay(1000)
                    withContext(Dispatchers.Main) {
                        _timeProgressLiveData.postValue(currentTimeProgress.secondsAsTime())
                    }
                }
                withContext(Dispatchers.Main) {
                    if (getTotalQuizNumber() - answerStatistic.correctAnswerCount <= WRONG_ANSWERS_LIMIT) {
                        _finalExamResult.postValue(FinalQuizResult.SUCCESS)
                    } else {
                        _finalExamResult.postValue(FinalQuizResult.FAILED_TIME_OUT)
                    }
                }
            }
        }
    }

    fun onAnswerSelected() {
        if (answerStatistic.wrongAnswerCount > WRONG_ANSWERS_LIMIT) {
            timerJob?.cancel()
            _finalExamResult.postValue(FinalQuizResult.FAILED_WRONG_ANSWER_LIMIT)
        } else {
            if (getTotalQuizNumber() - answerStatistic.correctAnswerCount <= WRONG_ANSWERS_LIMIT &&
                answerStatistic.correctAnswerCount + answerStatistic.wrongAnswerCount == getTotalQuizNumber()
            ) {
                timerJob?.cancel()
                _finalExamResult.postValue(FinalQuizResult.SUCCESS)
            }
        }
    }

    fun startQuizAgain() {
        timerJob?.cancel()
        _answerStatisticLiveData.postValue(AnswerStatistic(0, 0))
    }

    fun getScore(): Float {
        return (answerStatistic.correctAnswerCount.toFloat() / quizData.size.toFloat()) * 100
    }
}
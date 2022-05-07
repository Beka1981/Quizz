package ge.gogichaishvili.quizz.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ge.gogichaishvili.quizz.R
import ge.gogichaishvili.quizz.adapters.QuizAdapter
import ge.gogichaishvili.quizz.data.FinalQuizResult
import ge.gogichaishvili.quizz.databinding.FragmentQuizBinding
import ge.gogichaishvili.quizz.tools.Tools
import ge.gogichaishvili.quizz.viewmodels.QuizViewModel

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<QuizViewModel>()

    private lateinit var adapter: QuizAdapter

    private var start: Long? = null
    private var end: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startQuiz() //ეს პირდაპირ viewModel ის ინიტში შეგიძლია გამოიძახო
        start = System.currentTimeMillis() //ვიუმოდელის საქმეა უფრო ამის დათვლა

        viewModel.currentQuestionLiveData.observe(viewLifecycleOwner) { question ->
            binding.tvQuestion.text = question?.question.toString()
            binding.tvCount.text = "${question?.id}/${viewModel.getTotalQuizNumber()}"

            binding.rvAnswersRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter =
                QuizAdapter() // ეს ყოველ შემოსვლაზე დაესეტებაა ხელახლა overhead არის, ერთხელ დაუსეტე observe-ის გარეთ
            adapter.setData(question!!.answers)
            binding.rvAnswersRecyclerView.adapter = adapter.apply {
                setOnItemCLickListener { answer ->
                    if (answer.id == question.rightAnswer) {
                        viewModel.onRightAnswerClicked() /*1 ) სამი სხვადასხვა ქოლია ვიუმოდელზე
                         ამ სამის ერთში გატანა შეიძლებოდა და ვიუმოდელში გარჩევა იმის თუ არასწორი იყო თუ არა პასუხი
                         შემდეგ რესაიქლერვიუს დააფდეითება შესაბამისად
                        */
                        Tools.playSound(requireContext(), R.raw.success)
                    } else {
                        viewModel.onWrongAnswerClicked() //2)
                        Tools.playSound(requireContext(), R.raw.los)
                    }
                    answer.shouldUpdateUi = true //ამ ცვლადს სხვა სახელს დავარქმევდი
                    adapter.disableClicks()
                    adapter.updateAll(question.answers)
                    viewModel.onAnswerSelected() // 3)
                }
            }

        }

        binding.btnNext.setOnClickListener {
            Tools.playSound(requireContext(), R.raw.click)
            viewModel.getNextQuestion()
        }

        binding.btnPrevious.setOnClickListener {
            Tools.playSound(requireContext(), R.raw.click)
            viewModel.getPreviousQuestion()
        }


        viewModel.answerStatisticLiveData.observe(viewLifecycleOwner) {
            binding.examTestDashboardCorrect.text = it.correctAnswerCount.toString()
            binding.examTestDashboardWrong.text = it.wrongAnswerCount.toString()
        }

        viewModel.timeProgressLiveData.observe(viewLifecycleOwner) {
            binding.examTestDashboardTimer.text = it
        }

        viewModel.finalExamResult.observe(viewLifecycleOwner) {
            val percent = viewModel.getScore().toString()
            end = System.currentTimeMillis() - start!! //ვიუმოდელის საქმეა ესეც
            when (it) {
                FinalQuizResult.SUCCESS -> {
                    val resultFragment = ResultFragment.newInstance(
                        percent, Tools.millisecondsConverter(
                            end!!
                        )
                    )
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainerView, resultFragment)
                        addToBackStack(resultFragment::javaClass.name)
                        commit()
                    }
                }
                FinalQuizResult.FAILED_TIME_OUT -> {
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainerView, LoseFragment())
                        addToBackStack(LoseFragment::javaClass.name)
                        commit()
                    }
                }
                FinalQuizResult.FAILED_WRONG_ANSWER_LIMIT -> {
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainerView, LoseFragment())
                        addToBackStack(LoseFragment::javaClass.name)
                        commit()
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package ge.gogichaishvili.quizz.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ge.gogichaishvili.quizz.R
import ge.gogichaishvili.quizz.databinding.FragmentResultBinding
import ge.gogichaishvili.quizz.tools.Tools
import ge.gogichaishvili.quizz.viewmodels.QuizViewModel

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<QuizViewModel>()

    private var percent = String()
    private var time = String()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("PERCENT")?.let { // const val ად უნდა გაიტანო key
            percent = it
        }
        arguments?.getString("TIME")?.let {
            time = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Tools.playSound(requireContext(), R.raw.applause)

        binding.tvScore.text = "${getString(R.string.score)} $percent %"
        binding.tvTime.text = "${getString(R.string.time)} $time"

        binding.tvHome.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, MainFragment())
                addToBackStack(MainFragment::javaClass.name)
                commit()
            }
        }

        binding.btnReplay.setOnClickListener {
            viewModel.startQuizAgain().toString()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, QuizFragment())
                addToBackStack(QuizFragment::javaClass.name)
                commit()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(percent: String, time: String) = ResultFragment().apply {
            arguments = Bundle().apply {
                putString("PERCENT", percent)
                putString("TIME", time)
            }
        }
    }
}


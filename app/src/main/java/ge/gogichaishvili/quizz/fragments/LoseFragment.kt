package ge.gogichaishvili.quizz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ge.gogichaishvili.quizz.R
import ge.gogichaishvili.quizz.databinding.FragmentLoseBinding
import ge.gogichaishvili.quizz.tools.Tools
import ge.gogichaishvili.quizz.viewmodels.QuizViewModel

class LoseFragment : Fragment() {

    private var _binding: FragmentLoseBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<QuizViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoseBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Tools.playSound(requireContext(), R.raw.busy)

        binding.tvHome.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, MainFragment())
                addToBackStack(MainFragment::javaClass.name)
                commit()
            }
        }

        binding.btnReplay.setOnClickListener {

            viewModel.startQuizAgain()

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

}
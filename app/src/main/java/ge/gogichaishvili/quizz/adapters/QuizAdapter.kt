package ge.gogichaishvili.quizz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.gogichaishvili.quizz.R
import ge.gogichaishvili.quizz.data.Answer

import ge.gogichaishvili.quizz.databinding.ListItemBinding

class QuizAdapter () : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    private val answersList = mutableListOf<Answer>()
    private lateinit var itemClickListener: (Answer) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bindData(answersList[position])
    }

    fun setOnItemCLickListener(clickListener: (Answer) -> Unit) {
        itemClickListener = clickListener
    }

    override fun getItemCount(): Int {
        return answersList.size
    }

    fun setData (answer: List<Answer>) {
        answersList.clear()
        answersList.addAll(answer)
        notifyDataSetChanged()
    }

    fun updateAll(list: List<Answer>) {
        answersList.clear()
        answersList.addAll(list)
        notifyDataSetChanged()
    }

    fun disableClicks() {
        answersList.forEach { it.hasAlreadyAnswered = true }
    }

    inner class QuizViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(answer: Answer) {

            binding.tvAnswer.text = answer.answer
            if (answer.shouldUpdateUi) {
                binding.cardMain.setBackgroundResource(R.drawable.bg_button_gradient)
            }
            if (answer.hasAlreadyAnswered) {
                binding.cardMain.isEnabled = false
            }

            binding.cardMain.setOnClickListener {
                itemClickListener.invoke(answer)
            }
        }
    }

}




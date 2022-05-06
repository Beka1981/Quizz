package ge.gogichaishvili.quizz

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import ge.gogichaishvili.quizz.databinding.ActivityMainBinding
import ge.gogichaishvili.quizz.fragments.MainFragment


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(_binding?.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, MainFragment())
            addToBackStack(MainFragment::javaClass.name)
            commit()
        }

    }
}
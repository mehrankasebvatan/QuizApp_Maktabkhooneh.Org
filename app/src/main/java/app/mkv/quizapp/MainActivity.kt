package app.mkv.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.mkv.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val name = binding.inputName.text
            if (name.isNullOrEmpty()) Toast.makeText(
                this,
                "حتما باید اسمت رو بهمون بگی",
                Toast.LENGTH_LONG
            ).show()
            else startActivity(Intent(this, QuestionActivity::class.java).putExtra("name", name))

        }

    }
}
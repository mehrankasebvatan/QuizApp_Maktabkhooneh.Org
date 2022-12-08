package app.mkv.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.mkv.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    var correctAnswer = 0
    var wrongAnswer = 0
    var allQuestions = 0
    private var userName = ""
    private var score = 0
    var wrongPercent = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        correctAnswer = extra("correctAnswer").toInt()
        wrongAnswer = extra("wrongAnswer").toInt()
        allQuestions = extra("allQuestions").toInt()
        userName = extra("name")
        Log.d("INTENTS ==>", "correct: $correctAnswer, wrong: $wrongAnswer")
        doMath()
        fillPage()

    }

    private fun doMath() {
        score = if (wrongAnswer > 3) {
            correctAnswer - (wrongAnswer / 3)
        } else correctAnswer

        if (score < 0) score = 0
        

        if (wrongAnswer == 0) {
            binding.loDetail.visibility = View.GONE
        }


    }


    @SuppressLint("SetTextI18n")
    private fun fillPage() {

        wrongPercent = (wrongAnswer.toDouble() / allQuestions) * 100

        binding.apply {
            name.text = userName
            txtCorrect.text = " پاسخ های صحیح شما: $correctAnswer"
            txtWrong.text = " پاسخ های اشتباه شما: $wrongAnswer"
            // اینجا واقعا متوجه سوال نشدم. درصد پاسخ های اشتباه نسبت به پاسخ های صحیح
            txtPercent.text = " درصد پاسخ های اشتباه: $wrongPercent"
            binding.txtScore.text = " امتیاز شما: $score"
        }
    }

    private fun extra(s: String): String {
        return intent.extras!!.get(s) as String
    }


}
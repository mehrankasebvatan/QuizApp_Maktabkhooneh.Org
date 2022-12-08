package app.mkv.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import app.mkv.quizapp.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding
    private var currentQuestion = 0
    private lateinit var questionList: ArrayList<Question>
    private var selectedOption = 0
    private var answerCorrect = 0
    private var answerWrong = 0
    private var isWrong = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.name.text = "${intent.extras!!.get("name")}"



        questionList = Constants.getQuestions()
        loadQuestions()


    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestions() {
        selectedOption = 0
        isWrong = false
        val question = questionList[currentQuestion]

        binding.apply {
            txtQuestion.text = question.question
            imgQuestion.setImageResource(question.image)
            txtOne.text = question.optionOne
            txtTow.text = question.optionTow
            txtThree.text = question.optionThree
            txtFour.text = question.optionFour
            txtStep.text = "${currentQuestion + 1} / ${progress.max}"
            progress.progress = currentQuestion + 1
        }
    }


    fun onOptionClicked(view: View) {

        setDefaultOption()
        val selectOption = view as TextView
        selectOption.background =
            ContextCompat.getDrawable(this, R.drawable.option_background_choose)
        selectOption.setTextColor(ContextCompat.getColor(this, R.color.white))

        when (selectOption.id) {
            R.id.txtOne -> selectedOption = 1
            R.id.txtTow -> selectedOption = 2
            R.id.txtThree -> selectedOption = 3
            R.id.txtFour -> selectedOption = 4
        }


    }


    private fun setDefaultOption() {
        val options = ArrayList<TextView>()
        binding.apply {
            options.add(txtOne)
            options.add(txtTow)
            options.add(txtThree)
            options.add(txtFour)
        }

        for (t in options) {
            t.background = ContextCompat.getDrawable(this, R.drawable.option_background)
            t.setTextColor(ContextCompat.getColor(this, R.color.grey))
        }


    }


    private fun setBackgroundsForOptions(optionIndex: Int, drawableIndex: Int) {

        binding.apply {
            when (optionIndex) {
                1 -> txtOne.background =
                    ContextCompat.getDrawable(this@QuestionActivity, drawableIndex)
                2 -> txtTow.background =
                    ContextCompat.getDrawable(this@QuestionActivity, drawableIndex)
                3 -> txtThree.background =
                    ContextCompat.getDrawable(this@QuestionActivity, drawableIndex)
                4 -> txtFour.background =
                    ContextCompat.getDrawable(this@QuestionActivity, drawableIndex)
            }
        }


    }


    fun onSubmitClicked(view: View) {

        if (currentQuestion < questionList.size - 1) {
            if (selectedOption != 0) {


                checkAnswers()


            } else {
                Toast.makeText(this, "حتما باید یک گزینه رو انتخاب کنی!", Toast.LENGTH_LONG).show()
            }
        } else {

            if (selectedOption == questionList[currentQuestion].correctAnswer) {
                if (!isWrong) {
                    answerCorrect++
                }

                binding.btnResult.visibility = View.VISIBLE
                binding.btnAnswer.visibility = View.GONE
                binding.btnResult.setOnClickListener {
                    startActivity(
                        Intent(this, ResultActivity::class.java).putExtra(
                            "correctAnswer",
                            answerCorrect.toString()
                        ).putExtra(
                            "wrongAnswer", answerWrong.toString()
                        ).putExtra("allQuestions", (questionList.size + 1).toString())
                            .putExtra("name", binding.name.text.toString())
                    )
                    finish()
                }


            } else {
                if (!isWrong) {
                    answerWrong++
                }

                isWrong = true
                setBackgroundsForOptions(selectedOption, R.drawable.options_background_wrong)
                setBackgroundsForOptions(
                    questionList[currentQuestion].correctAnswer,
                    R.drawable.options_background_correct
                )

            }


        }

    }


    private fun checkAnswers() {
        setDefaultOption()

        if (selectedOption == questionList[currentQuestion].correctAnswer) {
            if (!isWrong) {
                answerCorrect++
            }
            currentQuestion++
            loadQuestions()

        } else {
            if (!isWrong) {
                answerWrong++
            }

            isWrong = true
            setBackgroundsForOptions(selectedOption, R.drawable.options_background_wrong)
            setBackgroundsForOptions(
                questionList[currentQuestion].correctAnswer,
                R.drawable.options_background_correct
            )

        }
        Log.d("Answers Result ==>  ", "wrong: $answerWrong , correct: $answerCorrect")
        Log.d(
            "Answers ==>  ",
            "question number: ${questionList[currentQuestion].id} , correct: ${questionList[currentQuestion].correctAnswer}, my answer: $selectedOption"
        )

        val name = binding.name
        if (answerCorrect > answerWrong) name.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.green
            )
        )
        else if (answerCorrect < answerWrong) name.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.red
            )
        )
        else if (answerCorrect == answerWrong) name.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.black
            )
        )


    }

}
























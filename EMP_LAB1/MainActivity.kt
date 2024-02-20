package com.example.lab1

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var arrayNumericButton : Array<Button> = arrayOf(
                    findViewById(R.id.btn0)
                    ,findViewById(R.id.btn1)
                    ,findViewById(R.id.btn2)
                    ,findViewById(R.id.btn3)
                    ,findViewById(R.id.btn4)
                    ,findViewById(R.id.btn5)
                    ,findViewById(R.id.btn6)
                    ,findViewById(R.id.btn7)
                    ,findViewById(R.id.btn8)
                    ,findViewById(R.id.btn9)
        )


        val dot: Button = findViewById(R.id.dot)
        val del: Button = findViewById(R.id.del)
        val plus: Button = findViewById(R.id.plus)
        val minis: Button = findViewById(R.id.minus)
        val multiply: Button = findViewById(R.id.multiply)
        val divide: Button = findViewById(R.id.divide)
        val bracketLeft: Button = findViewById(R.id.bracket_left)
        val bracketRight: Button = findViewById(R.id.bracket_right)
        val equals: Button = findViewById(R.id.equals)
        val AC: Button = findViewById(R.id.AC)


        val question: TextView = findViewById(R.id.question)
        val answer: TextView = findViewById(R.id.answer)

        fun setQuestion(string: String) {
            if (answer.text != "") {
                question.text = answer.text
                answer.text = ""
            }
            question.append(string)
        }

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

        } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val involve: Button = findViewById(R.id.involve)
            involve.setOnClickListener {
                if (question.text.toString().isNotEmpty()) {
                    if (question.text.toString().last() != '+' &&
                        question.text.toString().last() != '-' &&
                        question.text.toString().last() != '*' &&
                        question.text.toString().last() != '/' &&
                        question.text.toString().last() != '.' &&
                        question.text.toString().last() != '^' &&
                        question.text.toString().last() != '('
                    )
                        setQuestion("^")
                }
            }
        }


        for( i  in 0 until 9){
            arrayNumericButton[i].setOnClickListener { setQuestion(i.toString()) }
        }
        dot.setOnClickListener {
            if (question.text.toString().isNotEmpty()) {
                if (question.text.toString().last() != '+' &&
                    question.text.toString().last() != '-' &&
                    question.text.toString().last() != '*' &&
                    question.text.toString().last() != '/' &&
                    question.text.toString().last() != '.' &&
                    question.text.toString().last() != '^' &&
                    question.text.toString().last() != '(' &&
                    question.text.toString().last() != ')'
                )
                    if (!isNumWithDot(question.text.toString())) {
                        setQuestion(".")
                    }

            }
        }
        del.setOnClickListener {
            val str = question.text.toString()
            if (str.isNotEmpty()) {
                question.text = str.substring(0, str.length - 1)
                answer.text = ""
            }
        }
        plus.setOnClickListener {
            if (question.text.toString().isNotEmpty()) {
                if (question.text.toString().last() != '+' &&
                    question.text.toString().last() != '-' &&
                    question.text.toString().last() != '*' &&
                    question.text.toString().last() != '/' &&
                    question.text.toString().last() != '.' &&
                    question.text.toString().last() != '^' &&
                    question.text.toString().last() != '('
                )
                    setQuestion("+")
            }
        }
        minis.setOnClickListener {
            if (question.text.toString().isNotEmpty()) {
                if (question.text.toString().last() != '+' &&
                    question.text.toString().last() != '-' &&
                    question.text.toString().last() != '*' &&
                    question.text.toString().last() != '/' &&
                    question.text.toString().last() != '.' &&
                    question.text.toString().last() != '^'
                )
                    setQuestion("-")
            } else{
                setQuestion("-")
            }
        }
        multiply.setOnClickListener {
            if (question.text.toString().isNotEmpty()) {
                if (question.text.toString().last() != '+' &&
                    question.text.toString().last() != '-' &&
                    question.text.toString().last() != '*' &&
                    question.text.toString().last() != '/' &&
                    question.text.toString().last() != '.' &&
                    question.text.toString().last() != '^' &&
                    question.text.toString().last() != '('
                )
                    setQuestion("*")
            }
        }
        divide.setOnClickListener {
            if (question.text.toString().isNotEmpty()) {
                if (question.text.toString().last() != '+' &&
                    question.text.toString().last() != '-' &&
                    question.text.toString().last() != '*' &&
                    question.text.toString().last() != '/' &&
                    question.text.toString().last() != '.' &&
                    question.text.toString().last() != '^' &&
                    question.text.toString().last() != '('
                )
                    setQuestion("/")
            }
        }
        bracketLeft.setOnClickListener {
            if (question.text.toString().isNotEmpty()) {
                if (question.text.toString().last() != '.' &&
                    question.text.toString().last() != '(' &&
                    question.text.toString().last() != ')'
                ) {
                    setQuestion("(")
                }
            } else {
                setQuestion("(")
            }

        }
        bracketRight.setOnClickListener {
            if (question.text.toString().isNotEmpty()) {
                if (question.text.toString().last() != '.' &&
                    question.text.toString().last() != '(' &&
                    question.text.toString().last() != ')' &&
                    question.text.toString().last() != '+' &&
                    question.text.toString().last() != '-' &&
                    question.text.toString().last() != '*' &&
                    question.text.toString().last() != '/' &&
                    question.text.toString().last() != '^'
                ) {
                    setQuestion(")")
                }
            }
        }
        AC.setOnClickListener {
            question.text = ""
            answer.text = ""
        }
        equals.setOnClickListener {

                var resultString = "";
                try {

                    val ex = ExpressionBuilder(question.text.toString()).build()
                    val result = ex.evaluate()
                    resultString = result.toString();

                } catch (e: Exception) {
                    resultString = e.message.toString();
                }
                finally {
                    answer.text = resultString;
                }
            }



    }

    private fun isNumWithDot(insTxt: String): Boolean {
            return "\\d+(\\.\\d+)?".toRegex().findAll(insTxt).last().value.contains(".")
    }
}

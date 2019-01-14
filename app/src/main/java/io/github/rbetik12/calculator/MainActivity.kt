package io.github.rbetik12.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import net.objecthunter.exp4j.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var equation: String = " "
    private var result: Double = 0.0
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickNumbers(view: View) {
        /**
         * Gets an input symbols and adds it to equation variable
         * @param view App main window
         * @return nothing
         */
        when (view.id) {
            R.id.buttonZero -> {
                equation += "0"
            }
            R.id.buttonOne -> {
                equation += "1"
            }
            R.id.buttonTwo -> {
                equation += "2"
            }
            R.id.buttonThree -> {
                equation += "3"
            }
            R.id.buttonFour -> {
                equation += "4"
            }
            R.id.buttonFive -> {
                equation += "5"
            }
            R.id.buttonSix -> {
                equation += "6"
            }
            R.id.buttonSeven -> {
                equation += "7"
            }
            R.id.buttonEight -> {
                equation += "8"
            }
            R.id.buttonNine -> {
                equation += "9"
            }
            R.id.buttonPoint -> {
                equation += "."
            }
        }
        resultEquation.setText(equation)
    }

    fun onClickFunctions(view: View) {
        /**
         * Gets an input function(plus, minus, cos, etc.) and adds it to equation variable
         * @param view App main window
         * @return nothing
         */
        when (view.id) {
            R.id.buttonAdd -> {
                equation += "+"
            }
            R.id.buttonMinus -> {
                equation += "-"
            }
            R.id.buttonDivide -> {
                equation += "/"
            }
            R.id.buttonMultiply -> {
                equation += "*"
            }
        }
        resultEquation.setText(equation)
    }

    fun onClickUtils(view: View){
        /**
         * Gets an input and does some utility thing, like cleaning the output or solving the equation
         * @param view
         * @return nothing
         */
        when(view.id){
            R.id.buttonEquals -> {
                try{
                     expression = ExpressionBuilder(equation).build()
                }
                catch (e: ArithmeticException){
                    resultEquation.setText("Error")
                    equation = ""
                    return
                }
                result = expression.evaluate()
                val resultToStr = result.toString()
                Log.d("OUTPUT", "Substring is ${resultToStr.substring(resultToStr.length - 2, resultToStr.length)}")
                if (resultToStr.substring(resultToStr.length - 2, resultToStr.length) == ".0"){
                    resultEquation.setText(result.toInt().toString())
                    Log.d("OUTPUT", "Result is $result now if")
                }
                else {
                    resultEquation.setText(result.toString())
                    Log.d("OUTPUT", "Result is $result now")
                }
            }
            R.id.buttonClearEverything -> {
                equation = ""
                result = 0.0
                resultEquation.setText(null)
            }
            R.id.buttonClear -> {
                equation = equation.substring(0, equation.length - 1)
                resultEquation.setText(equation)
            }
        }
    }
}




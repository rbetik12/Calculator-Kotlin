package io.github.rbetik12.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import net.objecthunter.exp4j.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.math.round

class MainActivity : AppCompatActivity() {


    private var equation: String = " "
    private var result: Double = 0.0
    private lateinit var expression: Expression
    private val MAX_OUTPUT_LENGTH: Int = 19
    private val MAX_SYMBOLS_IN_DOUBLE_WEXP: Int = 7

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
                val isInt: Boolean = checkForInt(result)
                if (isInt){
                    resultEquation.setText(result.toString())
                    return
                }
                setOutput(result)
            }
            R.id.buttonClearEverything -> {
                equation = ""
                result = 0.0
                resultEquation.text = null
            }
            R.id.buttonClear -> {
                equation = equation.substring(0, equation.length - 1)
                resultEquation.setText(equation)
            }
        }
    }
    private fun checkForInt(result: Double): Boolean{
        /**
         * Checks if result can be put in output as integer(e.g. 12.0 -> 12)
         * @param [result] Result of equation
         * @return [true] if Int [false] if not
         */
        val resultToStr = result.toString()

        if (resultToStr.substring(resultToStr.length - 2, resultToStr.length) == ".0")
            return true
        return false
    }

    private fun setOutput(result: Double){
        /**
         * Rounds double result, if it contains lots of digits with exponent
         * @param result
         * @return nothing
         */
        val resultToStr: String = result.toString()

        Log.d("OUTPUT","Result is $result")
        Log.d("OUTPUT", "ResultStr is $resultToStr")
        if (resultToStr.length > MAX_OUTPUT_LENGTH && resultToStr.indexOf("E") != -1){
            Log.d("OUTPUT", "lmao")
            val exp: Double = abs(resultToStr.substring(resultToStr.indexOf("E") + 1, resultToStr.lastIndex + 1).toDouble())
            Log.d("OUTPUT","Exp is $exp")
            var tempResult: Double = result
            tempResult = Math.round(tempResult * pow(10.0, exp + MAX_SYMBOLS_IN_DOUBLE_WEXP)) / pow(10.0, exp + MAX_SYMBOLS_IN_DOUBLE_WEXP)
            Log.d("OUTPUT","Tempresult is $tempResult")
            resultEquation.setText(tempResult.toString())
        }
        else
            resultEquation.setText(resultToStr)
    }
}




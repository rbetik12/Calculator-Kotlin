package io.github.rbetik12.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import net.objecthunter.exp4j.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException
import java.lang.IllegalArgumentException
import java.lang.Math.abs
import java.lang.Math.pow
import java.util.*

class MainActivity : AppCompatActivity() {


    private var equation: String = " "
    private var result: Double = 0.0
    private lateinit var expression: Expression
    private val MAX_OUTPUT_LENGTH: Int = 19
    private val MAX_SYMBOLS_IN_DOUBLE_WEXP: Int = 9
    private val functions = arrayListOf<String>("+", "-", "*", "/")

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
            R.id.buttonLeftPar -> {
                equation += "("
            }
            R.id.buttonRightPar -> {
                equation += ")"
            }
        }
        resultEquation.setText(equation)
        scrollLeft()
        if (findFunc())
            count()
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
        scrollLeft()
        count()
    }

    fun onClickUtils(view: View){
        /**
         * Gets an input and does some utility thing, like cleaning the output or solving the equation
         * @param view
         * @return nothing
         */
        when(view.id){
            R.id.buttonEquals -> {
                count(true)
                midResultEquation.text = null
            }
            R.id.buttonClearEverything -> {
                equation = ""
                result = 0.0
                resultEquation.text = null
                midResultEquation.text = null
            }
            R.id.buttonClear -> {
                if (equation.length == 1)
                    midResultEquation.setText("")
                equation = when(equation.length){
                    0, 1 -> ""
                    else -> equation.substring(0, equation.length - 1)
                }
                resultEquation.setText(equation)
                count()
            }
        }
    }
    private fun checkForInt(): Boolean{
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

    private fun roundResult(){
        /**
         * Rounds double result, if it contains lots of digits with exponent.
         * @return nothing
         */
        val resultToStr: String = result.toString()

        if (resultToStr.length > MAX_OUTPUT_LENGTH && resultToStr.indexOf("E") != -1){
            val exp: Double = abs(resultToStr.substring(resultToStr.indexOf("E") + 1, resultToStr.lastIndex + 1).toDouble())

            result = Math.round(result * pow(10.0, exp + MAX_SYMBOLS_IN_DOUBLE_WEXP)) / pow(10.0, exp + MAX_SYMBOLS_IN_DOUBLE_WEXP)
        }
    }
    private fun scrollLeft(){
        /**
         * Shifts content of editText to the right, when called
         * @params none
         * @return nothing
         */
        scroll.post(Runnable { scroll.fullScroll(View.FOCUS_RIGHT) })
    }

    private fun count(isEqualsPressed: Boolean = false){
        /**
         * Counts the expression result, catches possible exceptions
         */

        try {
            expression = ExpressionBuilder(equation).build()
        }
        catch(e: IllegalArgumentException){
            return
        }
        catch(e: EmptyStackException){
            return
        }

        try {
            result = expression.evaluate()
        }
        catch(e:IllegalArgumentException){
            if (isEqualsPressed) {
                midResultEquation.text = null
                equation = ""
                setOutput(isEqualsPressed)
            }
            return
        }
        catch(e:ArithmeticException){
            equation = ""
            result = 0.0
            return
        }
        setOutput(isEqualsPressed)
    }

    private fun setOutput(isEqualsPressed: Boolean){
        /**
         * Set output, whether equal button was pressed or not
         */
        var strResult: String = ""

        if (checkForInt())
            strResult = result.toInt().toString()
        else {
            roundResult()
            strResult = result.toString()
        }

        if (isEqualsPressed){
            resultEquation.setText(strResult)
            equation = strResult
        }
        else{
            midResultEquation.setText(strResult)
        }
    }

    private fun findFunc(): Boolean{
        /**
         * Finds functions in equation string. Returns true if function was find or false
         * @return boolean
         */
        for (i in  0 until equation.length){
            for(j in 0 until functions.size){
                if (equation[i].toString() == functions[j])
                    return true
            }
        }
        return false
    }
}




package io.github.rbetik12.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var equation: String? = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickNumbers(view: View){
        when(view.id){
            R.id.buttonZero -> {equation += "0"}
            R.id.buttonOne -> {equation += "1"}
            R.id.buttonTwo -> {equation += "2"}
            R.id.buttonThree -> {equation += "3"}
            R.id.buttonFour -> {equation += "4"}
            R.id.buttonFive -> {equation += "5"}
            R.id.buttonSix -> {equation += "6"}
            R.id.buttonSeven -> {equation += "7"}
            R.id.buttonEight -> {equation += "8"}
            R.id.buttonNine -> {equation += "9"}
        }
    }

}




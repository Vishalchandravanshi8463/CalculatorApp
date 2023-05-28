package com.example.calculatormain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var canAddOperation = false
    private var canAddDecimal = true


    private lateinit var tvInputt:TextView
    private lateinit var tvOutput:TextView


    private lateinit var btnone:Button
    private lateinit var btnTwo:Button
    private lateinit var btnThree:Button
    private lateinit var btnFour:Button
    private lateinit var btnFive:Button
    private lateinit var btnSix:Button
    private lateinit var btnSeven:Button
    private lateinit var btnEight:Button
    private lateinit var btnNine:Button
    private lateinit var btnZero:Button
    private lateinit var btnAddition:Button
    private lateinit var btnSub:Button
    private lateinit var btnMul:Button
    private lateinit var btnDiv:Button
    private lateinit var btnEqual:Button
    private lateinit var btnPoint:Button
    private lateinit var btnClear:Button
    private lateinit var btnAC:Button
    private lateinit var btnModulo:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvInputt=findViewById<TextView>(/* id = */ R.id.tvInput)
        tvOutput=findViewById<TextView>(R.id.tvResult)
        btnone=findViewById<Button>(R.id.btn1)
         btnTwo=findViewById<Button>(R.id.btn2)
        btnThree=findViewById<Button>(R.id.btn3)
         btnFour=findViewById<Button>(R.id.btn4)
         btnFive=findViewById<Button>(R.id.btn5)
         btnSix=findViewById<Button>(R.id.btn6)
         btnSeven=findViewById<Button>(R.id.btn7)
        btnEight=findViewById<Button>(R.id.btn8)
         btnNine=findViewById<Button>(R.id.btn9)
         btnZero=findViewById<Button>(R.id.btn0)
         btnAddition=findViewById<Button>(R.id.btnPlus)
         btnSub=findViewById<Button>(R.id.btnMinus)
         btnMul=findViewById<Button>(R.id.btnMultiply)
        btnDiv=findViewById<Button>(R.id.btnDivide)
         btnEqual=findViewById<Button>(R.id.btnEqual)
         btnPoint=findViewById<Button>(R.id.btnDot)
        btnClear=findViewById<Button>(R.id.Clear)
        btnAC=findViewById<Button>(R.id.btnAC)
        btnModulo=findViewById<Button>(R.id.modulo)
    }



        fun allClearAction(view: View) {

            tvInputt.text = ""
            tvOutput.text = ""
        }

        fun backSpaceAction(view: View) {

            val length = tvInputt.length()
            if (length > 0) {
                tvInputt.text = tvInputt.text.subSequence(0, length - 1)
            }
        }

        fun equalsAction(view: View) {
            tvInputt.text=calculateResult()

        }

    private fun calculateResult(): String {

        val digitOperator=digitsOperators()
        if(digitOperator.isEmpty())return ""

        var timeDivisions=timeDivisonCalculate(digitOperator)
        if(timeDivisions.isEmpty())return ""


        val result=addSubCalculate(timeDivisions)

        
        return result.toString()

    }

    private fun addSubCalculate(passedList: MutableList<Any>): Float{

        var result=passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i!=passedList.lastIndex)
            {
                val operator=passedList[i]
                val nextDigit=passedList[i+1]as Float
                if(operator=='+')
                    {
                        result+=nextDigit
                    }
                if(operator=='-')
                {
                    result-=nextDigit
                }

            }
        }

        return result
    }

    private fun timeDivisonCalculate(passedList: MutableList<Any>): MutableList<Any> {

        var list=passedList
        while (list.contains('x')||list.contains('/'))
        {
           list= calcTimeDiv(list)
        }
        return list
    }

    private fun calcTimeDiv(passedList: MutableList<Any>): MutableList<Any>{

        val newList= mutableListOf<Any>()
        var reStartIndex=passedList.size
        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i!=passedList.lastIndex && i<reStartIndex)
            {
                val operator=passedList[i]
                val preDigit=passedList[i-1] as Float
                val nextDigit=passedList[i+1]as Float

                when(operator )
                {
                    'x'->
                    {
                        newList.add(preDigit * nextDigit)
                        reStartIndex=i+1
                    }
                    '/'->
                    {
                        newList.add(preDigit / nextDigit)
                        reStartIndex=i+1
                    }

                    else->
                    {
                        newList.add(preDigit )
                        newList.add(operator)

                    }

                }

            }
            if(i>reStartIndex)
            {
                newList.add(passedList[i])
            }
        }
        return newList
    }

    private fun digitsOperators():MutableList<Any>
    {
        val list= mutableListOf<Any>()
        var currentDigits=""
        for(character in tvInputt.text)
        {
            if(character.isDigit()||character=='.')
            {
                currentDigits+=character
            }
            else
            {
                list.add(currentDigits.toFloat())
                currentDigits=""
                list.add(character)
            }
        }
        if(currentDigits!="")
        {
            list.add(currentDigits.toFloat())
        }
        return list

    }

    fun numberAction(view: View) {
            if (view is Button) {
                if (view.text == ".") {
                    if (canAddDecimal) {
                        tvInputt.append(view.text)
                    }
                    canAddDecimal = false

                } else
                    tvInputt.append(view.text)

                canAddOperation = true
            }
        }

        fun operationAction(view: View) {

            if (view is Button && canAddOperation) {
                tvInputt.append(view.text)
                canAddOperation = false
                canAddDecimal = true
            }
        }

    }

package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var display2: TextView

    private var num1: Double = 0.0
    private var num2: Double = 0.0
    private var operation: String = ""
    private var result: Double = 0.0
    private var isNewOperation: Boolean = true
    private var hasResult: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        display2 = findViewById(R.id.display2)

        val numButtons = listOf<Button>(
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
            findViewById(R.id.buttonDecimal)
        )
        numButtons.forEach { button ->
            button.setOnClickListener { appendNumber(button.text.toString()) }
        }

        val operatorButtons = listOf<Button>(
            findViewById(R.id.buttonAdd),
            findViewById(R.id.buttonSubtract),
            findViewById(R.id.buttonMultiply),
            findViewById(R.id.buttonDivide)
        )
        operatorButtons.forEach { button ->
            button.setOnClickListener { setOperation(button.text.toString()) }
        }

        val equalButton = findViewById<Button>(R.id.buttonEqual)
        equalButton.setOnClickListener { calculate() }

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton.setOnClickListener { clear() }
    }

    private fun appendNumber(number: String) {
        if (isNewOperation || hasResult) {
            display.text = ""
            isNewOperation = false
            hasResult = false
        }
        display.append(number)
    }

    private fun setOperation(op: String) {
        if (display.text.isNotEmpty()) {
            if (!isNewOperation) {
                if (operation.isNotEmpty()) {
                    calculate()
                } else {
                    num1 = display.text.toString().toDouble()
                }
            }
            operation = op
            isNewOperation = true
            display2.text = "$num1 $operation"
        } else if (hasResult) {
            operation = op
            isNewOperation = true
            display2.text = "$num1 $operation"
        }
    }

    private fun calculate() {
        if (display.text.isNotEmpty()) {
            num2 = display.text.toString().toDouble()
            result = when (operation) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> {
                    if (num2 == 0.0) {
                        showErrorDialog("Error", "División por cero no es permitida.")
                        return
                    } else {
                        num1 / num2
                    }
                }
                else -> 0.0
            }
            display.text = result.toString()
            num1 = result
            operation = ""
            isNewOperation = true
            hasResult = true
        }
    }

    private fun clear() {
        display.text = "0"
        num1 = 0.0
        num2 = 0.0
        operation = ""
        display2.text = ""
        isNewOperation = true
        hasResult = false
    }

    private fun showErrorDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}

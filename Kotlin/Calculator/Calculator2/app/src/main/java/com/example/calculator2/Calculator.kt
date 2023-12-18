package com.example.calculator2

class Calculator {
    fun calculator(num1: Int, num2: Int, op: String) {
        var result = 0.0
        when (op) {
            "+" -> {
                result = num1.toDouble() + num2.toDouble()
            }

            "-" -> {
                result = num1.toDouble() - num2.toDouble()
            }

            "*" -> {
                result = num1.toDouble() * num2.toDouble()
            }

            "/" -> {
                result = num1.toDouble() / num2.toDouble()
            }

            "%" -> {
                result = num1.toDouble() % num2.toDouble()
            }

            else -> {
                println("연산 기호를 잘 못 입력했습니다.")
                return
            }
        }
        println("연산 결과는 $result 입니다.")
    }
}
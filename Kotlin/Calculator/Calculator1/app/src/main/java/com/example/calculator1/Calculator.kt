package com.example.calculator1

class Calculator {
    fun calculator(num1: Int, num2: Int, op: String) {
        val result: Double
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
            else -> {
                println("잘 못 된 연산 기호 입니다.")
                return
            }
        }
        println("연산 결과는 $result 입니다.")
    }
}
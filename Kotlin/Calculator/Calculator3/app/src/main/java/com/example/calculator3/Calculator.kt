package com.example.calculator3

class Calculator {
    fun calculator(num1: Int, num2: Int, op: String) {
        val result: Double
        when (op) {
            "+" -> {
                result = AddOperation().operation(num1, num2)
            }
            "-" -> {
                result = SubtractOperation().operation(num1, num2)
            }
            "*" -> {
                result = MultiplyOperation().operation(num1, num2)
            }
            "/" -> {
                result = DivideOperation().operation(num1, num2)
            }
            else -> {
                println("잘 못 된 연산 기호 입니다.")
                return
            }
        }
        println("연산 결과는 $result 입니다.")
    }
}

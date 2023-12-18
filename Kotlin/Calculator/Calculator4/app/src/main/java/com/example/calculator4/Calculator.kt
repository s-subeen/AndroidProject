package com.example.calculator4

class Calculator (private val operator: AbstractOperation){
    fun operate(num1: Int, num2: Int): Double {
        return operator.operation(num1, num2)
    }
}
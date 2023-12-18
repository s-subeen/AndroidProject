package com.example.calculator4

class SubtractOperation: AbstractOperation() {  // AbstractOperation 상속 받는 하위 클래스
    override fun operation(num1: Int, num2: Int): Double {
        return num1.toDouble() - num2.toDouble()
    }
}
package com.example.calculator3

fun main() {
    println("첫 번째 숫자를 입력해 주세요.")
    val num1 = try {
        readln().toInt()
    } catch (e: NumberFormatException) {
        println("잘 못 된 숫자 타입 입니다. 계산을 종료 합니다.")
        return
    }

    println("사칙 연산 기호를 입력해 주세요.")
    val op = readln()

    println("두 번째 숫자를 입력해 주세요.")
    val num2 = try {
        readln().toInt()
    } catch (e: NumberFormatException) {
        println("잘 못 된 숫자 타입 입니다. 계산을 종료 합니다.")
        return
    }

    val calculator = Calculator()
    calculator.calculator(num1, num2, op)
}
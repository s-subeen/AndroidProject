package com.example.calculator2

fun main() {
    val cal = Calculator()
    while (true) {
        println("첫 번째 숫자를 입력해 주세요.")
        val num1 = try {
            readln().toInt()
        } catch (e: NumberFormatException) {
            println("잘 못 된 숫자 타입 입니다. 계산을 종료 합니다.")
            return
        }

        println(
            "사칙 연산 기호를 입력해 주세요.\n" +
                    "덧셈: +, 뺄셈: -, 곱셈: *, 나눗셈: /, 나머지: %"
        )
        val op = readln()

        println("두 번째 숫자를 입력해 주세요.")
        val num2 = try {
            readln().toInt()
        } catch (e: NumberFormatException) {
            println("잘 못 된 숫자 타입 입니다. 계산을 종료 합니다.")
            return
        }

        cal.calculator(num1, num2, op)  // 연산 결과 출력

        println("계산을 종료 하려면 'q'를 계속 하려면 's'를 입력해 주세요.")
        if (readln() == "q") break
    }
}
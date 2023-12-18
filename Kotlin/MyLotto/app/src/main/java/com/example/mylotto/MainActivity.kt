package com.example.mylotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    // by lazy() 객체가 사용 될 때 {} 안에 선언한 것을 가지고 온다.
    // 늦게 초기화 할 내용을 미리 적어 놓고 by lazy를 선언한 프로퍼티를 사용 시 초기화가 된다.
    private val clearButton by lazy { findViewById<Button>(R.id.btn_clear) }
    private val addButton by lazy { findViewById<Button>(R.id.btn_add) }
    private val runButton by lazy { findViewById<Button>(R.id.btn_run) }
    private val numPick by lazy { findViewById<NumberPicker>(R.id.np_num) }

    private val numTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.tv_num1),
            findViewById(R.id.tv_num2),
            findViewById(R.id.tv_num3),
            findViewById(R.id.tv_num4),
            findViewById(R.id.tv_num5),
            findViewById(R.id.tv_num6),
        )
    }

    private var didRun = false // 실행 중인지 체크 하는 변수
    private val pickNumberSet = hashSetOf<Int>() // HashSet은 중복을 허용하지 않는 자료구조이다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // numberPicker의 최소 값과 최대 값 선언
        numPick.minValue = 1
        numPick.maxValue = 45

        // 3가지 초기화
        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initRunButton() {
        // 자동 생성 시작
        runButton.setOnClickListener {
            val list = getRandom() // 6개의 랜덤 값 생성
            didRun = true // 6개를 다 뽑아서 true 로 전환
            list.forEachIndexed { index, number ->
                // forEach: Collections의 각 element들에 대해서 특정한 작업할 수행할 수 있도록 한다.
                // forEach와 동일한 기능을 수행하며 value와 value의 index를 사용할 수 있다.
                val textView = numTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true
                setNumBack(number, textView)
            }

        }
    }

    private fun getRandom(): List<Int> {
        // filter(): 특정한 조건을 적용하여 Collection의 데이터를 필터링한다.
        val numbers = (1..45).filter { it !in pickNumberSet }
        // pickNumberSet = 1..45 중에 미리 지정한 숫자
        // shuffled(): Collection에 있는 요소들을 랜덤하게 재배열한다.
        // take(): Collection의 앞에서부터 n만큼만 취해 List를 반환한다.
        return (pickNumberSet + numbers.shuffled().take(6 - pickNumberSet.size)).sorted()
    }

    private fun initAddButton() { // 번호 추가하기
        addButton.setOnClickListener {
            when {
                didRun -> showToast("초기화 후에 시도해 주세요.")
                pickNumberSet.size >= 5 -> showToast("숫자는 최대 5개까지 선택할 수 있습니다.")
                pickNumberSet.contains(numPick.value) -> showToast("이미 선택 된 숫자 입니다.")
                else -> {
                    val textView =
                        numTextViewList[pickNumberSet.size] // 사이즈 만큼 numTextViewList 에서 꺼낸다.
                    textView.isVisible = true
                    textView.text = numPick.value.toString()
                    setNumBack(numPick.value, textView)
                    pickNumberSet.add(numPick.value)
                }
            }
        }
    }

    private fun setNumBack(number: Int, textView: TextView) {
        val background = when (number) {
            in 1..10 -> R.drawable.circle_yellow
            in 11..20 -> R.drawable.circle_blue
            in 21..30 -> R.drawable.circle_red
            in 31..40 -> R.drawable.circle_gray
            else -> R.drawable.circle_green
        }
        textView.background = ContextCompat.getDrawable(this, background)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numTextViewList.forEach { it.isVisible = false }
            didRun = false
            numPick.value = 1
        }
    }
}
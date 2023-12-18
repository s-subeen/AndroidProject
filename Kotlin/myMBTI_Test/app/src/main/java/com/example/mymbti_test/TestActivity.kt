package com.example.mymbti_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    val questionnaireResults = QuestionnaireResults()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.isUserInputEnabled = false // 화면을 터치로 움직 이지 못하게 함
    }

    fun moveToNextQuestion() {
        if (viewPager.currentItem == 3) { // 마지막 페이지 일 때 결과 화면으로 이동
            val intent = Intent(this@TestActivity, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("result", ArrayList(questionnaireResults.results))
            startActivity(intent)
        } else {
            val nextItem = viewPager.currentItem + 1
            if (nextItem < (viewPager.adapter?.itemCount ?: 0)) {
                viewPager.setCurrentItem(
                    nextItem,
                    true
                ) // 다음 페이지로 넘어갈 때 페이지 스크롤링 = smoothScroll: true
            }
        }
    }
}

class QuestionnaireResults { // 다음 버튼을 클릭할 때 호출
    val results = mutableListOf<Int>()
    fun addResponse(response: List<Int>) { // 1, 1, 2
        // [1, 1, 2]를 1과 2의 그룹 으로 묶음 -> 그룹의 개수를 count -> max 값의 key 값을 저장
        val mostFrequency = response.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        mostFrequency?.let { results.add(it) }
    }
}
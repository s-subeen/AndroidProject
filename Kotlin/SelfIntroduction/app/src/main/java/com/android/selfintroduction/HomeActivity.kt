package com.android.selfintroduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {
    private val tvId: TextView by lazy {
        findViewById(R.id.tvHomeId)
    }
    private val btnFinish: Button by lazy {
        findViewById(R.id.btnFinish)
    }
    private val ivIntro: ImageView by lazy {
        findViewById(R.id.ivCharacter)
    }
    private val idList = listOf(
        R.drawable.img_snoopy1,
        R.drawable.img_snoopy2,
        R.drawable.img_snoopy3,
        R.drawable.img_snoopy4,
        R.drawable.img_snoopy5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // 사진 랜덤 표시
        ivIntro.setImageResource(getResId())
        // 전달 받은 id 값 출력
        val idData = intent.getStringExtra("idFromSignInActivity")
        val strId = "아이디: $idData"
        tvId.text = strId
        // finish button
        btnFinish.setOnClickListener {
            finish()
        }
    }

    private fun getResId(): Int {
        val random = Random.nextInt(idList.size)  // 0 until idList.size
        return idList[random]
    }
}
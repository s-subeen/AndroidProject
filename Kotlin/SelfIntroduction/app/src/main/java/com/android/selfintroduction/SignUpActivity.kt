package com.android.selfintroduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val editName = findViewById<EditText>(R.id.etSignUpName)
        val editId = findViewById<EditText>(R.id.etSignUpId)
        val editPwd = findViewById<EditText>(R.id.etSignUpPwd)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            if (editName.text.isEmpty() || editId.text.isEmpty() || editPwd.text.isEmpty()) {
                btnSignUp.setOnClickListener {
                    if (editName.text.isBlank() || editId.text.isBlank() || editPwd.text.isBlank()) {
                        Toast.makeText(this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(
                            this, SignInActivity::class.java
                        ).apply {  // apply를 사용해 자신을 다시 반환
                            putExtra("idFromSignUpActivity", editId.text.toString())
                            putExtra("pwdFromSignUpActivity", editPwd.text.toString())
                        }
                        // 데이터를 전달 하기 위해 setResult 등록
                        setResult(RESULT_OK, intent)
                        if (!isFinishing) finish()
                    }
                }
            }
        }
    }
}
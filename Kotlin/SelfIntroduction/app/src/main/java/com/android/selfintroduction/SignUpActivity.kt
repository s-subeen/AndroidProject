package com.android.selfintroduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SignUpActivity : AppCompatActivity() {
    private val editName: EditText by lazy {
        findViewById(R.id.etSignUpName)
    }
    private val editId: EditText by lazy {
        findViewById(R.id.etSignUpId)
    }
    private val editPwd: EditText by lazy {
        findViewById(R.id.etSignUpPwd)
    }
    private val btnSignUp: Button by lazy {
        findViewById(R.id.btnSignUp)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignUp.setOnClickListener {
            if (editName.text.isBlank() || editId.text.isBlank() || editPwd.text.isBlank()) {
                makeToast(this, getString(R.string.text_empty_info))
            } else {
                val intent = Intent(
                    this, SignInActivity::class.java
                ).apply {  // apply를 사용해 자신을 다시 반환
                    putExtra("idFromSignUpActivity", editId.text.toString())
                    putExtra("pwdFromSignUpActivity", editPwd.text.toString())
                }

                setResult(RESULT_OK, intent) // 데이터를 전달 하기 위해 setResult 등록
                if (!isFinishing) finish()
            }
        }
    }
}

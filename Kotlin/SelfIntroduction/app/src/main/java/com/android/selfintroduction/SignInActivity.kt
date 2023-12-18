package com.android.selfintroduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class SignInActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>  // 전역변수 선언
    private val editId: EditText by lazy {
        findViewById(R.id.etSignInId)
    }
    private val editPwd: EditText by lazy {
        findViewById(R.id.etSignInPwd)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // SignUpActivity 전달 한 데이터를 getStringExtra를 사용해 받는다.

                val receiveId = result.data?.getStringExtra("idFromSignUpActivity") ?: ""
                val receivePwd = result.data?.getStringExtra("pwdFromSignUpActivity") ?: ""

                editId.setText(receiveId)
                editPwd.setText(receivePwd)
            }
        }
    }

    fun doOnBtnClick(view: View) {
        when (view.id) {
            R.id.btnSignIn -> {
                val id = editId.text.toString()
                val pwd = editPwd.text.toString()


                if (id.isBlank() || pwd.isBlank()) {
                    makeToast("아이디 또는 비밀번호를 확인해주세요.")
                } else {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("idFromSignInActivity", id)
                    startActivity(intent)
                    makeToast("로그인 성공")
                }
            }

            R.id.btnMoveSignUp -> {  // 회원가입
//                val intent = Intent(this, SignUpActivity::class.java)
//                activityResultLauncher.launch(intent)  // 데이터를 받아올 SignUpActivity 실행

                val intent = Intent(this, ValidationActivity::class.java)
                activityResultLauncher.launch(intent)
            }
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
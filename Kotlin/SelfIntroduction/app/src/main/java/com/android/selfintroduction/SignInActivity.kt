package com.android.selfintroduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.android.selfintroduction.Toast.makeToast

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
                    makeToast(this, getString(R.string.text_check_info))
                } else {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("idFromSignInActivity", id)
                    startActivity(intent)
                    makeToast(this, getString(R.string.text_success_login))
                }
            }

            R.id.btnMoveSignUp -> {  // 회원 가입
                val intent = Intent(this, SignUpActivity::class.java)
                activityResultLauncher.launch(intent)  // 데이터를 받아올 SignUpActivity 실행

//                val intent = Intent(this, ValidationActivity::class.java)  // 챌린지반 과제
//                startActivity(intent)
            }
        }
    }
}
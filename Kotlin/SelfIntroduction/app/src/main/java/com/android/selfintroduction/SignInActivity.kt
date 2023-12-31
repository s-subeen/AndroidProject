package com.android.selfintroduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.android.selfintroduction.Data.dataId
import com.android.selfintroduction.Data.dataPwd
import com.android.selfintroduction.Data.dataType
import com.android.selfintroduction.Data.typeSignIn
import com.android.selfintroduction.Data.typeSignUp
import com.android.selfintroduction.Toast.makeToast
import com.android.selfintroduction.UserManager.confirmSignIn

class SignInActivity : AppCompatActivity(), OnClickListener {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>  // 전역변수 선언
    private val editId: EditText by lazy {
        findViewById(R.id.etSignInId)
    }
    private val editPwd: EditText by lazy {
        findViewById(R.id.etSignInPwd)
    }
    private val btnSignIn: Button by lazy {
        findViewById(R.id.btnSignIn)
    }
    private val btnSignUp: Button by lazy {
        findViewById(R.id.btnMoveSignUp)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnSignIn.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val receiveId = result.data?.getStringExtra(dataId).orEmpty()
                val receivePwd = result.data?.getStringExtra(dataPwd).orEmpty()

                editId.setText(receiveId)
                editPwd.setText(receivePwd)
            }
        }
    }

    override fun onClick(view: View?) {
        if (view == null) return

        when (view.id) {
            R.id.btnSignIn -> {
                val id = editId.text.toString()
                val pwd = editPwd.text.toString()

                when {
                    id.isBlank() || pwd.isBlank() -> makeToast(getString(R.string.text_empty_info))
                    !confirmSignIn(id, pwd) -> makeToast(getString(R.string.text_check_info))
                    else -> {
                        val intent = Intent(this, SignUpActivity::class.java)
                        intent.putExtra(dataType, typeSignIn)
                        intent.putExtra(dataId, id)
                        startActivity(intent)
                        makeToast(getString(R.string.text_success_login))
                    }
                }
            }

            R.id.btnMoveSignUp -> {  // 회원 가입
                val intent = Intent(this, SignUpActivity::class.java)
                intent.putExtra(dataType, typeSignUp)
                activityResultLauncher.launch(intent)  // 데이터를 받아올 SignUpActivity 실행

            }
        }
    }
}
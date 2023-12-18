package com.android.selfintroduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import java.util.regex.Pattern

// 챌린지반 과제
class ValidationActivity : AppCompatActivity() {
    private val etName: EditText by lazy {
        findViewById(R.id.et_name)
    }
    private val etEmail: EditText by lazy {
        findViewById(R.id.et_email)
    }
    private val etAddress: EditText by lazy {
        findViewById(R.id.et_address)
    }
    private val etPwd: EditText by lazy {
        findViewById(R.id.et_pwd)
    }
    private val etPwdCheck: EditText by lazy {
        findViewById(R.id.et_pwd_check)
    }
    private val spinner: Spinner by lazy {
        findViewById(R.id.sp_email)
    }
    private val tvNameMessage: TextView by lazy {
        findViewById(R.id.tv_name_message)
    }
    private val tvEmailMessage: TextView by lazy {
        findViewById(R.id.tv_email_message)
    }
    private val tvPwdMessage: TextView by lazy {
        findViewById(R.id.tv_pwd_message)
    }
    private val tvPwdCheckMessage: TextView by lazy {
        findViewById(R.id.tv_pwd_check_message)
    }
    private val btnSignUp: Button by lazy {
        findViewById(R.id.btn_signUp)
    }

    private val editTexts
        get() = listOf(
            etName,
            etEmail,
            etAddress,
            etPwd,
            etPwdCheck
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)

        initView()
    }

    private fun setSpinner() {
        // 스피너에 아이템 등록
        spinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.emailList,
            android.R.layout.simple_spinner_item
        )

        val lastIndex = spinner.adapter.count - 1
        // init spinner
        spinner.setSelection(lastIndex, false)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 이메일 유효성 검사
                if (position == lastIndex) {
                    etAddress.isEnabled = true
                    etAddress.setText("")
                } else {
                    etAddress.isEnabled = false
                    val text = "@${spinner.selectedItem}"
                    etAddress.setText(text)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
        }
    }

    private fun initView() {
        setTextChangeListener()
        setOnFocusChangedListener()
        setSpinner()

        btnSignUp.setOnClickListener {
        }
    }

    private fun setTextChangeListener() {
        editTexts.forEach { editText ->
            editText.addTextChangedListener(EditTextWatcher(
                onChanged = { _, _, _, _ ->
                    editText.setErrorMessage()
                    setButtonEnable()
                }
            ))
        }
    }

    private fun setOnFocusChangedListener() {
        editTexts.forEach { editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    editText.setErrorMessage()
                    setButtonEnable()
                }
            }
        }
    }

    private fun EditText.setErrorMessage() {
        val message = when (this) {
            etName -> getMessageValidName()
            etEmail -> getMessageValidEmail()
            etAddress -> getMessageValidEmailRegex()
            etPwd -> getMessageValidPassword()
            etPwdCheck -> getConfirmPassword()
            else -> return
        }

        val textView = when (this) {
            etName -> tvNameMessage
            etEmail, etAddress -> tvEmailMessage
            etPwd -> tvPwdMessage
            etPwdCheck -> tvPwdCheckMessage
            else -> return
        }

        textView.text = message
        textView.setVisibility()
    }


    private fun TextView.setVisibility() {
        visibility = if (text.isBlank()) GONE else VISIBLE
    }

    private fun getMessageForInput(editText: EditText, message: String): String =
        if (editText.text.isBlank()) {
            message
        } else {
            ""
        }

    private fun getMessageValidName(): String =
        getMessageForInput(etName, "이름을 입력해주세요.")

    private fun getMessageValidEmail(): String =
        getMessageForInput(etEmail, "이메일을 입력해주세요.")


    private fun getMessageValidEmailRegex(): String {
        val text = etAddress.text.toString()
        val email = etEmail.text.toString() + text
        return when {
            text.isBlank() -> "이메일 주소를 입력해주세요."
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "이메일 형식을 확인해주세요."
            else -> ""
        }
    }

    private fun getMessageValidPassword(): String {  // 비밀번호 유효성 체크
        val text = etPwd.text.toString()
        val pattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{10,}\$"
        return when {
            text.length < 10 -> "10자 이상 입력해주세요."
            !Pattern.matches(pattern, text) -> "영문과 숫자를 포함해주세요."
            else -> ""
        }
    }

    private fun getConfirmPassword(): String =  // 비밀번호 확인
        if (etPwd.text.toString() != etPwdCheck.text.toString()) {
            "비밀번호가 일치하지 않습니다."
        } else {
            ""
        }

    private fun setButtonEnable() {
        btnSignUp.isEnabled = getMessageValidName().isBlank()
                && getMessageValidEmail().isBlank()
                && getMessageValidEmailRegex().isBlank()
                && getMessageValidPassword().isBlank()
                && getConfirmPassword().isBlank()
    }

}
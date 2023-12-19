package com.android.selfintroduction

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
    private val etEmailServiceProvider: EditText by lazy {
        findViewById(R.id.et_email_service_provider)
    }
    private val etPwd: EditText by lazy {
        findViewById(R.id.et_pwd)
    }
    private val etPwdCheck: EditText by lazy {
        findViewById(R.id.et_pwd_check)
    }
    private val spServiceProvider: Spinner by lazy {
        findViewById(R.id.sp_service_provider)
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
            etEmailServiceProvider,
            etPwd,
            etPwdCheck
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)

        initView()
    }

    private fun setSpinnerServiceProvider() {
        // 스피너에 어댑터 등록한다.
        // R.array.emailList : strings.xml 내의 emailList 아이템 목록을 추가한다.
        spServiceProvider.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.emailList,
            android.R.layout.simple_spinner_item
        )

        val lastIndex = spServiceProvider.adapter.count - 1 // 스피너의 마지막 요소
        // onItemSelectedListener가 최초에 실행되는 문제를 방지하고자 Listener 이전에 setselection(position, false)을 해준다.
        // 마지막 요소인 "직접 입력"으로 초기화
        spServiceProvider.setSelection(lastIndex, false)

        // 사용자가 선택한 값을 알기 위해 Listener를 추가한다.
        spServiceProvider.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == lastIndex) {  // "직접 입력" 일 경우
                    // EditText가 활성화 된다.
                    etEmailServiceProvider.isEnabled = true
                    etEmailServiceProvider.setText("")
                } else {
                    // EditText가 비활성화 되고 선택한 값을 출력해준다.
                    // gmail.com 선택시 @gmail.com 출력
                    etEmailServiceProvider.isEnabled = false
                    val text = "@${spServiceProvider.selectedItem}"
                    etEmailServiceProvider.setText(text)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
        }
    }

    private fun initView() {
        setTextChangeListener()
        setOnFocusChangedListener()
        setSpinnerServiceProvider()

        btnSignUp.setOnClickListener {
            // TODO 회원가입 버튼 클릭시
        }
    }

    private fun setTextChangeListener() {  // addTextChangedListener
        editTexts.forEach { editText ->
            editText.addTextChangedListener(EditTextWatcher(
                onChanged = { _, _, _, _ ->
                    editText.setErrorMessage()  // EditText 유효성 체크 후 에러 텍스트 노출
                    setButtonEnable()  // 유효성 체크에 따라 버튼 활성화 상태 변경
                }
            ))
        }
    }

    private fun setOnFocusChangedListener() {  // setOnFocusChangeListener
        editTexts.forEach { editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    editText.setErrorMessage()  // EditText 유효성 체크 후 에러 텍스트 노출
                    setButtonEnable()  // 유효성 체크에 따라 버튼 활성화 상태 변경
                }
            }
        }
    }

    // EditText별 유효성 체크 후 에러 텍스트 노출 및 Visibility 상태 변경
    private fun EditText.setErrorMessage() {
        val message = when (this) {
            etName -> getMessageValidName()
            etEmail -> getMessageValidEmail()
            etEmailServiceProvider -> getMessageValidEmailRegex()
            etPwd -> getMessageValidPassword()
            etPwdCheck -> getConfirmPassword()
            else -> return
        }

        val textView = when (this) {
            etName -> tvNameMessage
            etEmail, etEmailServiceProvider -> tvEmailMessage
            etPwd -> tvPwdMessage
            etPwdCheck -> tvPwdCheckMessage
            else -> return
        }

        textView.text = message
        textView.setVisibility()
    }


    // TextView가 isBlank()가 아닐 경우 에러 메시지가 노출 된 상태기에 TextView를 VISIBLE
    // isBlank()일 경우는 유효성 검사에 성공한 상태기에 GONE 처리 해준다.
    private fun TextView.setVisibility() {
        visibility = if (text.isBlank()) GONE else VISIBLE
    }

    private fun getMessageForInput(editText: EditText, message: String): String =
        if (editText.text.isBlank()) {    // EditText에 입력 된 값이 없을 경우
            message
        } else {
            ""
        }

    // 이름 유효성 체크
    private fun getMessageValidName(): String =
        getMessageForInput(etName, getString(R.string.text_input_name))

    // 이메일 유효성 체크
    private fun getMessageValidEmail(): String =
        getMessageForInput(etEmail, getString(R.string.text_input_email))

    // 이메일 정규 표현식 체크
    private fun getMessageValidEmailRegex(): String {
        val text = etEmailServiceProvider.text.toString()
        val email = etEmail.text.toString() + text
        return when {
            text.isBlank() -> getString(R.string.text_input_service_provider)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> getString(R.string.text_check_email)
            else -> ""
        }
    }

    // 비밀번호 유효성 체크
    private fun getMessageValidPassword(): String {
        val text = etPwd.text.toString()
        val pattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{10,}\$"
        return when {
            text.length < 10 -> getString(R.string.text_check_pwd_length)
            !Pattern.matches(pattern, text) -> getString(R.string.text_check_pwd_regex)
            else -> ""
        }
    }

    // 비밀번호가 일치하는지 체크
    private fun getConfirmPassword(): String =
        if (etPwd.text.toString() != etPwdCheck.text.toString()) {
            getString(R.string.text_pwd_mismach)
        } else {
            ""
        }

    // 버튼 활성화 상태 변경을 위한 함수
    private fun setButtonEnable() {
        btnSignUp.isEnabled = getMessageValidName().isBlank()
                && getMessageValidEmail().isBlank()
                && getMessageValidEmailRegex().isBlank()
                && getMessageValidPassword().isBlank()
                && getConfirmPassword().isBlank()
    }

}
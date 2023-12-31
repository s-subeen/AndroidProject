package com.android.selfintroduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.android.selfintroduction.Data.dataId
import com.android.selfintroduction.Data.dataPwd
import com.android.selfintroduction.Data.dataType
import com.android.selfintroduction.Data.typeSignIn
import com.android.selfintroduction.Data.typeSignUp
import com.android.selfintroduction.Toast.makeToast
import com.android.selfintroduction.UserManager.addUser
import com.android.selfintroduction.UserManager.findUser
import com.android.selfintroduction.UserManager.getUserInfo
import com.android.selfintroduction.UserManager.updateUser
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {  // 챌린지반 과제
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

    private var receiveType = ""
    private var userEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initView()

        /** 추가 된 부분 **/
        receiveType = intent.getStringExtra(dataType).toString() // 로그인 화면 에서 아이디 전달
        userEmail = intent.getStringExtra(dataId).toString() // 로그인 화면 에서 아이디 전달

        btnSignUp.text =
            getString(if (receiveType == typeSignUp) R.string.text_sign_up else R.string.text_btn_update)

        val info = getUserInfo(userEmail) // 전달 받은 아이디 검색
        info?.let {
            populateUserInfo(it)
        }
    }

    /** 추가 된 부분 **/
    private fun populateUserInfo(info: User) { // 회원 정보 수정 일 경우 정보 가져 오기
        Log.d("SignUpActivity", "!!!!!")
        val email = info.email.split("@")
        val serviceProvider = "@${email[1]}"
        etName.setText(info.name)
        etEmail.setText(email[0])
        etPwd.setText(info.password)

        val index = when (email[1]) {
            getString(R.string.email_service_provider_gmail) -> 0
            getString(R.string.email_service_provider_kakao) -> 1
            getString(R.string.email_service_provider_naver) -> 2
            else -> {
                etEmailServiceProvider.setText(serviceProvider)
                spServiceProvider.adapter.count - 1
            }
        }
        spServiceProvider.setSelection(index)
    }

    private fun performSignUp() { // 버튼 클릭 했을 때 액션
        val name = etName.text.toString()
        val id = etEmail.text.toString()
        val email = id + etEmailServiceProvider.text.toString()
        val password = etPwd.text.toString()
        val info = User(name, email, password)

        if (receiveType == typeSignUp) { // 회원 가입
            addUser(info) // 정보 추가
            setResultAndFinish(email, password)
        } else { // 회원 정보 수정
            updateUser(userEmail, info) // 정보 수정
            makeToast(getString(R.string.text_success_user_edit))
        }
    }

    private fun setResultAndFinish(email: String, password: String) { // 회면 가입 상태일 때 finish()
        val intent = Intent(this, SignInActivity::class.java).apply {
            putExtra(dataId, email)
            putExtra(dataPwd, password)
        }
        setResult(RESULT_OK, intent) // 아이디, 비밀번호 전달
        if (!isFinishing) finish()
    }

    /** 여기 까지 **/

    private fun setSpinnerServiceProvider() {
        spServiceProvider.adapter = ArrayAdapter(  // 스피너에 어댑터를 등록한다.
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf(
                getString(R.string.email_service_provider_gmail),
                getString(R.string.email_service_provider_kakao),
                getString(R.string.email_service_provider_naver),
                getString(R.string.email_service_provider_direct)
            )
        )

        val lastIndex = spServiceProvider.adapter.count - 1 // 스피너의 마지막 요소
        /*
        onItemSelectedListener가 최초에 실행되는 부분을 방지하고자 Listener 이전에 setselection(position, false)을 해준다.
        마지막 요소인 "직접 입력"으로 초기화
         */
        spServiceProvider.setSelection(lastIndex, false)

        spServiceProvider.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {  // 사용자가 선택한 값을 알기 위해 Listener를 추가한다.
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    /*
                    "직접 입력"일 경우는 ""로 입력 되어 있는 값을 지우고 EditText를 활성화
                    아닐 경우 선택된 값을 출력 하고 EditText를 비 활성화
                     */
                    val text =
                        if (position == lastIndex) "" else "@${spServiceProvider.selectedItem}"

                    etEmailServiceProvider.isEnabled = position == lastIndex
                    etEmailServiceProvider.setText(text)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) = Unit
            }
    }

    private fun initView() {
        setTextChangeListener()
        setOnFocusChangedListener()
        setSpinnerServiceProvider()

        btnSignUp.setOnClickListener {
            performSignUp()
        }
    }

    private fun setTextChangeListener() {  // addTextChangedListener
        editTexts.forEach { editText ->
            editText.addTextChangedListener {
                editText.setErrorMessage()  // EditText 유효성 체크 후 에러 텍스트 노출
                setButtonEnable()  // 유효성 체크에 따라 버튼 활성화 상태 변경
            }
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


    private fun EditText.setErrorMessage() {  // EditText별 유효성 체크 후 에러 텍스트 노출 및 Visibility 상태 변경
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
        textView.isVisible = message.isNotBlank()
    }

    private fun getMessageForInput(editText: EditText, message: String): String =
        if (editText.text.isBlank()) {    // EditText에 입력 된 값이 없을 경우
            message
        } else {
            ""
        }

    // 이름 유효성 체크
    private fun getMessageValidName(): String =
        getMessageForInput(etName, getString(R.string.text_input_name))  // 이름 유효성 체크

    private fun getMessageValidEmail(): String =
        getMessageForInput(etEmail, getString(R.string.text_input_email))  // 이메일 유효성 체크

    private fun getMessageValidEmailRegex(): String {
        val emailServiceProvider = etEmailServiceProvider.text.toString()
        val email = "${etEmail.text}$emailServiceProvider"

        return when {
            emailServiceProvider.isBlank() -> getString(R.string.text_input_service_provider)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> getString(R.string.text_check_email)
            receiveType == typeSignIn && userEmail == email -> "" // add
            findUser(email) -> getString(R.string.text_confirm_sign_up)
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

    private fun getConfirmPassword(): String =
        if (etPwd.text.toString() != etPwdCheck.text.toString()) {
            getString(R.string.text_pwd_mismach)
        } else {
            ""
        }

    private fun setButtonEnable() {  // 버튼 활성화 상태 변경을 위한 함수
        btnSignUp.isEnabled = getMessageValidName().isBlank()
                && getMessageValidEmail().isBlank()
                && getMessageValidEmailRegex().isBlank()
                && getMessageValidPassword().isBlank()
                && getConfirmPassword().isBlank()
    }

}

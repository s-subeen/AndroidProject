package com.android.selfintroduction.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.SavedStateViewModelFactory
import com.android.selfintroduction.R
import com.android.selfintroduction.databinding.ActivitySignUpBinding
import com.android.selfintroduction.user.UserManager.addUserEntity
import com.android.selfintroduction.user.UserManager.updateUserEntity

class SignUpActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER_EMAIL = "extra_user_email"
        const val EXTRA_USER_PASSWORD = "extra_user_password"
        const val EXTRA_ENTRY_TYPE = "extra_entry_type"
        const val EXTRA_USER_TYPE = "extra_user_entity"

        fun newIntent(
            context: Context,
            entryType: SignUpEntryType,
            entity: SignUpUserEntity? = null
        ): Intent =
            Intent(
                context,
                SignUpActivity::class.java
            ).apply {
                putExtra(EXTRA_ENTRY_TYPE, entryType.ordinal)
                putExtra(EXTRA_USER_TYPE, entity)
            }
    }

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private val editTexts
        get() = listOf(
            binding.etName,
            binding.etEmail,
            binding.etEmailProvider,
            binding.etPwd,
            binding.etPwdCheck
        )

    private val emailProvider
        get() = listOf(
            getString(R.string.email_service_provider_gmail),
            getString(R.string.email_service_provider_kakao),
            getString(R.string.email_service_provider_naver),
            getString(R.string.email_service_provider_direct)
        )

    // Activity-ktx를 사용하여 ViewModel 지연 생성
//    private val signUpViewModel: SignUpViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by viewModels {
        SavedStateViewModelFactory(application, this, intent?.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initViewModel()
    }

    private fun initViewModel() = with(signUpViewModel) {
        nameErrorMessage.observe(this@SignUpActivity) { error ->
            binding.tvNameMessage.text = getString(error.message)
        }
        emailErrorMessage.observe(this@SignUpActivity) { error ->
            binding.tvEmailMessage.text = getString(error.message)
        }
        serviceProvider.observe(this@SignUpActivity) { error ->
            binding.tvEmailMessage.text = getString(error.message)
        }
        passwordErrorMessage.observe(this@SignUpActivity) { error ->
            binding.tvPwdMessage.text = getString(error.message)
        }
        pwdConfirmErrorMessage.observe(this@SignUpActivity) { error ->
            binding.tvPwdCheckMessage.text = getString(error.message)
        }
        buttonEnabled.observe(this@SignUpActivity) { enabled ->
            binding.btnSignUp.isEnabled = enabled
        }
        entryType.observe(this@SignUpActivity) { entryType ->
            setSignUpBtnText(entryType)
        }
        userEntity.observe(this@SignUpActivity) { entity ->
            setUserEntity(entity)
        }
    }

    private fun setSignUpBtnText(entryType: SignUpEntryType) { // 버튼 텍스트 변경
        binding.btnSignUp.text = when (entryType) {
            SignUpEntryType.CREATE -> getString(R.string.text_sign_up)
            SignUpEntryType.UPDATE -> {
                getString(R.string.text_btn_update)
            }
        }
    }

    private fun setUserEntity(entity: SignUpUserEntity) { // 데이터 화면에 출력
        with(binding) {
            etName.setText(entity.name)
            etEmail.setText(entity.email)
            val index = emailProvider.indexOf(entity.emailService)
            // -1 일 경우 못찾음
            val selectedIndex = if (index < 0) {
                etEmailProvider.setText(entity.emailService)
                emailProvider.lastIndex
            } else {
                index
            }
            spServiceProvider.setSelection(selectedIndex)
        }
    }

    private fun setSpinnerServiceProvider() {
        with(binding.spServiceProvider) {
            adapter = ArrayAdapter(
                this@SignUpActivity,
                android.R.layout.simple_spinner_dropdown_item,
                emailProvider
            )

            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position == emailProvider.lastIndex) {
//                            binding.etEmailProvider.setText("")
                            binding.etEmailProvider.visibility = View.VISIBLE
                        } else {
                            binding.etEmailProvider.setText(emailProvider[position])
                            binding.etEmailProvider.visibility = View.GONE
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) = Unit
                }
        }
    }

    private fun initView() {
        setSpinnerServiceProvider()
        setTextChangeListener()
        setOnFocusChangedListener()
        binding.btnSignUp.setOnClickListener {
            confirmSignUp()
        }
    }

    private fun confirmSignUp() { // 회원 가입 버튼 클릭 했을 때
        val name = binding.etName.getString()
        val email = binding.etEmail.getString()
        val emailProvider = binding.etEmailProvider.getString()
        val password = binding.etPwd.getString()

        val entity = SignUpUserEntity(name, email, emailProvider)
        when (signUpViewModel.entryType.value) {
            SignUpEntryType.CREATE -> addUserEntity(entity)
            SignUpEntryType.UPDATE -> updateUserEntity(entity)
            else -> Unit
        }

        val intent = Intent().apply {
            putExtra(EXTRA_USER_EMAIL, "$email@$emailProvider")
            putExtra(EXTRA_USER_PASSWORD, password)
        }
        setResult(RESULT_OK, intent)
        if (isFinishing.not()) finish()
    }

    private fun setTextChangeListener() { // Text 변경 감지 리스너
        editTexts.forEach { editText ->
            editText.addTextChangedListener {
                editText.setErrorMessage()
                setButtonEnable()
            }
        }
    }

    private fun setOnFocusChangedListener() { // Focus 감지 리스너
        editTexts.forEach { editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    editText.setErrorMessage()
                    setButtonEnable()
                }
            }
        }
    }

    private fun EditText.setErrorMessage() { // EditText 에러 메세지
        when (this) {
            binding.etName -> signUpViewModel.setValidName(binding.etName.getString())
            binding.etEmail -> signUpViewModel.setValidEmail(binding.etEmail.getString())
            binding.etEmailProvider -> signUpViewModel.setEmailProvider(binding.etEmailProvider.getString())
            binding.etPwd -> signUpViewModel.setValidPassword(binding.etPwd.getString())
            binding.etPwdCheck -> signUpViewModel.setValidPasswordConfirm(
                binding.etPwd.getString(),
                binding.etPwdCheck.getString()
            )

            else -> Unit
        }
    }

    private fun setButtonEnable() { // 버튼 활성화
        signUpViewModel.checkEnabledButton(
            binding.etName.getString(),
            binding.etEmail.getString(),
            binding.etEmailProvider.getString(),
            binding.etPwd.getString(),
            binding.etPwdCheck.getString()
        )
    }

    private fun EditText.getString(): String = text.toString()
}
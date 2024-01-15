package com.android.selfintroduction.signup


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.android.selfintroduction.signup.SignUpActivity.Companion.EXTRA_ENTRY_TYPE
import com.android.selfintroduction.signup.SignUpActivity.Companion.EXTRA_USER_TYPE
import com.android.selfintroduction.signup.SignUpValidExtension.includeAt
import com.android.selfintroduction.signup.SignUpValidExtension.includeSpecialCharacters
import com.android.selfintroduction.signup.SignUpValidExtension.includeUpperCase
import com.android.selfintroduction.signup.SignUpValidExtension.validEmailServiceProvider

class SignUpViewModel(private val handle: SavedStateHandle) : ViewModel() {

    private val _entryType: MutableLiveData<SignUpEntryType> = MutableLiveData()
    val entryType: LiveData<SignUpEntryType> get() = _entryType

    private val _userEntity: MutableLiveData<SignUpUserEntity> = MutableLiveData()
    val userEntity: LiveData<SignUpUserEntity> get() = _userEntity

    private val _nameErrorMessage: MutableLiveData<SignUpErrorMessage> = MutableLiveData()
    val nameErrorMessage: LiveData<SignUpErrorMessage> get() = _nameErrorMessage

    private val _emailErrorMessage: MutableLiveData<SignUpErrorMessage> = MutableLiveData()
    val emailErrorMessage: LiveData<SignUpErrorMessage> get() = _emailErrorMessage

    private val _serviceProvider: MutableLiveData<SignUpErrorMessage> = MutableLiveData()
    val serviceProvider: LiveData<SignUpErrorMessage> get() = _serviceProvider

    private val _passwordErrorMessage: MutableLiveData<SignUpErrorMessage> = MutableLiveData()
    val passwordErrorMessage: LiveData<SignUpErrorMessage> get() = _passwordErrorMessage

    private val _pwdConfirmErrorMessage: MutableLiveData<SignUpErrorMessage> = MutableLiveData()
    val pwdConfirmErrorMessage: LiveData<SignUpErrorMessage> get() = _pwdConfirmErrorMessage

    private val _buttonEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val buttonEnabled: LiveData<Boolean> get() = _buttonEnabled

    init {
        // SavedStateHandle에서 데이터 받아오기
        val entryTypeOrdinal = handle[EXTRA_ENTRY_TYPE] ?: SignUpEntryType.CREATE.ordinal
        setEntryType(SignUpEntryType.values()[entryTypeOrdinal])

        if (entryType.value == SignUpEntryType.UPDATE) {
            setUserEntity(handle[EXTRA_USER_TYPE] ?: SignUpUserEntity("", "", ""))
        }
    }

    private fun setEntryType(entryType: SignUpEntryType) {
        // savedStateHandle에 저장하기
        handle[EXTRA_ENTRY_TYPE] = entryType.ordinal
        _entryType.value = entryType
    }

    private fun setUserEntity(entity: SignUpUserEntity) {
        handle[EXTRA_USER_TYPE] = entity
        _userEntity.value = entity
    }

    fun setValidName(name: String) {
        _nameErrorMessage.value = checkValidName(name)
    }

    fun setValidEmail(email: String) {
        _emailErrorMessage.value = checkValidEmail(email)
    }

    fun setEmailProvider(serviceProvider: String) {
        _serviceProvider.value = checkEmailProvider(serviceProvider)
    }

    fun setValidPassword(password: String) {
        _passwordErrorMessage.value = checkValidPassword(password)
    }

    fun setValidPasswordConfirm(password: String, confirm: String) {
        _pwdConfirmErrorMessage.value = checkValidPasswordConfirm(password, confirm)
    }

    private fun checkValidName(name: String): SignUpErrorMessage {
        return if (name.isBlank()) {
            SignUpErrorMessage.NAME_BLANK
        } else {
            SignUpErrorMessage.PASS
        }
    }

    private fun checkValidEmail(email: String): SignUpErrorMessage {
        return when {
            email.isBlank() -> SignUpErrorMessage.EMAIL_BLANK
            email.includeAt() -> SignUpErrorMessage.EMAIL_AT
            else -> SignUpErrorMessage.PASS
        }
    }

    private fun checkEmailProvider(
        serviceProvider: String
    ): SignUpErrorMessage {
        return if (
            serviceProvider.isBlank()
            || serviceProvider.validEmailServiceProvider().not()
        ) {
            SignUpErrorMessage.EMAIL_SERVICE_PROVIDER
        } else {
            SignUpErrorMessage.PASS
        }
    }

    private fun checkValidPassword(password: String): SignUpErrorMessage {
        return when {
            password.length < 10 -> SignUpErrorMessage.PASSWORD_LENGTH
            password.includeSpecialCharacters()
                .not() -> SignUpErrorMessage.PASSWORD_SPECIAL_CHARACTERS

            password.includeUpperCase().not() -> SignUpErrorMessage.PASSWORD_UPPERCASE
            else -> SignUpErrorMessage.PASS
        }
    }

    private fun checkValidPasswordConfirm(password: String, confirm: String): SignUpErrorMessage {
        return if (password != confirm) {
            SignUpErrorMessage.PASSWORD_MISMATCH
        } else {
            SignUpErrorMessage.PASS
        }
    }

    /**
    현재 상태를 보내서 한 번 더 체크
    LiveData 이벤트 전달 (뷰모델 - 뷰 관찰)
    editText
     */
    fun checkEnabledButton(
        name: String,
        email: String,
        emailProvider: String,
        password: String,
        confirmPassword: String
    ) {
        _buttonEnabled.value = checkValidName(name) == SignUpErrorMessage.PASS
                && checkValidEmail(email) == SignUpErrorMessage.PASS
                && checkEmailProvider(emailProvider) == SignUpErrorMessage.PASS
                && checkValidPassword(password) == SignUpErrorMessage.PASS
                && checkValidPasswordConfirm(password, confirmPassword) == SignUpErrorMessage.PASS
    }

}
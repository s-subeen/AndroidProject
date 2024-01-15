package com.android.selfintroduction.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.android.selfintroduction.databinding.ActivityUserBinding
import com.android.selfintroduction.signup.SignUpActivity
import com.android.selfintroduction.signup.SignUpActivity.Companion.EXTRA_USER_EMAIL
import com.android.selfintroduction.signup.SignUpActivity.Companion.EXTRA_USER_PASSWORD
import com.android.selfintroduction.signup.SignUpEntryType
import com.android.selfintroduction.signup.SignUpUserEntity

class UserActivity : AppCompatActivity() {
    private val binding: ActivityUserBinding by lazy {
        ActivityUserBinding.inflate(layoutInflater)
    }

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        setActivityResultLauncher()
    }

    private fun setActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                val email = it.data?.getStringExtra(EXTRA_USER_EMAIL).orEmpty()
                val password = it.data?.getStringExtra(EXTRA_USER_PASSWORD).orEmpty()

                binding.editTextId.setText(email)
                binding.editTextPassword.setText(password)
            }
        }
    }

    private fun initView() {
        binding.btUpdate.setOnClickListener {
            startActivity(
                SignUpActivity.newIntent(
                    context = this@UserActivity,
                    entryType = SignUpEntryType.UPDATE,
                    entity = SignUpUserEntity(
                        "스파르타",
                        "hello",
                        "r.com"
                    )
                )
            )
        }

        binding.btSignup.setOnClickListener {
            startActivity(
                SignUpActivity.newIntent(
                    context = this@UserActivity,
                    entryType = SignUpEntryType.CREATE
                )
            )
        }
    }
}




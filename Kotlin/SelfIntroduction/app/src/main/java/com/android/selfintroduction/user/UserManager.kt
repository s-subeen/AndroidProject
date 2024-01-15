package com.android.selfintroduction.user

import com.android.selfintroduction.signup.SignUpUserEntity

object UserManager {

    private val userList: MutableList<SignUpUserEntity> = arrayListOf()

    fun addUserEntity(entity: SignUpUserEntity) {
        userList.add(entity)
    }

    fun updateUserEntity(entity: SignUpUserEntity) {
        userList.find { it.email == entity.email }.let {
            it?.email = entity.email
            it?.name = entity.name
            it?.emailService = entity.emailService
        }
    }
}
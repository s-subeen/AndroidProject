package com.android.selfintroduction

object UserManager {
    private val userList = mutableListOf<User>()

    fun addUser(user: User) { // 회원 추가
        userList.add(user)
    }

    fun findUser(email: String): Boolean { // 이메일 검색
        return userList.any { it.email == email }
    }

    fun getUserInfo(email: String): User? {
        return userList.find { it.email == email }
    }

    fun confirmSignIn(email: String, password: String): Boolean { // 로그인 정보 확인
        val user = userList.find { it.email == email }
        return user?.password == password
    }

    fun updateUser(emailSearch: String, updatedUser: User) { // 회원 정보 수정
        userList.find { it.email == emailSearch }?.apply {
            name = updatedUser.name
            email = updatedUser.email
            password = updatedUser.password
        }
    }
}
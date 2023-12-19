package com.android.selfintroduction

import android.text.Editable
import android.text.TextWatcher

// 챌린지반 과제
// TextWatcher를 공통 클래스로 만들어 사용한다.
// 원하는 함수만 사용할 수 있게 하기 위해 nullable한 람다 함수를 매개 변수로 받도록 한다.
class EditTextWatcher(
    private val afterChanged: ((Editable?) -> Unit) = {},
    private val beforeChanged: ((CharSequence?, Int, Int, Int) -> Unit) = { _, _, _, _ -> },
    private val onChanged: ((CharSequence?, Int, Int, Int) -> Unit) = { _, _, _, _ -> }
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeChanged(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onChanged(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        afterChanged(s)
    }
}
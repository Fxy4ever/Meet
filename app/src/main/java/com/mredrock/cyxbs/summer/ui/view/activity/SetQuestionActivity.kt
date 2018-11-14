package com.mredrock.cyxbs.summer.ui.view.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.avos.avoscloud.AVUser
import com.mredrock.cyxbs.summer.R
import com.mredrock.cyxbs.summer.base.BaseActivity
import com.mredrock.cyxbs.summer.utils.HttpUtilManager
import com.mredrock.cyxbs.summer.utils.TextWatcheListener
import com.mredrock.cyxbs.summer.utils.Toasts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_set_question.*
import kotlinx.android.synthetic.main.summer_include_toolbar.*

class SetQuestionActivity : BaseActivity(){
    private val sp = App.getContext().getSharedPreferences("SetQuestion", Context.MODE_PRIVATE)

    private var q1 = sp.getString("SetQuestion_q1", "")
    private var q2 = sp.getString("SetQuestion_q2", "")
    private var q3 = sp.getString("SetQuestion_q3", "")
    private var a1 = sp.getString("SetQuestion_a1", "")
    private var a2 = sp.getString("SetQuestion_a2", "")
    private var a3 = sp.getString("SetQuestion_a3", "")
    private var note = sp.getString("SetQuestion_note", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_question)
        summer_include_tl.setNavigationOnClickListener {
            finish()
        }
        summer_include_tv.text = "我的知遇问题"
        setDefaultText()
        setETListener()
    }

    private fun setDefaultText() {
        setQuestion_a1.setText(a1)
        setQuestion_a2.setText(a2)
        setQuestion_a3.setText(a3)
        setQuestion_q1.setText(q1)
        setQuestion_q2.setText(q2)
        setQuestion_q3.setText(q3)
        setQuestion_note.setText(note)
    }

    private fun setETListener() {
        setQuestion_q1.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                q1 = s.toString()
            }
        })
        setQuestion_q2.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                q2 = s.toString()
            }
        })
        setQuestion_q3.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                q3 = s.toString()
            }
        })
        setQuestion_a1.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                a1 = s.toString()
            }
        })
        setQuestion_a2.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                a2 = s.toString()
            }
        })
        setQuestion_a3.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                a3 = s.toString()
            }
        })
        setQuestion_note.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note = s.toString()
            }
        })

        setQuestion_commit.setOnClickListener {view->
            if (a1.isNotEmpty() && a2.isNotEmpty() && a3.isNotEmpty()
                    && q1.isNotEmpty() && q2.isNotEmpty() && q3.isNotEmpty() && note.isNotEmpty()) {
                HttpUtilManager.getInstance()
                        .setQuestion(MainActivity.token,AVUser.getCurrentUser().objectId, note,q1,a1,q2,a2,q3,a3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {

                            Log.d("fxy",it.toString())

                            if (it.status == 200) {
                                Toasts.show("设置问题成功")
                                sp.edit().apply {
                                    putString("SetQuestion_q1", q1)
                                    putString("SetQuestion_q2", q2)
                                    putString("SetQuestion_q3", q3)
                                    putString("SetQuestion_a1", a1)
                                    putString("SetQuestion_a2", a2)
                                    putString("SetQuestion_a3", a3)
                                    putString("SetQuestion_note", note)
                                }.apply()
                                finish()
                            } else {
                                Toasts.show("设置问题失败")
                            }
                        }
            } else {
                Toasts.show("请填写️完整问题或答案")
            }
        }
    }
}

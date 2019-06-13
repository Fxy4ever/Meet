package com.mredrock.cyxbs.summer.ui.view.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.avos.avoscloud.AVUser
import com.mredrock.cyxbs.summer.R
import com.mredrock.cyxbs.summer.base.BaseActivity
import com.mredrock.cyxbs.summer.utils.DensityUtils
import com.mredrock.cyxbs.summer.utils.HttpUtilManager
import com.mredrock.cyxbs.summer.utils.TextWatcheListener
import com.mredrock.cyxbs.summer.utils.Toasts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_set_question.*
import kotlinx.android.synthetic.main.summer_include_toolbar.*

class SetQuestionActivity : BaseActivity(){
    private val sp = App.spHelper()

    private var q1:String = sp.get("SetQuestion_q1", "") as String
    private var q2:String = sp.get("SetQuestion_q2", "") as String
    private var q3:String = sp.get("SetQuestion_q3", "") as String
    private var a1:String = sp.get("SetQuestion_a1", "") as String
    private var a2:String = sp.get("SetQuestion_a2", "") as String
    private var a3:String = sp.get("SetQuestion_a3", "") as String
    private var note:String = sp.get("SetQuestion_note", "") as String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_question)
        summer_include_tl.setNavigationOnClickListener {
            finish()
        }
        DensityUtils.setTransparent(summer_include_tl,this)
        summer_include_tv.text = "我的树洞问题"
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


                            if (it.status == 200) {
                                Toasts.show("设置问题成功")
                                sp.apply {
                                    put("SetQuestion_q1", q1)
                                    put("SetQuestion_q2", q2)
                                    put("SetQuestion_q3", q3)
                                    put("SetQuestion_a1", a1)
                                    put("SetQuestion_a2", a2)
                                    put("SetQuestion_a3", a3)
                                    put("SetQuestion_note", note)
                                }
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

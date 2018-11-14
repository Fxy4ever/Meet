package com.mredrock.cyxbs.summer.ui.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.avos.avoscloud.AVUser
import com.mredrock.cyxbs.summer.R
import com.mredrock.cyxbs.summer.base.BaseActivity
import com.mredrock.cyxbs.summer.bean.MeetQuestionBean
import com.mredrock.cyxbs.summer.event.MeetEvent
import com.mredrock.cyxbs.summer.utils.HttpUtilManager
import com.mredrock.cyxbs.summer.utils.TextWatcheListener
import com.mredrock.cyxbs.summer.utils.Toasts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_answer.*
import kotlinx.android.synthetic.main.summer_include_toolbar.*
import org.greenrobot.eventbus.EventBus

class AnswerActivity : BaseActivity() {
    private var a1 = ""
    private var a2 = ""
    private var a3 = ""
    private var note = ""

    companion object {
        var rate: String = ""
        var user_id: String = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)
        val bean = MeetActivity.bean
        summer_include_tv.text = "匹配"
        if (bean.data != null) {
            init(bean)
        }
    }

    private fun init(bean: MeetQuestionBean) {
        answer_q1.text = bean.data.question.question_name1
        answer_q2.text = bean.data.question.question_name2
        answer_q3.text = bean.data.question.question_name3


        answer_a1.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                a1 = s.toString()
            }
        })
        answer_a2.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                a2 = s.toString()
            }
        })
        answer_a3.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                a3 = s.toString()
            }
        })
        answer_note.addTextChangedListener(object : TextWatcheListener() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note = s.toString()
            }
        })

        answer_commit.setOnClickListener {
            if (a1.isNotEmpty() && a2.isNotEmpty() && a3.isNotEmpty() && note.isNotEmpty()) {
                HttpUtilManager.getInstance().answer(MainActivity.token,
                        AVUser.getCurrentUser().objectId,
                        bean.data.question_id,
                        note,
                        bean.data.question.question_name1,
                        a1,
                        bean.data.question.question_name2,
                        a2,
                        bean.data.question.question_name3,
                        a3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {backBean->
                            Log.d("fxy",backBean.toString())
                            if (backBean.status == 200) {
                                rate = String.format("%.4f", (backBean.data.score_map.question1.toDouble() + backBean.data.score_map.question2.toDouble() + backBean.data.score_map.question3.toDouble()) / 3)
                                user_id = bean.data.question.user_id
                                Toasts.show("rate = $rate  id = $user_id")
                                    val intent = Intent(this, ChatActivity::class.java)
                                    val bundle = Bundle()

                                    bundle.putString("objectId", user_id)
                                    bundle.putString("rate", rate)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left)
                            } else {
                                Toasts.show("匹配不成功，请检查网络")
                            }
                        }
            }
        }
    }

}

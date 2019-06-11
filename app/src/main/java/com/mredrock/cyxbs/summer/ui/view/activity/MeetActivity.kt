package com.mredrock.cyxbs.summer.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.LinearInterpolator
import com.avos.avoscloud.AVUser
import com.mredrock.cyxbs.summer.R
import com.mredrock.cyxbs.summer.bean.MeetQuestionBean
import com.mredrock.cyxbs.summer.utils.HttpUtilManager
import com.mredrock.cyxbs.summer.utils.Toasts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_meet.*
import kotlinx.android.synthetic.main.summer_include_toolbar.*

class MeetActivity : AppCompatActivity() {

    companion object {
        lateinit var bean: MeetQuestionBean
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meet)
        meet_iv_icon.animate()
                .rotation(12800f)
                .setDuration(600000)
                .setInterpolator(LinearInterpolator())
                .start()
        summer_include_tv.text = "我的树洞"

        meet_btn_start.setOnClickListener { view ->
            meet_progressbar.visibility = View.VISIBLE
            HttpUtilManager.getInstance()
                    .meet(AVUser.getCurrentUser().objectId, MainActivity.token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { it ->
                        if (it.status == 200 && it.data.question.user_id != AVUser.getCurrentUser().objectId) {
                            Handler().postDelayed({
                                val intent = Intent(this, AnswerActivity::class.java)
                                bean = it
                                meet_progressbar.visibility = View.GONE
                                startActivity(intent)
                            }, 2000)
                        } else {
                            Toasts.show("服务器暂时无法匹配噢")
                        }
                    }
        }
    }
}

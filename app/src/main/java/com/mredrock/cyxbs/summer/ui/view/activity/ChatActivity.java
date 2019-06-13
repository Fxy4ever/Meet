package com.mredrock.cyxbs.summer.ui.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.ChatListAdapter;
import com.mredrock.cyxbs.summer.base.BaseActivity;
import com.mredrock.cyxbs.summer.bean.ChatBean;
import com.mredrock.cyxbs.summer.databinding.ActivityChatBinding;
import com.mredrock.cyxbs.summer.event.AudioEvent;
import com.mredrock.cyxbs.summer.event.ImageEvent;
import com.mredrock.cyxbs.summer.event.MeetEvent;
import com.mredrock.cyxbs.summer.event.TextEvent;
import com.mredrock.cyxbs.summer.ui.mvvm.model.ChatViewModel;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.Glide4Engine;
import com.mredrock.cyxbs.summer.utils.MyMessageHandler;
import com.mredrock.cyxbs.summer.utils.RecorderUtil;
import com.mredrock.cyxbs.summer.utils.TextWatcheListener;
import com.mredrock.cyxbs.summer.utils.Toasts;
import com.mredrock.cyxbs.summer.utils.UriUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends BaseActivity{
    private ActivityChatBinding binding;
    private CircleImageView voice;
    private Button send;
    private Button sendVoice;
    private Bundle bundle;
    private EditText et;
    private ImageButton photo;
    private TextView userName;
    private Toolbar toolbar;
    private boolean isClickVoice = false;
    private boolean isAskPer = false;
    private RecyclerView recyclerView;
    private ChatListAdapter adapter;
    private ChatViewModel model;
    private RecorderUtil recorderUtil;
    private int REQUEST_CODE_CHOOSE = 10086;
    private List<ChatBean> datas;
    private List<Uri> selects;
    private String myAvatar;
    private String yourAvatar;
    private String audioName = "";
    private String audioPath = "";
    private String imgName = "";
    private String imgPath = "";
    private AVUser user;
    private AVUser mine;
    private List<ChatBean> lists;

    private MyMessageHandler myMessageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        DensityUtils.setTransparent(binding.summerChatTl,this);
        askPermissions();
        setRecyclerView();
        setBinding();
        setListener();
    }

    private void initViewModel() {
        ChatViewModel.Factory factory = new ChatViewModel.Factory(getApplication(), mine, user);
        model = ViewModelProviders.of(ChatActivity.this, factory).get(ChatViewModel.class);
        model.getChatList().observe(this, chatBeans -> {
            adapter.setChatData(chatBeans);//这里会观察到数据改变
            if (chatBeans != null) {
                recyclerView.scrollToPosition(chatBeans.size()-1);
            }
        });
        String rate;
        rate= bundle.getString("rate","");
        if(!rate.equals("")){
            new Handler().postDelayed(() -> {
                model.sendText("我进行了你的遇见测试！我们之间的遇见匹配度为"+rate);
            },2000);
        }
    }




    private void setBinding() {
        /*
        绑定数据
         */
        toolbar = binding.summerChatTl;
        sendVoice = binding.summerPopChatSendVoice;
        send = binding.summerPopChatSend;
        et = binding.summerPopChatEt;
        photo = binding.summerPopChatPhoto;
        voice = binding.summerPopChatVoice;
        userName = binding.summerChatUserName;
        mine = AVUser.getCurrentUser();
        recorderUtil = new RecorderUtil();
        selects = new ArrayList<>();
        /*
        查询当前用户 这里不改成了mvvm了 因为要用user 心情好烦。。
         */
        bundle = getIntent().getExtras();

        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        if (bundle != null) {
            userQuery.whereEqualTo("objectId", bundle.getString("objectId"));
            userQuery.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if (e == null) {
                        user = list.get(0);
                        userName.setText(user.getUsername());
                        if(user.getAVFile("avatar")!=null)
                        yourAvatar = user.getAVFile("avatar").getUrl();
                        if(mine.getAVFile("avatar")!=null)
                        myAvatar = mine.getAVFile("avatar").getUrl();
                        adapter.setMyAvatar(myAvatar);
                        adapter.setYourAvatar(yourAvatar);
                        /*
                        在这里注册ViewModel
                         */
                        initViewModel();
                    }
                }
            });
        }
    }

    private void setRecyclerView() {
        recyclerView = binding.summerChatRv;
        datas = new ArrayList<>();
        int[] layouts = new int[]{R.layout.summer_chat_right_str, R.layout.summer_chat_right_photo,
                R.layout.summer_chat_right_voice, R.layout.summer_chat_left_str,
                R.layout.summer_chat_left_photo, R.layout.summer_chat_left_voice};
        adapter = new ChatListAdapter(this, datas, layouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {

        //输入栏监听
        et.addTextChangedListener(new TextWatcheListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    send.setBackground(getResources().getDrawable(R.drawable.summer_chat_send_shape_light));
                } else {
                    send.setBackground(getResources().getDrawable(R.drawable.summer_chat_send_shape));
                }
            }
        });
        binding.summerChatTl.setNavigationOnClickListener(v->{
            finish();
        });

        binding.summerPopChatSend.setOnClickListener(v -> {
            model.sendText(et.getText().toString());
            et.setText("");
        });

        binding.summerPopChatVoice.setOnClickListener(v -> {
            sendVoice();
        });

        binding.summerPopChatPhoto.setOnClickListener(v -> {
            if(isAskPer){
                Matisse.from(this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .capture(true)  // 开启相机，和 captureStrategy 一并使用否则报错
                        .captureStrategy(new CaptureStrategy(true,"com.mredrock.cyxbs.summer.fileprovider"))
                        .maxSelectable(1)
                        .gridExpectedSize(DensityUtils.getScreenWidth(this)/3)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .theme(R.style.Matisse_Dracula)
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });

        binding.summerPopChatSendVoice.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                binding.summerPopChatSendVoice.setBackground(getResources().getDrawable(R.drawable.summer_chat_send_shape_light));
                recorderUtil.recordStart();
            }
            try{
                if(event.getAction() == MotionEvent.ACTION_UP){
                    binding.summerPopChatSendVoice.setBackground(getResources().getDrawable(R.drawable.summer_chat_send_shape));
                    recorderUtil.recordStop((fileName,filePath) -> {
                        Toasts.show("录制成功");
                        audioName= fileName;
                        audioPath = filePath;
                        model.sendAudio(audioName,audioPath);
                    });
                }
            }catch (Exception e){
                Toasts.show("录制失败");
            }
            return false;
        });
    }

    @SuppressLint({"CheckResult"})
    private void askPermissions(){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ,Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.RECORD_AUDIO
                )
                .subscribe(permission -> {
                    if(permission.granted){
                        isAskPer = true;
                    }else{
                        Toasts.show("未获取权限");
                    }
                });
    }

    private void sendVoice() {
        if (isClickVoice) {
            voice.setImageDrawable(getResources().getDrawable(R.drawable.summer_icon_voice));
            sendVoice.setVisibility(View.GONE);
            isClickVoice = false;
        } else {
            voice.setImageDrawable(getResources().getDrawable(R.drawable.summer_icon_keyboard));
            sendVoice.setVisibility(View.VISIBLE);
            isClickVoice = true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTextEvent(TextEvent event) {
        model.getText(event);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageEvent(ImageEvent event) {
        model.getImage(event);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAudioEvent(AudioEvent event) {
        model.getAudio(event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        myMessageHandler = new MyMessageHandler();
        AVIMMessageManager.registerMessageHandler(AVIMMessage.class, myMessageHandler);
    }


    @Override
    protected void onPause() {
        super.onPause();
        AVIMMessageManager.unregisterMessageHandler(AVIMMessage.class, myMessageHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(model.getChatList()!=null)
            model.getChatList().removeObservers(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK){
            selects.clear();
            selects = Matisse.obtainResult(data);
                imgName = System.currentTimeMillis()/1000 + ".jpg";
                if(selects.get(0).toString().contains("provider")){
                    imgPath = UriUtil.getFPUriToPath(this,selects.get(0));
                    model.sendImage(imgName,imgPath);
                }
                else{
                    imgPath = UriUtil.getRealPathFromUri(this,selects.get(0));
                    model.sendImage(imgName,imgPath);
                }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
    }
}

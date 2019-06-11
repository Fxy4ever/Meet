package com.mredrock.cyxbs.summer.ui.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.base.BaseActivity;
import com.mredrock.cyxbs.summer.databinding.ActivityRegisterBinding;
import com.mredrock.cyxbs.summer.utils.ActivityManager;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.Toasts;
import com.mredrock.cyxbs.summer.utils.network.ApiGenerator;
import com.mredrock.cyxbs.summer.utils.network.ApiService;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity {
    public static final String TAG = "Register";

    private String tx_account="";
    private String tx_password="";
    private String tx_email="";
    private ActivityRegisterBinding binding;
    private AVUser user = new AVUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        DensityUtils.setTransparent(binding.summerRegisterTl, this);
        initListener();
    }


    private void initListener() {
        binding.registerToLogin.setOnClickListener(v -> {
                    RegisterActivity.this.finish();
                }
        );
        binding.registerAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tx_account = s.toString();
                user.setUsername(tx_account);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.registerPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tx_password = s.toString();
                user.setPassword(tx_password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.registerEma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tx_email = s.toString();
                user.setEmail(tx_email);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.registerCommit.setOnClickListener(v -> {
            Register();
        });
    }

    private void Register() {
        if(tx_account.length()>6&&tx_password.length()>6&&tx_email.length()>6){
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        App.spHelper().put("account", tx_account);
                        App.spHelper().put("password", tx_password);
                        App.spHelper().put("isChecked", true);
                        AVFile file = new AVFile("avatar", "http://lc-UYy61kgD.cn-n1.lcfile.com/JNehek2c7NrZyVt0n0BJdiqZxlYpQHKlJB2cxw1E.jpg", new HashMap<>());
                        file.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                user.put("avatar", file);
                                Log.d(TAG, "done: 默认头像保存成功");
                            }
                        });
                        ApiGenerator
                                .INSTANCE
                                .getApiService(ApiService.class)
                                .register(tx_account,tx_password,"15311111111",20)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(netBean -> {
                                    Log.d("test",netBean.toString());
                                });
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        ActivityManager.getInstance().finishAllActivity();
                        overridePendingTransition(R.anim.out_to_top, R.anim.in_from_bottm);
                    } else {
                        Toasts.show(e.getMessage());
                        if (binding.registerAccount.getText().length() == 0) {
                            binding.registerAccount.setError("用户名不能为空");
                        }
                        if (binding.registerPassword.getText().length() == 0) {
                            binding.registerPassword.setError("密码不能为空");
                        }
                        if (binding.registerEma.getText().length() == 0) {
                            binding.registerEma.setError("邮箱不能为空");
                        }
                    }
                }
            });
        }else {
            Toasts.show("账号或密码请勿少于6位");
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }
}

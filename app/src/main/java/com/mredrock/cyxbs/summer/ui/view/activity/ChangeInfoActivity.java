package com.mredrock.cyxbs.summer.ui.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.base.BaseActivity;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.Toasts;

import java.util.ArrayList;
import java.util.List;

public class ChangeInfoActivity extends BaseActivity {
    private Spinner spinner;
    private EditText first;
    private EditText secend;
    private Button commit;
    private List<String> dataList;
    private String kind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        init();
        initSP();
    }

    private void init(){
        DensityUtils.setTransparent(findViewById(R.id.summer_change_tl),this);
        spinner = findViewById(R.id.summer_change_spinner);
        first = findViewById(R.id.summer_change_et1);
        secend = findViewById(R.id.summer_change_et2);
        commit = findViewById(R.id.summer_change_commit);
    }

    private void initSP(){
        ArrayAdapter arrayAdapter =
                new ArrayAdapter<>(ChangeInfoActivity.this,R.layout.summer_item_sp,getData());
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kind = dataList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        commit.setOnClickListener(v->{
            if(first.getText().length()>0&&first.getText().toString().equals(secend.getText().toString())){
                AVUser user = AVUser.getCurrentUser();
                Log.d("chat", "initSP: "+kind+first.getText().toString());
                user.put(kind,first.getText().toString());
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e==null){
                            Toasts.show("修改成功");
                            finish();
                        }
                    }
                });

            }
        });
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.summer_change_tl);
        toolbar.setNavigationOnClickListener(v->{
            finish();
        });
    }

    private List<String> getData() {
        dataList = new ArrayList<>();
        dataList.add("password");
        dataList.add("email");
        dataList.add("sex");
        return dataList;
    }
}

package com.example.tally.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.tally.R;

public class BeizhuDialog extends Dialog implements View.OnClickListener {

    EditText et;
    Button cancelBtn, ensureBtn;
    OnEnsureListener onEnsureListener;

    // 设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public BeizhuDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_beizhu); // 设置对话框显示布局
        et = findViewById(R.id.dialog_beizhu_et);
        cancelBtn = findViewById(R.id.dialog_beizhu_btn_cancel);
        ensureBtn = findViewById(R.id.dialog_beizhu_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }

    public interface OnEnsureListener {
        public void onEnsure();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_beizhu_btn_cancel:
                cancel();
                break;
            case R.id.dialog_beizhu_btn_ensure:
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure();
                }
                break;
        }
    }

    // 获取输入数据的方法
    public String getEditText() {
        return et.getText().toString().trim();
    }

    // 设置 Dialog 的尺寸和屏幕尺寸一致
}

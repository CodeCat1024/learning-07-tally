package com.example.tally.frag_recrod;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tally.R;
import com.example.tally.db.AccountBean;
import com.example.tally.db.DBManger;
import com.example.tally.db.TypeBean;
import com.example.tally.util.BeizhuDialog;
import com.example.tally.util.KeyBoardUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 记录页面中的支出模块
 */
public abstract class  BaseRecordFragment extends Fragment implements View.OnClickListener{

    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    TextView typeTv, beizhuTv, timeTv;
    GridView typeGv;
    List<TypeBean> typeList;
    TypeBaseAdapter adapter;
    AccountBean accountBean; // 将需要插入到记账本当中的数据保存成对象的形式


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean(); // 创建对象
        accountBean.setTypename("其他");
        accountBean.setsImageId(R.mipmap.ic_qita_fs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view);
        // 获取当前时间，显示在 timeTv 上
        setInitTime();
        // 给 GridView 填充数据的方法
        loadDataToGv();
        // 设置 GridView 每一项的点击事件
        setGVListener();
        return view;
    }

    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos = position;
                adapter.notifyDataSetInvalidated(); // 提示绘制发生变化            }
                TypeBean typeBean = typeList.get(position);
                String typename = typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);
                int sImageId = typeBean.getsImageId();
                typeIv.setImageResource(sImageId);
                accountBean.setsImageId(sImageId);
            }
        });
    }

    protected void loadDataToGv() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);

    }

    private void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        beizhuTv = view.findViewById(R.id.frag_record_tv_beizhu);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        beizhuTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        // 让自定义软键盘显示出来
        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView, moneyEt);
        boardUtils.showKeyboard();
        // 设置接口，监听确定按钮被点击了
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                // 获取输入钱数
                String moneyStr = moneyEt.getText().toString();
                if (!TextUtils.isEmpty(moneyStr) || moneyStr.equals("0")) {
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                accountBean.setMoney(money);
                // 获取记录的信息，保存在数据库当中
                saveAccountToDB();

                // 返回上一级页面
                getActivity().finish();
            }
        });
    }

    // 必须进行重写
    public abstract void saveAccountToDB();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_record_tv_time:
                break;
            case R.id.frag_record_tv_beizhu:
                showBZDialog();
                break;
        }
    }

    // 弹出备注对话框
    public void showBZDialog() {
        BeizhuDialog dialog = new BeizhuDialog(getContext());
        dialog.show();

        dialog.setOnEnsureListener(new BeizhuDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String msg = dialog.getEditText();
                if (!TextUtils.isEmpty(msg)) {
                    beizhuTv.setText(msg);
                    accountBean.setBeizhu(msg);
                }
                dialog.cancel();
            }
        });
    }
}
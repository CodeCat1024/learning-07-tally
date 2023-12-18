package com.example.tally.frag_recrod;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tally.R;
import com.example.tally.db.DBManger;
import com.example.tally.db.TypeBean;

import java.util.List;


public class IncomeFragment extends BaseRecordFragment {

    @Override
    protected void loadDataToGv() {
        super.loadDataToGv();
        // 获取数据库当中的数据源
        List<TypeBean> outlist = DBManger.getTypeList(1);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.in_qt_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManger.insertItemToAccount(accountBean);
    }
}
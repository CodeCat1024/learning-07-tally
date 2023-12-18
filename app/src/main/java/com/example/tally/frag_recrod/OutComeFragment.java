package com.example.tally.frag_recrod;

import com.example.tally.R;
import com.example.tally.db.DBManger;
import com.example.tally.db.TypeBean;

import java.util.List;

public class OutComeFragment extends BaseRecordFragment{

    // 重写
    @Override
    protected void loadDataToGv() {
        super.loadDataToGv();
        // 获取数据库当中的数据源
        List<TypeBean> outlist = DBManger.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.ic_qita_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManger.insertItemToAccount(accountBean);
    }
}

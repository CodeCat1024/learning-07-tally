package com.example.tally.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责管理数据库的类
 * 主要对于表当中的内容进行增删改查
 */
public class DBManger {
    private static SQLiteDatabase db;
    // 初始化数据库对象
    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context); // 得到帮助类对象
        db = helper.getWritableDatabase(); // 得到数据库对象
    }

    /**
     * 读取数据库当中的数据，写入内存集合里
     * kind: 表示收入或者支出
     */
    public static List<TypeBean> getTypeList(int kind) {
        List<TypeBean> list = new ArrayList<>();

        // 读取 typedb 表当中的数据
        String sql = "select * from typedb where kind = " + kind;
        Cursor cursor = db.rawQuery(sql, null);

        // 循环读取游标内容，存储到对象当中
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind);
            list.add(typeBean);

        }
        return list;
    }

    /**
     * 向记账表中插入一条元素
     */
    public static void insertItemToAccount(AccountBean bean) {
        ContentValues values = new ContentValues();
        values.put("typename", bean.getTypename());
        values.put("sImageId", bean.getsImageId());
        values.put("beizhu", bean.getBeizhu());
        values.put("money", bean.getMoney());
        values.put("time", bean.getTime());
        values.put("year", bean.getYear());
        values.put("month", bean.getMoney());
        values.put("day", bean.getDay());
        values.put("kind", bean.getKind());
        db.insert("accounttb", null, values);
    }
}

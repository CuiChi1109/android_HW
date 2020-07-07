package com.example.hw_2.recycler;

import java.util.ArrayList;
import java.util.List;

public class TestDataSet {

    public static List<TestData> getData() {
        List<TestData> result = new ArrayList();
        result.add(new TestData("游戏小助手", "刚刚", "抖出好游戏"));
        result.add(new TestData("抖音小助手", "昨天","收下我双下巴的祝福"));
        result.add(new TestData("系统消息", "12-28", "账号登录提示"));
        result.add(new TestData("小张", "12:30", "你好"));
        result.add(new TestData("小李", "10:43","转发视频"));
        result.add(new TestData("小刘", "8:22","我是小刘"));
        result.add(new TestData("小马", "昨天","我是小马"));
        result.add(new TestData("小王", "昨天","[Hi]"));
        result.add(new TestData("小孙", "7-2","我是小孙"));
        result.add(new TestData("邮箱小助手", "6-22","新邮件通知"));
        return result;
    }

}

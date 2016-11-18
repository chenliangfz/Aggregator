package com.aggregator.card.mock;

import java.util.ArrayList;

/**
 * Created by ChenLiang on 16/11/8.
 */

public class Mocks {
    public static ArrayList<String> mMockValues = new ArrayList<>();

    static {
        for (int i = 0; i <= 20; i++) {
            mMockValues.add("MockValue:" + i);
        }
    }

    public static String USER_ACCOUNT = "account";

    public static String USER_PASSWORD = "password";

    public static String FAVICON= "http://note.youdao.com/favicon.ico";
}

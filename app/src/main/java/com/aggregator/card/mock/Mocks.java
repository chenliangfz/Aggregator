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

    public static String FAVICON= "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=850685754,900892460&fm=58";
}

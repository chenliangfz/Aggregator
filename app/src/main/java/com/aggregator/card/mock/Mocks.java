package com.aggregator.card.mock;

import com.aggregator.card.util.L;

import java.util.ArrayList;

/**
 * Created by ChenLiang on 16/11/8.
 */

public class Mocks {
    public static ArrayList<String> mMockValuse = new ArrayList<>();

    static {
        for (int i = 0; i <= 20; i++) {
            mMockValuse.add("MockValue:" + i);
        }
        L.e("Mocks "+mMockValuse.size());
    }
}

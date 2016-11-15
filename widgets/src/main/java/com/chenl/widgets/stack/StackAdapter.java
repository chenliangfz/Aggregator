package com.chenl.widgets.stack;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Marcin on 2015-04-13.
 */
public interface StackAdapter {
    View getView(int position, ViewGroup parent);
    int getCount();
}

package com.aggregator.card.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.aggregator.card.R;
import com.aggregator.card.core.inject.component.DaggerActivityComponent;
import com.aggregator.card.core.mvp.extension.StatusActivityView;
import com.aggregator.card.mock.MockMainActivity;
import com.aggregator.card.mock.Mocks;
import com.aggregator.card.ui.activity.member.AdditionActivity;
import com.aggregator.card.util.ImageLoader;
import com.aggregator.card.util.L;
import com.chenl.widgets.stack.RecentStack;
import com.chenl.widgets.stack.StackAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends StatusActivityView<MainActivityPresenter> {

    private static final int REQUEST_QR_CODE = 0x1;

    @BindView(R.id.recent_stack)
    RecentStack mRecentStack;


    @NonNull
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        DaggerActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        mRecentStack.setAdapter(new CardStackAdapter(Mocks.mMockValuse));
    }

    @OnClick(R.id.searcher)
    void onClickSearcher() {
        startActivity(new Intent(this, MockMainActivity.class));
    }


    @OnClick(R.id.addition)
    void onClickAddition() {
        startActivity(new Intent(this, AdditionActivity.class));
    }


    @OnClick(R.id.capture)
    void onClickCapture() {
        startActivityForResult(new Intent(this, SimpleCaptureActivity.class), REQUEST_QR_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_QR_CODE
                && data != null) {
            String result = data.getStringExtra("result");
            L.e("result:" + result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    static class CardStackAdapter implements StackAdapter{

        ArrayList<String> mValues = new ArrayList<>();

        CardStackAdapter(ArrayList<String> values){
            mValues.addAll(values);
        }

        @Override
        public View getView(int position, ViewGroup parent) {
            View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_membership_card,parent,false);
            ImageView businessCover = ((ImageView)childView.findViewById(R.id.business_cover));
            ImageLoader.load(mValues.get(position),businessCover);
            ImageView businessQRCode = ((ImageView)childView.findViewById(R.id.business_qr_code));
            ImageLoader.load(mValues.get(position),businessQRCode);
            return childView;
        }

        @Override
        public int getCount() {
            return mValues.size();
        }
    }

}

package com.aggregator.card.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.aggregator.card.R;
import com.aggregator.card.core.inject.component.DaggerActivityComponent;
import com.aggregator.card.core.mvp.extension.StatusActivityView;
import com.aggregator.card.mock.Mocks;
import com.aggregator.card.model.UserModel;
import com.aggregator.card.ui.activity.member.AdditionActivity;
import com.aggregator.card.ui.fragment.CardFragment;
import com.aggregator.card.util.ImageLoader;
import com.aggregator.card.util.L;
import com.chenl.widgets.flippablestackview.FlippableStackView;
import com.chenl.widgets.flippablestackview.StackPageTransformer;
import com.chenl.widgets.flippablestackview.indicator.OrientedPagerSlidingTabLayout;
import com.chenl.widgets.flippablestackview.utilities.ValueInterpolator;
import com.chenl.widgets.stack.RecentStack;
import com.chenl.widgets.stack.StackAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends StatusActivityView<MainActivityPresenter> {

    private static final int REQUEST_QR_CODE = 0x1;

    @BindView(R.id.recent_stack)
    RecentStack mRecentStack;

    @BindView(R.id.oriented_tab_indicator)
    OrientedPagerSlidingTabLayout mOrientedPagerSlidingTabLayout;

    @BindView(R.id.flippable_stack_view)
    FlippableStackView mFlippableStackView;

    @Inject
    UserModel mUserModel;

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
        initFlippableStackView();
        mStatusLayout.showContent();
    }

    @OnClick(R.id.searcher)
    void onClickSearcher() {
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
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    void initFlippableStackView() {
        final int NUMBER_OF_FRAGMENTS = 20;
        ArrayList<Fragment> fragments = new ArrayList<>();

        int startColor = getResources().getColor(R.color.mediumViolet);
        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endColor = getResources().getColor(R.color.mediumRed);
        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        ValueInterpolator interpolatorR = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endR, startR);
        ValueInterpolator interpolatorG = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endG, startG);
        ValueInterpolator interpolatorB = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endB, startB);

        for (int i = 0; i < NUMBER_OF_FRAGMENTS; ++i) {
            fragments.add(CardFragment.newInstance(Color.argb(255, (int) interpolatorR.map(i), (int) interpolatorG.map(i), (int) interpolatorB.map(i))));
        }
        ColorFragmentAdapter pageAdapter = new ColorFragmentAdapter(getSupportFragmentManager(), fragments);
        mFlippableStackView = (FlippableStackView) findViewById(R.id.flippable_stack_view);
        mFlippableStackView.initStack(4, StackPageTransformer.Orientation.VERTICAL, 0.8f, 0.7f, 0.9f, StackPageTransformer.Gravity.BOTTOM);
        mFlippableStackView.setAdapter(pageAdapter);
        mOrientedPagerSlidingTabLayout.setOrientedViewPager(mFlippableStackView);
    }


    void initRecentStack() {
        mRecentStack.setAdapter(new CardStackAdapter(Mocks.mMockValues));
    }

    static class CardStackAdapter implements StackAdapter {

        ArrayList<String> mValues = new ArrayList<>();

        CardStackAdapter(ArrayList<String> values) {
            mValues.addAll(values);
        }

        @Override
        public View getView(int position, ViewGroup parent) {
            View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card, parent, false);
            ImageView businessCover = ((ImageView) childView.findViewById(R.id.business_cover));
            ImageLoader.load(mValues.get(position), businessCover);
            ImageView businessQRCode = ((ImageView) childView.findViewById(R.id.business_qr_code));
            ImageLoader.load(mValues.get(position), businessQRCode);
            return childView;
        }

        @Override
        public int getCount() {
            return mValues.size();
        }
    }


    private class ColorFragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;

        public ColorFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }
    }

}

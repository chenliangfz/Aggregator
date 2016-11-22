package com.aggregator.card.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.aggregator.card.BuildConfig;
import com.aggregator.card.R;
import com.aggregator.card.core.BaseFragment;
import com.aggregator.card.mock.Mocks;
import com.aggregator.card.util.ImageLoader;

import butterknife.BindView;

/**
 * Created by ChenLiang on 16/11/16.
 */

public class ShopCardFragment extends BaseFragment {

    private static final String EXTRA_COLOR = BuildConfig.APPLICATION_ID + ".EXTRA_COLOR";

    public static ShopCardFragment newInstance(int backgroundColor) {
        ShopCardFragment fragment = new ShopCardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_COLOR, backgroundColor);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.shop_card)
    CardView mShopCard;

    @BindView(R.id.shop_cover)
    ImageView mShopCover;

    @BindView(R.id.shop_qr_code)
    ImageView mShopQRCode;

    @NonNull
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_card;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShopCard.setCardBackgroundColor(bundle.getInt(EXTRA_COLOR, Color.WHITE));
            mShopCover.setImageResource(R.mipmap.ic_launcher);
        }
        ImageLoader.loadCover(Mocks.FAVICON,mShopCover);
    }
}

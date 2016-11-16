package com.aggregator.card.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.aggregator.card.R;
import com.aggregator.card.core.BaseFragment;

import butterknife.BindView;

/**
 * Created by ChenLiang on 16/11/16.
 */

public class CardFragment extends BaseFragment {

    private static final String EXTRA_COLOR = "com.aggregator.card.EXTRA_COLOR";

    public static CardFragment newInstance(int backgroundColor) {
        CardFragment fragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_COLOR, backgroundColor);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.business_card)
    CardView mBusinessCard;

    @BindView(R.id.business_cover)
    ImageView mBusinessCover;

    @BindView(R.id.business_qr_code)
    ImageView mBusinessQRCode;

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
            mBusinessCard.setCardBackgroundColor(bundle.getInt(EXTRA_COLOR, Color.WHITE));
            mBusinessCover.setImageResource(R.mipmap.ic_launcher);
        }
    }
}

package com.aggregator.card.model;

import java.util.List;

/**
 * Created by ChenLiang on 16/11/17.
 */
public class MembershipModel extends BaseModel {
    /**
     * 店名
     */
    public String shopName;
    /**
     * 封面地址
     */
    public String shopCoverUrl;
    /**
     * 本地封面地址
     */
    public String localCoverUrl;
    /**
     * 封面颜色
     */
    public int coverRGBColor;
    /**
     * 店家二维码
     */
    public String shopQRCodeUrl;
    /**
     * 本地地址
     */
    public String localQRCodeUrl;
    /**
     * 背景颜色
     */
    public int shopRGBColor;
    /**
     * 优惠券列表
     */
    public List<CouponModel> coupons;
}

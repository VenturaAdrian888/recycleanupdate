package com.example.recyclearn;

public class Seller_ViewBuyerRewards {

    String productTitle, productPoints, productIcon,buyershopname;

    public Seller_ViewBuyerRewards() {}

    public Seller_ViewBuyerRewards(String productTitle, String productPoints, String productIcon, String buyershopname) {
        this.productTitle = productTitle;
        this.productPoints = productPoints;
        this.productIcon = productIcon;
        this.buyershopname = buyershopname;
    }
    public String getProductTitle() {
        return productTitle;
    }

    public String getProductPoints() {
        return productPoints;
    }

    public String getProductIcon() {
        return productIcon;
    }
    public void  setProductIcon(String productIcon){
        this.productIcon = productIcon;
    }
    public String getBuyershopname(){
        return buyershopname;
    }
}

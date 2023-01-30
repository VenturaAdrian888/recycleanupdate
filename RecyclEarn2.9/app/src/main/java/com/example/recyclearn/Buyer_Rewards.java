package com.example.recyclearn;

public class Buyer_Rewards {

    String productTitle, productPoints, productIcon;

    public Buyer_Rewards(){}

    public Buyer_Rewards(String productTitle, String productPoints, String productIcon) {
        this.productTitle = productTitle;
        this.productPoints = productPoints;
        this.productIcon = productIcon;
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
}

package com.chanshiguan.yumeng.Bean;

import java.util.List;

public class FoodDetailBean {

    /**
     * ImageNumber : 2
     * ImageUrl : [{"DetailImageUrl":"http://192.168.123.113/FoodDetail/1-1.jpg"},{"DetailImageUrl":"http://192.168.123.113/FoodDetail/1-2.jpg"},{"DetailImageUrl":"http://192.168.123.113/FoodDetail/1-3.jpg"},{"DetailImageUrl":"http://192.168.123.113/FoodDetail/1-4.jpg"},{"DetailImageUrl":"http://192.168.123.113/FoodDetail/1-5.jpg"}]
     */

    private int ImageNumber;
    private List<ImageUrlBean> ImageUrl;

    public int getImageNumber() {
        return ImageNumber;
    }

    public void setImageNumber(int ImageNumber) {
        this.ImageNumber = ImageNumber;
    }

    public List<ImageUrlBean> getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(List<ImageUrlBean> ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public static class ImageUrlBean {
        /**
         * DetailImageUrl : http://192.168.123.113/FoodDetail/1-1.jpg
         */

        private String DetailImageUrl;

        public String getDetailImageUrl() {
            return DetailImageUrl;
        }

        public void setDetailImageUrl(String DetailImageUrl) {
            this.DetailImageUrl = DetailImageUrl;
        }
    }
}

package haoqu.com.push.JSONModel;

import java.util.List;

/**
 *
 * 从服务器上每隔一段时间获取的消息.
 * Created by apple on 16/10/28.
 */

public class ActionMessageBean {


    /**
     * error : 0
     * carousel : [{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/sJJzLJgjZSS33Ow99oGCg9PPWgsQJo.png"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/V8WuXQ8U8Xz68UxWYWUW6fIdideDGZ.png"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/qs76k2I6883997KuyKs798k89ksukZ.png"}]
     * navigation : [{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/MHmJewm091HeEkzemCEw7M2GWwkwD0.jpg","title":"美食天下","id":"1","category":"fenlei"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/gggaUH44R5RJaFARU12xSAU7Gha41F.jpg","title":"酒店住宿","id":"2","category":"fenlei"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/Pp0fud87cXKe1cd7yEf28r87r17X2z.jpg","title":"母婴母爱","id":"7","category":"fenlei"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/VV00RFv0e89kfKehhVhkRjHp9kVekK.jpg","title":"美圈美业","id":"1","category":"fenlei"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/YAcbp5VmcvMbNgnbQ0MYn0pBannMma.jpg","title":"汽车房产","id":"1","category":"fenlei"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/g8102OK8iF188887IZUz42kuI2c77U.jpg","title":"全球购","id":"2","category":"fenlei"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/eLu2rDU444D5iG61DdD3z42lB7GrR4.jpg","title":"时时秒杀","id":"0","category":"miaosha"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/x6Wp3n8mnePEn265PPnwTx3e8Wm6iM.jpg","title":"拼团抢购","id":"0","category":"pintuan"}]
     * recommend : [{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/SNGCD9DgVgZ44V4C8vfN95GNLNNd8F.png","id":"1"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/bCSCAuc6a67sUIcInpsICeEICALACA.png","id":"88"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/09/t3j35Ls7igR7L2crrCrcwOXL00DDOW.png","id":"72"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/MnWGrrwDThHdgnPBjGRe6dRGrNBn6h.png","id":"22"},{"thumb":"http://192.168.0.148/newokgo/resource/attachment/images/2016/08/HPriR27lrjjh101n2IahmqRRpiAnpJ.png","id":"1"}]
     */

    private String error;
    /**
     * thumb : http://192.168.0.148/newokgo/resource/attachment/images/2016/08/sJJzLJgjZSS33Ow99oGCg9PPWgsQJo.png
     */

    private List<CarouselBean> carousel;
    /**
     * thumb : http://192.168.0.148/newokgo/resource/attachment/images/2016/08/MHmJewm091HeEkzemCEw7M2GWwkwD0.jpg
     * title : 美食天下
     * id : 1
     * category : fenlei
     */

    private List<NavigationBean> navigation;
    /**
     * thumb : http://192.168.0.148/newokgo/resource/attachment/images/2016/08/SNGCD9DgVgZ44V4C8vfN95GNLNNd8F.png
     * id : 1
     */

    private List<RecommendBean> recommend;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<CarouselBean> getCarousel() {
        return carousel;
    }

    public void setCarousel(List<CarouselBean> carousel) {
        this.carousel = carousel;
    }

    public List<NavigationBean> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<NavigationBean> navigation) {
        this.navigation = navigation;
    }

    public List<RecommendBean> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendBean> recommend) {
        this.recommend = recommend;
    }

    public static class CarouselBean {
        private String thumb;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public static class NavigationBean {
        private String thumb;
        private String title;
        private String id;
        private String category;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    public static class RecommendBean {
        private String thumb;
        private String id;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

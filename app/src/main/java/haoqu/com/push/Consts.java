package haoqu.com.push;

/**
 * Created by apple on 16/9/18.
 */
public class Consts {
    //baseurl
    public static final String baseUrl = "http://192.168.0.148/newokgo/";
    //weid
    public static final String weid = "1";


    //-获取首页信息(暂定版，后期修改)
    public static final String appIndex = "mobile.php?act=module&name=mmapp&do=appindex&weid=";
    //-获取分类商品列表
    public static final String appGoodList = "mobile.php?act=module&name=mmapp&do=goodslist&weid=";
    //-购物车
    public static final String appCart = "mobile.php?act=module&name=mmapp&do=cart&weid=";
    //-登录
    public static final String appLogin = "mobile.php?act=module&name=mmapp&do=login&weid=";



    //service拿到消息后,发送消息actiong
    public static final String Msg = "Msg";
    //退出登陆广播
    public static final String EXITACTION = "EXIT";
}

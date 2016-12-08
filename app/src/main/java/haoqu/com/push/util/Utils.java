package haoqu.com.push.util;

/**
 * Created by apple on 16/12/8.
 */

public class Utils {
    /**
     * 校验字符串是否为空
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }


}

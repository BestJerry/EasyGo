package com.easygo.jerry.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jerry on 2018/3/27.
 */

public class DateUtil {

    /**
     * 项目过程中生成系统当前时间时遇到了一个小问题，就是生成的时间是12小时进制的不是24小时进制的，
     * 后来又发现生成的时间跟当前系统时间对不上，两个问题出现的都是因为格式大小没有区分。

     yyyyMMdd HH:mm:ss：H是大写的话是24小时进制的。
     yyyyMMdd hh:mm:ss：h是小写的话是12小时进制的，注意分的mm与秒的ss一定要小写，不然结果不是正常想要的。

     测试了下，年yy可以是大写也可以是小写，月MM要大写，日dd要小写，hh大小写区分进制，分mm要小写，秒ss要小写。
     * @return
     */
    public static String getDate() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);

        return format.format(new Date());
    }
}

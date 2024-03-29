package com.gh.common;

import com.gh.common.service.*;
import com.gh.common.service.impl.*;

import java.util.Date;

public class SDK {

    /**
     * Date类型日期时间操作工具
     *
     * @return DateUtils
     */
    public static DateUtils getDateUtils() {
        return new DatetimeUtilsImpl<Date>();
    }

    /**
     * LocalDateTime数据类型日期时间操作工具
     *
     * @return LocalDateTimeUtils
     */
    public static LocalDateTimeUtils getLocalDateTimeUtils() {
        return new LocalDateTimeUtilsImpl();
    }

    /**
     * 加密工具类
     *
     * @return EncryptionUtils
     */
    public static EncryptionUtils encryptionUtils() {
        return new EncryptionUtilsImpl();
    }

    /**
     *bean与实体类互转
     * @return
     */
    public static BeanMapTool beanMapTool() {
        return new BeanMapToolImpl();
    }

    /**
     * Http请求工具
     *
     * @return HttpRequest
     */
    public static HttpRequest httpRequest() {
        return new HttpRequest();
    }

    /**
     * JSON WEB TOKEN工具
     *
     * @return JwtUtilsImpl
     */
    public static JwtUtils JWT() {
        return new JwtUtilsImpl();
    }

    public static void main(String[] args) throws Exception {
        JwtUtilsImpl jwt = new JwtUtilsImpl();
        String token = jwt.sign("admin", "123");
        System.err.println("token:" + token);

        boolean verity_1 = jwt.verity(token);
        System.err.println("verity_1:" + verity_1);

        boolean verity_2 = jwt.verity(token.substring(0, token.length() - 1));
        System.err.println("verity_2:" + verity_2);
    }
}

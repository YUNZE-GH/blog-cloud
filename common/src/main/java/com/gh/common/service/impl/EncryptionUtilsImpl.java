package com.gh.common.service.impl;

import com.gh.common.service.EncryptionUtils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * blog-cloud
 *
 * @author 3hgh
 * @version 1.0
 * @date 2021-04-22 15:38
 **/
public class EncryptionUtilsImpl implements EncryptionUtils {

    @Override
    public String useMD5Encryption(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
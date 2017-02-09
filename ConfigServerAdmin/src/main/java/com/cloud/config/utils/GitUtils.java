package com.cloud.config.utils;

import com.google.common.base.Charsets;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Project: ConfigServer
 * Module Desc:com.juntu.config.utils
 * User: zjprevenge
 * Date: 2016/11/21
 * Time: 14:39
 */
public class GitUtils {

    /**
     * 将base64编码的字符串转换成utf8编码的字符串
     *
     * @param source base64编码的字符串
     * @return
     */
    public static String decode(String source) {
        byte[] bytes = Base64.decodeBase64(source);
        return new String(bytes, Charsets.UTF_8);
    }

    /**
     * 将字符串转成base64编码的字符串
     *
     * @param source
     * @return
     */
    public static String encode(String source) {
        return Base64.encodeBase64String(source.getBytes(Charsets.UTF_8));
    }
}

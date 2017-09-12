package cc.idiary.utils.common;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class StringTools {

    /**
     * @param str
     * @return null or "".equals(str)
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /**
     * 获取不带“-”的32位UUID
     *
     * @return
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 获取md5加密
     *
     * @param str
     * @return
     */
    public static String Md5(String str) {
        return EncryptionTools.encryptByMD5(str).toUpperCase();
    }

    /**
     * 正则校验
     *
     * @param value
     * @param regex
     * @return
     */
    public static boolean valRegex(String value, String regex) {
        if (value == null) {
            return false;
        }
        return value.matches(regex);
    }

    public static String toISO(String sIn) {
        if (sIn == null || sIn.equals(""))
            return sIn;
        try {
            return new String(sIn.getBytes(), "ISO-8859-1");
        } catch (UnsupportedEncodingException usex) {
            return sIn;
        }
    }
}

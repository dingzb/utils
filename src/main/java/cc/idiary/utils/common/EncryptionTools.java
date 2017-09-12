package cc.idiary.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 加密工具类
 */
public class EncryptionTools {

    private static char[] digit = {'C', 'h', 'e', 'n', 'P', 'e', 'i', 'A',
            'n', '8', '1', '0', '2', '1', '6', 'C'};
    private static char[] commonDigit = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 字符串加密方法。传入一个字符串，返回经过SHA-1加密后的一个字符串
     *
     * @param str
     * @return
     */
    public static String encryptBySHA(String str) {
        return encryptStr(str, HashType.SHA_1, false);
    }

    /**
     * 字符串加密方法。传入一个字符串，返回经过MD5加密后的一个字符串
     *
     * @param str
     * @return
     */
    public static String encryptByMD5(String str) {
        return encryptStr(str, HashType.MD5, false);
    }

    public static String hash(File file, HashType type, boolean useCommonDigit) {
        if (file == null) {
            return "";
        }
        try {
            InputStream in = new FileInputStream(file);
            return hash(in, type, useCommonDigit);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String hash(InputStream in, HashType type,
                              boolean useCommonDigit) {
        if (in == null) {
            return "";
        }
        try {
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance(type.getValue());
            int numRead = 0;
            while ((numRead = in.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            in.close();
            return toHexString(md5.digest(), useCommonDigit);
        } catch (Exception e) {
            return "";
        }
    }

    public static String encryptStr(String str, HashType type,
                                    boolean useCommonDigit) {
        try {
            MessageDigest md = MessageDigest.getInstance(type.getValue());
            md.update(str.getBytes());
            byte b[] = md.digest();
            return toHexString(b, useCommonDigit);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.err.println("没有这种算法:" + type.getValue());
        }
        return "";
    }

    private static String toHexString(byte[] b, boolean useCommonDigit) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            if (useCommonDigit) {
                sb.append(commonDigit[(b[i] & 0xf0) >>> 4]);
                sb.append(commonDigit[b[i] & 0x0f]);
            } else {
                sb.append(digit[(b[i] & 0xf0) >>> 4]);
                sb.append(digit[b[i] & 0x0f]);
            }
        }
        return sb.toString();
    }

    public enum HashType {
        MD5("MD5"), SHA_1("SHA-1"), SHA_256("SHA-256"), SHA_384("SHA-384"), SHA_512(
                "SHA-512");
        private String value;

        private HashType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

}

package cc.yysy.utilscommon.utils;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 哈希封装类
 * 随机盐哈希：无需在数据库中加盐
 * 普通哈希
 * @author Wds
 */
public class HASHUtils {
    /**
     * 加盐哈希获取摘要
     * @param str
     * @return
     */
    public static String saltEncode(String str){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(str);
        return encode;
    }

    /**
     * 加盐哈希匹配摘要
     * @param a
     * @param b
     * @return
     */
    public static boolean saltMatches(String a,String b){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(a, b);
    }



    /*******普通哈希********/
    public static String encode(String str){
        try {
            byte[] bytes = str.getBytes("utf-8");
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            byte[] digest1 = digest.digest(bytes);
            if (digest1.length != 32) {
                System.out.println("计算hash值失败");
                return null;
            }
            return Hex.toHexString(digest1);
        } catch (UnsupportedEncodingException u) {
            System.exit(-2);
        } catch (NoSuchAlgorithmException n) {
            System.exit(-3);
        }
        return null;
    }
    public static boolean matches(String a,String b){
        return a.compareTo(b) == 0;
    }
}

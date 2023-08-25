package com.wuwei.homework.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static String md5(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = messageDigest.digest(text.getBytes());
        return Hex.encodeHexString(bytes);
    }
}

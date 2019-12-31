package com.tavi.duongnt.user_service.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncodeUtils {

    private static final Logger LOGGER = Logger.getLogger(EncodeUtils.class.getName());

    public static String getSHA256(String password) {
        String rs = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            rs = bytesToHex(md.digest());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "get-sha256: {0}", ex.getMessage());
        }
        return rs;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }


}

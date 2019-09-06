package com.techdevsolutions.shared.service;

import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static String md5(String stringToHash) {
        if (StringUtils.isEmpty(stringToHash)) {
            throw new IllegalArgumentException("String is null or empty");
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(stringToHash.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    public static String md5(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("File does not exist: " + file.getAbsolutePath());
        }

        byte[] bytes = new byte[(int) file.length()];
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(bytes);
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}

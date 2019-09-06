package com.techdevsolutions.files.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class AESEncryptionService {
    protected static final String ENCRYPTION = "AES";
    protected static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";
    protected static final String SECRET_KEY_FACTORY_INST = "PBKDF2WithHmacSHA256";
    protected static final Integer IV_SIZE = 16;
    protected static final Integer SALT_SIZE = 16;
    protected static final Integer KEY_LENGTH = 256;
    protected static final Integer ITERATIONS = 65536;
    protected static final SecureRandom RandomSecureRandom = new SecureRandom();

    private SecretKeySpec generateKeySpec(String secret, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (StringUtils.isEmpty(secret)) {
            throw new IllegalArgumentException("secret is empty");
        } else if (salt == null) {
            throw new IllegalArgumentException("salt is null");
        } else if (salt.length != AESEncryptionService.SALT_SIZE) {
            throw new IllegalArgumentException("invalid salt length. muist be: " + AESEncryptionService.SALT_SIZE);
        }

        SecretKeyFactory factory = SecretKeyFactory.getInstance(AESEncryptionService.SECRET_KEY_FACTORY_INST);
        Integer iterations = AESEncryptionService.ITERATIONS;
        Integer keyLength = AESEncryptionService.KEY_LENGTH;
        KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt, iterations, keyLength);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), AESEncryptionService.ENCRYPTION);
    }

    public String encrypt(String strToEncrypt, String secret, byte[] iv, byte[] salt) throws Exception {
        if (StringUtils.isEmpty(strToEncrypt)) {
            throw new IllegalArgumentException("String to encrypt is empty");
        } else if (StringUtils.isEmpty(secret)) {
            throw new IllegalArgumentException("secret is empty");
        } else if (iv == null) {
            throw new IllegalArgumentException("iv is null");
        } else if (iv.length != AESEncryptionService.IV_SIZE) {
            throw new IllegalArgumentException("invalid iv length. muist be: " + AESEncryptionService.IV_SIZE);
        } else if (salt == null) {
            throw new IllegalArgumentException("salt is null");
        } else if (salt.length != AESEncryptionService.SALT_SIZE) {
            throw new IllegalArgumentException("invalid salt length. muist be: " + AESEncryptionService.SALT_SIZE);
        }

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec = this.generateKeySpec(secret, salt);
        Cipher cipher = Cipher.getInstance(AESEncryptionService.CIPHER_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] bytes = strToEncrypt.getBytes(StandardCharsets.UTF_8.name());
        bytes = cipher.doFinal(bytes);
        return Base64.getEncoder().encodeToString(iv) +
                Base64.getEncoder().encodeToString(salt) +
                Base64.getEncoder().encodeToString(bytes);
    }

    public String decrypt(String strToDecrypt, String secret) throws Exception {
        if (StringUtils.isEmpty(strToDecrypt)) {
            throw new Exception("String to decrypt is empty");
        } else if (StringUtils.isEmpty(secret)) {
            throw new Exception("secret is empty");
        }  else if (strToDecrypt.length() <= 48) {
            throw new Exception("String to decrypt must be larger than 48 characters");
        }

        String ivStr = strToDecrypt.substring(0, 24);
        byte[] iv = Base64.getDecoder().decode(ivStr);
        String saltStr = strToDecrypt.substring(24, 48);
        byte[] salt = Base64.getDecoder().decode(saltStr);
        String encryptedStr = strToDecrypt.substring(48);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec = this.generateKeySpec(secret, salt);
        Cipher cipher = Cipher.getInstance(AESEncryptionService.CIPHER_MODE);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] bytes = Base64.getDecoder().decode(encryptedStr);
        bytes = cipher.doFinal(bytes);
        return new String(bytes);
    }

    public byte[] generateRandomBytes() {
        byte[] bytes = new byte[AESEncryptionService.IV_SIZE];
        AESEncryptionService.RandomSecureRandom.nextBytes(bytes);
        return bytes;
    }
}
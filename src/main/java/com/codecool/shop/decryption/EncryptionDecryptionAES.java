package com.codecool.shop.decryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class EncryptionDecryptionAES {
    private Cipher cipher;
    private KeyGenerator keyGenerator;
    private static EncryptionDecryptionAES instance = null;

    private void initiate() {
        try{
            cipher = Cipher.getInstance("AES");
            keyGenerator = KeyGenerator.getInstance("AES");
        }catch (NoSuchPaddingException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }

    }
    public  Map<String, Object> encrypt(String plainText) {
        try{
            SecretKey secretKey = keyGenerator.generateKey();

            byte[] plainTextByte = plainText.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = cipher.doFinal(plainTextByte);

            Base64.Encoder encoder = Base64.getEncoder();
            String encryptedText = encoder.encodeToString(encryptedByte);

            Map<String, Object> encMap = new HashMap<>();
            encMap.put("secretKey", secretKey);
            encMap.put("encryptedText", encryptedText);
            return encMap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String encryptedText, SecretKey secretKey)
            throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);

        return decryptedText;
    }


    private EncryptionDecryptionAES(){
        initiate();
    }

    public static EncryptionDecryptionAES getInstance(){
        if (instance == null){
            instance = new EncryptionDecryptionAES();
        }
        return instance;
    }
    public SecretKey decSecretKey(String encKey){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encodedKey = decoder.decode(encKey);
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length,"AES");
        return originalKey;
    }
    public String encSecretKey(SecretKey secretKey){
        Base64.Encoder encoder = Base64.getEncoder();
        String stringKey = encoder.encodeToString(secretKey.getEncoded());
        return stringKey;
    }


}

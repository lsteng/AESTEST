package com.aes.aestest;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author vipin.cb , vipin.cb@experionglobal.com <br>
 *         Sep 27, 2013, 5:18:34 PM <br>
 *         Package:- <b>com.veebow.util</b> <br>
 *         Project:- <b>Veebow</b>
 *         <p>
 */
public class AESCrypt {
    private final Cipher cipher;
    private final SecretKeySpec key;
    private AlgorithmParameterSpec spec;

//    private static final char[] seedArray    = {'&', 'x', 'A', '&', '1', 'I', 'd', '1', 'N', 'M', 'p', 'q', 'j', '6', 'Y', 'Z'};
//    private static final char[] ivArray      = {'G', 'X', 'N', 'L', 'j', 'h', 'Z', '4', 'y', 'x', '9', 'Z', 'z', 'c', 'D', 'Q'};
    private String SEED_16_CHARACTER    = "Gow5H^MAm0Qqw^km";//"nbLlfn&ZllPvvV2d"; //News加密用密碼
    private String ivParameter          = "Xg@fX10grTLa%PC%";//"vc3PdVcS7EXR5kG@"; //偏移量,可自行修改

    public final static String newsIvAES    = "tH#qWkAztoAo7dYK";//"&xA&1Id1NMpqj6YZ"; //News加密用密碼
    public final static String newsKeyAES   = "h9#!iMpYytA35rbH";//"GXNLjhZ4yx9ZzcDQ"; //News輸出偏移量
    public final static String mtvIvAES     = "1pxyXiKK7EUh8QW4";//"nbLlfn&ZllPvvV2d"; //MTV加密用密碼
    public final static String mtvKeyAES    = "&EIA^6B5h3pB9U51";//"vc3PdVcS7EXR5kG@"; //MTV輸出偏移量

    public AESCrypt() throws Exception {
        selectAES();

        // hash password with SHA-256 and crop the output to 128-bit for key
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        SEED_16_CHARACTER =  String.valueOf(seedArray);
        digest.update(SEED_16_CHARACTER.getBytes("UTF-8"));
        byte[] keyBytes = new byte[32];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        key = new SecretKeySpec(keyBytes, "AES");
        spec = getIV();
    }
    public AlgorithmParameterSpec getIV() {
//        String ivParameter = MainActivity.IvAES;
//        ivParameter =  String.valueOf(ivArray);
        byte[] iv = ivParameter.getBytes();
        //byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }
    public String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedText = new String(Base64.encode(encrypted, Base64.DEFAULT), "UTF-8");
        return encryptedText;
    }
    public String decrypt(String cryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(bytes);
        String decryptedText = new String(decrypted, "UTF-8");
        return decryptedText;
    }

    private void selectAES(){
        switch (MainActivity.pos){
            default:
            case 0:
                SEED_16_CHARACTER    = newsIvAES;
                ivParameter          = newsKeyAES;
                break;
            case 1:
                SEED_16_CHARACTER    = mtvIvAES;
                ivParameter          = mtvKeyAES;
                break;
        }
    }
}
package edu.utn.criptografia;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class CifradoSimetrico {
    private int keyLength;
    private byte[] key;
    private byte[] iv;

    public CifradoSimetrico(int keyLength) {
        this.keyLength = keyLength;

        SecureRandom random = new SecureRandom();
        key = new byte[this.keyLength / 8];
        random.nextBytes(key);
    
        // el initialization vector siempre tiene 128 bits
        // puede estar sin proteccion
        iv = new byte[16];
        random.nextBytes(iv); 
    }

    public byte[] cifrar(String plainText) {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
    
        byte[] cipherText;
        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
        
            cipherText = cipher.doFinal(plainText.getBytes());
            return cipherText;
        } catch (Exception e) {
            throw new RuntimeException("Falló cifrado! ", e);
        }
    }

    public String decifrar(byte[] cipherText) {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

        try {
            Cipher decipher = Cipher.getInstance("AES/CTR/PKCS5PADDING");
            decipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
            final byte[] plainTextAgainRaw = decipher.doFinal(cipherText);
            
            return new String(plainTextAgainRaw);
        } catch (Exception e) {
            throw new RuntimeException("Falló decifrado! ", e);
        }
    }

    public static void ejemplo() {
        final String plainText = "Viva la criptografía!";

        final CifradoSimetrico sim128 = new CifradoSimetrico(128);
        byte[] cipherText128 = sim128.cifrar(plainText);
        System.out.println("cifrado 128: " + Hex.encodeHexString(cipherText128));
        final String plainText128 = sim128.decifrar(cipherText128);
        System.out.println("decifrado 128: " + plainText128);

        final CifradoSimetrico sim256 = new CifradoSimetrico(256);
        byte[] cipherText256 = sim256.cifrar(plainText);
        System.out.println("cifrado 256: " + Hex.encodeHexString(cipherText256));
        final String plainText256 = sim256.decifrar(cipherText256);
        System.out.println("decifrado 256: " + plainText256);
    }
}

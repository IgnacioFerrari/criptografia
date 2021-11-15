package edu.utn.criptografia;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;

public class CifradoAsimetrico {
    private KeyPair keyPair;

    public CifradoAsimetrico(int keyLength) {
        try {

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keyLength);
            
            this.keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("RSA no soportado! ", e);
        }
    }

    // se usa la pública para cifrar
    public byte[] cifrar(byte plainText[]) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            
            byte[] cipherText = cipher.doFinal(plainText) ;
    
            return cipherText;
        } catch (Exception e) {
            throw new RuntimeException("RSA no soportado! ", e);
        }
    }

    // se usa la pública para cifrar
    public byte[] decifrar(byte cipherText[]) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            
            byte[] plainText = cipher.doFinal(cipherText) ;
    
            return plainText;
        } catch (Exception e) {
            throw new RuntimeException("RSA no soportado! ", e);
        }
    }

    public static void ejemplo() {
        SecureRandom random = new SecureRandom();
        
        // keys to cipher
        final List<Integer> aesKeySizes = new ArrayList<>();
        aesKeySizes.add(16);
        aesKeySizes.add(32);

        final List<Integer> rsaKeySizes = new ArrayList<>();
        rsaKeySizes.add(2048);
        rsaKeySizes.add(4096);

        for (final int aesKeySize : aesKeySizes) {
            final byte[] aesKey = new byte[aesKeySize];
            random.nextBytes(aesKey);

            System.out.println("AES key size: " + aesKeySize);

            for (final int rsaKeySize : rsaKeySizes) {
                System.out.println("RSA key size: " + rsaKeySize);

                final CifradoAsimetrico asym = new CifradoAsimetrico(rsaKeySize);
                byte[] cipherText = asym.cifrar(aesKey);

                // System.out.println("cifrado: " + Hex.encodeHexString(aesKey) + " => " + Hex.encodeHexString(cipherText));
                final byte[] plainText = asym.decifrar(cipherText);
                // System.out.println("decifrado : " + Hex.encodeHexString(cipherText) + " => " + Hex.encodeHexString(plainText));

                System.out.println("matchean: " + Arrays.equals(aesKey, plainText));
            }
        }

        /*final CifradoAsimetrico sim256 = new CifradoAsimetrico(256);
        byte[] cipherText256 = sim256.cifrar(plainText);
        System.out.println("cifrado 256: " + Hex.encodeHexString(cipherText256));
        final String plainText256 = sim256.decifrar(cipherText256);
        System.out.println("decifrado 256: " + plainText256);*/
    }
}

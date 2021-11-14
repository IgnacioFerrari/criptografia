package edu.utn.criptografia;

import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;


public class App 
{
    public static void main( String[] args )
    {
        Security.setProperty("crypto.policy", "unlimited");
        //Security.addProvider(new BouncyCastleProvider());
        /*Arrays.stream(Security.getProviders())
            .flatMap(provider -> provider.getServices().stream())
            .filter(service -> "Cipher".equals(service.getType()) && service.getAlgorithm().contains("AES"))
            .map(Provider.Service::getAlgorithm)
            .forEach(System.out::println);*/

            try {

                int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
                System.out.println("Max Key Size for AES : " + maxKeySize);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        final String plainText = "Viva la criptografía!";

        SecureRandom random = new SecureRandom();
        byte keyRaw[] = new byte[32];
        random.nextBytes(keyRaw);

        byte ivRaw[] = new byte[16];
        random.nextBytes(ivRaw);

        System.out.println(Hex.encodeHexString(keyRaw));
        System.out.println(Hex.encodeHexString(ivRaw));

        // encrypt
        IvParameterSpec iv = new IvParameterSpec(ivRaw);
        SecretKeySpec skeySpec = new SecretKeySpec(keyRaw, "AES");
  
        byte[] cipherText;
        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
      
            cipherText = cipher.doFinal(plainText.getBytes());
            System.out.println(Hex.encodeHexString(cipherText));
        } catch (Exception e) {
            System.err.println("Falló encriptación! " + e.getMessage());
            return;
        }


    }
}

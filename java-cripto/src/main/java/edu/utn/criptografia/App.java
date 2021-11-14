package edu.utn.criptografia;

import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import edu.CifradoAsimetrico;


public class App 
{
    public static void main( String[] args )
    {
        //Security.setProperty("crypto.policy", "unlimited");
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

        //CifradoSimetrico.ejemplo();
        CifradoAsimetrico.ejemplo();
    }
}

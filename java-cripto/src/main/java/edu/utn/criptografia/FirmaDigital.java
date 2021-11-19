package edu.utn.criptografia;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.crypto.Cipher;

// https://www.baeldung.com/java-digital-signature
public class FirmaDigital {
    final char[] PASSWORD = "utn_frba_criptografia_2021".toCharArray();

    public byte[] firmar(final String message) {
        // todo: deshardcodear
        final PrivateKey privateKey;
        
        try {
            final KeyStore keyStore = KeyStore.getInstance("PKCS12");
            final InputStream is = new FileInputStream("emisor_keystore.p12");
            keyStore.load(is, PASSWORD);
            privateKey = (PrivateKey) keyStore.getKey("emisor", PASSWORD);
            
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageHash = md.digest(message.getBytes());
            
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] digitalSignature = cipher.doFinal(messageHash);
            
            return digitalSignature;
        } catch (CertificateException | IOException | KeyStoreException | UnrecoverableKeyException e) {
            throw new RuntimeException("No se pudo cargar la clave privada!", e);
        } catch (Exception criptoException) {
            throw new RuntimeException("Algún algoritmo no es soportado", criptoException);
        }
    }

    public boolean verificar(final String message, final byte[] signature) {
        // todo: deshardcodear
        final PublicKey publicKey;
        
        try {
            final KeyStore keyStore = KeyStore.getInstance("PKCS12");
            final InputStream is = new FileInputStream("receptor_keystore.p12");
            keyStore.load(is, PASSWORD);
            Certificate certificate = keyStore.getCertificate("receptor");
            
            publicKey = certificate.getPublicKey();
            
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageHash = md.digest(message.getBytes());
            
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decriptedHash = cipher.doFinal(signature);
            
            return Arrays.equals(messageHash, decriptedHash);
        } catch (CertificateException | IOException | KeyStoreException e) {
            throw new RuntimeException("No se pudo cargar la clave privada!", e);
        } catch (Exception criptoException) {
            throw new RuntimeException("Algún algoritmo no es soportado", criptoException);
        }
    }

    public static void ejemplo() {
        final String mensaje = "Autenticación + No repudio + integridad";
        final FirmaDigital firmaDigital = new FirmaDigital();
        final byte[] firma = firmaDigital.firmar(mensaje);
        final boolean verifica = firmaDigital.verificar(mensaje, firma);
        System.out.println("verifica? " + verifica);
    }
}
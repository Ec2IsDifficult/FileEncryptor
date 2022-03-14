package sample;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

public class KeyGeneration {
    public SecretKeySpec generateKey() throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public IvParameterSpec generateIv() throws Exception{
        SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public IvParameterSpec getIv(byte[] receivedEncryptedMessage){
        return new IvParameterSpec(Arrays.copyOfRange(receivedEncryptedMessage,0,16));
    }

    public byte[] getMessage(byte[] receivedEncryptedMessage){
        return Arrays.copyOfRange(receivedEncryptedMessage,16,receivedEncryptedMessage.length);
    }

    public void test(){
        System.out.println("Jajo");
    }
}
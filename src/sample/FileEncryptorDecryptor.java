package sample;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.util.Arrays;

public class FileEncryptorDecryptor {
    Cipher cipher;
    MessageDigest messageDigest;
    KeyStore keyStore;
    KeyStoreLogic keyStoreLogic;
    KeyGeneration keyGeneration;
    public FileEncryptorDecryptor() throws Exception{
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        this.keyStoreLogic = new KeyStoreLogic();
        this.keyGeneration = new KeyGeneration();
        //TODO: basically all of this is singleton logic stored in the wrong class
        this.keyStore = this.keyStoreLogic.loadExistingKeyStore(FileUtil.KeyStorePath, FileUtil.keyStorePassword);
        this.cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        this.messageDigest = MessageDigest.getInstance("SHA-256", "BC");
    }

    public byte[] encryptFile(byte[] binaryFile) throws Exception {
        SecretKeySpec key = this.keyGeneration.generateKey();
        IvParameterSpec iv = this.keyGeneration.generateIv();
        this.cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encryptedFile = this.cipher.doFinal(binaryFile);
        this.keyStoreLogic.storeKey(this.keyStore, key);
        this.keyStoreLogic.writeKeyStoreToFile(FileUtil.KeyStorePath, this.keyStore, FileUtil.keyStorePassword);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(iv.getIV());
        stream.write(encryptedFile);
        return stream.toByteArray();
    }

    public byte[] createSignature(byte[] binaryFile) throws Exception {
        return this.messageDigest.digest(binaryFile);
    }

    public byte[] decryptFile(byte[] binaryFile) throws Exception {
        Key key = keyStoreLogic.getKeyFromStore(this.keyStore, "firstKey", "burger");
        IvParameterSpec iv = this.keyGeneration.getIv(binaryFile);
        this.cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return this.cipher.doFinal(this.keyGeneration.getMessage(binaryFile));
    }

    public boolean checkIntegrity() {

        return false;
    }
}

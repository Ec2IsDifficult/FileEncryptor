package sample;

import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.Security;

public class KeyStoreLogic {


    public KeyStoreLogic(){
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public KeyStore generateNewKeyStore() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("BKS", "BC");
        keyStore.load(null, null);
        return keyStore;
    }

    public KeyStore loadExistingKeyStore(String storeFileName, String pw) throws Exception{
        KeyStore keyStore = KeyStore.getInstance("BKS", "BC");
        FileInputStream fis = new FileInputStream(storeFileName);
        keyStore.load(fis, pw.toCharArray());
        fis.close();
        return keyStore;
    }

    public void storeKey(KeyStore keyStore, SecretKeySpec key) throws Exception{
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection("burger".toCharArray());
        keyStore.setEntry("firstKey", entry, protectionParameter);
    }

    public Key getKeyFromStore(KeyStore keyStore, String alias, String pw) throws Exception {
        return keyStore.getKey(alias, pw.toCharArray());
    }

    public void writeKeyStoreToFile(String storeFileName, KeyStore keyStore, String pw) throws Exception{
        FileOutputStream fOut = new FileOutputStream(storeFileName);
        keyStore.store(fOut, pw.toCharArray());
        fOut.close();
    }
}
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public class Controller {

    private Stage stage;
    File file;
    private final FileEncryptorDecryptor fileEncryptorDecryptor;
    byte[] fileInByteFormat;
    private byte[] encryptedFile;
    private byte[] fileSignature;

    //Encryption
    @FXML
    TextArea fileSample;
    @FXML
    Button chooseFileToEncrypt;
    @FXML
    Button encryptFile;

    //Decryption
    @FXML
    Button chooseFileToDecrypt;
    @FXML
    Button DecryptFile;
    public Controller() throws Exception{
        this.fileEncryptorDecryptor = new FileEncryptorDecryptor();
    }

    public void setStage(Stage stage) throws Exception{
        this.stage = stage;
    }

    @FXML
    private void closeButtonAction(){
        this.stage.close();
    }

    @FXML
    private void changeToDecrypt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("decryptView.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void changeToEncrypt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("encryptView.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void chooseFileAction() {
        FileChooser fileChooser = new FileChooser();
        this.file = fileChooser.showOpenDialog(this.stage);
        this.fileInByteFormat = FileUtil.readAllBytes(this.file.toPath());
        System.out.println(Hex.toHexString(this.fileInByteFormat));
        displayFileSample(this.fileInByteFormat);
    }

    @FXML
    private void encryptFileAction() throws Exception {
        this.encryptedFile = this.fileEncryptorDecryptor.encryptFile(this.fileInByteFormat);
        this.fileSignature = this.fileEncryptorDecryptor.createSignature(this.fileInByteFormat);
        displayEncryptedFileSample(encryptedFile, fileSignature);
    }

    @FXML
    private void decryptFileAction() throws Exception {
        byte[] decryptedFile = this.fileEncryptorDecryptor.decryptFile(this.fileInByteFormat);
        displayDecryptedFileSample(new String(decryptedFile, StandardCharsets.UTF_8));
    }

    @FXML
    private void writeEncryptedFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(this.stage);
        FileUtil.write(selectedFile.toPath(), this.encryptedFile);
        File file = new File(selectedFile.getPath() + ".sha256");
        FileUtil.write(file.toPath(), this.fileSignature);
        this.fileSample.setText("File saved!");
    }

    @FXML
    private void verifyIntegrity() {

    }

    private void displayDecryptedFileSample(String file) {
        this.fileSample.setText(file);

    }

    private void displayEncryptedFileSample(byte[]... args) {
        this.fileSample.setText("");
        for (byte[] arg:args) {
            this.fileSample.appendText(Hex.toHexString(arg));
            this.fileSample.appendText("\n");
        }
    }

    private void displayFileSample(byte[] input) {
        String str = new String(input, StandardCharsets.UTF_8);
        this.fileSample.setText(str);
    }
}

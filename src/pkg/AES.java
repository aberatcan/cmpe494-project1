package pkg;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class AES {

    public static void main(String[] args) throws IOException {
        // args[0] = "e" or "d"
        // args[1] = keyFile
        // args[2] = input file

        //key file
        String keyFile = "src/pkg/" + args[1];
        Scanner keyScanner = new Scanner(new File(keyFile));
        String encryptionKey = keyScanner.nextLine();


        byte[] key = encryptionKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        //encryption
        if (args[0].equals("e")) {
            // input file
            String inputFile = "src/pkg/" + args[2];
            Scanner inputScanner = new Scanner(new File(inputFile));

            //output file
            String outputFile = "src/pkg/" + args[2] + ".enc";
            File outP = new File(outputFile);
            PrintStream printStream = new PrintStream(outP);


            //write encrypted text to output file
            while (inputScanner.hasNextLine()) {
                String plainString = inputScanner.nextLine();
                String encryptedString = encrypt(plainString, secretKey);
                printStream.println(encryptedString);
            }

        }

        //decryption
        if (args[0].equals("d")) {
            //decrypt input file
            String inputFile = "src/pkg/" + args[2];
            Scanner inputScanner = new Scanner(new File(inputFile));

            //output file
            String outputFile = "src/pkg/" + args[2] + ".dec";
            PrintStream printStream = new PrintStream(outputFile);

            while (inputScanner.hasNextLine()) {
                String encryptedString = inputScanner.nextLine();
                String decryptedString = decrypt(encryptedString, secretKey);
                printStream.println(decryptedString);
            }

        }
    }

    public static String encrypt(String plainString, SecretKeySpec secretKey) {
        try {

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainString.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String cipherText, SecretKeySpec secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
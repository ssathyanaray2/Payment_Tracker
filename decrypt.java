import java.util.Scanner;
import java.io.*; 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.CipherInputStream;
import javax.imageio.stream.ImageOutputStream;

public class decrypt{
   public static void main(String[] arg) throws Exception {
	   Scanner scanner = new Scanner(System.in);
		 byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
	   File inputFile = new File("outputimage.png");
      FileInputStream fis = new FileInputStream(inputFile);
	   System.out.print("Decryption Password: ");
       System.out.flush();
       PBEKeySpec pbeKeySpec = new PBEKeySpec(scanner.next().toCharArray());
	   PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);
       SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
       SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
	   Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
       pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
       CipherInputStream cis = new CipherInputStream(fis, pbeCipher);
        BufferedImage input = ImageIO.read(cis);
		FileOutputStream output = new FileOutputStream("decrypt.png");
		ImageIO.write(input, "PNG", output);
        cis.close();
	   
	   
   }
}
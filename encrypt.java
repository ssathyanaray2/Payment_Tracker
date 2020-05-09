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


public class encrypt{
   public static void main(String[] arg) throws Exception {
	    Scanner scanner = new Scanner(System.in);
		 byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
		 {
     File inputFile = new File("C:\\Users\\SINDHURA\goldfinch.jpg");
      BufferedImage input = ImageIO.read(inputFile);
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	  System.out.print("Password: ");
     System.out.flush();
     PBEKeySpec pbeKeySpec = new PBEKeySpec(scanner.nextLine().toCharArray());
	 PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);
     SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
	 Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
     pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
     FileOutputStream output = new FileOutputStream("outputimage.txt");
     CipherOutputStream cos = new CipherOutputStream(output, pbeCipher);
     //File outputFile = new File("image.png");
      ImageIO.write(input,"PNG",cos);
      cos.close();
		 }
   }
}
		
package analyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.codec.binary.Hex;

public class Main {
	
	public static void main(String [] args) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		
		String pathToFile = "/home/dulaj/eclipse-workspace/VirusHOLMS_help/may_be_virus.docx";
		String dbFilePath = "/home/dulaj/eclipse-workspace/VirusHOLMS/src/db.txt";
		
		Scanner s = new Scanner(new File(dbFilePath));
		ArrayList <String> virusDB = new ArrayList<String>();
		while (s.hasNext()){
		    virusDB.add(s.next());
		}
		s.close();
		File file = new File(pathToFile);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		String digest = getDigest(new FileInputStream(file), md, 2048);

		for(int i=0;i<virusDB.size();i++) {
			if(digest.equals(virusDB.get(i))) {
				System.out.println("Its a virus");
				return;
			}
		}
		System.out.println("not a virus and digest is: "+digest);
		
	}
	
	public static String getDigest(InputStream is, MessageDigest md, int byteArraySize)
			throws NoSuchAlgorithmException, IOException {

		md.reset();
		byte[] bytes = new byte[byteArraySize];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			md.update(bytes, 0, numBytes);
		}
		byte[] digest = md.digest();
		String result = new String(Hex.encodeHex(digest));
		return result;
	}
}

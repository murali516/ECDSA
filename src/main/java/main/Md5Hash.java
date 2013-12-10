package	main;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class Md5Hash {
	
	public String getMD5Hash(File file){
		StringBuffer sb = null;
		
		try{
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        FileInputStream fis = new FileInputStream(file);
	 
	        byte[] dataBytes = new byte[1024];
	        int nread = 0; 
	        
	        while ((nread = fis.read(dataBytes)) != -1) {
	          md.update(dataBytes, 0, nread);
	        };
	        
	        byte[] mdbytes = md.digest();
	        
	        sb = new StringBuffer();
	        for (int i = 0; i < mdbytes.length; i++) {
	          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        System.out.println("Digest(in hex format):: " + sb.toString());
	        fis.close();
	        
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return sb.toString();
	}
	

//	public static void main(String[] args){
//		String fileName = "/home/francis/JarFiles/PirateFileFlusher_ver_0.9.tar.gz";
//		try{
//	        MessageDigest md = MessageDigest.getInstance("MD5");
//	        FileInputStream fis = new FileInputStream(fileName);
//	 
//	        byte[] dataBytes = new byte[1024];
////	        byte[] saltValue = "P!R@T33D".getBytes();
//	        int nread = 0; 
//	        
//	        while ((nread = fis.read(dataBytes)) != -1) {
//	          md.update(dataBytes, 0, nread);
//	        };
//	        
////	        md.update(saltValue);
//	        byte[] mdbytes = md.digest();
//	        
//	        StringBuffer sb = new StringBuffer();
//	        for (int i = 0; i < mdbytes.length; i++) {
//	          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
//	        }
//	 
//	        System.out.println("Digest(in hex format):: " + sb.toString());
//	        fis.close();
//	        
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//	}
}

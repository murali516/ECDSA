package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.asn1.x9.KeySpecificInfo;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class ExplicitVerify {
	
	
	public static void main(String[] args){
		TOBase64 base64Val = new TOBase64();
	  try {
		Security.addProvider(new BouncyCastleProvider());
		FileInputStream keyfis = new FileInputStream(new File(new String("public_key")));
      	byte[] encKey = new byte[keyfis.available()];  
      	keyfis.read(encKey);
      	
      	keyfis.close();
      	
      	X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
      	
      	KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
      	PublicKey pubKey1 = keyFactory.generatePublic(pubKeySpec);
      	
      	//Signature
//      	FileInputStream sigfis = new FileInputStream(new File(new String("signature")));
      	char[] buffer = "MEACHjv+ElKKB7MQH1XO9icCvAMOUUniDboSL+5AIbe4PQIeFo13paQWdda+08/3dJvEGChWXq1+CM+B/AtxHb2X".toCharArray();
      	
      	byte[] sigToVerify = new byte[buffer.length];
      	for (int i = 0; i < sigToVerify.length; i++) {
      	sigToVerify[i] = (byte) buffer[i];
      	}
      	
      	
      	byte[] sigToVerifyval = base64Val.decodeFromBase64(sigToVerify);
      	System.out.println("Signature is " + new BigInteger(1, sigToVerifyval).toString(16));
//      	sigfis.read(sigToVerify);
//      	sigfis.close();
      	
      	Signature sig = Signature.getInstance("SHA256withECDSA", "BC"); 
      	
      	sig.initVerify(pubKey1);
      	System.out.println("Public key is " + new BigInteger(1, encKey).toString(16));
      	FileInputStream datafis = new FileInputStream(new String("/home/francis/JarFiles/PirateFileFlusher_ver_0.4.tar.gz"));
      	BufferedInputStream bufin = new BufferedInputStream(datafis);

      	byte[] buffer1 = new byte[1024];
      	int len;
      	while (bufin.available() != 0) {
      	    len = bufin.read(buffer1);
      	    sig.update(buffer1, 0, len);
      	};

      	bufin.close();
      	
      	System.out.println("checking");
      	boolean verifies = sig.verify(sigToVerifyval);

      	System.out.println("signature verifies: " + verifies);

	        } catch (Exception e) {
	            System.err.println("Caught exception " + e.toString());
	        }
	        	
}
	
}

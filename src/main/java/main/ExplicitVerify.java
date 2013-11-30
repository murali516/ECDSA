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
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ExplicitVerify {
	
	
	public static void main(String[] args){
	  try {
		Security.addProvider(new BouncyCastleProvider());
		FileInputStream keyfis = new FileInputStream(new File(new String("public_key")));
      	byte[] encKey = new byte[keyfis.available()];  
      	keyfis.read(encKey);
      	keyfis.close();
      	
      	X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
      	KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
      	PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
      	
      	//Signature
      	FileInputStream sigfis = new FileInputStream(new File(new String("signature")));
      	byte[] sigToVerify = new byte[sigfis.available()]; 
      	System.out.println("Signature is " + new BigInteger(1, sigToVerify).toString(16));
      	sigfis.read(sigToVerify);
      	sigfis.close();
      	
      	Signature sig = Signature.getInstance("SHA256withECDSA", "BC"); 
      	
      	sig.initVerify(pubKey);
      	System.out.println("Public key is " + new BigInteger(1, encKey).toString(16));
      	FileInputStream datafis = new FileInputStream(new String("E:\\PirateFileFlusher.jar"));
      	BufferedInputStream bufin = new BufferedInputStream(datafis);

      	byte[] buffer = new byte[1024];
      	int len;
      	while (bufin.available() != 0) {
      	    len = bufin.read(buffer);
      	    sig.update(buffer, 0, len);
      	};

      	bufin.close();
      	
      	boolean verifies = sig.verify(sigToVerify);

      	System.out.println("signature verifies: " + verifies);

	        } catch (Exception e) {
	            System.err.println("Caught exception " + e.toString());
	        }
	        	
}
}

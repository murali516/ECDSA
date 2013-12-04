package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;

import org.bouncycastle.jcajce.provider.asymmetric.EC;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import java.security.spec.InvalidKeySpecException;
import org.bouncycastle.jce.spec.*;

public class ExplicitPait {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException {
		
		Security.addProvider(new BouncyCastleProvider());
		File file = new File(new String("/home/francis/JarFiles/PirateFileFlusher_ver_0.6.tar.gz"));
		TOBase64 base64 = new TOBase64();
		ECNamedCurveParameterSpec ecCurveSpec = ECNamedCurveTable.getParameterSpec("prime239v1");
		EllipticCurve ellipticCurve = EC5Util.convertCurve(ecCurveSpec.getCurve(), ecCurveSpec.getSeed()); 
		ECParameterSpec specParams = EC5Util.convertSpec(ellipticCurve, ecCurveSpec); 
		
		ECPrivateKeySpec priKey = new ECPrivateKeySpec(
                new BigInteger("876300101507107567501066130761671078357010671067781776716671676178726717"), // d
                specParams);

		ECPublicKeySpec pubKey = new ECPublicKeySpec(
                     ECPointUtil.decodePoint(
                    		 ellipticCurve,
                              Hex.decode("025b6dc53bc61a2548ffb0f671472de6c9521a9d2d2534e65abfcbd5fe0c70")), // Q
                 specParams);
		
		final KeyFactory kf = KeyFactory.getInstance("ECDSA", "BC");
		final PrivateKey privKey = kf.generatePrivate(priKey);
		final byte[] rawPrivKey = privKey.getEncoded();

		// Recreate the public key.
		final PublicKey publKey = kf.generatePublic(pubKey);
		final byte[] rawPubKey = publKey.getEncoded();
	 
		Signature dsa = Signature.getInstance("SHA256withECDSA", "BC"); 
     	dsa.initSign(privKey);
    	
    	FileInputStream fis = new FileInputStream(file);
    	BufferedInputStream bufin = new BufferedInputStream(fis);
    	byte[] buffer = new byte[1024];
    	int len;
    	while ((len = bufin.read(buffer)) >= 0) {
    	    dsa.update(buffer, 0, len);
    	};
    	bufin.close();
    	
    	byte[] realSig = dsa.sign();
    	
     	
     	System.out.println("Signature is " + new BigInteger(1, realSig).toString(16));
     	FileOutputStream sigfos = new FileOutputStream("signature");
     	byte[] base64Val = base64.encodeToBase64(realSig);
     	sigfos.write(base64Val);
     	System.out.println("Signature in base 64 is " + new String(base64Val));
     	sigfos.close();
     	
     	System.out.println("Private key is " + new BigInteger(1, rawPrivKey).toString(16));
     	FileOutputStream privateKey = new FileOutputStream("privat_key");
     	privateKey.write(rawPrivKey);
     	privateKey.close();
     	
     	System.out.println("Public key is " + new BigInteger(1, rawPubKey).toString(16));
     	FileOutputStream publicKey = new FileOutputStream("public_key");
     	
     	byte[] EncPubKey = base64.encodeToBase64(rawPubKey);
     	publicKey.write(rawPubKey);
     	System.out.println("Public Key in base 64 is " + new String(EncPubKey));
     	
//     	publicKey.write(rawPubKey);
     	publicKey.close();
	}
}

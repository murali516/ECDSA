package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;

import org.bouncycastle.jcajce.provider.asymmetric.EC;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.security.spec.EllipticCurve;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPoint;
import java.security.spec.ECParameterSpec;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.jce.spec.*;

public class ExplicitPait {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException {
		
		Security.addProvider(new BouncyCastleProvider());
		File file = new File(new String("E:\\PirateFileFlusher.jar"));
		
		EllipticCurve curve = new EllipticCurve(
		            new ECFieldFp(new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839")), // q
		            new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16), // a             
		            new BigInteger("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a", 16)); // b

		ECParameterSpec ecSpec = new ECParameterSpec(
		            curve,
		            ECPointUtil.decodePoint(curve, Hex.decode("020ffa963cdca8816ccc33b8642bedf905c3d358573d3f27fbbd3b3cb9aaaf")), // G
		            new BigInteger("883423532389192164791648750360308884807550341691627752275345424702807307"), // n
		            1); // h

		ECPrivateKeySpec priKey = new ECPrivateKeySpec(
	            new BigInteger("876300101507107567501066130761671078357010671067781776716671676178726717"), // d
	            ecSpec);
		
		ECPublicKeySpec pubKey = new ECPublicKeySpec(
	            ECPointUtil.decodePoint(curve, Hex.decode("025b6dc53bc61a2548ffb0f671472de6c9521a9d2d2534e65abfcbd5fe0c70")), // Q
	            ecSpec);
		
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
     	sigfos.write(realSig);
     	sigfos.close();
     	
     	System.out.println("Private key is " + new BigInteger(1, rawPrivKey).toString(16));
//     	FileOutputStream privateKey = new FileOutputStream("privat_key");
//     	privateKey.write(rawPrivKey);
//     	privateKey.close();
     	
     	System.out.println("Public key is " + new BigInteger(1, rawPubKey).toString(16));
     	FileOutputStream publicKey = new FileOutputStream("public_key");
     	publicKey.write(rawPubKey);
     	publicKey.close();
	}
}

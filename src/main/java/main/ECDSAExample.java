package main;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;

import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.util.encoders.Hex;


public class ECDSAExample {
	
	
	public ECField getECField(){
		BigInteger fp = new BigInteger("FFFFFFFE"+"FFFFFFFF"+"FFFFFFFF"+"FFFFFFFF"+
                 "FFFFFFFF"+"00000000"+"FFFFFFFF"+"FFFFFFFF", 16);
		ECField f = new ECFieldFp(fp);
		return f;
	}
	
	public EllipticCurve getCurve(){
		   BigInteger a = new BigInteger("FFFFFFFE"+"FFFFFFFF"+"FFFFFFFF"+"FFFFFFFF"+
                   "FFFFFFFF"+"00000000"+"FFFFFFFF"+"FFFFFFFC", 16);

		   BigInteger b = new BigInteger("28E9FA9E"+"9D9F5E34"+"4D5A9E4B"+"CF6509A7"+
                   "F39789F5"+"15AB8F92"+"DDBCBD41"+"4D940E93", 16);
		   byte[] seed = null;
		   
		   EllipticCurve curve = new EllipticCurve(getECField(), a, b, seed );
		   
		   return curve;
	}

    public ECParameterSpec getParameterSpecs()
    {
        BigInteger x = new BigInteger("32C4AE2C"+"1F198119"+"5F990446"+"6A39C994"+
                                            "8FE30BBF"+"F2660BE1"+"715A4589"+"334C74C7", 16);
        BigInteger y = new BigInteger("BC3736A2"+"F4F6779C"+"59BDCEE3"+"6B692153"+
                                            "D0A9877C"+"C62A4740"+"02DF32E5"+"2139F0A0", 16);
        
        ECPoint g = new ECPoint(x, y);

        BigInteger n = new BigInteger("FFFFFFFE"+"FFFFFFFF"+"FFFFFFFF"+"FFFFFFFF"+
                                            "7203DF6B"+"21C6052B"+"53BBF409"+"39D54123", 16);
        int h = 1;

        ECParameterSpec gb256 = new ECParameterSpec(getCurve(), g, n, h);
    
    return gb256;
    } 
    
    
}

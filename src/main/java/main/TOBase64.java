package main;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class TOBase64{

    public byte[] encodeToBase64(byte[] hash) {
        //encoding  byte array into base 64
        byte[] encoded = Base64.encodeBase64(hash);     
        System.out.println("Base64 Encoded String - : " + new String(encoded));
        return encoded;
    }
    
    public byte[] decodeFromBase64(byte[] hash) {
    	byte[] decoded = Base64.decodeBase64(hash);
         System.out.println("Base64 Encoded String : " + new String(decoded));
    	return decoded;
    }
    
}



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.authentication;

import java.io.UnsupportedEncodingException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PasswordUtil {
    /** The SHA512 hash length */
    public static final int SHA512_LENGTH = 64;
    
    public static final int SALT_LENGTH = 8;
    
    //HASH_METHOD_SSHA512("SSHA-512", "SHA-512", "ssha512"),
    public static final String SHA512_NAME = "SSHA-512";
    public static final String SHA512_ALGORITHM = "SHA-512";
    public static final String SHA512_PREFIX = "ssha512";
    public static final String UTF8 = "UTF-8";
    public static final byte[] EMPTY_BYTES = new byte[0];
    
    private static Logger logger = LoggerFactory.getLogger(PasswordUtil.class);
    
    public static String createHashedPasswordWithSalt(String pwd) 
            throws UnsupportedEncodingException {
        byte[] bytes = pwd.getBytes(UTF8);
        String newPass = new String(bytes, UTF8);
        // create a Salt
        ByteSource salt = new SecureRandomNumberGenerator().nextBytes(SALT_LENGTH);
        Sha512Hash hash = new Sha512Hash(newPass, salt);
        int hashLength = hash.getBytes().length;
        int saltLength = salt.getBytes().length;
        byte[] hashedPasswordWithSaltBytes = new byte[hashLength + saltLength];
        merge(hashedPasswordWithSaltBytes, hash.getBytes(), salt.getBytes());
        
        ByteSource hashAndSalt = ByteSource.Util.bytes(hashedPasswordWithSaltBytes);
        
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(SHA512_PREFIX.toUpperCase()).append("}");
        sb.append(hashAndSalt.toBase64());
        logger.info(sb.toString());
        System.out.println(sb.toString());

        return sb.toString();
    }
    
    private static void merge(byte[] all, byte[] left, byte[] right) {
        System.arraycopy(left, 0, all, 0, left.length);
        System.arraycopy(right, 0, all, left.length, right.length);
    }
    
    private static void split( byte[] all, int offset, byte[] left, byte[] right ) {
        System.arraycopy( all, offset, left, 0, left.length );
        System.arraycopy( all, offset + left.length, right, 0, right.length );
    }
    
}

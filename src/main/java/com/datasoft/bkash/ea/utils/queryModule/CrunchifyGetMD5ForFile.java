package com.datasoft.bkash.ea.utils.queryModule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Mahadi Hasan Joy
 * @since 22-03-2021
 */

@Component
@Slf4j
public class CrunchifyGetMD5ForFile {

    public  byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
    /**
     * @author Mahadi Hasan Joy
     * @since 22-03-2021
     * @param crunchifyFile
     * @return
     */
    public String crunchifyGetMd5ForFile(String crunchifyFile) {
        File myFile = new File(crunchifyFile);
        return crunchifyGetMd5ForFile(myFile);
    }

    /**
     * @author Mahadi Hasan Joy
     * @since 22-03-2021
     * @param crunchifyFile
     * @return
     */
    public String crunchifyGetMd5ForFile(File crunchifyFile) {
        String crunchifyValue = null;
        FileInputStream crunchifyInputStream = null;
        try {
            crunchifyInputStream = new FileInputStream(crunchifyFile);

            // md5Hex converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
            // The returned array will be double the length of the passed array, as it takes two characters to represent any given byte.
            crunchifyValue = DigestUtils.md5Hex(IOUtils.toByteArray(crunchifyInputStream));
        } catch (IOException e) {
            log("Hey there is an error: " + e);
        } finally {
            IOUtils.closeQuietly(crunchifyInputStream);
        }
        return crunchifyValue;
    }

    // Simple log util
    private void log(String string) {
        log.info("");
    }


}

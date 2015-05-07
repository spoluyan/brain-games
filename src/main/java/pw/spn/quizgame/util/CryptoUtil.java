package pw.spn.quizgame.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CryptoUtil {
    private CryptoUtil() {
    }

    public static String encryptWithMD5(String stringToEncrypt) {
        if (stringToEncrypt == null) {
            return null;
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm is not supported.");
        }
        byte[] passBytes = stringToEncrypt.getBytes();
        md.reset();
        byte[] digested = md.digest(passBytes);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digested.length; i++) {
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        return sb.toString();
    }
}

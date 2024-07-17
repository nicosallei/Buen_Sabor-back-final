package NetDevops.BuenSabor.service.funcionalidades;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SeguridadService {



public String hashWithSHA256(String passwordToHash) {
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
}

private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
            hexString.append('0');
        }
        hexString.append(hex);
    }
    return hexString.toString();
}
}

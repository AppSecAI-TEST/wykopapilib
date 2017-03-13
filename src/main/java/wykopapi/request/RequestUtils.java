package wykopapi.request;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class RequestUtils {
    private static final String MD5 = "MD5";
    private static final String CHARSET = "UTF-8";
    private final MessageDigest messageDigest;

    public RequestUtils() {
        try {
            messageDigest = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            throw new Error("MD5 init failed");
        }
    }

    public String getMD5(String string) {
        try {
            messageDigest.reset();
            byte[] bytes = string.getBytes(CHARSET);
            byte[] md5 = messageDigest.digest(bytes);
            return DatatypeConverter.printHexBinary(md5);
        } catch (UnsupportedEncodingException e) {
            throw new Error("Charset not supported");
        }
    }
}

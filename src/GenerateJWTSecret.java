import java.security.SecureRandom;
import java.util.Base64;

public class GenerateJWTSecret {
    public GenerateJWTSecret() {
    }

    public static void main(String[] var0) {
        SecureRandom var1 = new SecureRandom();
        byte[] var2 = new byte[32];
        var1.nextBytes(var2);
        String var3 = Base64.getEncoder().encodeToString(var2);
        System.out.println("New JWT Secret: " + var3);
    }
}
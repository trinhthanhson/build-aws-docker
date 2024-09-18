package ptithcm.tttn.function;

import java.util.Random;

public class GenerateOtp {
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a random number between 100000 and 999999
        return String.valueOf(otp);
    }
}

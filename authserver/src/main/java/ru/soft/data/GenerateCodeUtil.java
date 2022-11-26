package ru.soft.data;

import lombok.experimental.UtilityClass;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@UtilityClass
class GenerateCodeUtil {

    public static String generateCode() {
        String code;
        try {
            SecureRandom random =
                    SecureRandom.getInstanceStrong();

            int c = random.nextInt(9000) + 1000;

            code = String.valueOf(c);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(
                    "Problem when generating the random code.");
        }
        return code;
    }
}

package sun.net.www.protocol.s3;

import org.apache.commons.lang3.RandomStringUtils;

public class Randoms {

    public static String randomString() {
        return RandomStringUtils.randomAlphanumeric(12);
    }
}

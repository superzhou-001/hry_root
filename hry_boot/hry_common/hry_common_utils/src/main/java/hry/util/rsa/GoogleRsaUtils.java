package hry.util.rsa;

public class GoogleRsaUtils {
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/HTvGLteQGDGaL83gydeVpM2jegnZXQAAnFL3bElTOjrhtVkswHNRrpfSOvltgvs5NbYbgVFqj8NrF1OpYmAmQ7BV7dZjuI5Cq8YZt5L7Iq3TP0yhvLL/NlHS8DW5hi4I32GBdvMWBfvKVe5NvsWtd0OZRNVlHUmQepWZzQFxyQIDAQAB";
    /**
     * 加密
     * @param key
     * @return
     */
    public static String encryptedDataOnJava(String key) {
        return RSAUtils.encryptedDataOnJava(key, publicKey);
    }

    private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL8dO8Yu15AYMZovzeDJ15WkzaN6CdldAACcUvdsSVM6OuG1WSzAc1Gul9I6+W2C+zk1thuBUWqPw2sXU6liYCZDsFXt1mO4jkKrxhm3kvsirdM/TKG8sv82UdLwNbmGLgjfYYF28xYF+8pV7k2+xa13Q5lE1WUdSZB6lZnNAXHJAgMBAAECgYEAmmrrdjk48XOq+HMxvTQ5Xe8YPMOJm7h6HQQyQTqckIPXObIGyJMDWndgsnv2N5cCl/ofF2BIwXpBopDph042cq1jaTx6B1Oud8QYb220GSA0AR/JwirpDK4mJkR75H41odB/xg9INIQoaRQke/HW5tRq5dBrjvy2gy8te5kS21ECQQDzTaymOvoS9+WsSGiGtSL6sch3L7JBW1iJ+l88GkLTBc7BiLvBu+HsjKDb2VUx8btKe4ZrlE4w+cWcf4kREL5FAkEAyRZbDvXSuvsckPRdx+/DAjx/pELY/435JME1fY3lVNwuO5mhn4HvkaOUwsoqqGtERb+Cuf9gm95RcQWjjy9vtQJAdddDLegWwyUqKn2Pkt8cqAt/qqqBTcJuabXIdpXJk+eWMoqFVdooHKgoKRNkfiI0smiNvIFCNdpj6d6jho5zeQJBAJip00nhUTJJkJ9wFn7DRT6+ZX10jsA9rge8c3q1roUlgCgZc/3gLNxZ34ee6pHglf2J7pp9CoBrYVM4H7uGHP0CQHN+IDDz1jBawY6Ip+ntUq2RFHbCprKK1YexsZKnuEjpioOU3F9M9VxxGpYs8Bd/rvgnXrQNvUA2zh6ESQeLJ6Y=";

    /**
     * 解密
     * @param data
     * @return
     */
    public static String decryptDataOnJava(String data) {

        return RSAUtil.decryptDataOnJava(data, privateKey);
    }

}

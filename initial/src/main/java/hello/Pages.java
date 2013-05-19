package hello;


public class Pages {

    public static final String HOME = "home";
    public static final String WELCOME = "welcome";
    public static final String LOGOUT = "logout";
    public static final String LOGIN = "login";

    private static String[] securePages = new String[]{HOME};
    private static String[] insecurePages = new String[]{LOGIN, LOGOUT, WELCOME};

    public static String[] getSecurePages() {
        return securePages;
    }

    public static String[] getInsecurePages() {
        return insecurePages;
    }


}

package utils;

/* 
 * Credits to DenimMazuki
 * https://github.com/DenimMazuki/URLShortener/blob/master/src/main/java/urlshortener/app/common/URLValidator.java 
 * 
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class URLValidator {
    public static final URLValidator INSTANCE = new URLValidator();
    private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public static boolean validateURL(String url) {
        Matcher m = URL_PATTERN.matcher(url);
        return m.matches();
    }
    
    public static String buildURL(String url) {
    	if (url.startsWith("http")) {
    		return url;
    	} else {
    		return "http://www." + url;
    	}
    }
}

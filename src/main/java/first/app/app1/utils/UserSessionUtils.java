package first.app.app1.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserSessionUtils {
    public static String getUserUsername()
    {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

}

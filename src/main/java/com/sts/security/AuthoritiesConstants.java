package com.sts.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String DRIVER = "ROLE_DRIVE";
    public static final String MODERATOR = "ROLE_MODERATOR";
    public static final String PARENT = "ROLE_PARENT";

    private AuthoritiesConstants() {
    }
}

package config;

import java.security.Principal;

public class PrincipalImplement implements Principal {

    @Override
    public String getName() {
        return "Login";
    }
}
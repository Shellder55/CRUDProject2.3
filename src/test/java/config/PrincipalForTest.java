package config;

import java.security.Principal;

public class PrincipalForTest implements Principal {

    @Override
    public String getName() {
        return "Login";
    }
}
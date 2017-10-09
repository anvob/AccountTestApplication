package com.anvob.accounttestapplication.AccountAuthenticator;

/**
 * Created by anvob on 06.10.2017.
 */

public interface ServerAuthenticate {
    public String userSignUp(final String name, final String email, final String pass, String authType) throws Exception;
    public String userSignIn(final String user, final String pass, String authType) throws Exception;
}

package br.ufsc.labtec.mazk.interceptor;

import android.util.Base64;

import retrofit.RequestInterceptor;

/**
 * Created by Mihael Zamin on 29/03/2015.
 */
public class LoginRequestInterceptor implements RequestInterceptor {
    private String username;
    private String password;

    public LoginRequestInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void intercept(RequestFacade requestFacade) {

        if (!username.isEmpty() && !password.isEmpty()) {
            final String authorizationValue = encodeCredentialsForBasicAuthorization();
            requestFacade.addHeader("Authorization", authorizationValue);
        }
    }

    private String encodeCredentialsForBasicAuthorization() {
        final String userAndPassword = this.getUsername() + ":" + this.getPassword();
        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }
}

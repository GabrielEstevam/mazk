package br.ufsc.labtec.mazk.exceptions;

/**
 * Created by Mihael Zamin on 30/03/2015.
 */
public class LoginFailedException extends Exception {
    public LoginFailedException(String detailMessage) {
        super("Falha na autenticação: " + detailMessage);
    }
}

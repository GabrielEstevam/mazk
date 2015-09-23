package br.ufsc.labtec.mazk.services.util;

import br.ufsc.labtec.mazk.services.UsuarioResource;

/**
 * Created by Mihael Zamin on 30/03/2015.
 */
public class UsuarioService extends ServiceGenerator<UsuarioResource> {
    public UsuarioService() {
        super(UsuarioResource.class);
    }

    @Override
    public UsuarioResource createService(String baseUrl, String username, String password) {
        return super.createService(baseUrl, username, password);
    }
}

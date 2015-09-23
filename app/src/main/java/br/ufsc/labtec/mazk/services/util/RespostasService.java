package br.ufsc.labtec.mazk.services.util;

import br.ufsc.labtec.mazk.services.RespostasResource;

/**
 * Created by Mihael Zamin on 30/03/2015.
 */
public class RespostasService extends ServiceGenerator<RespostasResource> {
    public RespostasService() {
        super(RespostasResource.class);
    }

    @Override
    public RespostasResource createService(String baseUrl, String username, String password) {
        return super.createService(baseUrl, username, password);
    }
}

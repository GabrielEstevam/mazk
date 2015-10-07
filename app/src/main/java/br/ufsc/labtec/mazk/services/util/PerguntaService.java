package br.ufsc.labtec.mazk.services.util;

import br.ufsc.labtec.mazk.services.PerguntaResource;

/**
 * Created by Mihael Zamin on 30/03/2015.
 */
public class PerguntaService extends ServiceGenerator<PerguntaResource> {
    public PerguntaService() {
        super(PerguntaResource.class);
    }

    @Override
    public PerguntaResource createService(String baseUrl, String username, String password) {
        return super.createService(baseUrl, username, password);
    }
}

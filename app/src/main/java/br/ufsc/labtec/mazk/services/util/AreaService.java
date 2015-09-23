package br.ufsc.labtec.mazk.services.util;

import br.ufsc.labtec.mazk.services.AreaResource;

/**
 * Created by Mihael Zamin on 30/03/2015.
 */
public class AreaService extends ServiceGenerator<AreaResource> {
    public AreaService() {
        super(AreaResource.class);
    }

    @Override
    public AreaResource createService(String baseUrl, String username, String password) {
        return super.createService(baseUrl, username, password);
    }
}

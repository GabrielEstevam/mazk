package br.ufsc.labtec.mazk.adapters.dialog;

import android.content.Context;

import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.adapters.AreaListAdapter;
import br.ufsc.labtec.mazk.beans.Area;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.AreaResource;
import br.ufsc.labtec.mazk.services.util.AreaService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mihael Zamin on 23/09/2015.
 */
public class AreaListDialogAdapter extends AreaListAdapter {
    private Usuario u;
    private AreaResource ur;

    public AreaListDialogAdapter(Context context, Usuario u) {
        super(context);
        ur = new AreaService().createService(context.getString(R.string.server_url), u.getEmail(), u.getSenha());
        ur.getAreas(new Callback<List<Area>>() {
            @Override
            public void success(List<Area> areas, Response response) {
                AreaListDialogAdapter.this.setList(areas);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}

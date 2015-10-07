package br.ufsc.labtec.mazk.activities.fragments.main.manager.usuario;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnUserEditChoosedListener;
import br.ufsc.labtec.mazk.adapters.UsuarioListAdapter;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.UsuarioResource;
import br.ufsc.labtec.mazk.services.util.UsuarioService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mihael Zamin on 12/08/2015.
 * Fragmento usado para listar os usuários
 */
public class ListUsuarioFragment extends Fragment {
    private ListView listView;
    private UsuarioListAdapter adapter;
    private Usuario u;
    private ProgressDialog progressDialog;
    private OnUserEditChoosedListener editChoosedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listusuario, container, false);
        listView = (ListView) v.findViewById(R.id.fragment_lvUsuario);
        adapter = new UsuarioListAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editChoosedListener.userEditChoosed(adapter.getUsuarioList().get(position));
            }
        });
        UsuarioResource ur = new UsuarioService().createService(getString(R.string.server_url), u.getEmail(), u.getSenha());
        progressDialog = ProgressDialog.show(getActivity(), "", "Carregando...");
        ur.listar(new Callback<List<Usuario>>() {
            @Override
            public void success(List<Usuario> usuarios, Response response) {
                Log.i("LUF", "List usuarios size: " + usuarios.size());
                adapter.setUsuarioList(usuarios);
                progressDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            u = ((CurrentUserCallback) activity).getCurrentUser();
            editChoosedListener = (OnUserEditChoosedListener) activity;
        } catch (ClassCastException e) {
            Log.e("LUF", "Activity must implement interfaces.");
        }
        super.onAttach(activity);
    }
}

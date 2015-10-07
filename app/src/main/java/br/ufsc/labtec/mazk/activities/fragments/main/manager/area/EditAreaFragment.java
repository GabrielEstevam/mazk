package br.ufsc.labtec.mazk.activities.fragments.main.manager.area;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentAreaCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnEditEndListener;
import br.ufsc.labtec.mazk.adapters.FilteredAreaListAdapter;
import br.ufsc.labtec.mazk.beans.Area;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.AreaResource;
import br.ufsc.labtec.mazk.services.util.AreaService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mihael Zamin on 09/09/2015.
 */
public class EditAreaFragment extends Fragment {
    private Area a;
    private Usuario u;
    private AutoCompleteTextView txtPai;
    private EditText txtNome;
    private Area areaPai;
    private FilteredAreaListAdapter adapter;
    private OnEditEndListener editEndListener;
    private AreaResource ar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_area, container, false);
        txtPai = (AutoCompleteTextView) v.findViewById(R.id.areaPai);
        txtNome = (EditText) v.findViewById(R.id.txtEditNomeArea);
        ar = new AreaService().createService(getString(R.string.server_url), u.getEmail(), u.getSenha());
        adapter = new FilteredAreaListAdapter(getActivity(), u);
        txtPai.setAdapter(adapter);
        if (a != null) {
            txtNome.setText(a.getNome());
            areaPai = a.getArea();

            txtPai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (adapter.getList() != null) {
                        areaPai = adapter.getList().get(position);
                        txtPai.setText(areaPai.getNome());
                    } else {
                        areaPai = null;
                        txtPai.setError("Nenhuma área será selecionada como pai");
                    }

                }
            });

        }
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            a = ((CurrentAreaCallback) activity).getCurrentArea();
            u = ((CurrentUserCallback) activity).getCurrentUser();
            editEndListener = (OnEditEndListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_area:
                if (a == null)
                    a = new Area();
                if (a.getArea() != null) {
                    a.getArea().getAreaList().remove(a);
                }
                a.setArea(areaPai);
                if (areaPai != null) {
                    if (areaPai.getAreaList() == null)
                        if (areaPai.getAreaList().isEmpty())
                            areaPai.setAreaList(new ArrayList<Area>());
                    areaPai.getAreaList().add(a);
                }
                a.setNome(txtNome.getText().toString());
                ar.addArea(a, new Callback<Area>() {
                    @Override
                    public void success(Area area, Response response) {
                        Toast.makeText(getActivity(), "Operação realizada com sucesso.", Toast.LENGTH_SHORT).show();
                        editEndListener.onEditEnd();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(), "Erro ao completar transação: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_edit_area, menu);
    }
}

package br.ufsc.labtec.mazk.activities.fragments.main.manager.usuario;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnUserEditChoosedListener;
import br.ufsc.labtec.mazk.adapters.TipoListAdapter;
import br.ufsc.labtec.mazk.beans.Tipo;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.UsuarioResource;
import br.ufsc.labtec.mazk.services.util.UsuarioService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mihael Zamin on 25/09/2015.
 */
public class EditUsuarioFragment extends Fragment {
    private Usuario u;
    private TextView txtEmail;
    private TextView txtNome;
    private TextView txtExperiencia;
    private Spinner spinnerTipo;
    private TipoListAdapter adapter;
    private Tipo tipoSelecionado;
    private Usuario admin;
    private OnUserEditChoosedListener userEditChoosedListener;
    private UsuarioResource ur;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_editusuario, null);
        //Cria e exibe um dialog de progresso que será exibido enquanto não tivermos todos os dados criados.
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Carregando...");
        ;
        txtEmail = (TextView) v.findViewById(R.id.fragment_tvEmail);
        txtNome = (TextView) v.findViewById(R.id.fragment_tvNome);
        txtExperiencia = (TextView) v.findViewById(R.id.fragment_tvExp);
        spinnerTipo = (Spinner) v.findViewById(R.id.fragment_spinnerTipo);
        //Atribui os valores do usuario correspondente.
        txtNome.setText(u.getNome());
        txtEmail.setText(u.getEmail());
        txtExperiencia.setText(String.valueOf(u.getExperiencia().doubleValue()));
        //Cria um adapter para listar os tipos.
        adapter = new TipoListAdapter(getActivity());
        //Atribui o adapter ao spinner onde irão os tipos.
        spinnerTipo.setAdapter(adapter);
        //O tipo selecionado inicial será o que o usuário possuir.
        tipoSelecionado = u.getTipo();
        //Cria um serviço para obtenção de lista
        ur = new UsuarioService().createService(getString(R.string.server_url), admin.getEmail(), admin.getSenha());
        //Obtém uma lista de todos os tipos de usuário registrados no banco de dados.
        ur.getAllTipos(new Callback<List<Tipo>>() {
            @Override
            public void success(List<Tipo> tipos, Response response) {
                //Caso haja sucesso, ele seta no adapter a lista de todos os tipos a serem exibidos no spinner.
                adapter.setList(tipos);
                //Seta o tipo atual do usuário.
                spinnerTipo.setSelection(tipos.indexOf(tipoSelecionado));
                //Para de exibir o dialog de progresso.
                progressDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                //Para de exibir o dialog de progresso.
                progressDialog.dismiss();
            }
        });


        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Seta o tipo atual do usuário.
                tipoSelecionado = adapter.getList().get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nada para fazer.
            }
        });

        return v;
    }

    private void save() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Salvando...");
        final Tipo tipoAntigo = u.getTipo();
        //Atualiza o tipo do usuario
        if (u.getTipo().getUsuarioList() != null)
            u.getTipo().getUsuarioList().remove(u);
        if (tipoSelecionado.getUsuarioList() == null)
            tipoSelecionado.setUsuarioList(new ArrayList<Usuario>());
        tipoSelecionado.getUsuarioList().add(u);
        u.setTipo(tipoSelecionado);
        //Envia a requisição de update.
        ur.updateUsuario(u, new Callback<Usuario>() {
            @Override
            public void success(Usuario usuario, Response response) {
                Toast.makeText(getActivity(), "Salvo com sucesso", Toast.LENGTH_LONG).show();
                u = usuario;
                progressDialog.dismiss();
                userEditChoosedListener.userEditFinished();

            }

            @Override
            public void failure(RetrofitError error) {
                if (u.getTipo().getUsuarioList() != null)
                    u.getTipo().getUsuarioList().remove(u);
                tipoAntigo.getUsuarioList().add(u);
                u.setTipo(tipoAntigo);
                Toast.makeText(getActivity(), "Erro ao salvar: " + error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            //Obtém o usuário do tipo administrador que está editando.
            admin = ((CurrentUserCallback) activity).getCurrentUser();
            //Obtém o callback de ações de edição.
            userEditChoosedListener = (OnUserEditChoosedListener) activity;
            //Obtém o usuário a ser editado
            u = userEditChoosedListener.getUserToEdit();
        } catch (ClassCastException e) {
            Log.e("EUF", "Activity must implement callbacks");
        }
        setHasOptionsMenu(true);
        super.onAttach(activity);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_edit_usuario, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_usuario:
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

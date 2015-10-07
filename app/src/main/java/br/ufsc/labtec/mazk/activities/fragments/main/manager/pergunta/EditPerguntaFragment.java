package br.ufsc.labtec.mazk.activities.fragments.main.manager.pergunta;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentQuestionCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnEditEndListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.pergunta.OnExemploEditRequested;
import br.ufsc.labtec.mazk.adapters.AlternativaEditAdapter;
import br.ufsc.labtec.mazk.adapters.AreaListAdapter;
import br.ufsc.labtec.mazk.beans.Alternativa;
import br.ufsc.labtec.mazk.beans.Area;
import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.AreaResource;
import br.ufsc.labtec.mazk.services.PerguntaResource;
import br.ufsc.labtec.mazk.services.util.AreaService;
import br.ufsc.labtec.mazk.services.util.PerguntaService;
import br.ufsc.labtec.mazk.view.custom.MeditDialog;
import medit.core.MeditText;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mihael Zamin on 09/04/2015.
 * Fragmento usado para editar as perguntas
 */
public class EditPerguntaFragment extends Fragment {
    private Pergunta p;
    private boolean create;
    private byte[] explicacao;
    private ViewHolder holder;
    private AlternativaEditAdapter adapter;
    private Alternativa sel;
    private Usuario usuario;
    private MeditDialog medit;
    private OnEditEndListener editEndListener;
    private Context context;
    private AreaListAdapter ald;
    private AreaResource areaResource;
    private Area areaSelecionada;

    public static EditPerguntaFragment newInstance() {
        EditPerguntaFragment fragment = new EditPerguntaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editpergunta, container, false);
        //explicacao = p.getExplicacao();
        medit = new MeditDialog(getActivity(), null, getActivity());
        holder = new ViewHolder((ListView) v.findViewById(R.id.lvAlternativa),
                (Spinner) v.findViewById(R.id.spinArea),
                (EditText) v.findViewById(R.id.etEnunciado),
                (TextView) v.findViewById(R.id.tvEnunciano), (CheckBox) v.findViewById(R.id.cbAtivo));
        adapter = new AlternativaEditAdapter(getActivity());
        holder.getListaAlternativa().setAdapter(adapter);
        ald = new AreaListAdapter(getActivity());
        areaResource = new AreaService().createService(getString(R.string.server_url), usuario.getEmail(), usuario.getSenha());
        holder.getSpinnerArea().setAdapter(ald);
        //Obtém todas as áreas
        areaResource.getAreas(new Callback<List<Area>>() {
            @Override
            public void success(List<Area> areas, Response response) {
                ald.setList(areas);
                if (p.getAreaList() != null) {
                    if(!p.getAreaList().isEmpty()) {
                        areaSelecionada = p.getAreaList().get(0);
                        holder.getSpinnerArea().setSelection(ald.getList().indexOf(areaSelecionada));
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        holder.getSpinnerArea().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaSelecionada = ald.getList().get(position);
              /*  Area a = ald.getList().get(position);
                if(EditPerguntaFragment.this.p.getAreaList() != null)
                    for(Area ar : EditPerguntaFragment.this.p.getAreaList())
                    {
                        if(ar.getPerguntaList() != null)
                            ar.getPerguntaList().remove(p);
                    }
                EditPerguntaFragment.this.p.setAreaList(new ArrayList<Area>());
                EditPerguntaFragment.this.p.getAreaList().add(a);
                if(a.getPerguntaList() == null)
                    a.setPerguntaList(new ArrayList<Pergunta>());
                    a.getPerguntaList().add(p);
                holder.getSpinnerArea().setSelection(position);*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (p != null) {
            create = false;
            explicacao = p.getExplicacao();

            fill(holder);
        } else {
            p = new Pergunta();
            create = true;
        }


        return v;
    }

    private void fill(ViewHolder holder) {
        if (p.getAreaList() != null) {
            if(!p.getAreaList().isEmpty()) {
                areaSelecionada = p.getAreaList().get(0);
                holder.getSpinnerArea().setSelection(ald.getList().indexOf(areaSelecionada));
            }
        }
        if (p.getAlternativaList() != null)
            if (!p.getAlternativaList().isEmpty())
                adapter.setAlternativas(p.getAlternativaList());
        holder.getTxtEnunciado().setText(p.getEnunciado());
        holder.getCbAtivo().setChecked(p.getAtivo());



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.editpergunta, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_alternativa) {
            if (p == null) p = new Pergunta();
            Alternativa a = new Alternativa();
            a.setPergunta(p);
            adapter.add(a);
            p.setAlternativaList(adapter.getAlternativas());
            return super.onOptionsItemSelected(item);
        }
        if (item.getItemId() == R.id.change_explicacao) {
            medit = new MeditDialog(getActivity(), explicacao, getActivity());

            medit.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    explicacao = ((MeditText) v.getTag()).toHtml().getBytes(Charset.forName("UTF-8"));
                }
            });
            medit.show();
            return true;
        }
        if (item.getItemId() == R.id.action_item_pergunta_add) {
            salvarPergunta();
            editEndListener.onEditEnd();
            return true;
        }
        if (item.getItemId() == R.id.change_exemplos) {
            ((OnExemploEditRequested) getActivity()).exemploEditRequested();
            return super.onOptionsItemSelected(item);

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        try {
            this.p = ((CurrentQuestionCallback) activity).getCurrentQuestion();
            usuario = ((CurrentUserCallback) activity).getCurrentUser();
            editEndListener = (OnEditEndListener) activity;

            setHasOptionsMenu(true);

        } catch (ClassCastException e) {
            Log.e("EditPergunta", "Must implement callbacks");
        }
    }

    private void salvarPergunta() {
        if (p == null) p = new Pergunta();
        p.setAtivo(holder.getCbAtivo().isChecked());
        p.setEnunciado(holder.getTxtEnunciado().getText().toString());
        PerguntaResource pr = new PerguntaService().createService(getString(R.string.server_url), usuario.getEmail(), usuario.getSenha());
        List<Alternativa> list = adapter.getAlternativas();
        p.setAlternativaList(list);
        p.setExplicacao(explicacao);
        Area a = areaSelecionada;

        if (EditPerguntaFragment.this.p.getAreaList() != null)
            for (Area ar : EditPerguntaFragment.this.p.getAreaList()) {
                if (ar.getPerguntaList() != null)
                    ar.getPerguntaList().remove(p);
            }
        EditPerguntaFragment.this.p.setAreaList(new ArrayList<Area>());
        EditPerguntaFragment.this.p.getAreaList().add(a);

        if (a.getPerguntaList() == null)
            a.setPerguntaList(new ArrayList<Pergunta>());
        a.getPerguntaList().add(p);
          if (create)
            pr.addPergunta(p, new Callback<Pergunta>() {
                @Override
                public void success(Pergunta pergunta, Response response) {
                    p = pergunta;
                    Toast.makeText(context, "Pergunta inserida com sucesso", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("EPF", error.getMessage());
                    Toast.makeText(context, "Erro ao inserir", Toast.LENGTH_SHORT).show();


                }
            });
        else pr.updatePergunta(p, new Callback<Pergunta>() {
            @Override
            public void success(Pergunta pergunta, Response response) {
                p = pergunta;
                Toast.makeText(context, "Pergunta atualizada com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("EPF", error.getMessage());
                Toast.makeText(context, "Erro ao alterar os dados da pergunta", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void setAddImage(int requestCode, int resultCode, Intent data) {
        medit.addImage(requestCode, resultCode, data);
    }

    private class ViewHolder {
        private ListView listaAlternativa;
        private Spinner spinnerArea;
        private EditText txtEnunciado;
        private TextView tvExplicacao;
        private CheckBox cbAtivo;

        private ViewHolder(ListView listaAlternativa, Spinner spinnerArea, EditText txtEnunciado, TextView tvExplicacao, CheckBox cbAtivo) {
            this.listaAlternativa = listaAlternativa;
            this.spinnerArea = spinnerArea;
            this.txtEnunciado = txtEnunciado;
            this.tvExplicacao = tvExplicacao;
            this.cbAtivo = cbAtivo;
        }

        private ViewHolder() {
        }

        public CheckBox getCbAtivo() {
            return cbAtivo;
        }

        public void setCbAtivo(CheckBox cbAtivo) {
            this.cbAtivo = cbAtivo;
        }

        public ListView getListaAlternativa() {
            return listaAlternativa;
        }

        public void setListaAlternativa(ListView listaAlternativa) {
            this.listaAlternativa = listaAlternativa;
        }

        public Spinner getSpinnerArea() {
            return spinnerArea;
        }

        public void setSpinnerArea(Spinner spinnerArea) {
            this.spinnerArea = spinnerArea;
        }

        public EditText getTxtEnunciado() {
            return txtEnunciado;
        }

        public void setTxtEnunciado(EditText txtEnunciado) {
            this.txtEnunciado = txtEnunciado;
        }

        public TextView getTvExplicacao() {
            return tvExplicacao;
        }

        public void setTvExplicacao(TextView tvExplicacao) {
            this.tvExplicacao = tvExplicacao;
        }


    }
}

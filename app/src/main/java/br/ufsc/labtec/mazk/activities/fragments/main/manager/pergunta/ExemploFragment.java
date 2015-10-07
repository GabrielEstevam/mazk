package br.ufsc.labtec.mazk.activities.fragments.main.manager.pergunta;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.nio.charset.Charset;
import java.util.ArrayList;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentQuestionCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnEditEndListener;
import br.ufsc.labtec.mazk.adapters.ExplicacaoListAdapter;
import br.ufsc.labtec.mazk.beans.Exemplo;
import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.PerguntaResource;
import br.ufsc.labtec.mazk.services.util.PerguntaService;
import medit.core.CheckButton;
import medit.core.HtmlBuilder;
import medit.core.MeditText;

/**
 * Created by Mihael Zamin on 09/04/2015.
 */
public class ExemploFragment extends Fragment {
    private Pergunta p;
    private Usuario u;
    private OnEditEndListener editEndListener;
    private MeditText medit;
    private CheckButton boldBtn;
    private CheckButton italicBtn;
    private CheckButton underlineBtn;
    private CheckButton colorBtn;
    private ImageButton imageButton;
    private Button cancelBtn;
    private Button okBtn;
    private PerguntaResource pr;
    private Exemplo currentExemplo;
    private ListView listView;
    private ExplicacaoListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_explicacao, container, false);
        getReferences(v);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentExemplo == null) {
                    Exemplo e = new Exemplo();
                    e.setPergunta(p);
                    e.setConteudo(medit.toHtml().getBytes(Charset.forName("UTF-8")));
                    if (p.getExemploList() == null)
                        p.setExemploList(new ArrayList<Exemplo>());
                    p.getExemploList().add(e);
                    adapter.add(e);
                } else
                    currentExemplo.setConteudo(medit.toHtml().getBytes(Charset.forName("UTF-8")));
                currentExemplo = null;
                medit.setText("");
                changeTitle();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExemplo = null;
                medit.setText("");
                changeTitle();
            }
        });
        changeTitle();

        adapter = new ExplicacaoListAdapter(getActivity());
        if (p.getExemploList() != null)
            adapter.setList(p.getExemploList());
        listView.setAdapter(adapter);

        pr = new PerguntaService().createService(getString(R.string.server_url), u.getEmail(), u.getSenha());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentExemplo = adapter.getList().get(position);
                medit.setText(HtmlBuilder.htmltoSpanned(new String(currentExemplo.getConteudo(), Charset.forName("UTF-8")), getActivity().getResources()));
                changeTitle();
            }
        });

        return v;
    }

    private void getReferences(View v) {
        cancelBtn = (Button) v.findViewById(R.id.btn_cancel_explicacao);
        okBtn = (Button) v.findViewById(R.id.explicacao_button_ok);
        boldBtn = (CheckButton) v.findViewById(R.id.fe_bold);
        italicBtn = (CheckButton) v.findViewById(R.id.fe_italic);
        underlineBtn = (CheckButton) v.findViewById(R.id.fe_underline);
        colorBtn = (CheckButton) v.findViewById(R.id.fe_color);
        imageButton = (ImageButton) v.findViewById(R.id.fe_image);
        medit = (MeditText) v.findViewById(R.id.explicacao_medit);
        medit.setColorButton(colorBtn);
        medit.setBoldButton(boldBtn);
        medit.setItalicButton(italicBtn);
        medit.setUnderlineButton(underlineBtn);
        medit.setAddImageButton(imageButton, getActivity());
        listView = (ListView) v.findViewById(R.id.list_exemplos);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            u = ((CurrentUserCallback) activity).getCurrentUser();
            p = ((CurrentQuestionCallback) activity).getCurrentQuestion();
            editEndListener = (OnEditEndListener) activity;

        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }

    public void cancel() {
        p.setExemploList(adapter.getBaseList());
    }


    public void setAddImage(int requestCode, int resultCode, Intent data) {
        medit.addImageHere(requestCode, resultCode, data, getActivity(), 1);
    }

    private void save() {

    }
    private void changeTitle() {
        if (currentExemplo == null)
            okBtn.setText("Adicionar");
        else okBtn.setText("Alterar");
        refreshCancelButtonState();
    }

    private void refreshCancelButtonState() {
        cancelBtn.setEnabled(currentExemplo != null);
    }
}

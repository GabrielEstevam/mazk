package br.ufsc.labtec.mazk.activities.fragments.resposta;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentQuestionCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.resposta.OnAlternativaSelected;
import br.ufsc.labtec.mazk.activities.fragments.listeners.resposta.OnAnsweredListener;
import br.ufsc.labtec.mazk.adapters.AlternativaRespostaAdapter;
import br.ufsc.labtec.mazk.beans.Alternativa;
import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Resposta;
import br.ufsc.labtec.mazk.services.PerguntaResource;

/**
 * Created by Mihael Zamin on 14/04/2015.
 */
public class RespostaFragment extends Fragment implements OnAlternativaSelected {

    private OnAnsweredListener onAnsweredListener;
    private Pergunta pergunta;
    private ListView viewAlternativas;
    private AlternativaRespostaAdapter adapter;
    private long tempoInicial;
    private TextView tvEnunciado;
    private Resposta r;
    private ResourceCallback rc;

    public RespostaFragment() {
    }

    public static RespostaFragment newInstance() {
        RespostaFragment fragment = new RespostaFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resposta, container, false);
        viewAlternativas = (ListView) v.findViewById(R.id.listAlternativas);
        adapter = new AlternativaRespostaAdapter(getActivity());
        adapter.addOnAlternativaSelectedListener(this);
        pergunta.setAlternativaList(new ArrayList<>(new LinkedHashSet<>(pergunta.getAlternativaList())));
        adapter.setAlternativas(pergunta.getAlternativaList());
        viewAlternativas.setAdapter(adapter);
        tvEnunciado = (TextView) v.findViewById(R.id.tvEnunciano);
        tvEnunciado.setText(pergunta.getEnunciado());
        tempoInicial = System.nanoTime();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            onAnsweredListener = (OnAnsweredListener) activity;
            this.pergunta = ((CurrentQuestionCallback) activity).getCurrentQuestion();
          /*  for(Alternativa a : pergunta.getAlternativaList())
                a.setPergunta(pergunta);*/
            rc = ((ResourceCallback) activity);
            r = new Resposta();
            r.setPergunta(pergunta);
           /* for( Alternativa a : pergunta.getAlternativaList())
            {
                a.setPergunta(pergunta);
            }
            /*for( Resposta r : pergunta.getRespostaList())
            {
                r.setPergunta(pergunta);
            }
            for( Exemplo e : pergunta.getExemploList())
            {
                e.setPergunta(pergunta);
            }*/
        } catch (ClassCastException e) {
            Log.e("RF", "Activity must implement callbacks");
        }
        super.onAttach(activity);
    }

    @Override
    public void alternativaSelected(Alternativa a) {
        Long delta = System.nanoTime() - tempoInicial;
        Double tempoDecorrido = delta.doubleValue() / 1e6;
        if (a.getRespostaList() == null)
            a.setRespostaList(new ArrayList<Resposta>());
        a.getRespostaList().add(r);
        r.setAlternativa(a);
        r.setTempoDecorrido(tempoDecorrido.intValue());
        r.setData(new Date(Calendar.getInstance().getTimeInMillis()));
        onAnsweredListener.onAnswered(r);
    }

    public interface ResourceCallback {
        public PerguntaResource getPerguntaResource();
    }
}

package br.ufsc.labtec.mazk.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.ProgressFragment;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.ChangeActivityCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentQuestionCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.NextFragmentRequester;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.RespostaCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.TentativaCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.resposta.OnAnsweredListener;
import br.ufsc.labtec.mazk.activities.fragments.resposta.RespostaErradaFragment;
import br.ufsc.labtec.mazk.activities.fragments.resposta.RespostaFragment;
import br.ufsc.labtec.mazk.activities.fragments.resposta.TentativaFinishedFragment;
import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Resposta;
import br.ufsc.labtec.mazk.beans.Tentativa;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.beans.json.DefaultGson;
import br.ufsc.labtec.mazk.services.PerguntaResource;
import br.ufsc.labtec.mazk.services.RespostasResource;
import br.ufsc.labtec.mazk.services.util.PerguntaService;
import br.ufsc.labtec.mazk.services.util.RespostasService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mihael Zamin on 13/04/2015.
 */
public class RespostasActivity extends Activity implements RespostaCallback, NextFragmentRequester, CurrentQuestionCallback, OnAnsweredListener, TentativaCallback, ChangeActivityCallback, RespostaFragment.ResourceCallback {
    private RespostasResource rr;
    private Usuario usuario;
    private Tentativa t;
    private Stack<Pergunta> perguntaStack;
    private Resposta current;
    private List<Resposta> respostasList;
    private PerguntaResource pr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta);
        String json = this.getIntent().getExtras().getString("usuario");
        try {
            this.usuario = new ObjectMapper().readValue(json, Usuario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rr = new RespostasService().createService(getString(R.string.server_url), usuario.getEmail(), usuario.getSenha());
        respostasList = new ArrayList<>();
        perguntaStack = new Stack<>();
        showProgress();
        rr.getRandom(new Callback<List<Pergunta>>() {
            @Override
            public void success(List<Pergunta> perguntas, Response response) {

                perguntaStack.addAll(perguntas);
                startNewFragment();

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(RespostasActivity.this, "Erro ao requisitar novas perguntas\n" + error.getMessage(), Toast.LENGTH_LONG).show();
                backToMainActivity();
            }
        });
        rr.getTentativa(new Callback<Tentativa>() {
            @Override
            public void success(Tentativa tentativa, Response response) {
                RespostasActivity.this.t = tentativa;
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(RespostasActivity.this, "Erro ao requisitar uma nova tentativa\n" + error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();

                backToMainActivity();

            }
        });
        pr = new PerguntaService().createService(getString(R.string.server_url), usuario.getEmail(), usuario.getSenha());

    }

    private void backToMainActivity() {
        Intent i = new Intent(RespostasActivity.this, MainActivity.class);
        i.putExtra("usuario", DefaultGson.getDefaultGson().toJson(usuario));
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public Pergunta getCurrentQuestion() {

        return perguntaStack.pop();

    }

    public void startNewFragment() {
        getFragmentManager().beginTransaction().replace(R.id.resposta_container, new RespostaFragment()).commit();
    }

    @Override
    public void onAnswered(Resposta r) {
        r.setTentativa(t);
        respostasList.add(r);
        current = r;
        if (r.getAlternativa().isCorreta()) {
            showRespostaCertaToast();
            nextFragment();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.resposta_container, new RespostaErradaFragment()).commit();
        }


    }

    @Override
    public Tentativa getCurrentTentativa() {
        return t;
    }

    private void showRespostaCertaToast() {
        Toast t = new Toast(RespostasActivity.this);
        t.setView(getLayoutInflater().inflate(R.layout.resposta_certa, null));
        t.setGravity(Gravity.CENTER_HORIZONTAL, 0, 10);
        t.show();
    }

    public void showProgress() {
        getFragmentManager().beginTransaction().replace(R.id.resposta_container, new ProgressFragment()).commit();

    }

    @Override
    public void nextFragment() {
        if (!perguntaStack.empty())
            startNewFragment();
        else {
            getFragmentManager().beginTransaction().replace(R.id.resposta_container, new ProgressFragment()).commit();
            t.setRespostaList(respostasList);
            rr.addRespostas(t, new Callback<Tentativa>() {
                @Override
                public void success(Tentativa tentativa, Response response) {
                    RespostasActivity.this.t = tentativa;
                    for (Resposta r : RespostasActivity.this.t.getRespostaList())
                        r.setTentativa(RespostasActivity.this.t);
                    getFragmentManager().beginTransaction().replace(R.id.resposta_container, new TentativaFinishedFragment()).commit();

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(RespostasActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_LONG);
                    backToMainActivity();
                    // rr.addRespostas(respostasList, this);
                }
            });
        }
    }

    @Override
    public Resposta getCurrentResposta() {
        return current;
    }

    @Override
    public void changeActivity() {
        backToMainActivity();
    }

    @Override
    public PerguntaResource getPerguntaResource() {
        return pr;
    }
}

package br.ufsc.labtec.mazk.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.initial.LoginFragment;
import br.ufsc.labtec.mazk.activities.fragments.initial.RegisterFragment;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnRegisterRequestedListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnUserIdentifiedListener;
import br.ufsc.labtec.mazk.beans.Alternativa;
import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Resposta;
import br.ufsc.labtec.mazk.beans.Tentativa;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.beans.json.DateSerializer;
import br.ufsc.labtec.mazk.beans.json.DefaultGson;

/**
 * Created by Mihael Zamin on 03/04/2015.
 */
public class InitialActivity extends Activity implements OnUserIdentifiedListener, OnRegisterRequestedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        LoginFragment lf = new LoginFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, lf, "login").commit();
       /*ObjectMapper mapper = new ObjectMapper();
        Pergunta p = new Pergunta();
        List<Alternativa> a = new ArrayList<>();
        p.setAlternativaList(a);
        p.setEnunciado("tESTE");
        p.setAtivo(true);
        Alternativa al = new Alternativa();
        al.setDescricao("AAA");
        al.setCorreta(true);
        al.setPergunta(p);
        a.add(al);
        al = new Alternativa();
        al.setDescricao("BA");
        al.setCorreta(false);
        al.setPergunta(p);
        a.add(al);
        String josn = null;
        try {
            josn = mapper.writer().writeValueAsString(p);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Log.i("INITIAL", josn);
        Pergunta p2;
        try {
            p2 = mapper.readValue(josn, Pergunta.class);
            if(p2 != null)
                if(p2.getAlternativaList() != null)
                    if(p2.getAlternativaList().size() > 0)
                Log.i("INITIAL", "Worked");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void registerRequested() {
        RegisterFragment rf = new RegisterFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, rf, "register").addToBackStack(null).commit();
    }

    @Override
    public void userIdentified(Usuario usuario) {
        Intent i = new Intent(InitialActivity.this, MainActivity.class);

        ObjectMapper mapper = new ObjectMapper();

        String josn = null;
        try {
            josn = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(usuario);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Log.i("INITIAL", josn);
        i.putExtra("usuario", josn);

        startActivity(i);
    }

}

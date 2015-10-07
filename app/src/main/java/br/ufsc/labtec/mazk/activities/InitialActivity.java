package br.ufsc.labtec.mazk.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.initial.LoginFragment;
import br.ufsc.labtec.mazk.activities.fragments.initial.RegisterFragment;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnRegisterRequestedListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnUserIdentifiedListener;
import br.ufsc.labtec.mazk.beans.Usuario;

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
        i.putExtra("usuario", josn);

        startActivity(i);
    }

}

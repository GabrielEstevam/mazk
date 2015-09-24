package br.ufsc.labtec.mazk.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentQuestionCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnAddRequestedListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnStartNewTentativa;
import br.ufsc.labtec.mazk.activities.fragments.listeners.pergunta.OnEditChoosedListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.pergunta.OnEditEndListener;
import br.ufsc.labtec.mazk.activities.fragments.main.StatsFragment;
import br.ufsc.labtec.mazk.activities.fragments.main.manager.pergunta.EditPerguntaFragment;
import br.ufsc.labtec.mazk.activities.fragments.main.manager.pergunta.ManagePerguntasFragment;
import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.UsuarioResource;
import br.ufsc.labtec.mazk.services.util.UsuarioService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity
        implements OnStartNewTentativa, OnEditEndListener, OnAddRequestedListener, NavigationDrawerFragment.NavigationDrawerCallbacks, OnEditChoosedListener, CurrentUserCallback, CurrentQuestionCallback{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Usuario u;
    private boolean adminMode;
    private Pergunta p;
    private boolean doubleBackToExitPressedOnce;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = "Mazk";
        json = this.getIntent().getExtras().getString("usuario");
       // Log.i("JSON", json);
        try {
            this.u = new ObjectMapper().readValue(json, Usuario.class);
            UsuarioResource ur = new UsuarioService().createService(getString(R.string.server_url), u.getEmail(), u.getSenha());
            ur.login(new Callback<Usuario>() {
                @Override
                public void success(Usuario usuario, Response response) {
                     MainActivity.this.u = usuario;
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up the drawer.

        adminMode = (TextUtils.equals(u.getTipo().getNome(), "Administrador") || TextUtils.equals(u.getTipo().getNome(), "Tutor"));
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), adminMode);
        epf = EditPerguntaFragment.newInstance();
    }

    @Override
    public  void onNavigationDrawerItemSelected(int position){
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if(adminMode) {
            if(position == 0) {
                mTitle = "Estatísticas";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, StatsFragment.newInstance(), "estatísticas").addToBackStack(null).commit();


            }
             else if(position == 1)
            {
                mTitle = "Perguntas";
                    fragmentManager.beginTransaction().replace(R.id.container, ManagePerguntasFragment.newInstance(), "perguntas").addToBackStack(null).commit();
            }
        }
        else fragmentManager.beginTransaction()
                .replace(R.id.container, StatsFragment.newInstance(), "estatísticas").commit();
        restoreActionBar();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = "Estatísticas";
                break;
            case 1:
                mTitle = /*getString(R.string.title_section2);*/ "Perguntas";
                break;


        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);
    }
    private EditPerguntaFragment epf;
    @Override
    public void editChoosed(Pergunta pe) {
        p = pe;
        epf = EditPerguntaFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container, epf).addToBackStack(null).commit();



    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {

            if (doubleBackToExitPressedOnce) {
                Intent i = new Intent(MainActivity.this, InitialActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Pressione voltar novamente para fazer logout", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
    @Override
    public Usuario getCurrentUser() {
        return u;
    }

    @Override
    public Pergunta getCurrentQuestion() {
        return this.p;
    }

    @Override
    public void addRequested() {
        this.p = null;
        getFragmentManager().beginTransaction().replace(R.id.container, EditPerguntaFragment.newInstance()).addToBackStack(null).commit();

    }

    @Override
    public void editEndListener() {
        getFragmentManager().popBackStack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        epf.setAddImage(requestCode, requestCode, data);
    }

    @Override
    public void startNewTentativa() {
        Intent i = new Intent(MainActivity.this, RespostasActivity.class);
        ObjectMapper mapper = new ObjectMapper();
        i.putExtra("usuario", json);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}

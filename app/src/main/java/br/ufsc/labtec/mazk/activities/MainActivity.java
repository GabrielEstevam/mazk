package br.ufsc.labtec.mazk.activities;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentAreaCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentQuestionCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnEditEndListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnStartNewTentativa;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnUserEditChoosedListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.area.OnAreaAddRequested;
import br.ufsc.labtec.mazk.activities.fragments.listeners.area.OnAreaEditChoosedListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.pergunta.OnExemploEditRequested;
import br.ufsc.labtec.mazk.activities.fragments.listeners.pergunta.OnPerguntaAddRequestedListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.pergunta.OnPerguntaEditChoosedListener;
import br.ufsc.labtec.mazk.activities.fragments.main.StatsFragment;
import br.ufsc.labtec.mazk.activities.fragments.main.manager.area.EditAreaFragment;
import br.ufsc.labtec.mazk.activities.fragments.main.manager.area.ListAreasFragment;
import br.ufsc.labtec.mazk.activities.fragments.main.manager.pergunta.EditPerguntaFragment;
import br.ufsc.labtec.mazk.activities.fragments.main.manager.pergunta.ExemploFragment;
import br.ufsc.labtec.mazk.activities.fragments.main.manager.pergunta.ManagePerguntasFragment;
import br.ufsc.labtec.mazk.activities.fragments.main.manager.usuario.EditUsuarioFragment;
import br.ufsc.labtec.mazk.activities.fragments.main.manager.usuario.ListUsuarioFragment;
import br.ufsc.labtec.mazk.beans.Area;
import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.PerguntaResource;
import br.ufsc.labtec.mazk.services.UsuarioResource;
import br.ufsc.labtec.mazk.services.util.PerguntaService;
import br.ufsc.labtec.mazk.services.util.UsuarioService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity
        implements OnExemploEditRequested, OnUserEditChoosedListener, OnEditEndListener, CurrentAreaCallback, OnAreaAddRequested, OnAreaEditChoosedListener, OnStartNewTentativa, OnPerguntaAddRequestedListener, NavigationDrawerFragment.NavigationDrawerCallbacks, OnPerguntaEditChoosedListener, CurrentUserCallback, CurrentQuestionCallback {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Usuario u;
    private Area currentArea;
    private boolean adminMode;
    private Pergunta p;
    private boolean doubleBackToExitPressedOnce;
    private String json;
    private ExemploFragment exemploFragment;
    private PerguntaResource pr;
    private ProgressDialog progress;
    private EditPerguntaFragment epf;
    private Usuario userToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userToEdit = null;
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
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if (adminMode) {
            switch (position) {
                case 0:
                default:
                    mTitle = "Estatísticas";
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, StatsFragment.newInstance(), "estatísticas").addToBackStack(null).commit();
                    break;

                case 1:
                    mTitle = "Perguntas";
                    fragmentManager.beginTransaction().replace(R.id.container, ManagePerguntasFragment.newInstance(), "perguntas").addToBackStack(null).commit();
                    break;
                case 2:
                    mTitle = "Usuários";
                    fragmentManager.beginTransaction().replace(R.id.container, new ListUsuarioFragment(), "usuarios").addToBackStack(null).commit();
                    break;
                case 3:
                    mTitle = "Áreas";
                    fragmentManager.beginTransaction().replace(R.id.container, new ListAreasFragment(), "areas").addToBackStack(null).commit();
                    break;
            }
        } else fragmentManager.beginTransaction()
                .replace(R.id.container, StatsFragment.newInstance(), "estatísticas").commit();
        restoreActionBar();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = "Estatísticas";
                break;
            case 1:
                mTitle = "Perguntas";
                break;
            case 2:
                mTitle = "Usuários";
                break;
            case 3:
                mTitle = "Áreas";
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

    @Override
    public void editChoosed(Pergunta pe) {
        p = pe;
        epf = EditPerguntaFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container, epf).addToBackStack(null).commit();


    }

    @Override
    public void onBackPressed() {
        if (exemploFragment != null) {
            if (exemploFragment.isVisible()) {
                AlertDialog alert = new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Salvar progresso")
                        .setMessage("Deseja salvar antes de retornar à tela de edição de perguntas?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progress = ProgressDialog.show(MainActivity.this, "Carregando",
                                        "Salvando seu progresso...", true);
                                pr = new PerguntaService().createService(getString(R.string.server_url), u.getEmail(), u.getSenha());
                                pr.updatePergunta(p, new Callback<Pergunta>() {
                                    @Override
                                    public void success(Pergunta pergunta, Response response) {
                                        p.setExemploList(p.getExemploList());
                                        progress.dismiss();
                                        getFragmentManager().popBackStack();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(MainActivity.this, "Erro ao concluir operação: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                        progress.dismiss();
                                    }
                                });

                            }

                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exemploFragment.cancel();
                                getFragmentManager().popBackStack();
                            }

                        }).setCancelable(true)
                        .show();
                return;
            }
        }
        if (getFragmentManager().getBackStackEntryCount() == 0) {

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
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
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
    public void addPerguntaRequested() {
        this.p = null;
        getFragmentManager().beginTransaction().replace(R.id.container, EditPerguntaFragment.newInstance()).addToBackStack(null).commit();

    }

    @Override
    public void onEditEnd() {
        getFragmentManager().popBackStack();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (epf.isVisible())
            epf.setAddImage(requestCode, resultCode, data);
        else if (exemploFragment.isVisible())
            exemploFragment.setAddImage(requestCode, resultCode, data);
    }

    @Override
    public void startNewTentativa() {
        Intent i = new Intent(MainActivity.this, RespostasActivity.class);
        ObjectMapper mapper = new ObjectMapper();
        i.putExtra("usuario", json);
        startActivity(i);
    }

    @Override
    public void areaEditChoosed(Area a) {
        currentArea = a;
        getFragmentManager().beginTransaction().replace(R.id.container, new EditAreaFragment()).addToBackStack(null).commit();
    }

    @Override
    public void areaAddRequested() {
        this.currentArea = null;
        getFragmentManager().beginTransaction().replace(R.id.container, new EditAreaFragment()).addToBackStack(null).commit();
    }

    @Override
    public Area getCurrentArea() {
        return currentArea;
    }


    @Override
    protected void onResume() {
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
        super.onResume();
    }

    @Override
    public void userEditChoosed(Usuario usuario) {
        userToEdit = usuario;
        getFragmentManager().beginTransaction().replace(R.id.container, new EditUsuarioFragment(), "editUsuario").commit();

    }

    @Override
    public Usuario getUserToEdit() {
        return userToEdit;
    }

    @Override
    public void userEditFinished() {
        getFragmentManager().beginTransaction().replace(R.id.container, new ListUsuarioFragment(), "usuarios").addToBackStack(null).commit();

    }

    @Override
    public void exemploEditRequested() {
        getFragmentManager().beginTransaction().replace(R.id.container, new ExemploFragment(), "exemplos").addToBackStack(null).commit();

    }
}

package br.ufsc.labtec.mazk.activities.fragments.main.manager.pergunta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.pergunta.OnPerguntaAddRequestedListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.pergunta.OnPerguntaEditChoosedListener;
import br.ufsc.labtec.mazk.adapters.PerguntasAdapter;
import br.ufsc.labtec.mazk.beans.Pergunta;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.PerguntaResource;
import br.ufsc.labtec.mazk.services.util.PerguntaService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mihael Zamin on 08/04/2015.
 */
public class ManagePerguntasFragment extends Fragment {
    private ListView listView;
    private PerguntaResource pr;
    private Usuario u;
    private OnPerguntaEditChoosedListener editChoosedListener;
    private OnPerguntaAddRequestedListener addRequestedListener;
    private PerguntasAdapter adapter;
    private ProgressBar mProgressView;

    public static ManagePerguntasFragment newInstance() {
        ManagePerguntasFragment f = new ManagePerguntasFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manageperguntas, container, false);
        pr = new PerguntaService().createService(getString(R.string.server_url), u.getEmail(), u.getSenha());
        listView = (ListView) v.findViewById(R.id.listPerguntas);
        mProgressView = (ProgressBar) v.findViewById(R.id.perguntas_progress);
        showProgress(true);
        pr.listarPerguntas(new Callback<List<Pergunta>>() {
            @Override
            public void success(List<Pergunta> perguntas, Response response) {
                adapter = new PerguntasAdapter(getActivity(), R.layout.layout_listperguntaitem, perguntas);
                listView.setAdapter(adapter);
                showProgress(false);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("mngPergunta", error.getMessage());
                showProgress(false);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editChoosedListener.editChoosed(adapter.getItem(position));
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_perguntas, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_pergunta) {
            addRequestedListener.addPerguntaRequested();

            return true;
        } else if (item.getItemId() == R.id.refresh_perguntas) {
            showProgress(true);
            pr.listarPerguntas(new Callback<List<Pergunta>>() {
                @Override
                public void success(List<Pergunta> perguntas, Response response) {
                    adapter = new PerguntasAdapter(getActivity(), R.layout.layout_listperguntaitem, perguntas);
                    listView.setAdapter(adapter);
                    showProgress(false);

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("mngPergunta", error.getMessage());
                    Toast.makeText(getActivity(), "Erro ao atualizar", Toast.LENGTH_LONG);
                    showProgress(false);
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            editChoosedListener = (OnPerguntaEditChoosedListener) activity;
            addRequestedListener = (OnPerguntaAddRequestedListener) activity;

            u = ((CurrentUserCallback) activity).getCurrentUser();
            setHasOptionsMenu(true);
        } catch (ClassCastException e) {
            Log.e("ManagePerguntasFragment", "Current activity must implement Listeners");
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MRlistView2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            listView.setVisibility(show ? View.GONE : View.VISIBLE);
            listView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    listView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            listView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

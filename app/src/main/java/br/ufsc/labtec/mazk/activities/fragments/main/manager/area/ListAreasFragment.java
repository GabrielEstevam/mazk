package br.ufsc.labtec.mazk.activities.fragments.main.manager.area;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.area.OnAreaAddRequested;
import br.ufsc.labtec.mazk.activities.fragments.listeners.area.OnAreaEditChoosedListener;
import br.ufsc.labtec.mazk.adapters.AreaListAdapter;
import br.ufsc.labtec.mazk.beans.Area;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.AreaResource;
import br.ufsc.labtec.mazk.services.util.AreaService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mihael Zamin on 12/08/2015.
 */
public class ListAreasFragment extends Fragment {

    private AreaResource ar;
    private ListView listView;
    private AreaListAdapter areaAdapter;
    private Usuario u;
    private View mProgressView;
    private OnAreaAddRequested addRequested;
    private OnAreaEditChoosedListener editChoosedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listareas, container, false);
        mProgressView = v.findViewById(R.id.frameProgressArea);
        listView = (ListView) v.findViewById(R.id.listArea);
        areaAdapter = new AreaListAdapter(getActivity());
        listView.setAdapter(areaAdapter);
        ar = new AreaService().createService(getString(R.string.server_url), u.getEmail(), u.getSenha());
        showProgress(true);
        ar.getAreas(new Callback<List<Area>>() {
            @Override
            public void success(List<Area> areas, Response response) {
                if (areas != null)
                    if (!areas.isEmpty())
                        areaAdapter.setList(areas);
                showProgress(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "Erro ao requisitar as areas.", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editChoosedListener.areaEditChoosed(areaAdapter.getList().get(position));
            }
        });
        return v;

    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            u = ((CurrentUserCallback) activity).getCurrentUser();
            addRequested = (OnAreaAddRequested) activity;
            editChoosedListener = (OnAreaEditChoosedListener) activity;
            setHasOptionsMenu(true);
        } catch (ClassCastException e) {

            e.printStackTrace();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list_area, menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_area:
                addRequested.areaAddRequested();
                break;
            case R.id.refresh_areas:
                showProgress(true);
                ar.getAreas(new Callback<List<Area>>() {
                    @Override
                    public void success(List<Area> areas, Response response) {
                        areaAdapter.setList(areas);
                        showProgress(false);
                        Toast.makeText(getActivity(), "Atualizado.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showProgress(false);
                        Toast.makeText(getActivity(), "Erro ao requisitar atualização de áreas.", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

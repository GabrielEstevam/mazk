package br.ufsc.labtec.mazk.activities.fragments.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.MainActivity;
import br.ufsc.labtec.mazk.activities.RespostasActivity;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnStartNewTentativa;
import br.ufsc.labtec.mazk.beans.Tentativa;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.beans.json.DefaultGson;
import br.ufsc.labtec.mazk.services.UsuarioResource;
import br.ufsc.labtec.mazk.services.util.UsuarioService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mihael Zamin on 07/04/2015.
 */
public class StatsFragment extends Fragment {
    private Usuario u;
    private GraphView graph;
    private TextView txtStatus;
    private TextView txtStart;
    private OnStartNewTentativa osnt;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        graph = (GraphView) v.findViewById(R.id.graph);
        txtStart = (TextView)v.findViewById(R.id.start_tentativa);
        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            osnt.startNewTentativa();

            }
        });
        txtStatus = (TextView)v.findViewById(R.id.txtStatus);
        if(u!= null) {
            UsuarioResource ur = new UsuarioService().createService(getString(R.string.server_url), u.getEmail(), u.getSenha());
            ur.getAllTentativas(new Callback<List<Tentativa>>() {
                @Override
                public void success(List<Tentativa> tentativas, Response response) {
                    if (tentativas != null) {
                        if (!tentativas.isEmpty()) {
                            List<DataPoint> pointVector = new ArrayList<>();
                            int i = 1;
                            for (Tentativa t : tentativas) {
                                if(t.getDesempenho() != null) {
                                    pointVector.add(new DataPoint(i, t.getDesempenho()));
                                    i++;
                                }
                            }
                            if(i-1 > 0) {
                                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>((DataPoint[]) pointVector.toArray());
                                graph.addSeries(series);
                                graph.setTitle("Seu desempenho");
                            }

                        } else txtStatus.setText("Você não possui tentativas");
                    } else txtStatus.setText("Você não possui tentativas");

                }

                @Override
                public void failure(RetrofitError error) {
                    txtStatus.setText("Você não possui tentativas");

                }
            });

        }else txtStatus.setText("Você não possui tentativas");
        return v;
    }
    public static StatsFragment newInstance()
    {
        StatsFragment f = new StatsFragment();
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
           u =  ((CurrentUserCallback)activity).getCurrentUser();
            osnt = (OnStartNewTentativa)activity;
        }catch(ClassCastException e)
        {
            Log.e("STATS", "Must implement current user callback");
            e.printStackTrace();

        }
    }
}

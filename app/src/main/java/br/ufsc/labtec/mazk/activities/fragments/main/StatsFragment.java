package br.ufsc.labtec.mazk.activities.fragments.main;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.CurrentUserCallback;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnStartNewTentativa;
import br.ufsc.labtec.mazk.beans.Tentativa;
import br.ufsc.labtec.mazk.beans.Usuario;
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

    public static StatsFragment newInstance() {
        StatsFragment f = new StatsFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        graph = (GraphView) v.findViewById(R.id.graph);
        txtStart = (TextView) v.findViewById(R.id.start_tentativa);
        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                osnt.startNewTentativa();

            }
        });
        txtStatus = (TextView) v.findViewById(R.id.txtStatus);
        UsuarioResource ur = new UsuarioService().createService(getString(R.string.server_url), u.getEmail(), u.getSenha());
        ur.login(new Callback<Usuario>() {
            @Override
            public void success(Usuario usuario, Response response) {
                StatsFragment.this.u = usuario;
                try {

                    if (!u.getTentativaList().isEmpty()) {
                        List<DataPoint> pointList = new ArrayList<DataPoint>();
                        int i = 0;
                        for (Tentativa t : u.getTentativaList()) {
                            if (t.getDesempenho() > 0) {
                                pointList.add(new DataPoint(i, t.getDesempenho().doubleValue()));
                                i++;
                            }
                        }
                        DataPoint[] array = pointList.toArray(new DataPoint[pointList.size()]);
                        LineGraphSeries<DataPoint> serie = new LineGraphSeries<DataPoint>(array);
                        graph.addSeries(serie);
                        NumberFormat formatter = new DecimalFormat("##.##");
                        txtStatus.setText("Seu nível de experiência: " + formatter.format(u.getExperiencia().doubleValue()));
                    } else txtStatus.setText("Você não possui tentativas");
                } catch (NullPointerException e) {
                    txtStatus.setText("Você não possui tentativas");
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

            u = ((CurrentUserCallback) activity).getCurrentUser();
            osnt = (OnStartNewTentativa) activity;
        } catch (ClassCastException e) {
            Log.e("STATS", "Must implement current user callback");
            e.printStackTrace();

        }
    }
}

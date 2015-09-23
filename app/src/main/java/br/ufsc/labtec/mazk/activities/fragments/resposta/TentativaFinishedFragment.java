package br.ufsc.labtec.mazk.activities.fragments.resposta;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.ChangeActivityCallback;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.TentativaCallback;
import br.ufsc.labtec.mazk.beans.Resposta;
import br.ufsc.labtec.mazk.beans.Tentativa;

/**
 * Created by Mihael Zamin on 16/04/2015.
 */
public class TentativaFinishedFragment extends Fragment {
    private Tentativa tentativa;
    private int acertos;
    private ChangeActivityCallback cac;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tentativafinished, container, false);
        acertos = 0;
        for(Resposta r : tentativa.getRespostaList())
        {
            if(r.getAlternativa().isCorreta())
                acertos++;
        }
        Log.i("TentFinish", "Número de acertos: " + acertos);
        ((TextView)v.findViewById(R.id.ftf_acertos)).setText(String.valueOf(acertos));
        ((TextView)v.findViewById(R.id.ftf_erros)).setText(String.valueOf(tentativa.getRespostaList().size() - acertos));
        ((Button)v.findViewById(R.id.ftf_button_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cac.changeActivity();
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            this.tentativa = ((TentativaCallback)activity).getCurrentTentativa();
            cac= (ChangeActivityCallback)activity;

        }catch (ClassCastException ex)
        {
            Log.e("TFF", "Activity must implement tentativaCallback");
        }
    }
}

package br.ufsc.labtec.mazk.activities.fragments.resposta;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.NextFragmentRequester;
import br.ufsc.labtec.mazk.activities.fragments.callbacks.RespostaCallback;
import br.ufsc.labtec.mazk.beans.Alternativa;
import br.ufsc.labtec.mazk.beans.Resposta;
import medit.core.HtmlBuilder;

/**
 * Created by Mihael Zamin on 22/04/2015.
 */
public class RespostaErradaFragment extends Fragment {
    private Resposta r;
    private NextFragmentRequester nfr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_respostaerrada, container, false);
        TextView explicacao = (TextView)v.findViewById(R.id.fr_explicacao);
if(r.getPergunta().getExplicacao() != null)
            explicacao.setText(Html.fromHtml(new String(r.getPergunta().getExplicacao(), Charset.forName("UTF-8")), new HtmlBuilder(getActivity()), null));

        TextView selecionado = (TextView)v.findViewById(R.id.alternativa_selecionada);
        selecionado.setText(r.getAlternativa().getDescricao());
        TextView correta = (TextView)v.findViewById(R.id.fr_alternativa_certa);

        List<Alternativa> list  = r.getPergunta().getAlternativaList();
        for(Alternativa a : list) {
            if (a.isCorreta()) {
                correta.setText(a.getDescricao());
                break;
            }
        }
        Button btn = (Button) v.findViewById(R.id.button_nextfragment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nfr.nextFragment();
            }
        });



        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        r = ((RespostaCallback)activity).getCurrentResposta();
        nfr = (NextFragmentRequester)activity;

        super.onAttach(activity);
    }
}

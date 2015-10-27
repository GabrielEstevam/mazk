package br.ufsc.labtec.mazk.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.listeners.resposta.OnAlternativaSelected;
import br.ufsc.labtec.mazk.beans.Alternativa;
import br.ufsc.labtec.mazk.view.custom.ViewHolder;

/**
 * Created by Mihael Zamin on 18/04/2015.
 */
public class AlternativaRespostaAdapter extends BaseAdapter {
    private List<Alternativa> alternativas;
    private Context context;
    private OnAlternativaSelected onAlternativaSelected;

    public AlternativaRespostaAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return alternativas.size();
    }

    @Override
    public Alternativa getItem(int position) {
        return alternativas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            convertView = vi.inflate(R.layout.adapter_resposta, parent, false);
        }
        Button alternativaButton = ViewHolder.get(convertView, R.id.button_alternativa);

        alternativaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAlternativaSelected != null) {
                    onAlternativaSelected.alternativaSelected(getItem(position));
                }
            }
        });
        alternativaButton.setText(getItem(position).getDescricao());
        return convertView;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
        notifyDataSetChanged();
    }

    public void addOnAlternativaSelectedListener(OnAlternativaSelected onAlternativaSelected) {
        this.onAlternativaSelected = onAlternativaSelected;
        notifyDataSetChanged();
    }


}

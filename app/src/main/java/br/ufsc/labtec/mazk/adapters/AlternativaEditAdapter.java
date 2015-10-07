package br.ufsc.labtec.mazk.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.beans.Alternativa;

/**
 * Created by Mihael Zamin on 09/04/2015.
 */
public class AlternativaEditAdapter extends BaseAdapter {
    private final Context context;
    private List<Alternativa> alternativas;

    // the context is needed to inflate views in getView()
    public AlternativaEditAdapter(Context context) {
        this.context = context;
        alternativas = Collections.EMPTY_LIST;
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
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_alternativaedit, null);
            holder.setRbCorreta((RadioButton) convertView.findViewById(R.id.rbCorreta));
            holder.setEtAlternativa((EditText) convertView.findViewById(R.id.etAlternativa));
            holder.pos = position;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Alternativa current = getItem(position);
        if (!TextUtils.isEmpty(current.getDescricao()))
            holder.getEtAlternativa().setText(current.getDescricao());
        holder.getRbCorreta().setChecked(current.isCorreta());
        holder.getRbCorreta().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlternativaEditAdapter.this.setCorreta(position);


                }
            }
        });
        holder.getEtAlternativa().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getItem(position).setDescricao(s.toString());

            }
        });

        return convertView;

    }

    public void setCorreta(int p) {

        int i = 0;
        for (Alternativa a : alternativas) {
            a.setCorreta(i == p);
            i++;
        }
        this.notifyDataSetChanged();
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
        notifyDataSetChanged();
    }

    public void add(Alternativa a) {
        a.setCorreta(false);
        if (alternativas.isEmpty())
            alternativas = new ArrayList<>();
        alternativas.add(a);
        notifyDataSetChanged();
    }

    private static class ViewHolder {

        public int pos;
        private EditText etAlternativa;
        private RadioButton rbCorreta;

        public RadioButton getRbCorreta() {
            return rbCorreta;
        }

        public void setRbCorreta(RadioButton rbCorreta) {
            this.rbCorreta = rbCorreta;
        }

        public EditText getEtAlternativa() {
            return etAlternativa;
        }

        public void setEtAlternativa(EditText etAlternativa) {
            this.etAlternativa = etAlternativa;
        }
    }

}

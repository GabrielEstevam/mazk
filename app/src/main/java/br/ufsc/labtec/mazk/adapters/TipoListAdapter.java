package br.ufsc.labtec.mazk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.beans.Tipo;

/**
 * Created by Mihael Zamin on 25/09/2015.
 * Adapter para listar todos os tipos.
 */
public class TipoListAdapter extends BaseAdapter {
    private Context context;
    private List<Tipo> list;

    public TipoListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_list_tipo, null);
            holder.setTxtNome((TextView) convertView.findViewById(R.id.adapter_tipo_txtNome));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.getTxtNome().setText(list.get(position).getNome());
        return convertView;
    }

    public List<Tipo> getList() {
        return list;
    }

    public void setList(List<Tipo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView txtNome;

        public TextView getTxtNome() {
            return txtNome;
        }

        public void setTxtNome(TextView txtNome) {
            this.txtNome = txtNome;
        }
    }

    ;
}

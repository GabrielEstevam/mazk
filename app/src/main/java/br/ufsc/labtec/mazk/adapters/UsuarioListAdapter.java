package br.ufsc.labtec.mazk.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.beans.Usuario;

/**
 * Created by Mihael Zamin on 24/09/2015.
 */
public class UsuarioListAdapter extends BaseAdapter {
    private List<Usuario> usuarioList;
    private Context context;

    public UsuarioListAdapter(Context context) {
        this.context = context;
        this.usuarioList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return usuarioList.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarioList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_list_usuario, null);
            holder.setTxtNome((TextView) convertView.findViewById(R.id.adapter_tvUsuarioNome));
            holder.setTxtEmail((TextView) convertView.findViewById(R.id.adapter_tvUsuarioEmail));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (holder == null)
            Log.i("UAL", "Holder null");
        if (usuarioList == null)
            Log.i("UAL", "Usuario List null");
        if (usuarioList.get(position) == null)
            Log.i("UAL", "Elemento " + position + " null");
        holder.getTxtNome().setText(usuarioList.get(position).getNome());
        holder.getTxtEmail().setText(usuarioList.get(position).getEmail());
        return convertView;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView txtNome;
        private TextView txtEmail;

        public TextView getTxtNome() {
            return txtNome;
        }

        public void setTxtNome(TextView txtNome) {
            this.txtNome = txtNome;
        }

        public TextView getTxtEmail() {
            return txtEmail;
        }

        public void setTxtEmail(TextView txtEmail) {
            this.txtEmail = txtEmail;
        }
    }

    ;
}

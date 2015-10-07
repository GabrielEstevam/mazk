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
import br.ufsc.labtec.mazk.beans.Area;

/**
 * Created by Mihael Zamin on 01/09/2015.
 */
public class AreaListAdapter extends BaseAdapter {
    private List<Area> list;
    private Context context;

    public AreaListAdapter(Context context) {

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
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_listarea, null);
            holder.setTxtArea((TextView) convertView.findViewById(R.id.txtNomeArea));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.getTxtArea().setText(list.get(position).getNome());
        return convertView;
    }

    public List<Area> getList() {
        return list;
    }

    public void setList(List<Area> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListAndNotNotify(List<Area> list) {
        this.list = list;

    }

    private class ViewHolder {
        private TextView txtArea;

        public TextView getTxtArea() {
            return txtArea;
        }

        public void setTxtArea(TextView txtArea) {
            this.txtArea = txtArea;
        }
    }
}

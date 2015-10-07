package br.ufsc.labtec.mazk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.beans.Exemplo;
import medit.core.HtmlBuilder;

/**
 * Created by Mihael Zamin on 01/09/2015.
 */
public class ExplicacaoListAdapter extends BaseAdapter {
    private List<Exemplo> list;
    private List<Exemplo> baseList;
    private Context context;

    public ExplicacaoListAdapter(Context context) {

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
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_listexplicacao, null);

            holder.setTextView((TextView) convertView.findViewById(R.id.txtAdapterExplicacao));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.getTextView().setText(HtmlBuilder.htmltoSpanned(new String(list.get(position).getConteudo(), Charset.forName("UTF-8")), context.getResources()));

        return convertView;
    }

    public List<Exemplo> getList() {
        return list;
    }

    public void setList(List<Exemplo> list) {
        this.baseList = list;
        this.list = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public List<Exemplo> getBaseList() {
        return baseList;
    }

    public void setListAndNotNotify(List<Exemplo> list) {
        this.baseList = list;
        this.list = new ArrayList<>(list);

    }

    public void add(Exemplo e) {
        list.add(e);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView textView;

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
}

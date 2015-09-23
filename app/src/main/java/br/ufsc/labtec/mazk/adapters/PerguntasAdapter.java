package br.ufsc.labtec.mazk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.beans.Pergunta;

/**
 * Created by Mihael Zamin on 09/04/2015.
 */
public class PerguntasAdapter extends ArrayAdapter<Pergunta> {
    private int resource;
    private ViewHolder perguntaView;
    private List<Pergunta> src;
    public PerguntasAdapter(Context context, int resource, List<Pergunta> objects) {
        super(context, resource, objects);
        this.resource = resource;
        src = objects;
    }

    @Override
    public Pergunta getItem(int position) {
        return src.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pergunta p = getItem(position);
        if(convertView==null)
        {
            perguntaView = new ViewHolder();
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
           convertView = vi.inflate(resource, parent, false);
            perguntaView.setTitulo((TextView)convertView.findViewById(R.id.txtTituloPergunta));
            perguntaView.setSubtitulo((TextView)convertView.findViewById(R.id.txtSubPergunta));
            convertView.setTag(perguntaView);
        }
        else
        {
            perguntaView = (ViewHolder) convertView.getTag();
        }
        perguntaView.getTitulo().setText(p.getEnunciado());
        if(p.getAreaList() != null) {
            if (!p.getAreaList().isEmpty()) {
                perguntaView.getSubtitulo().setText((p.getAtivo() ? "Ativo" : "Oculta") + " - Área: " + p.getAreaList().get(0).getNome());
                return convertView;
            }
        }

        perguntaView.getSubtitulo().setText((p.getAtivo() ? "Ativo" : "Oculta"));
        return convertView;
    }
    static class ViewHolder{
      TextView titulo, subtitulo;

        public TextView getTitulo() {
            return titulo;
        }

        public void setTitulo(TextView titulo) {
            this.titulo = titulo;
        }

        public TextView getSubtitulo() {
            return subtitulo;
        }

        public void setSubtitulo(TextView subtitulo) {
            this.subtitulo = subtitulo;
        }
    };
}

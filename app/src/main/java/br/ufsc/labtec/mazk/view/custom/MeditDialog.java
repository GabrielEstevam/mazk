package br.ufsc.labtec.mazk.view.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import br.ufsc.labtec.mazk.R;
import medit.core.MeditText;

/**
 * Created by Mihael Zamin on 22/04/2015.
 */
public class MeditDialog extends Dialog {
    private Activity activity;
    private MeditText mEdit;
    private Button btnOk;
    private byte[] explicacao;
    private View.OnClickListener ocl;
    public MeditDialog(Context context) {
        this(context, null);
    }

    public MeditDialog(Context context, byte[] explicacao)
    {
        super(context);
        this.explicacao = explicacao;
    }
    public MeditDialog(Context context, byte[] explicacao, Activity activity)
    {
        this(context, explicacao);
        this.activity = activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_explicacao);
        mEdit = (MeditText)findViewById(R.id.de_medit);
        btnOk = (Button)findViewById(R.id.de_button_ok);
        btnOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setTag(mEdit);

                ocl.onClick(v);
                MeditDialog.this.dismiss();
            }
        });
        mEdit.setBoldButton(findViewById(R.id.de_bold));
        mEdit.setColorButton(findViewById(R.id.de_color));
        mEdit.setItalicButton(findViewById(R.id.de_italic));
        mEdit.setUnderlineButton(findViewById(R.id.de_underline));
        mEdit.setAddImageButton(findViewById(R.id.de_image), activity);
        if(explicacao != null)

                mEdit.setText(mEdit.htmltoSpanned(new String(explicacao, Charset.forName("UTF-8"))));



    }
    public void setActivity(Activity activity)
    {
        this.activity = activity;


    }
    public void setListener(final android.view.View.OnClickListener listener)
    {
        this. ocl = listener;
    }
    public void addImage(int requestCode, int resultCode, Intent data)
    {
        mEdit.addImageHere(requestCode, resultCode, data, activity, 1);
    }



}

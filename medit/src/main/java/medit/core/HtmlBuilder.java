package medit.core;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Base64;

public class HtmlBuilder implements ImageGetter {
	private Resources res;
	
	public HtmlBuilder(Context context)
	{
		this.res = context.getResources();
	}
	public HtmlBuilder(Resources res)
	{
		this.res = res;
	}

    public Drawable getDrawable(String source) {            
        byte[] data;
		 data = Base64.decode(source,Base64.DEFAULT);
		 Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);    
		 Drawable d = new BitmapDrawable(res, bitmap);
		 d.setBounds(0,0,d.getIntrinsicWidth(), d.getIntrinsicHeight());   // <-----
		 return d;
    }
    public static Spanned htmltoSpanned(String html, Resources res)
	{
    	
		return Html.fromHtml(html, new HtmlBuilder(res), null);
	}

}

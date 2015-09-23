package medit.core;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import medit.core.clicklisteners.ImagePickerClickListener;
import medit.core.utils.ImageFactory;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MeditText extends EditText implements TextWatcher,
		OnClickListener, ColorPickerDialog.OnColorChangedListener {

	private ImageButton addImgBtn;
	private CheckButton colorBtn;
	private CheckButton boldBtn;
	private CheckButton italicBtn;
	private CheckButton underlineBtn;
	private ColorPickerDialog colorDialog;
	private Paint currentColor;
	private float maxImageSize = (float)360;

	private void newButtons(Context context) {
		this.addImgBtn = new ImageButton(context);
		this.colorBtn = new CheckButton(context);
		this.boldBtn = new CheckButton(context);
		this.italicBtn = new CheckButton(context);
		this.underlineBtn = new CheckButton(context);
		this.setMaxImageSize((float)360);
		
	}

	public MeditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		newButtons(context);
		this.addTextChangedListener(this);
	}

	public ImageButton getAddImageButton() {
		return addImgBtn;
	}

	public void setAddImageButton(View addImgBtn, Activity a) {
		this.addImgBtn = (ImageButton) addImgBtn;
		this.addImgBtn.setOnClickListener(new ImagePickerClickListener(a));

	}

	public CheckButton getColorButton() {
		return colorBtn;
	}

	public void setColorButton(View colorBtn) {
		this.colorBtn = (CheckButton) colorBtn;
		this.colorBtn.setOnClickListener(this);
		currentColor = new Paint();
		currentColor.setColor(Color.BLACK);
		colorDialog = new ColorPickerDialog(this.getContext(), this,
				currentColor.getColor());
	}

	public CheckButton getBoldButton() {
		return boldBtn;
	}

	public MeditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		newButtons(context);
		this.addTextChangedListener(this);
	}

	public MeditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		newButtons(context);
		this.addTextChangedListener(this);
	}

	public void setBoldButton(View boldBtn) throws IllegalArgumentException {
		if (boldBtn instanceof Button) {
			this.boldBtn = (CheckButton) boldBtn;
			this.boldBtn.setOnClickListener(this);
		} else
			throw new IllegalArgumentException();

	}

	public CheckButton getItalicButton() {
		return italicBtn;
	}

	public void setItalicButton(View italicBtn) {
		this.italicBtn = (CheckButton) italicBtn;
		this.italicBtn.setOnClickListener(this);
	}

	public CheckButton getUnderlineButton() {
		return underlineBtn;
	}

	public void setUnderlineButton(View underlineBtn) {
		this.underlineBtn = (CheckButton) underlineBtn;
		this.underlineBtn.setOnClickListener(this);

	}

	public ColorPickerDialog getColorDialog() {
		return colorDialog;
	}

	public void setColorDialog(ColorPickerDialog colorDialog) {
		this.colorDialog = colorDialog;
	}

	public Paint getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Paint currentColor) {
		this.currentColor = currentColor;
	}

	public float getMaxImageSize() {
		return maxImageSize;
	}

	public void setMaxImageSize(float maxImageSize) {
		this.maxImageSize = maxImageSize;
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		boolean[] all = new boolean[4];
		if (this.getBoldButton() != null) {
			if (all[0] = this.getBoldButton().isChecked()) {

				this.changeSpan(android.graphics.Typeface.BOLD,
						this.getBoldButton(), s);
			}
		}
		if (this.getItalicButton() != null) {
			if (all[1] = this.getItalicButton().isChecked()) {
				if (this.getBoldButton().isChecked()) {
					this.changeSpan(android.graphics.Typeface.BOLD_ITALIC,
							this.getItalicButton(), s);
				} else
					this.changeSpan(android.graphics.Typeface.ITALIC,
							this.getItalicButton(), s);
			}
		}
		if (this.getUnderlineButton() != null) {
			if (all[2] = this.getUnderlineButton().isChecked()) {
				this.changeSpan(this.getUnderlineButton(), s,
						new UnderlineSpan());
			}
		}
		if (this.getColorButton() != null) {
			if (all[3] = this.getColorButton().isChecked()) {
				this.changeSpan(this.getColorButton(), s,
						new ForegroundColorSpan(currentColor.getColor()));
			}
		}
	
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void onClick(View v) {

		CheckButton b = (CheckButton) v;

		if (this.getSelectionEnd() - this.getSelectionStart() > 0) {
			if (b.equals(getUnderlineButton())) {
				this.changeSpan(new UnderlineSpan(), this.getEditableText(),
						this.getSelectionStart(), this.getSelectionEnd());
			} else if (b.equals(getBoldButton())) {

				this.changeSpan(new StyleSpan(android.graphics.Typeface.BOLD),
						this.getEditableText(), this.getSelectionStart(),
						this.getSelectionEnd());

			} else if (b.equals(getItalicButton())) {
				this.changeSpan(
						new StyleSpan(android.graphics.Typeface.ITALIC),
						this.getEditableText(), this.getSelectionStart(),
						this.getSelectionEnd());
			}
		} else if (!b.isChecked()) {

			b.setPosition(this.getSelectionEnd());
			if (b.equals(getColorButton())) {
				this.getColorDialog().show();
			}

		} else if (b.equals(getColorButton())) {
			getColorButton().setTextColor(Color.BLACK);
		}

	}

	private void changeSpan(int type, CheckButton b, Editable s) {
		Log.i("Change Span", "Started.");
		if (b.getPosition() != this.getSelectionEnd()) {
			StyleSpan[] ss = s.getSpans(b.getPosition(),
					this.getSelectionEnd(), StyleSpan.class);

			for (int i = 0; i < ss.length; i++) {
				if (ss[i].getStyle() == type) {
					s.removeSpan(ss[i]);
				}
			}
			int end = this.getSelectionEnd();
			int mStart = b.getPosition();
			if (end - mStart > 0)
				s.setSpan(new StyleSpan(type), mStart, end,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		Log.i("Change Span", "Finished.");
	}

	private void changeSpan(Object span, Editable s, int end, int start) {
		Log.i("Change Span", "Started.");
		if (end != start && end - start > 0) {
			if (span instanceof UnderlineSpan) {

				UnderlineSpan[] ss = s
						.getSpans(start, end, UnderlineSpan.class);
				for (int i = 0; i < ss.length; i++) {
					s.removeSpan(ss[i]);
				}
				if (ss.length > 0) {
					s.setSpan(new UnderlineSpan(), start, end,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}

			} else if (span instanceof StyleSpan) {
				int type = ((StyleSpan) span).getStyle();

				boolean havespan = false;

				StyleSpan[] ss = s.getSpans(start, end, StyleSpan.class);

				for (int i = 0; i < ss.length; i++) {
					if (ss[i].getStyle() == type) {
						s.removeSpan(ss[i]);
						havespan = true;
					}
				}

				if (!havespan)
					s.setSpan(new StyleSpan(type), start, end,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		Log.i("Change Span", "Finished.");
	}

	private void changeSpan(CheckButton b, Editable s, Object span) {
		Log.i("Change Span", "Started.");
		if (b.getPosition() != this.getSelectionEnd()) {
			if (span instanceof UnderlineSpan) {
				UnderlineSpan[] ss = s.getSpans(b.getPosition(),
						this.getSelectionEnd(), UnderlineSpan.class);
				for (int i = 0; i < ss.length; i++) {
					s.removeSpan(ss[i]);
				}
			} else if (span instanceof ForegroundColorSpan) {
				ForegroundColorSpan[] ss = s.getSpans(b.getPosition(),
						this.getSelectionEnd(), ForegroundColorSpan.class);
				for (int i = 0; i < ss.length; i++) {
					s.removeSpan(ss[i]);
				}
			}
			int end = this.getSelectionEnd();
			int mStart = b.getPosition();
			if (end - mStart > 0)
				s.setSpan(span, mStart, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		Log.i("Change Span", "Finished.");
	}

	public MeditText(Context context, ImageButton addImgBtn,
			CheckButton colorBtn, CheckButton boldBtn, CheckButton italicBtn,
			CheckButton underlineBtn) {
		super(context);
		this.addImgBtn = addImgBtn;
		this.colorBtn = colorBtn;
		this.boldBtn = boldBtn;
		this.italicBtn = italicBtn;
		this.underlineBtn = underlineBtn;
		this.addTextChangedListener(this);
	}

	@Override
	public void colorChanged(int color) {
		currentColor.setColor(color);
		getColorButton().setTextColor(color);
	}
	private ProgressDialog dialog;
	private  void showProgress () {
	    dialog = new ProgressDialog(this.getContext());
	    dialog.setCancelable(true);
	    dialog.setMessage("Processando, por favor aguarde...");
	    dialog.show();

	}
	 	
 

	public void addImageHere(int requestCode, int resultCode, Intent data,
			Activity a, int imageRequestCode) {
		showProgress();
		if (requestCode == imageRequestCode && resultCode == Activity.RESULT_OK
				&& data != null) {
			String selectedImagePath;
			Uri selectedImageUri = data.getData();
			if (Build.VERSION.SDK_INT < 19) {
				selectedImagePath = getPath(selectedImageUri, a);
				Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
				
				SpanImage(ImageFactory.scaleDown(bitmap, this.getMaxImageSize(), true));
				

			} else {
				ParcelFileDescriptor parcelFileDescriptor;
				try {
					parcelFileDescriptor = a.getContentResolver()
							.openFileDescriptor(selectedImageUri, "r");
					FileDescriptor fileDescriptor = parcelFileDescriptor
							.getFileDescriptor();
					Bitmap image = BitmapFactory
							.decodeFileDescriptor(fileDescriptor);
					parcelFileDescriptor.close();
					SpanImage(ImageFactory.scaleDown(image, this.getMaxImageSize(), true));
					

				} catch (FileNotFoundException e) {
					e.printStackTrace();					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		dialog.cancel();
	}

	private String getPath(Uri contentUri, Activity a) {
		String res = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = a.getContentResolver().query(contentUri, proj, null,
				null, null);
		if (cursor.moveToFirst()) {
			;
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			res = cursor.getString(column_index);
		}
		cursor.close();
		return res;
	}

	private void SpanImage(Bitmap image) {
		String newImage = "[";
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append("\n" + newImage);
		builder.setSpan(new ImageSpan(getContext(), image), builder.length()
				- newImage.length(), builder.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.append("\n");
		this.getEditableText().append(builder);
		

	}

	public String toHtml() {
		showProgress();
		String html = Html.toHtml(this.getEditableText());
		ImageSpan[] images = this.getEditableText().getSpans(0,
				this.getEditableText().length(), ImageSpan.class);
		Pattern p = Pattern.compile("null");
		Matcher m = p.matcher(html);
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while (m.find()) {
			m.appendReplacement(sb,
					ImageFactory.drawableToBase64(images[i].getDrawable()));
			i++;
		}
		m.appendTail(sb);
		Log.i("String length", String.valueOf(sb.toString().length()));
		dialog.cancel();
		return sb.toString();

	}
	public Spanned htmltoSpanned(String html)
	{
		return Html.fromHtml(html, new HtmlBuilder(this.getContext()), null);
	}
}

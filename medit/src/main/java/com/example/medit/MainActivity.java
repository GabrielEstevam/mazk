package com.example.medit;

import medit.core.HtmlBuilder;
import medit.core.MeditText;
import medit.core.utils.ImageFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnSeekBarChangeListener {

	public final int RESULT_LOAD_IMAGE = 1;
	private MeditText mEdit;
	private TextView tv;
	private TextView tv2;
	private SeekBar sb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mEdit = (MeditText) this.findViewById(R.id.editText1);
		mEdit.setBoldButton(this.findViewById(R.id.boldBtn));
		mEdit.setItalicButton(this.findViewById(R.id.italicBtn));
		mEdit.setUnderlineButton(this.findViewById(R.id.underline));
		mEdit.setColorButton(this.findViewById(R.id.colorBtn));
		mEdit.setAddImageButton(this.findViewById(R.id.imageBtn), this);
		tv = (TextView) this.findViewById(R.id.textView1);
		tv2 = (TextView) this.findViewById(R.id.textView2);
		sb = (SeekBar) this.findViewById(R.id.seekBar);
		sb.setMax(100);
		sb.setProgress(100);
		sb.setOnSeekBarChangeListener(this);
		tv2.setText(String.valueOf(sb.getProgress()) + "%");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.UnderlineBtn, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
			return true;
		else if (id == R.id.toTextView) {
			if (mEdit.getText().length() > 0) {
				String txt = mEdit.toHtml();

				tv.setText(HtmlBuilder.htmltoSpanned(txt, getResources()));
				tv.append("\n+ " + txt);
			}
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mEdit.addImageHere(requestCode, resultCode, data, this,
				RESULT_LOAD_IMAGE);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		tv2.setText(String.valueOf(progress) + "%");
		ImageFactory.IMAGE_QUALITY = progress;
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
		
	}

}

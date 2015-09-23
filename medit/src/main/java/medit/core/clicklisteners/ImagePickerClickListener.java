package medit.core.clicklisteners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ImagePickerClickListener implements OnClickListener {

	private Activity a;

	public ImagePickerClickListener(Activity activity) {
		this.a = activity;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		this.a.startActivityForResult(intent, 1);
	}

}

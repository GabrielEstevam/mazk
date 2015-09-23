package medit.core.clicklisteners;

import medit.core.RefBool;
import android.view.View;
import android.view.View.OnClickListener;

public class CheckedClickListener implements OnClickListener {

	private RefBool checked;
	private OnClickListener l; 
	public CheckedClickListener(RefBool checkListener, OnClickListener l) {
		this.checked = checkListener;
		this.l = l;
	}

	@Override
	public void onClick(View v) {		
		l.onClick(v);
		this.checked.setValue(!this.checked.getValue());
	}
	

}

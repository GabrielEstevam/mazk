package medit.core;

import medit.core.clicklisteners.CheckedClickListener;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class CheckButton extends Button {

	private RefBool checked;
	private int position;
	
	public CheckButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		checked = new RefBool(false);
	}
	public CheckButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		checked = new RefBool(false);
	}
	public CheckButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		checked = new RefBool(false);
	}
	public boolean isChecked() {
		return checked.getValue();
	}
	public void setChecked(boolean checked) {
		this.checked.setValue(checked);
	}
	public void setOnClickListener(OnClickListener l)
	{
		super.setOnClickListener(new CheckedClickListener(this.checked, l));
		
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

}

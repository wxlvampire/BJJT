package hssc.androidview.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import hssc.androidview.R;
import hssc.androidview.utils.AppUtils;

public class BladeView extends View {
	private BladeViewItemListener mOnItemClickListener;
	String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z" ,"#" };
	private int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;
	private PopupWindow mPopupWindow;
	private TextView mPopupText;
	private Context context;
	private Handler handler = new Handler();

	public BladeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context= context;
	}

	public BladeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context= context;
	}

	public BladeView(Context context) {
		super(context);
		this.context= context;
	}

	public int getChoose() {
		return choose;
	}

	public void setChoose(int choose) {
		this.choose = choose;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#00000000"));
		}

		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.length;
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.parseColor("#a3a3a3"));
			paint.setTextSize(AppUtils.dip2px(context, 15));
			paint.setAntiAlias(true);

			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;

			float bitmapX = xPos - AppUtils.dip2px(context,5);
			float bitmapY = yPos - (AppUtils.dip2px(context, 15)/2);
			if (i == choose) {
				paint.setColor(Color.parseColor("#121a1c"));
				Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_choose_fans);
				canvas.drawBitmap(bitmap,bitmapX,bitmapY, paint);
			}

			canvas.drawText(b[i], xPos, yPos, paint);

			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final int c = (int) (y / getHeight() * b.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c) {
				if (c > 0 && c < b.length) {
					performItemClicked(c);
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c) {
				if (c > 0 && c < b.length) {
					performItemClicked(c);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
//			choose = -1;
//			dismissPopup();
//			invalidate();
			break;
		}
		return true;
	}

	private void showPopup(int item) {
		if (mPopupWindow == null) {
			handler.removeCallbacks(dismissRunnable);
			mPopupText = new TextView(getContext());
			mPopupText.setBackgroundColor(Color.GRAY);
			mPopupText.setTextColor(Color.CYAN);
			mPopupText.setTextSize(50);
			mPopupText.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL);
			mPopupWindow = new PopupWindow(mPopupText, 100, 100);
		}

		String text = "";
		if (item == b.length - 1) {
			text = "#";
		} else {
			text = Character.toString((char) ('A' + item - 1));
		}
		mPopupText.setText(text);
		if (mPopupWindow.isShowing()) {
			mPopupWindow.update();
		} else {
			mPopupWindow.showAtLocation(getRootView(),
					Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
		}
	}

	private void dismissPopup() {
		handler.postDelayed(dismissRunnable, 800);
	}

	Runnable dismissRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mPopupWindow != null) {
				mPopupWindow.dismiss();
			}
		}
	};

	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnItemClickListener(BladeViewItemListener listener) {
		mOnItemClickListener = listener;
	}

	private void performItemClicked(int item) {
		if (mOnItemClickListener != null) {
			mOnItemClickListener.onItemClick(b[item]);
//			showPopup(item);
		}
	}

//	public interface RecycleOnItemClickListener {
//		void onItemClick(String s);
//	}

}

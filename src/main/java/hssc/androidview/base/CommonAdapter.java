package hssc.androidview.base;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class CommonAdapter<T> extends BaseAdapter {

	public LayoutInflater inflater;
	public List<T> list;
	public Context context;

	public static final int DRAWABLE_LEFT = 0;
	public static final int DRAWABLE_TOP = 1;
	public static final int DRAWABLE_RIGHT = 2;
	public static final int DRAWABLE_BOTTOM = 3;

	public CommonAdapter(List<T> list,Context context){
		this.list = list;
		this.context= context;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return list ==null? 0:list.size();
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
	public View getView(int position, View view, ViewGroup container) {
		
		return setView(position,view,container);
	}

	public abstract View setView(int position, View view, ViewGroup container);
	@SuppressWarnings("unchecked")
	public static <V extends View> V findView(View view, int id) {
		return (V) view.findViewById(id);
	}

	public void setDrawable(TextView view,int drawableId,int location){
		if (drawableId == 0){
			view.setCompoundDrawables(null, null, null, null);
		}else {
			switch (location) {
				case DRAWABLE_LEFT:
					view.setCompoundDrawables(getDrawable(drawableId), null, null, null);
					break;
				case DRAWABLE_TOP:
					view.setCompoundDrawables(null, getDrawable(drawableId), null, null);
					break;
				case DRAWABLE_RIGHT:
					view.setCompoundDrawables(null, null, getDrawable(drawableId), null);
					break;
				case DRAWABLE_BOTTOM:
					view.setCompoundDrawables(null, null, null, getDrawable(drawableId));
					break;
			}
		}

	}

	private Drawable getDrawable(int drawableId){
		Drawable drawable= context.getResources().getDrawable(drawableId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		return drawable;
	}
}

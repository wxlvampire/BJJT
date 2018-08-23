package hssc.androidview.views.recycle;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by li on 2017/10/9.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder{
    //使用数组把条目中的View保存起来
    private SparseArray<View> views;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<View>();
    }
    public  <T extends View> T findView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}

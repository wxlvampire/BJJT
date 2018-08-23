package hssc.androidview.views.recycle;

import java.util.List;

/**
 * Created by li on 2017/10/9.
 */
public abstract class CommonAdapter<T> extends BaseAdapter<T,BaseViewHolder>{

    public OnReloadListener onReloadListener;
    public CommonAdapter(List<T> list, int layoutId) {
        super(list, layoutId);
    }
    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }
    public void onReload(){
        if (onReloadListener != null){
            onReloadListener.onReload();
        }
    }
}

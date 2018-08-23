package hssc.androidview.views.recycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hssc.androidview.R;

/**
 * Created by li on 2017/10/9.
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder>{
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = TYPE_HEADER+1;
    public static final int TYPE_EMPTY = TYPE_NORMAL+1;

    public List<T> list;
    private int layoutId;
    private RecycleOnItemClickListener recycleOnItemClickListener;
    private View mHeaderView;

    public BaseAdapter(List<T> list,int layoutId) {
        this.list = list;
        this.layoutId = layoutId;
    }

    public void setRecycleOnItemClickListener(RecycleOnItemClickListener recycleOnItemClickListener) {
        this.recycleOnItemClickListener = recycleOnItemClickListener;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public abstract int getEmptyLayoutId();

    @Override
    public int getItemViewType(int position) {
        if (list.size() == 0){
            return TYPE_EMPTY;
        }
        if(mHeaderView == null){
            return TYPE_NORMAL;
        }
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY && getEmptyLayoutId() != 0){
            View emptyView= LayoutInflater.from(parent.getContext()).inflate(getEmptyLayoutId(),parent,false);
            return new BaseViewHolder(emptyView);
        }
        if(mHeaderView != null && viewType == TYPE_HEADER){
            return new BaseViewHolder(mHeaderView);
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
        BaseViewHolder viewHolder = new BaseViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if(getItemViewType(position) == TYPE_HEADER){
            return;
        }

        final int poi = getRealPosition(holder);
        final T item = getItem(poi);
        onBind(holder,poi, item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recycleOnItemClickListener != null && list.size() > 0){
                    recycleOnItemClickListener.onItemClick(poi,item);
                }
            }
        });
    }
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    public T getItem(int position) {
        if (position >= list.size()) return null;
        return list.get(position);
    }


    /**
     * 下拉刷新，清除原有数据，添加新数据
     *
     * @param newData
     */
    public void refreshData(List<T> newData) {
        list.clear();
        list.addAll(newData);
        notifyItemRangeChanged(0, list.size());
    }

    /**
     * 在原来数据的末尾追加新数据
     *
     * @param moreData
     */
    public void loadMoreData(List<T> moreData) {
        int lastPosition = list.size();
        list.addAll(lastPosition, moreData);
        notifyItemRangeInserted(lastPosition, moreData.size());
    }



    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : list.size() + 1;
    }
    public abstract void onBind(BaseViewHolder viewHolder, int position, T data);
    /** ============== 打开Activity ============ **/
    /**
     * 根据类打开Activity
     * @param clazz
     */
    public void startActivity(Context context,Class<?> clazz) {
        startActivity(context,clazz, null);
    }

    /**
     * 根据类打开Activity,并携带参数
     * @param clazz
     * @param bundle
     */
    public void startActivity(Context context,Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}

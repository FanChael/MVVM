package com.hl.lib_banner.view.viewpaper2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hl.lib_banner.R;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 轮播VP2图片适配器 - 未做基础适配器依赖，想更加独立一些
 *
 * @Author: hl
 * @Date: created at 2020/4/29 10:08
 * @Description: com.hl.lib_banner.view.viewpaper2.adapter
 */
public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.CardViewHolder> {
    private List<String> imgList;
    public static final int mLooperCount = 500;

    public ViewPagerAdapter(List<String> imgList) {
        this.imgList = imgList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder(parent.getContext(),
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_banner_item,
                                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.setImageView(imgList.get(position % imgList.size()));
    }

    @Override
    public int getItemCount() {
        //return null == imgList ? 0 : imgList.size();
        return null == imgList ? 0 : imgList.size() * mLooperCount;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (null != recyclerView) {
            int count = recyclerView.getChildCount();
            for (int i = 0; i < count; ++i) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                if (viewHolder instanceof CardViewHolder) {
                    CardViewHolder cardViewHolder = (CardViewHolder) viewHolder;
                    cardViewHolder.recycle();
                }
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CardViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        // 滑动的时候会走这个，利用控件复用，不要去释放Bitmap哟，不然Viewpaper构造两个页面的时候，一个释放，一个设置，就会出现问题
    }

    /**
     * 轮播卡片-Item
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private WeakReference<Context> weakReference;
        // 这种方式可以解决因为Context为null的问题，内部做了处理
        private RequestManager mRequestManager;

        public CardViewHolder(Context context, @NonNull View itemView) {
            super(itemView);
            weakReference = new WeakReference<>(context);
            mRequestManager = Glide.with(weakReference.get());
            imageView = itemView.findViewById(R.id.vbi_iv);
        }

        public ImageView getImageView() {
            return imageView;
        }

        /**
         * 设置图片
         *
         * @param imgUrl
         */
        public void setImageView(String imgUrl) {
            if (null == imageView || TextUtils.isEmpty(imgUrl)) {
                throw new NullPointerException("控件被销毁或者图片路径问题!");
            }
            // Glide加载图片
            mRequestManager
                    .load(imgUrl)
                    .placeholder(R.drawable.place_holder_banner)
                    .into(imageView);
        }

        /**
         * 图片资源释放
         */
        public void recycle() {
            if (null != imageView) {
                // 这里无需处理，Glide内部已经做了相关资源释放
                // mRequestManager.clear(imageView);
            }
        }
    }
}

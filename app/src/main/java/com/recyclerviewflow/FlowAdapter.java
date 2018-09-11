package com.recyclerviewflow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by net on 2018/5/27.
 */

public class FlowAdapter extends MultiItemTypeAdapter<FlowModel> {



    public FlowAdapter(final Context context, List<FlowModel> datas) {
        super(context, datas);

        addItemViewDelegate(FlowModel.TITLE, new ItemViewDelegate<FlowModel>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.textview;
            }

            @Override
            public boolean isForViewType(FlowModel item, int position) {
                return item.type == FlowModel.TITLE;
            }

            @Override
            public void convert(ViewHolder holder, FlowModel flowModel, int position) {
                holder.setText(R.id.textView,flowModel.title);
            }
        });
        addItemViewDelegate(FlowModel.PIC, new ItemViewDelegate<FlowModel>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.pic_text;
            }

            @Override
            public boolean isForViewType(FlowModel item, int position) {
                return item.type == FlowModel.PIC;
            }

            @Override
            public void convert(ViewHolder holder, FlowModel flowModel, final int position) {
                holder.setText(R.id.name,flowModel.title);
                final ScaleImageView imageView = (ScaleImageView) holder.getView(R.id.imageview);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(mContext.getResources(),flowModel.resId,options);
                Log.i("FlowAdapter","outwidth "+options.outWidth+"  outheight "+options.outHeight);
                imageView.setInitSize(options.outWidth,options.outHeight);
                Glide.with(context).load(flowModel.resId).into(imageView);
//                Glide.with(context).load(、flowModel.resId).asBitmap().into(new BitmapImageViewTarget(imageView){
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        super.onResourceReady(resource, glideAnimation);
//                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//                        layoutParams.height = resource.getHeight();
//                        imageView.setLayoutParams(layoutParams);
//
//
//                    }
//                });
            }
        });
    }


}

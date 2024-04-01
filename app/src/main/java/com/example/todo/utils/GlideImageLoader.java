package com.example.todo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideImageLoader extends  ImageLoader{

    private final RequestManager mRequestManager;
    private final GlideCircleTransform GLIDE_CIRCLE_TRANSFROM;


    public GlideImageLoader(Context context){
        mRequestManager= Glide.with(context);
        GLIDE_CIRCLE_TRANSFROM = new GlideCircleTransform(context);
    }
    public static GlideImageLoader create(Context context){
        return new GlideImageLoader(context);
    }
    @Override
    public void load(String uri, View view) {
        load(uri,view,OPTION_CENTER_CROP);
    }

    @Override
    protected void loadWithOptions(String uri, View view, int options) {
        if(TextUtils.isEmpty(uri)||view==null)
             throw new IllegalArgumentException("uri or view is null");
        if(!(view instanceof ImageView))
            throw new IllegalArgumentException("view is not ImageView");
        ImageView imageView=(ImageView)view;
        //compatible with older version
        if(uri.startsWith("assets://")) uri=uri.substring(9);
        RequestBuilder<Drawable> builder=mRequestManager.load("file://android_asset/"+uri);
        if(hasOptions(OPTION_CENTER_CROP)){
            builder.centerCrop();
        }
        if(hasOptions(OPTION_CIRCLE_CROP)){
            builder.transform(GLIDE_CIRCLE_TRANSFROM);
        }
        builder.into(imageView);
    }

    private static class GlideCircleTransform extends BitmapTransformation{

        GlideCircleTransform(Context context){

        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool,toTransform);
        }

        public static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas=new Canvas(result);
            Paint paint=new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            return result;
        }
        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(getClass().getName().getBytes(CHARSET));
        }
    }
}

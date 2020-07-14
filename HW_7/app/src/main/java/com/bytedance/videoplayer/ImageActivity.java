package com.bytedance.videoplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.security.MessageDigest;
import java.util.Locale;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class ImageActivity extends AppCompatActivity{
    private String TAG = "Image";
    private ImageView mImageView;
    private Button btn_load;
    private final static String IMAGE_URL = "https://i.loli.net/2020/07/14/g6p5evzL7tJGkmH.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mImageView = findViewById(R.id.iv_photo);
        btn_load = findViewById(R.id.btn_load);
        btn_load.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                loadImage();
            }
        });
    }

    public class CornersTransform extends BitmapTransformation {
        private float radius;

        public CornersTransform(int radius) {
            this.radius = radius;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth,
                                   int outHeight)
        {
            return cornersImage(pool, toTransform);
        }

        private Bitmap cornersImage(BitmapPool pool, Bitmap bitmapSource) {
            Bitmap tempBitmap = null;
            try {
                if (bitmapSource == null) {
                    return null;
                }
                tempBitmap = pool.get(bitmapSource.getWidth(), bitmapSource.getHeight(),
                        Bitmap.Config.ARGB_8888);
                if (tempBitmap == null) {
                    tempBitmap = Bitmap.createBitmap(bitmapSource.getWidth(),
                            bitmapSource.getHeight(), Bitmap.Config.ARGB_8888);
                }
                Canvas canvas = new Canvas(tempBitmap);
                Paint paint = new Paint();
                paint.setShader(new BitmapShader(bitmapSource, BitmapShader.TileMode.CLAMP,
                        BitmapShader.TileMode.CLAMP));
                paint.setAntiAlias(true);

                RectF rectF = new RectF(0f, 0f, bitmapSource.getWidth(),
                        bitmapSource.getHeight());
                canvas.drawRoundRect(rectF, radius, radius, paint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tempBitmap;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }

    private void loadImage(){
/*        Log.i(TAG, "loadImage");
        RequestOptions cropOptions = new RequestOptions();
        cropOptions = cropOptions.circleCrop();*/

        int radius = 300;
        RequestOptions options = new RequestOptions().transform(new CornersTransform(radius));
        Glide.with(ImageActivity.this)
                .load(IMAGE_URL)
                //.apply(cropOptions)
                .apply(options)
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.icon_failure)
                .fallback(R.drawable.ic_launcher_background)
////                .thumbnail(Glide.with(this).load(IMAGE_URL2))
                .transition(withCrossFade(4000))
                .into(mImageView);
    }

}

package com.example.facedetection.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class FaceOverlayView extends View {

    private Paint backgroundPaint;
    private Paint borderPaint;
    private Paint transparentPaint;
    private RectF faceRect;
    private int borderColor = Color.WHITE;
    public FaceOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor("#99000000")); // ৬০% কালো

        // ২. স্বচ্ছ অংশ কাটার জন্য পেইন্ট
        transparentPaint = new Paint();
        transparentPaint.setColor(Color.TRANSPARENT);
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        // ৩. গোল বর্ডার পেইন্ট
        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(12f); // বর্ডার এর পুরুত্ব
        borderPaint.setAntiAlias(true); // স্মুথ এজ এর জন্য
    }

    // বর্ডার কালার স্মুথলি চেঞ্জ করার ফাংশন
    public void setBorderColor(int newColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofArgb(this.borderColor, newColor);
        colorAnimation.setDuration(300); // ৩০০ মিলিসেকেন্ড অ্যানিমেশন
        colorAnimation.addUpdateListener(animator -> {
            this.borderColor = (int) animator.getAnimatedValue();
            borderPaint.setColor(this.borderColor);
            invalidate(); // ভিউ রি-ড্র করো
        });
        colorAnimation.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // পুরো স্ক্রিন কালো করা
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        // মাঝখানে ওভাল শেইপ ক্যালকুলেশন (স্ক্রিনের মাঝখানে)
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radiusX = getWidth() * 0.35f; // প্রস্থের ৩৫%
        float radiusY = getHeight() * 0.25f; // উচ্চতার ২৫%

        if (faceRect == null) {
            faceRect = new RectF(centerX - radiusX, centerY - radiusY, centerX + radiusX, centerY + radiusY);
        }

        // লেয়ার সেভ করা (কাটার জন্য জরুরি)
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);

        // কালো ব্যাকগ্রাউন্ড আবার আঁকা (লেয়ারের ওপর)
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        // মাঝখানের অংশ কেটে ফেলা (যাতে ক্যামেরা দেখা যায়)
        canvas.drawOval(faceRect, transparentPaint);

        // কাটা অংশের চারপাশে বর্ডার আঁকা
        canvas.drawOval(faceRect, borderPaint);

        canvas.restoreToCount(layerId);
    }
}
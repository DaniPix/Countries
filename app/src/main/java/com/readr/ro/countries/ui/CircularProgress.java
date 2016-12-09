package com.readr.ro.countries.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.readr.ro.countries.R;
import com.readr.ro.countries.service.DownloadObserver;
import com.readr.ro.countries.service.DownloadProgressListener;

public class CircularProgress extends View implements DownloadObserver.DownloadStatusListener, DownloadProgressListener {

    private int size;

    private int thickness;

    private int progress = 0;

    private RectF circleBounds = new RectF();
    private Paint fillPaint = new Paint();
    private Paint emptyPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint signPaint = new Paint();


    public CircularProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        size = ta.getInt(R.styleable.RoundProgressBar_size, 35);
        int textSize = ta.getInt(R.styleable.RoundProgressBar_text_size, size / 4);
        int signSize = ta.getInt(R.styleable.RoundProgressBar_sign_size, textSize);

        thickness = ta.getInt(R.styleable.RoundProgressBar_overall_thickness, size / 10);
        int textThickness = ta.getInt(R.styleable.RoundProgressBar_text_thickness, thickness / 4);

        int fillColor = ta.getColor(R.styleable.RoundProgressBar_fill_color, Color.parseColor("#97CA1B"));
        int emptyColor = ta.getColor(R.styleable.RoundProgressBar_empty_color, Color.parseColor("#FFFFFF"));
        int textColor = ta.getColor(R.styleable.RoundProgressBar_text_color, Color.parseColor("#4E4E4E"));

        int fillAlpha = ta.getInt(R.styleable.RoundProgressBar_fill_alpha, 255);
        int emptyAlpha = ta.getInt(R.styleable.RoundProgressBar_empty_alpha, 255);
        int textAlpha = ta.getInt(R.styleable.RoundProgressBar_text_alpha, 255);

        DisplayMetrics mMetrics = getContext().getResources().getDisplayMetrics();
        thickness = (int) (mMetrics.density * thickness);
        size = (int) (mMetrics.density * size);
        signSize = (int) (mMetrics.density * signSize);
        textSize = (int) (mMetrics.density * textSize);

        circleBounds = new RectF(thickness, thickness, size - thickness,
                size - thickness);

        ta.recycle();

        fillPaint.setColor(fillColor);
        fillPaint.setStyle(Paint.Style.STROKE);
        fillPaint.setStrokeWidth(thickness - 2);
        fillPaint.setAlpha(fillAlpha);
        fillPaint.setAntiAlias(true);

        emptyPaint.setColor(emptyColor);
        emptyPaint.setStyle(Paint.Style.STROKE);
        emptyPaint.setStrokeWidth(thickness - 2);
        emptyPaint.setAlpha(emptyAlpha);
        emptyPaint.setAntiAlias(true);

        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setStrokeWidth(textThickness);
        textPaint.setAlpha(textAlpha);
        textPaint.setAntiAlias(true);

        signPaint.setColor(textColor);
        signPaint.setTextSize(signSize);
        signPaint.setTextAlign(Paint.Align.LEFT);
        signPaint.setStrokeWidth(textThickness);
        signPaint.setAlpha(textAlpha);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int xPos = canvas.getWidth() / 2;
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint
                .ascent()) / 2));

        canvas.drawCircle(size / 2, size / 2, size / 2 - thickness, emptyPaint);
        canvas.drawArc(circleBounds, -90, (progress * 360 / 100) % 361, false,
                fillPaint);

        String sign = "%";
        String percentage = Integer.toString(progress % 101);
        float percentMeasuredSize = textPaint.measureText(percentage);
        float signMeasuredSize = signPaint.measureText(sign);
        float textMeasuredSize = percentMeasuredSize + signMeasuredSize;
        canvas.drawText(percentage, xPos - (textMeasuredSize / 2), yPos, textPaint);
        canvas.drawText(sign, xPos - (textMeasuredSize / 2) + percentMeasuredSize, yPos - 7, signPaint);

    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int mProgress) {
        Log.d("ZUZU", "setProgress: " + mProgress);
        this.progress = mProgress;
        invalidate();
    }


    @Override
    public void onProgressChanged(int newProgress) {
        setProgress(newProgress);
    }

    @Override
    public void onComplete(boolean success) {
        setVisibility(success ? GONE : VISIBLE);
    }

    public void notifyProgressUpdate(int progress) {
        setProgress(progress);
    }


    @Override
    public void notifyDownloadCancelled() {
        setVisibility(GONE);
    }

    @Override
    public void notifyDownloadFinished() {
        setVisibility(GONE);
    }

    @Override
    public void notifyDownloadFailed() {
        setVisibility(GONE);
    }
}


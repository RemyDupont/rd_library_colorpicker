package com.dupontremy.rdslider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by Rémy on 04/11/2016.
 */

public class RDSlider extends SeekBar {

    private String TAG = "RDSlider";
    private int max = 100;
    private int currentValue = 0;
    private int thumbColor = Color.parseColor("#398b5c");
    private Drawable thumb;
    private int progressBarFinished = Color.parseColor("#398b5c");
    private int progressBarUnfinished = Color.parseColor("#9cc5ad");
    private RDSliderInterface rdSliderInterface;

    public RDSlider(Context context) {
        super(context);
    }

    public RDSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RDSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    Public Methods
     */

    public RDSlider max(int max) {
        this.max = max;
        return this;
    }

    public RDSlider rdSliderInterface(RDSliderInterface rdSliderInterface) {
        this.rdSliderInterface = rdSliderInterface;
        return this;
    }

    public RDSlider thumb(Drawable thumb) {
        this.thumb = thumb;
        return this;
    }

    public RDSlider progressColorList(int[] progressColorStateList) {
        this.thumbColor = progressColorStateList[0];
        this.progressBarFinished = progressColorStateList[1];
        this.progressBarUnfinished = progressColorStateList[2];
        return this;
    }

    public void create() {
        setMax(max);
        if (thumb != null)
            setThumb(thumb);
        getThumb().setColorFilter(thumbColor, PorterDuff.Mode.SRC_IN);

        LayerDrawable layerDrawable = (LayerDrawable) getProgressDrawable();
        layerDrawable.findDrawableByLayerId(R.id.backgroundProgressBar).setColorFilter(progressBarUnfinished, PorterDuff.Mode.SRC_IN);
        layerDrawable.findDrawableByLayerId(R.id.progressColor).setColorFilter(progressBarFinished, PorterDuff.Mode.SRC_IN);

        setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(rdSliderInterface != null)
                    rdSliderInterface.slideEvent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /*
    Getters
     */
    public int getCurrentValue() {
        return currentValue;
    }

    public int getMax() {
        return max;
    }
}

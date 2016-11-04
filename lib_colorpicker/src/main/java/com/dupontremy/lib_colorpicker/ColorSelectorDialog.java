package com.dupontremy.lib_colorpicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rdupont on 03/11/2016.
 */

public class ColorSelectorDialog extends Dialog {


    private Context context;
    private int red, green, blue;
    private int color;
    private SurfaceView oldColorSurfaceView, newColorSurfaceView;
    private TextView redText, greenText, blueText, hexValue;
    private SeekBar redSlider, greenSlider, blueSlider;
    private ColorSelectorInterface colorSelectorInterface;
    private Button ok, cancel;
    private String hexColor;
    private DialogMode mode = DialogMode.MINIMIZED;
    private boolean coloredSides = false;
    List<Drawable> thumbs;


    /*
    Constructors
     */
    public ColorSelectorDialog(Context context) {
        super(context);
        this.context = context;
        red = 0;
        green = 0;
        blue = 0;

    }

    /*
    Public Methods
     */

    public ColorSelectorDialog setColorSelectorInterface(ColorSelectorInterface colorSelectorInterface) {
        this.colorSelectorInterface = colorSelectorInterface;
        return this;
    }

    public ColorSelectorDialog setCurrentColor(int currentColor) {
        this.color = currentColor;
        red = Color.red(currentColor);
        green = Color.green(currentColor);
        blue = Color.blue(currentColor);
        return this;
    }

    public ColorSelectorDialog setThumbs(List<Drawable> thumbs) {
        this.thumbs = thumbs;
        return this;
    }

    public ColorSelectorDialog setColoredSlides(boolean coloredSides) {
        this.coloredSides = coloredSides;
        return this;
    }

    public void create(DialogMode mode) {
        this.mode = mode;
        init();
        initDesign();
        show();
    }

    /*
    Private Methods
     */
    private void init() {

        setContentView(getLayout());

        color = Color.rgb(red, green, blue);

        newColorSurfaceView = (SurfaceView) findViewById(R.id.newColor);
        redSlider = (SeekBar) findViewById(R.id.redSlider);
        greenSlider = (SeekBar) findViewById(R.id.greenSlider);
        blueSlider = (SeekBar) findViewById(R.id.blueSlider);

        if(mode == DialogMode.FULL) {
            oldColorSurfaceView = (SurfaceView) findViewById(R.id.oldColor);

            redText = (TextView) findViewById(R.id.redText);
            greenText = (TextView) findViewById(R.id.greenText);
            blueText = (TextView) findViewById(R.id.blueText);
            hexValue = (TextView) findViewById(R.id.hexValue);

            redText.setText(""+red);
            greenText.setText(""+green);
            blueText.setText(""+blue);

            hexColor = String.format("#%06X", (0xFFFFFF & color));
            hexValue.setText(hexColor);
        }

        redSlider.setMax(255);
        greenSlider.setMax(255);
        blueSlider.setMax(255);

        redSlider.setProgress(red);
        greenSlider.setProgress(green);
        blueSlider.setProgress(blue);

        redSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setRed(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        greenSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setGreen(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        blueSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setBlue(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ok = (Button) findViewById(R.id.ok);
        cancel = (Button) findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(colorSelectorInterface != null) {
                    colorSelectorInterface.colorEvent(color);
                }
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initDesign() {

        if(mode == DialogMode.FULL)
            oldColorSurfaceView.setBackgroundColor(color);

        newColorSurfaceView.setBackgroundColor(color);

        if(thumbs != null) {
            redSlider.setThumb(thumbs.get(0));
            greenSlider.setThumb(thumbs.get(1));
            blueSlider.setThumb(thumbs.get(2));
        }

        if (coloredSides) {
            redSlider.getProgressDrawable().setBounds(10,20,10,20);
            redSlider.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            redSlider.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

            greenSlider.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            greenSlider.getThumb().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

            blueSlider.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
            blueSlider.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        }
        else {
            int defaultColor = Color.parseColor("#888888");

            redSlider.getProgressDrawable().setColorFilter(defaultColor, PorterDuff.Mode.SRC_IN);
            redSlider.getThumb().setColorFilter(defaultColor, PorterDuff.Mode.SRC_IN);

            greenSlider.getProgressDrawable().setColorFilter(defaultColor, PorterDuff.Mode.SRC_IN);
            greenSlider.getThumb().setColorFilter(defaultColor, PorterDuff.Mode.SRC_IN);

            blueSlider.getProgressDrawable().setColorFilter(defaultColor, PorterDuff.Mode.SRC_IN);
            blueSlider.getThumb().setColorFilter(defaultColor, PorterDuff.Mode.SRC_IN);
        }

    }

    private void setRed(int red) {
        this.red = red;
        if (mode == DialogMode.FULL)
            redText.setText(""+red);
        setSurfaceView();
    }

    private void setGreen(int green) {
        this.green = green;
        if (mode == DialogMode.FULL)
            greenText.setText(""+green);
        setSurfaceView();
    }

    private void setBlue(int blue) {
        this.blue = blue;
        if (mode == DialogMode.FULL)
            blueText.setText(""+blue);
        setSurfaceView();
    }

    private void setSurfaceView() {
        color = Color.rgb(red, green, blue);
        newColorSurfaceView.setBackgroundColor(color);

        if (mode == DialogMode.FULL)
            hexValue.setText(String.format("#%06X", (0xFFFFFF & color)));
    }

    private int getLayout() {
        if (mode == DialogMode.MINIMIZED)
            return R.layout.minimized_color_selctor_dialog;
        else
            return R.layout.full_color_selctor_dialog;
    }
}

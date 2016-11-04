package com.dupontremy.rd_library_colorpicker;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.dupontremy.lib_colorpicker.ColorSelectorDialog;
import com.dupontremy.lib_colorpicker.ColorSelectorInterface;
import com.dupontremy.lib_colorpicker.DialogMode;
import com.dupontremy.rdslider.RDSlider;
import com.dupontremy.rdslider.RDSliderInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RDSliderInterface {

    private Context context = this;
    protected Button button, button2;
    protected TextView textView, textView2;
    protected int textColor = Color.BLACK;
    protected int textColor2 = Color.BLACK;
    protected ColorSelectorInterface colorSelectorInterface, colorSelectorInterface2;
    protected RDSlider rdSlider;
    protected TextView slideValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView = (TextView) findViewById(R.id.text);
        textView2 = (TextView) findViewById(R.id.text2);
        slideValue = (TextView) findViewById(R.id.slideValue);

        colorSelectorInterface = new ColorSelectorInterface() {
            @Override
            public void colorEvent(int color) {
                setTextViewColor(color);
            }
        };
        colorSelectorInterface2 = new ColorSelectorInterface() {
            @Override
            public void colorEvent(int color) {
                setTextViewColor2(color);
            }
        };

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ColorSelectorDialog(context)
                        .setCurrentColor(textColor)
                        .setColorSelectorInterface(colorSelectorInterface)
                        .setColoredSlides(true)
                        .create(DialogMode.MINIMIZED);
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ColorSelectorDialog(context)
                        .setCurrentColor(textColor2)
                        .setColorSelectorInterface(colorSelectorInterface2)
                        .create(DialogMode.FULL);
            }
        });

        rdSlider = (RDSlider) findViewById(R.id.slider);
        rdSlider.max(100)
                .thumb(getThumb("thumb.png"))
                .progressColorList(getColorList())
                .rdSliderInterface(this)
                .create();
    }

    private void setTextViewColor(int color) {
        this.textColor = color;
        textView.setTextColor(color);
    }

    private void setTextViewColor2(int color) {
        this.textColor2 = color;
        textView2.setTextColor(color);
    }

    protected List<Drawable> getThumbs(String filename) {

        List<Drawable> result = new ArrayList<>();

        for (int i =0; i < 3; i++) {
            Drawable drawable = null;
            InputStream inputStream = null;
            try {
                inputStream = context.getAssets().open(filename);
                drawable = Drawable.createFromStream(inputStream, null);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            result.add(drawable);
        }
        return result;
    }

    protected Drawable getThumb(String filename) {
        return getDensityDrawable(filename, DisplayMetrics.DENSITY_XHIGH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    public int[] getColorList() {
        int[] colors = new int[3];
        colors[0] = Color.parseColor("#ff0000");
        colors[1] = Color.parseColor("#ff0000");
        colors[2] = Color.parseColor("#ff6a6a");
        return colors;
    }

    public Drawable getDensityDrawable(String imageName, int density) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDensity = density;   // ex : DisplayMetrics.DENSITY_HIGH
        return Drawable.createFromResourceStream(context.getResources(), null, getInputStream(imageName), null, opts);
    }

    public InputStream getInputStream(String fileName) {
        if(fileName == null || fileName.equals(""))
            return null;

        try {
            return context.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void slideEvent(int currentValue) {
        slideValue.setText(""+currentValue);
    }
}

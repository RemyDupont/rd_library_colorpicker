package com.dupontremy.rd_library_colorpicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.dupontremy.lib_colorpicker.ColorSelectorDialog;
import com.dupontremy.lib_colorpicker.ColorSelectorInterface;
import com.dupontremy.lib_colorpicker.DialogMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    protected Button button, button2;
    protected TextView textView, textView2;
    protected int textColor = Color.BLACK;
    protected int textColor2 = Color.BLACK;
    protected ColorSelectorInterface colorSelectorInterface, colorSelectorInterface2;

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
                        .setThumbs(getThumbs("thumb.png"))
                        .create(DialogMode.FULL);
            }
        });
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
}

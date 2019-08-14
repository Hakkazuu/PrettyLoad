package com.github.hakkazuu.prettyload_sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.hakkazuu.prettyload.MakePretty;
import com.github.hakkazuu.prettyload.PrettyLoad;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @MakePretty private TextView textView1;
    @MakePretty private EditText editText2;
    @MakePretty private List<String> kek = new ArrayList<>();

    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testFragment();
    }

    private void testActivity() {
        //textView1 = findViewById(R.id.textview1);
        //editText2 = findViewById(R.id.edittext2);

        PrettyLoad.init(this, this)
                .setDrawable(R.drawable.rounded_background)
                .setDuration(1000)
                .setColors(R.color.green1, R.color.green2, R.color.green3);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoading = !isLoading;
                if(isLoading) PrettyLoad.start();
                else PrettyLoad.stop();
            }
        });
    }

    private void testFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new MainFragment(), "MainFragment");
        fragmentTransaction.commit();
    }
}

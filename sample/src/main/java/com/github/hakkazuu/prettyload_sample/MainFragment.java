package com.github.hakkazuu.prettyload_sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.hakkazuu.prettyload.MakePretty;
import com.github.hakkazuu.prettyload.PrettyLoad;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MainFragment extends Fragment {

    private static final String MAIN_TAG = "MAIN";
    private static final String SECONDARY_TAG = "SECONDARY";

    private View mView;

    @MakePretty(tag = MAIN_TAG) private TextView prettyTextView1;
    @MakePretty(tag = MAIN_TAG) private TextView prettyTextView2;
    @MakePretty(tag = SECONDARY_TAG) private TextView prettyTextView3;

    private boolean isMainLoading = false;
    private boolean isSecondaryLoading = false;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        prettyTextView1 = mView.findViewById(R.id.pretty_text_view1);
        prettyTextView2 = mView.findViewById(R.id.pretty_text_view2);
        prettyTextView3 = mView.findViewById(R.id.pretty_text_view3);

        PrettyLoad.init(getContext(), this)
                .setDrawable(R.drawable.rounded_background)
                .setDuration(1000)
                .setColors(R.color.green1, R.color.green2);

        Button button1 = mView.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMainLoading = !isMainLoading;
                if(isMainLoading) {
                    prettyTextView1.setText(null);
                    prettyTextView2.setText(null);
                    PrettyLoad.start(MAIN_TAG);
                } else {
                    prettyTextView1.setText("Hakkazuu");
                    prettyTextView2.setText("PrettyLoad");
                    PrettyLoad.stop(MAIN_TAG);
                }
            }
        });

        Button button2 = mView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSecondaryLoading = !isSecondaryLoading;
                if(isSecondaryLoading) {
                    prettyTextView3.setText(null);
                    PrettyLoad.start(SECONDARY_TAG);
                } else {
                    prettyTextView3.setText("more...");
                    PrettyLoad.stop(SECONDARY_TAG);
                }
            }
        });

        Button button3 = mView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMainLoading = !isMainLoading;
                isSecondaryLoading = !isSecondaryLoading;
                if(isMainLoading & isSecondaryLoading) {
                    prettyTextView1.setText(null);
                    prettyTextView2.setText(null);
                    prettyTextView3.setText(null);
                    PrettyLoad.start();
                } else {
                    prettyTextView1.setText("Hakkazuu");
                    prettyTextView2.setText("PrettyLoad");
                    prettyTextView3.setText("more...");
                    PrettyLoad.stop();
                }
            }
        });

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
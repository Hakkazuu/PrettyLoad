package com.github.hakkazuu.prettyload_sample;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.hakkazuu.prettyload.ViewAnimation;
import com.github.hakkazuu.prettyload.PrettyLoad;
import com.github.hakkazuu.prettyload.ViewGroupAnimation;

import java.util.Arrays;

public class MainFragment extends Fragment {

    private static final String MAIN_TAG = "MAIN";
    private static final String SECONDARY_TAG = "SECONDARY";

    private View mView;
    private PrettyLoad mPrettyLoad;

    private FrameLayout frameLayout;
    @ViewAnimation() private TextView firstTextView;
    @ViewAnimation() private TextView secondTextView;
    @ViewAnimation() private Button firstButton;
    @ViewAnimation() private Button secondButton;
    @ViewAnimation() private Button thirdButton;
    @ViewGroupAnimation(
            placeholderLayoutResId = R.layout.item_user_placeholder,
            placeholderLayoutViewIds = {
                    R.id.item_user_placeholder_name_text_view,
                    R.id.item_user_placeholder_country_text_view
            }, orientation = PrettyLoad.ORIENTATION_VERTICAL)
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        frameLayout = mView.findViewById(R.id.fragment_main_frame_layout);
        firstTextView = mView.findViewById(R.id.fragment_main_first_text_view);
        secondTextView = mView.findViewById(R.id.fragment_main_second_text_view);
        firstButton = mView.findViewById(R.id.fragment_main_first_button);
        firstButton.setOnClickListener( view -> mPrettyLoad.start());
        secondButton = mView.findViewById(R.id.fragment_main_second_button);
        thirdButton = mView.findViewById(R.id.fragment_main_third_button);

        recyclerView = mView.findViewById(R.id.fragment_main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        userAdapter = new UserAdapter(Arrays.asList(
              new User("Username 1", "Country 1"),
              new User("Username 2", "Country 2"),
              new User("Username 3", "Country 3"),
              new User("Username 4", "Country 4"),
              new User("Username 5", "Country 5"),
              new User("Username 6", "Country 6")
        ));

        mPrettyLoad = new PrettyLoad.Builder(getContext(), this)
                .setPlaceholderDrawable(R.drawable.rounded_white_background)
                .setAnimationSettings(PrettyLoad.ANIMATION_TYPE_ALL_TOGETHER, 1000)
                .setColors(R.color.gray1, R.color.gray2)
                .setOnErrorListener(error -> Log.d("PRETTY_LOG", error))
                .build();

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                mPrettyLoad.start();
            }

        }.start();

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                recyclerView.setAdapter(userAdapter);
                mPrettyLoad.stop();
            }

        }.start();

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
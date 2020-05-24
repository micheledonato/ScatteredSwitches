package com.devmicheledonato.scatteredswitches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {

    private TextView mWinTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        mWinTextView = (TextView) findViewById(R.id.win_text_view);
    }

    public void showWinText() {
        mWinTextView.setVisibility(View.VISIBLE);
    }

    public void hideWinText() {
        mWinTextView.setVisibility(View.INVISIBLE);
    }

}

package com.devmicheledonato.scatteredswitches;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;

import java.util.Random;

public class LevelFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = LevelFragment.class.getSimpleName();

    private LevelActivity mActivity;

    private SharedPreferences mSharedPreferences;
    private PreferenceScreen mPrefScreen;
    private int mPrefCount;

    private boolean[] mStatus;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Log.d(TAG, "onCreatePreferences");

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.level_one);

        mSharedPreferences = getPreferenceScreen().getSharedPreferences();
        mPrefScreen = getPreferenceScreen();
        mPrefCount = mPrefScreen.getPreferenceCount();

        mStatus = new boolean[mPrefCount];
        // reset status to false
        setStatus();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SwitchPreferenceCompat preference = (SwitchPreferenceCompat) findPreference(key);
        if (preference != null && preference.isChecked()) {
            unregisterListener();
            getStatus();
            scatter(preference.getOrder());
            setStatus();
            registerListener();
        }
        win();
    }

    private void win() {
        boolean win = true;
        for (int i = 0; i < mPrefCount; i++) {
            if (!mStatus[i]) {
                win = false;
                break;
            }
        }
        if (win) {
            mActivity.showWinText();
        }
    }

    private void setStatus() {
        for (int i = 0; i < mPrefCount; i++) {
            SwitchPreferenceCompat p = (SwitchPreferenceCompat) mPrefScreen.getPreference(i);
            p.setChecked(mStatus[i]);
        }
    }

    private void getStatus() {
        for (int i = 0; i < mPrefCount; i++) {
            SwitchPreferenceCompat p = (SwitchPreferenceCompat) mPrefScreen.getPreference(i);
            mStatus[i] = p.isChecked();
        }
    }

    private void scatter(int prefClickedOrder) {
        Random random = new Random();
        for (int i = 0; i < mPrefCount; i++) {
            if (i != prefClickedOrder) {
                mStatus[i] = random.nextBoolean();
            }
        }

    }

    private void registerListener() {
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void unregisterListener() {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        registerListener();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (LevelActivity) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        unregisterListener();
    }
}

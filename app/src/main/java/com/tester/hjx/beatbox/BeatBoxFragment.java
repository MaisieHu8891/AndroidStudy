package com.tester.hjx.beatbox;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tester.hjx.beatbox.databinding.FragmentBeatBoxBinding;

public class BeatBoxFragment extends Fragment {
    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        FragmentBeatBoxBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false);
        return binding.getRoot();
    }
}

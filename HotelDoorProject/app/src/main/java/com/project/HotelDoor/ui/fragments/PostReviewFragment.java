package com.project.HotelDoor.ui.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.viewmodel.PostReviewViewModel;
import com.project.HotelDoor.R;

import java.util.ArrayList;

public class PostReviewFragment extends Fragment {

    private PostReviewViewModel postReviewViewModel;

    //XML Files
    TextView reviewDescription;
    RatingBar ratingReviewBar;
    TextView inputHotelName;
    TextView inputHotelAddress;
    Button postReview;
    Button  cancelPostReview;

    public static PostReviewFragment newInstance() {
        return new PostReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_review_fragment, container, false);

        reviewDescription = view.findViewById(R.id.inputReviewDescription);
         ratingReviewBar = view.findViewById(R.id.ratingReviewBar);
         inputHotelName = view.findViewById(R.id.inputHotelName);
         inputHotelAddress = view.findViewById(R.id.inputHotelAddress);
         postReview = view.findViewById(R.id.postRevButton);
         cancelPostReview = view.findViewById(R.id.cancelPostRevButton);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postReviewViewModel = new ViewModelProvider(this).get(PostReviewViewModel.class);


    }

}
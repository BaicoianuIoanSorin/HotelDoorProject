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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.data.Role;
import com.project.HotelDoor.data.User;
import com.project.HotelDoor.viewmodel.PostReviewViewModel;
import com.project.HotelDoor.R;

import java.util.ArrayList;
import java.util.Random;

public class PostReviewFragment extends Fragment {

    private PostReviewViewModel postReviewViewModel;

    //XML Files
    TextView reviewDescription;
    RatingBar ratingReviewBar;
    TextView inputHotelName;
    TextView inputHotelAddress;
    Button postReview;
    Button cancelPostReview;

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

        postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postReviewViewModel.getCurrentUser().getValue() != null) {
                    postReviewViewModel.getHotel(inputHotelName.getText().toString());
                    postReviewViewModel.getHotelLiveData().observe(getViewLifecycleOwner(), hotel -> {
                        Random randomNumber = new Random();
                        int uniqueNumber = randomNumber.nextInt(100000) + 1;
                        Review review;
                        if(hotel != null)
                        {
                                review = new Review(hotel.getName(),
                                        postReviewViewModel.getCurrentUser().getValue().getUid(),
                                        reviewDescription.getText().toString(),
                                        ratingReviewBar.getRating(), 0,uniqueNumber);

                                hotel.getReviews().add(review);
                                postReviewViewModel.updateHotel(hotel);
                        }
                        else {
                            Hotel newHotel = new Hotel(inputHotelAddress.getText().toString(), inputHotelName.getText().toString(), new ArrayList<Review>());

                            review = new Review(newHotel.getName(),
                                    postReviewViewModel.getCurrentUser().getValue().getUid(),
                                    reviewDescription.getText().toString(),
                                    ratingReviewBar.getRating(), 0, uniqueNumber);
                            newHotel.getReviews().add(review);

                            postReviewViewModel.postHotel(newHotel);
                        }
                        //verify reviews post by user
                        postReviewViewModel.setUser(postReviewViewModel.getCurrentUser().getValue().getUid());

                        postReviewViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                            if(user != null)
                            {
                                int reviews = user.getReviews() + 1;
                                user.setReviews(reviews);
                                postReviewViewModel.updateUser("reviews",reviews);
                                if(user.getReviews() > 10 && user.getReviews() <= 15)
                                {
                                    postReviewViewModel.updateRole(Role.ACTIVE);
                                }
                                else if(user.getReviews() > 15 && user.getReviews() <= 20)
                                {
                                    postReviewViewModel.updateRole(Role.LEGEND);
                                }
                                else if(user.getReviews() > 20)
                                {
                                    postReviewViewModel.updateRole(Role.TRIPADVISOR);
                                }
                                else {
                                    postReviewViewModel.updateRole(Role.MEMBER);
                                }
                            }
                        });
                    });
                }
            }
        });
    }

}
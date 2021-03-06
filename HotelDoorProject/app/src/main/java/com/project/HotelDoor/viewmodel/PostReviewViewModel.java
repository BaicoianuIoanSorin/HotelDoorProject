package com.project.HotelDoor.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.ReviewRepository;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.data.Role;
import com.project.HotelDoor.data.User;
import com.project.HotelDoor.data.UserRepository;

public class PostReviewViewModel extends AndroidViewModel {

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;

    public PostReviewViewModel(Application application)
    {
        super(application);
        reviewRepository = ReviewRepository.getInstance(application);
        userRepository = UserRepository.getInstance(application);
    }

//    public Review getReview(String hotelName, String userUID)
//    {
//        return reviewRepository.getReview(hotelName,userUID);
//    }

    public void setAuthenticationMessage(boolean thread, String message)
    {
        reviewRepository.setAuthenticationMessage(thread,message);
    }
    public void updateHotel(Hotel hotel) {
        reviewRepository.updateHotel(hotel);
    }
    public void getHotel(String name)
    {
        reviewRepository.getHotel(name);
    }

    public MutableLiveData<Hotel> getHotelLiveData()
    {
        return reviewRepository.getHotelLiveData();
    }
    public void postHotel(Hotel hotel)
    {
        reviewRepository.postHotel(hotel);
    }

    public LiveData<FirebaseUser> getCurrentUser()
    {
        return  reviewRepository.getCurrentUser();
    }

    public LiveData<User> getUser()
    {
        return userRepository.getUser();
    }

    public void setUser(String uid)
    {
        userRepository.setUser(uid);
    }
    public void updateUser(String column, Object object)
    {
        userRepository.updateUser(column,object);
    }

    public void updateRole(Role role)
    {
        userRepository.updateRole(role);
    }
}
package com.project.HotelDoor.data;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.project.HotelDoor.data.DAO.ReviewDAO;
import com.project.HotelDoor.data.DAO.UserDAO;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ReviewRepository {

    private ReviewDAO reviewDAO;
    private UserDAO userDAO;
    private static ReviewRepository instance;

    public static ReviewRepository getInstance(Application app)
    {
        if(instance == null)
        {
              instance = new ReviewRepository(app);
        }
        return instance;
    }

    public ReviewRepository(Application application)
    {
        reviewDAO = ReviewDAO.getInstance(application);
        userDAO = UserDAO.getInstance(application);
    }

    public void updateHotel(Hotel hotel) {
        reviewDAO.updateHotel(hotel);
    }
    public void getHotel(String name)
    {
        reviewDAO.getHotel(name);
    }
    public void postHotel(Hotel hotel)
    {
        reviewDAO.postHotel(hotel);
    }
//    public Review getReview(String hotelName, String userUID)
//    {
//        return reviewDAO.getReview(hotelName, userUID);
//    }

    public void setAuthenticationMessage(boolean thread, String message)
    {
        userDAO.setAuthenticationMessage(thread,message);
    }

//    public ArrayList<Review> getReviews(ArrayList<Hotel> hotels) {
//        return reviewDAO.getReviews(hotels);
//    }

    public void getHotels()
    {
        reviewDAO.getHotels();
    }

    public MutableLiveData<Review> getReviewLiveData() {
        return reviewDAO.getReviewLiveData();
    }

    public void setReviewLiveData(Review review)
    {
        reviewDAO.setReviewLiveData(review);
    }

    public MutableLiveData<ArrayList<Hotel>> getHotelsLiveData()

    {
        return reviewDAO.getHotelsLiveData();
    }

    public MutableLiveData<ArrayList<Review>> getReviewsLiveData()
    {
        return reviewDAO.getReviewsLiveData();
    }

    public MutableLiveData<String> getHotelNameLiveData()
    {
        return reviewDAO.getHotelNameLiveData();
    }

    public void setHotelNameLiveData(String hotelName) {
        reviewDAO.setHotelNameLiveData(hotelName);
    }

    public MutableLiveData<Hotel> getHotelLiveData()
    {
        return reviewDAO.getHotelLiveData();
    }

//    public void loadReviewData(Context context, Application app, RecyclerView revFeed)
//    {
//        reviewDAO.loadReviewData(context,app,revFeed);
//    }

    public MutableLiveData<Boolean> getProgressBar()
    {
        return reviewDAO.getProgressBar();
    }

    public LiveData<FirebaseUser> getCurrentUser()
    {
        return userDAO.getCurrentUser();
    }

    public MutableLiveData<Boolean> getIsLikePressed() {
        return reviewDAO.getIsLikePressed();
    }

    public void setIsLikePressed(boolean statement) {
        reviewDAO.setIsLikePressed(statement);
    }

    public void removeHotel(Hotel hotel)
    {
        reviewDAO.removeHotel(hotel);
    }

    public User getUserModal(String uid)
    {
        return userDAO.getUserModal(uid);
    }
}

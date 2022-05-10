package com.project.HotelDoor.data.DAO;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.project.HotelDoor.R;
import com.project.HotelDoor.data.Hotel;
import com.project.HotelDoor.data.Review;
import com.project.HotelDoor.data.ReviewAdapter;
import com.project.HotelDoor.data.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class ReviewDAO {
    private static ReviewDAO instance;
    private final Application app;
    private boolean statement;
    private final UserDAO userDAO;
    private Hotel hotel = null;
    private Review review = null;
    private ArrayList<Review> reviewsArrayList;
    private ArrayList<Hotel> hotelArrayList;

    //Firebase Database
    private FirebaseFirestore firebaseDatabase;

    private MutableLiveData<Boolean> getProgressBar = new MutableLiveData<>(false);


    public ReviewDAO(Application app) {
        this.app = app;
        firebaseDatabase = FirebaseFirestore.getInstance();
        userDAO = UserDAO.getInstance(app);
        reviewsArrayList = new ArrayList<>();
        hotelArrayList = new ArrayList<>();
    }

    public static ReviewDAO getInstance(Application application) {
        if (instance == null) {
            instance = new ReviewDAO(application);
        }
        return instance;
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return getProgressBar;
    }

    public void postHotel(Hotel hotel) {
        Map<String, Object> hotelMap = new HashMap<>();
        hotelMap.put("name", hotel.getName());
        hotelMap.put("address", hotel.getAddress());
        hotelMap.put("reviews", FieldValue.arrayUnion(hotel.getReviews().toArray()));

        firebaseDatabase.collection("hotels").document(hotel.getName())
                .set(hotelMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Hotel inserted successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing the user", e);
                    }
                });
    }

    public Hotel getHotel(String name) {
        DocumentReference docRef = firebaseDatabase.collection("hotels").document(name);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    hotel = task.getResult().toObject(Hotel.class);
                } else {
                    hotel = null;
                    Log.e(TAG, task.getException().getMessage());
                }
            }
        });
        return hotel;
    }

//    //prob not using it
//    public Review getReview(String hotelName, String userUID) {
//        ArrayList<Review> reviews = getReviews();
//        for(Review reviewItem : reviews)
//        {
//            if(reviewItem.getHotelName().equals(hotelName) && reviewItem.getUserUID().equals(userUID))
//            {
//                return reviewItem;
//            }
//        }
//        return null;
//    }

    public void updateHotel(Hotel hotel) {
        DocumentReference hotelDocument = firebaseDatabase.collection("hotels").document(hotel.getName());
        hotelDocument
                .update(
                        "reviews", FieldValue.arrayUnion(hotel.getReviews().toArray())
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Document Hotel with " + hotelDocument.getId() + " has been updated");
                        userDAO.setAuthenticationMessage(true, "Information for Hotel has been updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating user document " + hotelDocument.getId(), e);
                        userDAO.setAuthenticationMessage(true, "Information couldn't be updated.");
                    }
                });
    }

    public ArrayList<Hotel> getHotels()
    {
        hotelArrayList = new ArrayList<>();
        firebaseDatabase.collection("hotels")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Hotel hotel = document.toObject(Hotel.class);
                                hotelArrayList.add(hotel);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return hotelArrayList;
    }

//    public ArrayList<Review> getReviews(ArrayList<Hotel> hotelss) {
//        ArrayList<Hotel> hotels = getHotels();
//        ArrayList<Review> reviews = new ArrayList<>();
//        for(Hotel hotel : hotels)
//        {
//            reviews.addAll(hotel.getReviews());
//        }
//        return reviews;
//    }
}

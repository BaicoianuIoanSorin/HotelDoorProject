package com.project.HotelDoor.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.project.HotelDoor.data.UserRepository;

public class RegisterViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public RegisterViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public void registerAccount(Activity activity, String email, String password)
    {
        userRepository.registerAccount(activity,email,password);
    }

    public void setSignInPressed(Boolean isSignInPressed) {
        userRepository.setSignInPressed(isSignInPressed);
    }

    public void forgotPassword(View view){
        userRepository.forgotPassword(view);
    }
}

package com.example.drinkeasy.db;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

import static android.content.ContentValues.TAG;

public class UserAccess {

    private User userData = new User();
    public  UserAccess(){

    }

    public User getActivateUserData() {
        //TODO 오류 해결해야함..
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User userdt = documentSnapshot.toObject(User.class);
                updateUser(userdt);
            }
        });

        return userData;
    }

    public void updateUser(User user) {
        userData = user;
    }

}

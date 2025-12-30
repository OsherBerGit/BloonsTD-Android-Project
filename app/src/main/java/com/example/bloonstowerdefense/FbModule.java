package com.example.bloonstowerdefense;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// google explanations
// https://firebase.google.com/docs/database/android/lists-of-data#java_1


public class FbModule {
    FirebaseDatabase database;
    Context context;
    ArrayList<TowertoFb> towers;
    Query Quetow, Queco, Quelif, Querou;

    public FbModule(Context context, ArrayList<TowertoFb> TowertoFb) {
        //database = FirebaseDatabase.getInstance("https://fbrecordst-default-rtdb.firebaseio.com");
        database = FirebaseDatabase.getInstance();
        this.context = context;
        this.towers = TowertoFb;

        // read the records from the Firebase and order them by the record from highest to lowest
        Quetow = database.getReference("towers");
        Queco = database.getReference("coins");
        Quelif = database.getReference("life");
        Querou = database.getReference("round");

//        Quetow.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                towers.clear();  // clear the array list
//                if (snapshot.getValue() != null) {
//                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                        TowertoFb currentTower = userSnapshot.getValue(TowertoFb.class);
//                        towers.add(0, currentTower);
//                    }
//                    ((GameActivity)context).setNewTowers(towers);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });

//        Queco.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.getValue() != null) {
//                    int lastCoins = snapshot.getValue(Integer.class);
//                    ((GameActivity) context).setNewCoins(lastCoins);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });

//        Quelif.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.getValue() != null) {
//                    int lastLife = snapshot.getValue(Integer.class);
//                    ((GameActivity) context).setNewLife(lastLife);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });

//        Querou.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.getValue() != null) {
//                    int lastRound = snapshot.getValue(Integer.class);
//                    ((GameActivity) context).setNewRound(lastRound);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
    }

    public void clear() {
        DatabaseReference towers = database.getReference("towers");
        towers.setValue(null);
        DatabaseReference coins = database.getReference("coins");
        coins.setValue(null);
        DatabaseReference life = database.getReference("life");
        life.setValue(null);
        DatabaseReference round = database.getReference("round");
        round.setValue(null);
    }

    public void setTower(float x, float y, String t) {
        // Write a message to the database
        DatabaseReference myRef = database.getReference("towers").push(); // push adds new node with unique value
        //DatabaseReference myRef = database.getReference("records/" + FirebaseAuth.getInstance().getUid());
        TowertoFb Tfb = new TowertoFb(x, y, t);
        myRef.setValue(Tfb);
    }

    public void setCoins(int coins) {
        // Write a message to the database
        DatabaseReference myRef = database.getReference("coins"); // push adds new node with unique value
        //DatabaseReference myRef = database.getReference("records/" + FirebaseAuth.getInstance().getUid());
        myRef.setValue(coins);
    }

    public void setLife(int life) {
        // Write a message to the database
        DatabaseReference myRef = database.getReference("life"); // push adds new node with unique value
        //DatabaseReference myRef = database.getReference("records/" + FirebaseAuth.getInstance().getUid());
        myRef.setValue(life);
    }

    public void setRound(int round) {
        // Write a message to the database
        DatabaseReference myRef = database.getReference("round"); // push adds new node with unique value
        //DatabaseReference myRef = database.getReference("records/" + FirebaseAuth.getInstance().getUid());
        myRef.setValue(round);
    }

    public void getTowers(){
        Quetow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                towers.clear();  // clear the array list
                if (snapshot.getValue() != null) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        TowertoFb currentTower = userSnapshot.getValue(TowertoFb.class);
                        towers.add(0, currentTower);
                    }
                    ((GameActivity)context).setNewTowers(towers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getCoins(){
        Queco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    int value = snapshot.getValue(Integer.class);
                    ((GameActivity) context).setNewCoins(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getLife() {
        Quelif.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    int value = snapshot.getValue(Integer.class);
                    ((GameActivity) context).setNewLife(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getRound() {
        Querou.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    int value = snapshot.getValue(Integer.class);
                    ((GameActivity)context).setNewRound(value);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
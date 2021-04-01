package com.skibnev.foodcontrolapp.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.skibnev.foodcontrolapp.model.Product;
import com.skibnev.foodcontrolapp.util.CallBack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class FirebaseRepository {

    private DatabaseReference databaseReference;
    private String dbURL = "https://foodcontrolapp-default-rtdb.firebaseio.com/";
    private String tableName = "products";
    private List<Product> products;

    public FirebaseRepository() {
        Log.i("FirebaseDBHelper", "In constructor of FirebaseDBHelper class");
        FirebaseDatabase database = FirebaseDatabase.getInstance(dbURL);
        database.setPersistenceEnabled(true);
        databaseReference = database.getReference();
        databaseReference.keepSynced(true);
    }

    public List<Product> findAll() {
        Log.i("FirebaseDBHelper", "In method of findAll");
        databaseReference.child(tableName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("findAll()", "Task is successfull");
                GenericTypeIndicator<List<Product>> genericTypeIndicator = new GenericTypeIndicator<List<Product>>() {
                };
                products = snapshot.getValue(genericTypeIndicator);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDBHelper", "Error: " + error.getDetails());
            }
        });
        return products;
    }

    public void writeToDB(String id, Product response) {
        databaseReference.child(tableName).child(id).setValue(response);
    }

    public void getByName(String name, CallBack callBack) {
        databaseReference.child(tableName).orderByChild("name").equalTo(name).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
                callBack.onError(task.getException());
            } else {
                List<Product> productsList = null;
                GenericTypeIndicator<Map<String, Product>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Product>>() {
                };
                Map<String, Product> products = Objects.requireNonNull(task.getResult()).getValue(genericTypeIndicator);
                if (products != null) {
                    productsList = new ArrayList<>(products.values());
                }
                if (productsList != null && !productsList.isEmpty()) {
                    callBack.onSuccess(productsList.get(0));
                } else {
                    callBack.onSuccess(null);
                }
            }
        });
    }

    public void searchByNamePrefix(String prefix, CallBack callBack) {
        databaseReference.child(tableName).orderByChild("name").startAt(prefix).endAt(prefix + "\uf8ff").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
                callBack.onError(task.getException());
            } else {
                Set<Product> productsSet = null;
                if (task.getResult().getValue() instanceof ArrayList) {
                    GenericTypeIndicator<List<Product>> genericTypeIndicator = new GenericTypeIndicator<List<Product>>() {
                    };
                    List<Product> products = Objects.requireNonNull(task.getResult()).getValue(genericTypeIndicator);
                    if (products != null) {
                        productsSet = new HashSet<>(products);
                    }
                } else {
                    GenericTypeIndicator<Map<String, Product>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Product>>() {
                    };
                    Map<String, Product> products = Objects.requireNonNull(task.getResult()).getValue(genericTypeIndicator);
                    if (products != null) {
                        productsSet = new HashSet<>(products.values());
                    }
                }
                if (productsSet != null && !productsSet.isEmpty()) {
                    callBack.onSuccess(productsSet);
                } else {
                    callBack.onSuccess(null);
                }
            }
        });
    }
}

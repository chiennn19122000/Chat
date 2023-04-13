package com.example.chatgptfree.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chatgptfree.R
import com.example.chatgptfree.utils.BASE_URL_REALTIME_DB
import com.example.chatgptfree.utils.PreferenceHelper
import com.example.chatgptfree.utils.TOKEN
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.reflect.jvm.internal.impl.load.java.Constant


class MainActivity : AppCompatActivity() {

    private var preferenceHelper: PreferenceHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceHelper = PreferenceHelper(this)
        supportActionBar?.hide()
        val database = FirebaseDatabase.getInstance(BASE_URL_REALTIME_DB)
        val myRef = database.getReference(TOKEN)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value: String = dataSnapshot.getValue(String::class.java).toString()
                preferenceHelper?.token = value
                Log.d("TAG", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

}
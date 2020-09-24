package com.everton.evgram.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseConfiguration {
    private lateinit var referenciaFirebase: DatabaseReference
    private lateinit var referenciaAuth: FirebaseAuth

    fun getFirebaseAuth(): FirebaseAuth {
        referenciaAuth = FirebaseAuth.getInstance()
        return referenciaAuth
    }

    fun getFirebase(): DatabaseReference {
        referenciaFirebase = FirebaseDatabase.getInstance().reference
        return referenciaFirebase
    }

}
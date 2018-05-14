package com.example.karim.ahawy

import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DatabaseReference

class FirebaseShit {

    fun loadDatabase(firebaseData: DatabaseReference){


        val availableAhwas: List <Ahawy> = mutableListOf(
                Ahawy("Sohba", "30.0825542", "31.3317824"),
                Ahawy("Bad Days", "30.0820090", "31.3382710"),
                Ahawy("Tamr Henna", "30.0520940", "31.3360530"),
                Ahawy("Arbilla", "30.0598480", "31.3565240")
        )

        availableAhwas.forEach {
            val key = firebaseData.child("ahwas").push().key
            it.uuid=key
            firebaseData.child("ahwas").child(key).setValue(it)

        }
    }
}
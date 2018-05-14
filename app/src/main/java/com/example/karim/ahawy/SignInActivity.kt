package com.example.karim.ahawy

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.firebase.ui.auth.AuthUI

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.FirebaseOptions
import kotlinx.android.synthetic.main.activity_sign_in.*


/**
 * Created by karim on 5/12/2018.
 */
class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private final val TAG = "SignInActivity"
    var mAuth: FirebaseAuth? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)



        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { }

        val signinBtn = findViewById<Button>(R.id.btn_email_sign_in)
        val crtAccountBtn = findViewById<Button>(R.id.btn_email_create_account)
        val signoutBtn = findViewById<Button>(R.id.btn_sign_out)
        val vrfyEmailBtn = findViewById<Button>(R.id.btn_verify_email)
        signinBtn.setOnClickListener{ onClick(signinBtn)}
        crtAccountBtn.setOnClickListener{onClick(crtAccountBtn)}
        signoutBtn.setOnClickListener{onClick(signoutBtn)}
        vrfyEmailBtn.setOnClickListener{onClick(vrfyEmailBtn)}
    }

    override fun onClick(v: View?) {

        TODO()

        when(v?.id){
            R.id.btn_email_sign_in ->{

            }
        }
    }


    override fun onStart() {
        super.onStart()
        val currentUser=mAuth!!.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }

    }
    private fun signIn(email: String, password: String) {
        Log.e(TAG, "signIn" +email)

        if(!validateForm(email, password)) {
            return
        }
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    task ->
                    if(task.isSuccessful){
                        Log.e(TAG, "signIn: Success!")
                        val user = mAuth!!.getCurrentUser()
                        updateUI(user)
                    }else{
                        Log.e(TAG, "signIn: failed", task.exception)
                        Toast.makeText(applicationContext, "Authentication failed",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }

    }

    private fun signUp(email:String, password:String) {
        Log.e(TAG, "signUp:"+email)
        if(!validateForm(email, password))
            return

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful)
                }
    }

    private fun signOut() {
        mAuth!!.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification(){
        findViewById<View>(R.id.btn_verify_email).isEnabled=false

        val user =mAuth!!.currentUser
        user!!.sendEmailVerification().addOnCompleteListener(this){
            task -> findViewById<View>(R.id.btn_verify_email).isEnabled=true
                if(task.isSuccessful){
                    Toast.makeText(applicationContext, "Email sent to:" + user.email!!,
                            Toast.LENGTH_SHORT).show()}
            else{
                    Log.e(TAG, "verificatioin by email failed!", task.exception)
                    Toast.makeText(applicationContext, "Failed to send email", Toast.LENGTH_SHORT).show()
                }
                }
        }


    private fun validateForm(email: String, password: String): Boolean {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(applicationContext, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun updateUI(user: FirebaseUser?){
        TODO()
    }

}
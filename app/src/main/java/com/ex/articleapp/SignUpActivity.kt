package com.ex.articleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ex.articleapp.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class SignUpActivity : AppCompatActivity() {

    private val TAG = "SignUpActivity"

    //ViewBinding
    private lateinit var signUpBinding: ActivitySignUpBinding

    //FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = signUpBinding.root
        setContentView(view)

       FirebaseApp.initializeApp(applicationContext)

        auth = Firebase.auth

        signUpBinding.buttonNext.setOnClickListener {
            buttonClick()
        }

        signUpBinding.loginText.setOnClickListener {
            startActivity(Intent(this,LogInActivity::class.java))
        }
    }

    //Firebase Create New User
    private fun buttonClick() {
        if(signUpBinding.emailSignUp.text.toString() == ""){
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show()
        }else if(signUpBinding.emailPasswordSignUp.text.toString() == ""){
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show()
        }else if(signUpBinding.emailSignUp.text.toString() == "" || signUpBinding.emailPasswordSignUp.text.toString() == ""){
            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show()
        }else{
            val email : String = signUpBinding.emailSignUp.text.toString()
            val password : String = signUpBinding.emailPasswordSignUp.text.toString()

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    Log.d(TAG, "buttonClick: SuccessFully Created")
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(Intent(this,InformationActivity::class.java))
                }else{
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed : Something Went Wrong", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
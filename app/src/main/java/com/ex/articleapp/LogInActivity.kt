package com.ex.articleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.ex.articleapp.databinding.ActivityLogInBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var logInBinding: ActivityLogInBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInBinding = ActivityLogInBinding.inflate(layoutInflater)
        val view = logInBinding.root
        setContentView(view)

        FirebaseApp.initializeApp(this)

        firebaseAuth = Firebase.auth

        logInBinding.buttonLogin.setOnClickListener {
            login()
        }

        logInBinding.createOneText.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }

    private fun login() {
        if(logInBinding.emailLogIn.text.toString() == ""){
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show()
        }else if(logInBinding.emailPasswordLogin.text.toString() == ""){
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show()
        }else if(logInBinding.emailLogIn.text.toString() == "" || logInBinding.emailPasswordLogin.text.toString() == ""){
            Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show()
        }else{
            val email : String = logInBinding.emailLogIn.text.toString()
            val password : String = logInBinding.emailPasswordLogin.text.toString()

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(Intent(this,MainActivity::class.java))
                }else{
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
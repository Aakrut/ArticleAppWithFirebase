package com.ex.articleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ex.articleapp.databinding.ActivityInformationBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InformationActivity : AppCompatActivity() {

    private val TAG = "InformationActivity"

    //viewBinding
    private lateinit var informationBinding: ActivityInformationBinding

    //FireBaseAuth
    private lateinit var auth: FirebaseAuth

    //FirebaseFireStore
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        informationBinding = ActivityInformationBinding.inflate(layoutInflater)
        val view = informationBinding.root
        setContentView(view)

        FirebaseApp.initializeApp(applicationContext)

        auth = Firebase.auth

        db = Firebase.firestore



        informationBinding.buttonSignup.setOnClickListener {
            addInformation()
        }


    }

    //Firebase Adding Informations to Database

    private fun addInformation() {
        if(informationBinding.usernameSignUp.text.toString() == ""){
            Toast.makeText(this, "Please Enter Your Username", Toast.LENGTH_SHORT).show()
        }else if(informationBinding.fullnameSignUp.text.toString() == ""){
            Toast.makeText(this, "Please Enter the Full Name", Toast.LENGTH_SHORT).show()
        }else if(informationBinding.usernameSignUp.text.toString() == "" || informationBinding.fullnameSignUp.text.toString() == ""){
            Toast.makeText(this, "Please Enter Username and FullName", Toast.LENGTH_SHORT).show()
        }else{

            val username : String = informationBinding.usernameSignUp.text.toString()
            val fullName : String = informationBinding.fullnameSignUp.text.toString()

            //current userId
            val uid : String = auth.currentUser?.uid.toString()

                val hashmap = hashMapOf(
                    "username" to username,
                    "fullName" to fullName,
                    "uid" to uid,
                    "bio" to "I am Using Article App",
                    "profile_photo" to "https://firebasestorage.googleapis.com/v0/b/messenger-clone-7c509.appspot.com/o/image.png?alt=media&token=7e022f6b-dd1e-4246-a3dc-9ad09f26137f"
                )

            db.collection("Users").document(uid).set(hashmap).addOnSuccessListener {

                Toast.makeText(this, "SuccessFully Created Account", Toast.LENGTH_SHORT).show()

                val db = Firebase.firestore
                val ref = db.collection("Follow").document(uid)

                val hashmap = hashMapOf(
                        uid to true
                )

                ref.collection("Following").document(uid).set(hashmap).addOnCompleteListener {
                    task ->
                    if(task.isSuccessful){
                        Log.d(TAG, "addInformation: SuccessFully Following")
                    }
                }
                startActivity(Intent(this,MainActivity::class.java))

            }.addOnFailureListener {
                Toast.makeText(this, "on Fail : Data not saved", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
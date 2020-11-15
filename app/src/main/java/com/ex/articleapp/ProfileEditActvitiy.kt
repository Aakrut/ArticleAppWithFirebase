package com.ex.articleapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ex.articleapp.data.User
import com.ex.articleapp.databinding.ActivityProfileEditActvitiyBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso

class ProfileEditActvitiy : AppCompatActivity() {

    private val TAG = "ProfileEditActivity"

    //ViewBinding
    private lateinit var profileEditActvitiyBinding: ActivityProfileEditActvitiyBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    //Firebase Storage
    private var storage_e : StorageReference?= null

    private var image_URI: Uri ?= null

    private val RequestCode = 438
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileEditActvitiyBinding = ActivityProfileEditActvitiyBinding.inflate(layoutInflater)
        val view = profileEditActvitiyBinding.root
        setContentView(view)
        
        firebaseAuth = Firebase.auth

        storage_e = FirebaseStorage.getInstance().reference.child("User Images")

        //Database initialize
        val db = Firebase.firestore

        //FireBase FireStore Reference
        val ref = db.collection("Users").document(firebaseAuth.currentUser!!.uid)
        
        ref.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val user : User ?= snapshot.toObject(User::class.java)
                profileEditActvitiyBinding.profileEditTextUsername.setText(user!!.username).toString()
                profileEditActvitiyBinding.profileEditTextFullName.setText(user.fullName).toString()
                profileEditActvitiyBinding.profileEditTextBio.setText(user.bio).toString()


                Picasso.get().load(user!!.profile_photo).into(profileEditActvitiyBinding.profileEditImageViewCirlce)
                Log.d(TAG, "Current data: ${snapshot.data}")

            } else {
                Log.d(TAG, "Current data: null")
            } }
        
        profileEditActvitiyBinding.buttonUpdateProfile.setOnClickListener { 
            updateProfile()
        }

        profileEditActvitiyBinding.profileEditImageViewCirlce.setOnClickListener {
          pickImage()
        }

    }

    //Firebase Update

    private fun updateProfile() {
        if(profileEditActvitiyBinding.profileEditTextUsername.text.toString() == "" || profileEditActvitiyBinding.profileEditTextFullName.text.toString() == ""
            ||profileEditActvitiyBinding.profileEditTextBio.text.toString() == ""){
            Toast.makeText(this, "Please Fill Out All The Fields", Toast.LENGTH_SHORT).show()
        }else if(profileEditActvitiyBinding.profileEditTextUsername.text.toString() == ""){
            Toast.makeText(this, "Please Enter Your Username", Toast.LENGTH_SHORT).show()
        }else if(profileEditActvitiyBinding.profileEditTextFullName.text.toString() == ""){
            Toast.makeText(this, "Please Enter Your Fullname", Toast.LENGTH_SHORT).show()
        }else if(profileEditActvitiyBinding.profileEditTextBio.text.toString() == ""){
            Toast.makeText(this, "Please Enter Your Bio", Toast.LENGTH_SHORT).show()
        }else{
            
            val hashmap = hashMapOf(
                "username" to profileEditActvitiyBinding.profileEditTextUsername.text.toString(),
                "fullName" to profileEditActvitiyBinding.profileEditTextUsername.text.toString(),
                "bio" to profileEditActvitiyBinding.profileEditTextBio.text.toString(),
            )
            
            val db = Firebase.firestore
            val ref = db.collection("Users").document(firebaseAuth.currentUser!!.uid).update(hashmap as Map<String, Any>)
            
            ref.addOnSuccessListener {
                Toast.makeText(this, "profile Updated SuccessFully ", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "updateProfile: $hashmap")
            }.addOnFailureListener {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, RequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RequestCode && resultCode == Activity.RESULT_OK && data!!.data != null){
            image_URI = data.data
            Toast.makeText(this, "Please Wait Image Uploading...", Toast.LENGTH_SHORT).show()
            uploadImagetoFirestore()
        }
    }


    private fun uploadImagetoFirestore() {
        if(image_URI != null){
            val fileRef = storage_e!!.child(System.currentTimeMillis().toString() + ".jpg")

            var uploadTask : StorageTask<*>
            uploadTask = fileRef.putFile(image_URI!!)

            uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{
                    task ->
                if(!task.isSuccessful){
                    task.exception?.let {
                        throw it
                    }

                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    val downloadUri = task.result
                    val url = downloadUri.toString()

                    val firebase_fire = Firebase.firestore
                    firebase_fire.collection("Users").document(firebaseAuth.currentUser!!.uid).update("profile_photo",url)
                }
            }
        }
    }

}



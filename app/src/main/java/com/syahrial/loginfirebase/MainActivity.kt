package com.syahrial.loginfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.syahrial.loginfirebase.databinding.ActivityDaftarBinding
import com.syahrial.loginfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityMainBinding
    //Actionbar
    private lateinit var actionBar: ActionBar
    //firebaseauth
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //configure action bar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //init firebaseauth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser();

        //handle click, logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        //check user is login or logout
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            //user not null, user is logged in
            val email = firebaseUser.email
            //set to text view
            binding.EmailTV.text =email
        }
        else{
            //user is null, or user is not logged in
            startActivity(Intent(this, MasukActivity::class.java))
            finish()
        }
    }
}
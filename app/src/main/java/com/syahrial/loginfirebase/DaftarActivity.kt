package com.syahrial.loginfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.syahrial.loginfirebase.databinding.ActivityDaftarBinding

class DaftarActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityDaftarBinding
    //Actionbar
    private lateinit var actionBar: ActionBar
    //proggress bar
    private lateinit var progressDialog: ProgressDialog
    //firebaseauth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure action bar
        actionBar = supportActionBar!!
        actionBar.title = "Register"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("Creating Account in...")
        progressDialog.setCanceledOnTouchOutside(false)
        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        //handle click begin sign up
        binding.btSignup.setOnClickListener {
            //validate data
            validateData();
        }
    }

    private fun validateData() {
        //get data
        email = binding.emailET.text.toString().trim()
        password = binding.passwordET.text.toString().trim()
        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailET.error ="Invalid Email"
        }else if (TextUtils.isEmpty(password)){
            ///password isnt entered
            binding.passwordET.error = "please enter password"
        }else if (password.length<6){
            //password length is less than 6
            binding.passwordET.error = "password at least 6 characters long"
        }else{
            //datainvalid,continue signup
            firebaseSignup();
        }
    }

    private fun firebaseSignup() {
        //show progress
        progressDialog.show()
        //create account
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //sign up success
                progressDialog.dismiss()
                //validate data
                val firebaseUser =firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created woth email $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {e->
                //sign up failed
                progressDialog.dismiss()
                Toast.makeText(this, "Sign up Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()//goback to activity when back of action bar clicked
        return super.onNavigateUp()
    }
}
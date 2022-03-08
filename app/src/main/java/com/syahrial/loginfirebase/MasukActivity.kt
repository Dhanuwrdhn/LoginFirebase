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
import com.syahrial.loginfirebase.databinding.ActivityMasukBinding
import java.util.regex.Pattern

class MasukActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding:ActivityMasukBinding
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
        binding = ActivityMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionBar
        actionBar = supportActionBar!!
        actionBar.title="login"
        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)
        //handle klik, open regist
        binding.noaccountTV.setOnClickListener {
            startActivity(Intent(this,DaftarActivity::class.java))
        }
        //buttonlogin
        binding.btLog.setOnClickListener {
            validateData();
        }
        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser();
    }

    private fun validateData() {
        //get data
        email = binding.emailET.text.toString().trim()
        password=binding.passwordET.text.toString().trim()
        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid form
            binding.emailET.error = "Salah Email"
        }else if (TextUtils.isEmpty(password)){
            //invalid password
            binding.passwordET.error = "Please masukkan Password"
        }else{
            //data is validate , begin login
            firebaselogin();
        }
    }

    private fun firebaselogin() {
        //show progrres
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //login succses
                progressDialog.dismiss()
                //getuserinfo
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "logged in as $email", Toast.LENGTH_SHORT).show()
                //open mainactivity
                startActivity(Intent(this,MainActivity::class.java))
                finish()

            }
            .addOnFailureListener {e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user already logged in into profile activity
        //get current user
        var firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            //user is already logged in
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}
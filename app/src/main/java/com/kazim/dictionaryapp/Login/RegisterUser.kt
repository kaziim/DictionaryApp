package com.kazim.dictionaryapp.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kazim.dictionaryapp.R
import com.kazim.dictionaryapp.User

class RegisterUser : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null

    private lateinit var tvNameSurname:TextView
    private lateinit var tvAge:TextView
    private lateinit var tvEmail:TextView
    private lateinit var tvPassword:TextView
    private lateinit var progressBar:ProgressBar
    private lateinit var btRegister:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        mAuth = FirebaseAuth.getInstance();

        btRegister = findViewById(R.id.btRegister)
        btRegister.setOnClickListener(this)

        tvNameSurname = findViewById(R.id.tvNameSurname)
        tvAge = findViewById(R.id.tvAge)
        tvEmail = findViewById(R.id.tvEmail)
        tvPassword = findViewById(R.id.tvPassword)

        progressBar = findViewById(R.id.progressBar)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btRegister -> registerUser()
        }
    }

    fun registerUser(){
        var email = tvEmail.text.toString().trim()
        var password = tvPassword.text.toString().trim();
        var fullName = tvNameSurname.text.toString().trim();
        var age = tvAge.text.toString().trim();

        if(fullName.isEmpty()){
            tvNameSurname.error = "Full name is required!"
            tvNameSurname.requestFocus()
            return
        }
        if(age.isEmpty()){
            tvAge.error = "Age is required!"
            tvAge.requestFocus()
            return
        }
        if(email.isEmpty()){
            tvEmail.error = "Email is required!"
            tvEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            tvEmail.error = "Please provide valid email!"
            tvEmail.requestFocus()
            return
        }
        if(password.isEmpty()){
            tvPassword.error = "Password is required!"
            tvPassword.requestFocus()
            return
        }
        if(password.length <6){
            tvPassword.error = "Min password length should be 6 characters!"
            tvPassword.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE
        mAuth?.createUserWithEmailAndPassword(email,password)?.addOnCompleteListener(object: OnCompleteListener<AuthResult>{
            override fun onComplete(task: Task<AuthResult>) {

                if(task.isSuccessful){
                    var user = User(fullName,age, email)

                    FirebaseAuth.getInstance().currentUser?.let {
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(it.uid)
                            .setValue(user).addOnCompleteListener(object: OnCompleteListener<Void>{
                                override fun onComplete(task: Task<Void>) {
                                    if (task.isSuccessful){
                                        Toast.makeText(this@RegisterUser,"User has been registered succesfully",Toast.LENGTH_LONG).show()
                                        progressBar.visibility = View.GONE
                                    }else{
                                        Toast.makeText(this@RegisterUser,"Failed to register, try again!",Toast.LENGTH_LONG).show()
                                        progressBar.visibility = View.GONE
                                    }

                                }
                            })
                    }
                }else{
                    Toast.makeText(this@RegisterUser,"Failed to register, try again!",Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }
        })


    }
}
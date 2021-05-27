package com.kazim.dictionaryapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class LoginScreen : AppCompatActivity(),View.OnClickListener {

    lateinit var register:TextView
    lateinit var tvEmail:TextView
    lateinit var tvPassword:TextView
    lateinit var loginButton: Button
    lateinit var progressBar: ProgressBar

    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        register = findViewById(R.id.tvRegister)
        register.setOnClickListener(this)

        loginButton = findViewById(R.id.btLogin)
        loginButton.setOnClickListener(this)

        tvEmail = findViewById(R.id.tvEmail)
        tvPassword = findViewById(R.id.tvPassword)

        progressBar = findViewById(R.id.progressBar)

        mAuth = FirebaseAuth.getInstance()

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvRegister -> startActivity(Intent(this, RegisterUser::class.java))
            R.id.btLogin -> userLogin()

        }
    }

    private fun userLogin() {
        var email = tvEmail.text.toString().trim()
        var password = tvPassword.text.toString().trim()

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

        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(object: OnCompleteListener<AuthResult>{
            override fun onComplete(task: Task<AuthResult>) {
                if (task.isSuccessful){
                    //TODO redirect to user profile
                    startActivity(Intent(this@LoginScreen, AddWord::class.java))
                    progressBar.visibility = View.GONE
                }else{
                    Toast.makeText(this@LoginScreen,"Failed to log in, please check your credentials!",Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }
        })
    }
}
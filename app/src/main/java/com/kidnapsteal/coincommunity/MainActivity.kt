package com.kidnapsteal.coincommunity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kidnapsteal.coincommunity.presentation.friend.FriendFragment
import com.kidnapsteal.coincommunity.util.Ln
import dagger.android.support.DaggerAppCompatActivity
import java.util.*


class MainActivity : DaggerAppCompatActivity() {

    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()

        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as FriendFragment?

            if (fragment == null) {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, FriendFragment(), FriendFragment::class.java.simpleName)
                        .commit()

                val firestore = FirebaseFirestore.getInstance()
                val db = firestore.collection("user").document("kidnapsteal1").collection("chats")
                        .document("anjonjeng").collection("message").document("02f5e108-1847-4a9e-9447-fdb25077cd35")
                db.delete().addOnCompleteListener {
                    if (it.isSuccessful) {
                        Ln.d("SuccessDelete", "Deleted")
                    } else {
                        Ln.d("SuccessDelete", "failed")
                    }
                }
            }

        } else {
            val providers = Arrays.asList(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.PhoneBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build())
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                user?.let {
                    it.providerData.map {
                        Ln.d("displayName : ${it.displayName}\n" +
                                "email : ${it.email}\n" +
                                "emailVerified : ${it.isEmailVerified}\n" +
                                "phoneNumber : ${it.phoneNumber}\n" +
                                "photoUrl : ${it.photoUrl}\n" +
                                "userID : ${it.uid}\n" +
                                "providers : ${it.providerId}")
                    }
                }
            } else {
                when (response == null) {
                    false -> {
                        Ln.d(response?.error!!)
                    }
                }
            }
        }
    }

}

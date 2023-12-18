package com.example.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.databinding.SearchRvBinding
import com.example.instagramclone.utils.FOLLOW
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchAdapter(var context: Context, var usersList: ArrayList<User>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: SearchRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = SearchRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isFollow = false

        Glide.with(context).load(usersList[position].image).placeholder(R.drawable.vector_profile)
            .into(holder.binding.profileImage)
        holder.binding.name.text = usersList[position].email

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
            .whereEqualTo("email", usersList[position].email).get().addOnSuccessListener {
                if (it.documents.size == 0) {
                    isFollow = false
                } else {
                    isFollow = true
                    holder.binding.followButton.text = "Unfollow"
                }
            }

        holder.binding.followButton.setOnClickListener {
            if (isFollow) {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                    .whereEqualTo("email", usersList[position].email).get().addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                            .document(
                                it.documents[0].id
                            ).delete()
                        holder.binding.followButton.text = "Follow"
                        isFollow = false
                    }
            } else {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document()
                    .set(usersList[position])
                holder.binding.followButton.text = "Unfollow"
                isFollow = true
            }
        }
    }
}
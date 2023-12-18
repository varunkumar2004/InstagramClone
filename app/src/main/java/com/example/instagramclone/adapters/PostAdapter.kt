package com.example.instagramclone.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Models.Post
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.databinding.PostRvBinding
import com.example.instagramclone.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostAdapter(var context: Context, var postList: ArrayList<Post>) :
    RecyclerView.Adapter<PostAdapter.MyHolder>() {
    inner class MyHolder(var binding: PostRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList[position].uid).get().addOnSuccessListener {
                var user: User = it.toObject<User>()!!
                Log.d("TryActivity", "nothing")
                Glide.with(context).load(user!!.image).placeholder(R.drawable.profile).into(holder.binding.profileImage)
                holder.binding.profileName.text = user.name
            }
        } catch (e: Exception) {
            Log.d("TryActivity", "exception")
        }

        Glide.with(context).load(postList[position].postUrl).placeholder(R.drawable.background_placeholder).into(holder.binding.postImage)

        try {
            val time = TimeAgo.using(postList[position].time.toLong())
            holder.binding.profileBio.text = time
        } catch (e: Exception) {
            holder.binding.profileBio.text = ""
        }

        holder.binding.sharePost.setOnClickListener {
            var intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, postList[position].postUrl)
            context.startActivity(intent)
        }

        holder.binding.caption.text = postList[position].caption
        holder.binding.likeButton.setOnClickListener {
            holder.binding.likeButton.setImageResource(R.drawable.vector_like)
        }
    }
}
package com.example.instagramclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.Models.Post
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.adapters.MyReelAdapter
import com.example.instagramclone.databinding.FragmentMyReelBinding
import com.example.instagramclone.utils.REEL
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class MyReelFragment : Fragment() {
    private lateinit var binding: FragmentMyReelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyReelBinding.inflate(inflater, container, false)
        var reelList = ArrayList<Reel>()
        var adapter = MyReelAdapter(requireContext(), reelList)
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).get().addOnSuccessListener {
            var tempList = ArrayList<Reel>()
            for (i in it.documents) {
                var reel = i.toObject<Reel>()!!
                tempList.add(reel)
            }

            reelList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}
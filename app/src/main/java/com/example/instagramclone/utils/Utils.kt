package com.example.instagramclone.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, folderName: String, callback: (String?) -> Unit) {
    var imageUrl: String? = null
    val uid = UUID.randomUUID().toString()
    FirebaseStorage.getInstance().getReference(folderName).child(uid).putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { it ->
                imageUrl = it.toString()
                callback(imageUrl)
            }
        }
}

fun uploadVideo(
    uri: Uri, folderName: String, progressDialog: ProgressDialog, callback: (String?) -> Unit
) {
    var videoUrl: String? = null
    progressDialog.setTitle("Uploading...")
    progressDialog.show()
    val uid = UUID.randomUUID().toString()


    FirebaseStorage.getInstance().getReference(folderName).child(uid).putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { it ->
                videoUrl = it.toString()
                progressDialog.dismiss()
                callback(videoUrl)
            }
        }.addOnProgressListener {
            var uploadValue: Long = (it.bytesTransferred / it.totalByteCount) * 100 // percentage
            progressDialog.setMessage("Uploaded $uploadValue%")
        }
}
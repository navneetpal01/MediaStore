package com.example.mediastore

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MediaStoreUtil(
    private val context : Context
){


    suspend fun saveImage(bitmap : Bitmap){
        //Should be run in the IO thread
        withContext(Dispatchers.IO){
            val resolver = context.contentResolver

            val imageCollection = MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY,
            )
            val timeInMillis = System.currentTimeMillis()

            val imageContentValues = ContentValues().apply {
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES
                    )
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "${timeInMillis}_image" + ".jpg"
                )
                put(
                    MediaStore.Images.Media.MIME_TYPE,
                    "image/jpg"
                )
                put(
                    MediaStore.Images.Media.DATE_TAKEN,
                    timeInMillis
                )
                put(
                    MediaStore.Images.Media.IS_PENDING,
                    1
                )
            }

            val imageMediaStoreUri = resolver.insert(
                imageCollection,imageContentValues
            )

            imageMediaStoreUri?.let {

            }


        }
    }

}
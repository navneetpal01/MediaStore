package com.example.mediastore

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


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
                    MediaStore.MediaColumns.RELATIVE_PATH,
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

            imageMediaStoreUri?.let {uri ->
                try {
                    resolver.openOutputStream(uri)?.let {outputStream ->
                        bitmap.compress(
                            Bitmap.CompressFormat.JPEG,100,outputStream
                        )

                    }

                    imageContentValues.clear()
                    imageContentValues.put(
                        MediaStore.Images.Media.IS_PENDING,0
                    )

                    //Refresh the gallary
                    resolver.update(
                        uri,imageContentValues,null,null
                    )

                }catch (e : Exception){
                    e.printStackTrace()
                    resolver.delete(uri,null,null)
                }

            }


        }
    }
    suspend fun saveVideo(file : File){
        //Should be run in the IO thread
        withContext(Dispatchers.IO){
            val resolver = context.contentResolver

            val videoCollection = MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY,
            )
            val timeInMillis = System.currentTimeMillis()

            val videoContentValues = ContentValues().apply {
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_MOVIES
                    )
                put(
                    MediaStore.Video.Media.DISPLAY_NAME,
                    "${timeInMillis}_video"
                )
                put(
                    MediaStore.Video.Media.MIME_TYPE,
                    "video/mp4"
                )
                put(
                    MediaStore.Video.Media.DATE_ADDED,
                    timeInMillis
                )
                put(
                    MediaStore.Video.Media.IS_PENDING,
                    1
                )
            }

            val videoMediaStoreUri = resolver.insert(
                videoCollection,videoContentValues
            )

            videoMediaStoreUri?.let { uri ->
                try {
                    //OutPut Stream
                    resolver.openOutputStream(uri)?.use {outputStream ->
                        resolver.openInputStream(
                            Uri.fromFile(file)
                        )?.use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }

                    videoContentValues.clear()
                    videoContentValues.put(
                        MediaStore.Images.Media.IS_PENDING,0
                    )

                    //Refresh the gallary
                    resolver.update(
                        uri,videoContentValues,null,null
                    )

                }catch (e : Exception){
                    e.printStackTrace()
                    resolver.delete(uri,null,null)
                }

            }


        }
    }
    suspend fun saveAudio(file : File){
        //Should be run in the IO thread
        withContext(Dispatchers.IO){
            val resolver = context.contentResolver

            val audioCollection = MediaStore.Audio.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY,
            )
            val timeInMillis = System.currentTimeMillis()

            val audioContentValues = ContentValues().apply {
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_MUSIC
                    )
                put(
                    MediaStore.Video.Media.DISPLAY_NAME,
                    "${timeInMillis}_song"
                )
                put(
                    MediaStore.Video.Media.MIME_TYPE,
                    "audio/mpeg"
                )
                put(
                    MediaStore.Video.Media.DATE_ADDED,
                    timeInMillis
                )
                put(
                    MediaStore.Video.Media.IS_PENDING,
                    1
                )
            }

            val audioMediaStoreUri = resolver.insert(
                audioCollection,audioContentValues
            )

            audioMediaStoreUri?.let { uri ->
                try {
                    //OutPut Stream
                    resolver.openOutputStream(uri)?.use {outputStream ->
                        resolver.openInputStream(
                            Uri.fromFile(file)
                        )?.use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }

                    audioContentValues.clear()
                    audioContentValues.put(
                        MediaStore.Images.Media.IS_PENDING,0
                    )

                    //Refresh the gallary
                    resolver.update(
                        uri,audioContentValues,null,null
                    )

                }catch (e : Exception){
                    e.printStackTrace()
                    resolver.delete(uri,null,null)
                }

            }


        }
    }

}
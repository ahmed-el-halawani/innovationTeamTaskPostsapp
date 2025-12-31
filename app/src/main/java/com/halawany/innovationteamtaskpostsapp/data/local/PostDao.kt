package com.halawany.innovationteamtaskpostsapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.halawany.innovationteamtaskpostsapp.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("DELETE FROM posts") suspend fun clearPosts()

    @Query("SELECT * FROM posts") fun getPosts(): PagingSource<Int, PostEntity>

    @Query("SELECT * FROM posts WHERE id = :id") fun getPostById(id: String): PostEntity?
}

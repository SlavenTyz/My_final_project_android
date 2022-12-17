package com.example.my_final_project.viewmodel

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.my_final_project.R
import com.example.my_final_project.model.api.PostApi
import com.example.my_final_project.view.AddFragment
import com.example.my_final_project.model.database.Post
import com.example.my_final_project.model.database.PostDatabase
import com.example.my_final_project.model.model.StateFragment
import com.example.my_final_project.view.ViewFragment
import com.google.android.filament.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel: ViewModel() {
    private val _postsLiveData: MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val postLiveData: LiveData<List<Post>> = _postsLiveData

    private val _currentFragment: MutableLiveData<StateFragment> = MutableLiveData(StateFragment.View)
    val currentFragment: MutableLiveData<StateFragment> = _currentFragment

    var database: PostDatabase? = null

    fun goToView(fragmentManager: FragmentManager, viewFragment: ViewFragment){
        fragmentManager.beginTransaction()
            .replace(R.id.container, viewFragment)
            .commit()
    }

    fun goToAdd(fragmentManager: FragmentManager, addFragment: AddFragment){
        fragmentManager.beginTransaction()
            .replace(R.id.container, addFragment)
            .commit()
    }

    fun getPostsFromServer(postApi: PostApi, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val response = postApi.getPosts()
            putToDB(response.body()!!, context)
        }
    }

    fun putToDB(posts: List<Post>, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            if (database == null) {
                database = Room.databaseBuilder(context, PostDatabase::class.java, "posts")
                    .build()
            }
            database!!.getDao().insertPosts(*posts.toTypedArray())
        }
    }

    fun getPostsFromDb(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            if (database == null) {
                database = Room.databaseBuilder(context, PostDatabase::class.java, "posts")
                    .build()
            }
            _postsLiveData.postValue(database!!.getDao().getPosts())
        }
    }


    fun putPostToDb(post: Post,context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            if (database == null) {
                database = Room.databaseBuilder(context, PostDatabase::class.java, "posts")
                    .build()
            }
            database!!.getDao().insertPost(post)
        }
    }

    fun changeFragment(stateFragments: StateFragments){
        _currentFragment.postValue(stateFragments)
    }
}

private fun <T> MutableLiveData<T>.postValue(stateFragments: StateFragments) {

}

class StateFragments {

}

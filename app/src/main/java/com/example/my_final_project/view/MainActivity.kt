package com.example.my_final_project.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.my_final_project.R
import com.example.my_final_project.model.model.StateFragment
import com.example.my_final_project.viewmodel.PostViewModel
import com.example.my_final_project.viewmodel.StateFragments

class MainActivity : AppCompatActivity() {

    private val postViewModel: PostViewModel by viewModels()
    private lateinit var addFragment: AddFragment
    private lateinit var viewFragment: ViewFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment = AddFragment.newInstance()
        viewFragment = ViewFragment.newInstance()
        postViewModel.currentFragment.observe(this) {
            when(it){
                StateFragment.View -> postViewModel.goToView(supportFragmentManager, viewFragment)
                StateFragment.Add -> postViewModel.goToAdd(supportFragmentManager, addFragment)
            }
        }
    }
}
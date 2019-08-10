package com.example.mistyglossary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mistyglossary.database.getDatabase
import com.example.mistyglossary.databinding.ActivityMainBinding
import com.example.mistyglossary.injection.DaggerFragmentComponent
import com.example.mistyglossary.injection.module.RepositoryModule
import com.example.mistyglossary.repository.Repository
import com.example.mistyglossary.util.NavClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    lateinit var repository : Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.mainToolbar)

        val fragmentComponent = DaggerFragmentComponent.builder()
            .mainIOComponent(MistyApplication.get(application).mainIOComponent)
            .repositoryModule(RepositoryModule(applicationContext,"")).build()

        repository = fragmentComponent.getRepository()

        binding.mainToolbar.setNavigationOnClickListener (NavClickListener(this,
            binding.downDropper,
            ContextCompat.getDrawable(applicationContext!!, R.drawable.s_backdrop_icon) ,
            ContextCompat.getDrawable(applicationContext!!, R.drawable.backdrop_icon_f),
            AccelerateDecelerateInterpolator()) )

        binding.selectLanB.setOnClickListener {
            findNavController(R.id.main_navcontroller).navigate(R.id.languageFragment)
        }

        binding.savedWordB.setOnClickListener {
            findNavController(R.id.main_navcontroller).navigate(R.id.preservationFragment)
        }

        binding.clearB.setOnClickListener {
            val coroutineScope = CoroutineScope(Dispatchers.Main)

            coroutineScope.launch {
                withContext(Dispatchers.IO){
                    repository.clear()
                }
            }
        }
    }
}

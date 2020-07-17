package com.example.playingwithcoroutineflow.mvvm.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playingwithcoroutineflow.R
import com.example.playingwithcoroutineflow.adapters.ExampleAdapter
import com.example.playingwithcoroutineflow.mvvm.models.Callback
import com.example.playingwithcoroutineflow.mvvm.models.Status
import com.example.playingwithcoroutineflow.mvvm.viewmodels.MainViewModel
import com.example.playingwithcoroutineflow.room.entities.ExampleEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()
    private var  adapter : ExampleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exampleDataRefreshingListener()
        setupAdapter()
        pagedDataListener()

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getSomeData(isRefresh = true)
        }
    }

    private fun exampleDataRefreshingListener(){
        viewModel.isRefreshed().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    println("data refreshed")
                }
                else -> {
                    println("error = ${it.error}")
                }
            }
            swipeRefreshLayout.isRefreshing = it.status == Status.LOADING
        })
    }

    private var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val position = viewHolder.adapterPosition
            val item = adapter?.getExampleItem(position)
            viewModel.deleteItem(item)
        }
    }


    private fun setupAdapter(){
        val layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = layoutManager
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        adapter = ExampleAdapter()
        recyclerView.adapter = adapter

        adapter?.setOnItemClickListener(object:Callback{
            override fun process(any: Any?) {
                val item  = any as ExampleEntity
                findNavController().navigate(MainFragmentDirections.gotoInfoFragment(item.name))
            }
        })
    }

    private fun pagedDataListener(){
        viewModel.examplesLiveData.observe(viewLifecycleOwner, Observer { data ->
            if (data != null)
                adapter?.submitList(data)
        })
    }

}
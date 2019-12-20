package com.east.architecture_components.paginglibrary

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.east.architecture_components.R
import com.east.architecture_components.paginglibrary.adapter.CheeseAdapter
import com.east.architecture_components.paginglibrary.viewholder.CheeseViewHolder
import com.east.architecture_components.paginglibrary.viewmodel.CheeseViewModel
import kotlinx.android.synthetic.main.activity_paging_library.*

class PagingLibraryActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE){
        ViewModelProviders.of(this).get(CheeseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_library)

        //create a adapter for recycleview
        val adapter = CheeseAdapter()
        cheeseList.adapter = adapter

        //将适配器订阅到ViewModel，以便在列表更改时刷新适配器中的项目
        viewModel.allCheese.observe(this, Observer(adapter::submitList))

        initSwipeToDelete()

        initAddButtionListener()
    }

    private fun initSwipeToDelete(){
        ItemTouchHelper(object :ItemTouchHelper.Callback(){
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CheeseViewHolder).cheese?.let {
                    viewModel.remove(it)
                }
            }

        }).attachToRecyclerView(cheeseList)
    }

    private fun addCheese(){
        val newCheese = inputText.text.trim()
        if(newCheese.isNotEmpty()){
            viewModel.insert(newCheese)
            inputText.setText("")
        }
    }

    private fun initAddButtionListener(){
        addButton.setOnClickListener {
            addCheese()
        }

        inputText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                addCheese()
                true    //返回true，保留软键盘。false，隐藏软键盘
            }
            false
        }

        inputText.setOnKeyListener { v, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                addCheese()
                true
            }
            false
        }
    }


}

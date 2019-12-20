package com.east.customview.material_design_1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.east.customview.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_simple_practice.*
import java.util.*
import kotlin.collections.ArrayList

class SimplePracticeActivity : AppCompatActivity() {

    private val fruits = arrayOf(
        Fruit("Apple", R.drawable.apple), Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange), Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear), Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple), Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry), Fruit("Mango", R.drawable.mango)
    )

    private var fruitList = ArrayList<Fruit>()

    private lateinit var adapter :FruitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_practice)

        setSupportActionBar(toolbar)
        if(supportActionBar!=null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        nav_view.setCheckedItem(R.id.nav_call)
        nav_view.setNavigationItemSelectedListener {
            drawer_layout.closeDrawers()
            true
        }

        fab.setOnClickListener {
                Snackbar.make(it,"Data deleted",Snackbar.LENGTH_SHORT)
                    .setAction("Undo") {
                        Toast.makeText(
                            this@SimplePracticeActivity,
                            "Data restored",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.show()
        }

        initFruits()
        adapter = FruitAdapter(fruitList)
        var gridManager = GridLayoutManager(this,2)
        recycler_view.layoutManager = gridManager
        recycler_view.adapter = adapter

        swipe_refresh.setOnRefreshListener {
            refreshFruits()
        }

    }

    private fun initFruits() {
        fruitList.clear()
        for (i in 0..49) {
            val random = Random()
            val index = random.nextInt(fruits.size)
            fruitList.add(fruits[index])
        }
    }

    private fun refreshFruits() {
        Thread(Runnable {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                swipe_refresh.isRefreshing = false
            }
        }).start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> drawer_layout.openDrawer(GravityCompat.START)
            R.id.backup -> Toast.makeText(this,
                "You clicked Backup",
                Toast.LENGTH_SHORT
            ).show()
            R.id.delete -> Toast.makeText(
                this,
                "You clicked Delete",
                Toast.LENGTH_SHORT
            ).show()
            R.id.settings -> Toast.makeText(
                this,
                "You clicked Settings",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

}

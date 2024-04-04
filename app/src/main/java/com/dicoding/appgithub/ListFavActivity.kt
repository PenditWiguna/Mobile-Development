package com.dicoding.appgithub

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.appgithub.adapter.AdapterListFav
import com.dicoding.appgithub.data.FavItem
import com.dicoding.appgithub.detail.DetailActivity
import com.dicoding.appgithub.shared.Theme
import com.dicoding.appgithub.ui.MainActivity

class ListFavActivity : AppCompatActivity() {

    var DB: Database? = null
    private var theme: Theme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        DB = Database(this)
        theme = Theme(this)

        val getSetList: MutableList<FavItem> = ArrayList()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val linearLayout: LinearLayout = findViewById(R.id.layoutFavourite)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this@ListFavActivity, 1)
        recyclerView.addOnItemTouchListener(
            RecyclerViewOnClick(
                this@ListFavActivity,
                recyclerView,
                object : RecyclerViewOnClick.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        pindahDetail(getSetList[position].Nama)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        val builder = AlertDialog.Builder(this@ListFavActivity)
                        builder.setMessage("Apakah ingin menghapus nama ini dari daftar favorite?")
                            .setCancelable(false)
                            .setPositiveButton("Ya") { dialog, id ->
                                DB!!.deleteFav(getSetList[position].IdFav.toString())
                                Toast.makeText(this@ListFavActivity, "Berhasil menghapus data favourite", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@ListFavActivity, ListFavActivity::class.java))
                            }
                            .setNegativeButton("Tidak") { dialog, id ->
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()

                    }
                })
        )

        if(theme!!.choose.equals("White")){
            linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }else{
            linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }

        val res: Cursor = DB!!.dataFav
        if (res.count == 0) {
            Toast.makeText(this@ListFavActivity, "No Entry Exists", Toast.LENGTH_SHORT).show()
        }else{
            while (res.moveToNext()) {
                getSetList.add(
                    FavItem(
                        res.getInt(0),
                        res.getString(1),
                        res.getString(2)
                    )
                )
            }
            val adapterMoviesMoviesSeries = AdapterListFav(getSetList, this@ListFavActivity)
            recyclerView.adapter = adapterMoviesMoviesSeries
        }
    }

    fun pindahDetail(username: String){
        Intent(this, DetailActivity::class.java).apply {
            putExtra("username", username)
            startActivity(this)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@ListFavActivity, MainActivity::class.java))
        finish()
    }
}
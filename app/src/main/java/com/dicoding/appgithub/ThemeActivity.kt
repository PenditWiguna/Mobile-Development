package com.dicoding.appgithub

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.appgithub.adapter.AdapterListFav
import com.dicoding.appgithub.data.FavItem
import com.dicoding.appgithub.detail.DetailActivity
import com.dicoding.appgithub.shared.Theme
import com.dicoding.appgithub.ui.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView


class ThemeActivity : AppCompatActivity() {

    private var theme: Theme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_theme)

        theme = Theme(this)

        val spinner: Spinner = findViewById(R.id.spinner)
        val buttonSimpan: MaterialButton = findViewById(R.id.buttonSimpan)
        val tittle: MaterialTextView = findViewById(R.id.title)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.activityThemeLayout)

        if(theme!!.choose.equals("White")){
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            tittle.setTextColor(ContextCompat.getColor(this, R.color.black))
            val adapter = CustomSpinnerAdapter(
                this,
                R.array.Languages, ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.white)
            )
            spinner.adapter = adapter
            spinner.setSelection(1)
        }else{
            constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            tittle.setTextColor(ContextCompat.getColor(this, R.color.white))
            val adapter = CustomSpinnerAdapter(
                this,
                R.array.Languages, ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.black)
            )
            spinner.adapter = adapter
            spinner.setSelection(0)
        }

        buttonSimpan.setOnClickListener{
            theme!!.choose = spinner.selectedItem.toString()
            Toast.makeText(this, "Berhasil merubah tema", Toast.LENGTH_SHORT).show()
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    class CustomSpinnerAdapter(
        context: Context,
        private val itemsArrayResourceId: Int,
        private val textColor: Int,
        private val backgroundColor: Int
    ) : ArrayAdapter<String>(context, R.layout.item_spinner, context.resources.getStringArray(itemsArrayResourceId)) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.setTextColor(textColor)
            view.setBackgroundColor(backgroundColor)
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.setTextColor(textColor)
            view.setBackgroundColor(backgroundColor)
            return view
        }
    }
}
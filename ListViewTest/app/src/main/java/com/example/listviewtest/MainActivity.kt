package com.example.listviewtest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val data = ArrayList<Fruit>()

    fun initData(){
        repeat(2){
            with(data){
                add(Fruit("Apple", R.drawable.apple_pic))
                add(Fruit("Banana", R.drawable.banana_pic))
                add(Fruit("Orange", R.drawable.orange_pic))
                add(Fruit("Watermelon", R.drawable.watermelon_pic))
                add(Fruit("Pear", R.drawable.pear_pic))
                add(Fruit("Grape", R.drawable.grape_pic))
                add(Fruit("Pineapple", R.drawable.pineapple_pic))
                add(Fruit("Strawberry", R.drawable.strawberry_pic))
                add(Fruit("Cherry", R.drawable.cherry_pic))
                add(Fruit("Mango", R.drawable.mango_pic))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        listView.adapter = MyAdapter(this, R.layout.fruit_item, data)

        listView.setOnItemClickListener{ parent, view, position, id ->
            val fruit = data[position]
            Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
        }
    }

}

class MyAdapter(context: Context, val resource: Int, objects: List<Fruit>): ArrayAdapter<Fruit>(context, resource, objects) {
    class ViewHolder(val imgView: ImageView, val textView: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if(convertView == null){
            view = LayoutInflater.from(context).inflate(resource, parent, false)
            val imageView = view.findViewById<ImageView>(R.id.fruitImage)
            val textView = view.findViewById<TextView>(R.id.fruitName)
            viewHolder = ViewHolder(imageView, textView)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val fruit = getItem(position)
        fruit?.let {
            viewHolder.imgView.setImageResource(it.imgId)
            viewHolder.textView.text = it.name
        }
        return view
    }
}
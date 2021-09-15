package com.wisekrakr.wiserecipes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wisekrakr.wiserecipes.R
import com.wisekrakr.wiserecipes.entities.CategoryItem

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.RecipeViewHolder>(){

    private var listener: OnItemClickListener? = null
    private var context: Context? = null
    private var arrayCategories = ArrayList<CategoryItem>()


    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){

        val name : TextView
        val thumbnail : ImageView

        init {
            name = view.findViewById(R.id.tv_category_name)
            thumbnail = view.findViewById(R.id.img_thumbnail)
        }

    }

    fun setData(arrayData: List<CategoryItem>){
        arrayCategories = arrayData as ArrayList<CategoryItem>
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        context = parent.context
        return RecipeViewHolder(LayoutInflater.from(context)
            .inflate(
                R.layout.category_item,
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.name.text = arrayCategories[position].strCategory

        Glide.with(context!!).load(arrayCategories[position].strCategoryThumb).into(holder.thumbnail)

        holder.itemView.rootView.setOnClickListener {
            listener!!.onClick(arrayCategories[position].strCategory)
        }
    }

    override fun getItemCount(): Int {
        return arrayCategories.size
    }


    fun setClickListener(clickListener: OnItemClickListener){
        this.listener = clickListener
    }

    interface OnItemClickListener{
        fun onClick(categoryName:String)
    }
}
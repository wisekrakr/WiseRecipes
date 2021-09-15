package com.wisekrakr.wiserecipes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wisekrakr.wiserecipes.HomeActivity
import com.wisekrakr.wiserecipes.R
import com.wisekrakr.wiserecipes.entities.MealItem

class MealAdapter: RecyclerView.Adapter<MealAdapter.RecipeViewHolder>(){

    private var listener: OnItemClickListener? = null
    private var context: Context? = null
    private var arrayMeals = ArrayList<MealItem>()

    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){

        val name : TextView
        val thumbnail : ImageView

        init {
            name = view.findViewById(R.id.tv_meal_name)
            thumbnail = view.findViewById(R.id.img_thumbnail)
        }
    }

    fun setData(arrayData: List<MealItem>){
        arrayMeals = arrayData as ArrayList<MealItem>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        context = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context)
            .inflate(
                R.layout.meal_item,
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.name.text = arrayMeals[position].strMeal

        Glide.with(context!!).load(arrayMeals[position].strMealThumb).into(holder.thumbnail)

        holder.itemView.rootView.setOnClickListener {
            listener!!.onClick(arrayMeals[position].idMeal)
        }
    }

    override fun getItemCount(): Int {
        return arrayMeals.size
    }

    interface OnItemClickListener{
        fun onClick(id:String)
    }


    fun setClickListener(clickListener: MealAdapter.OnItemClickListener){
        this.listener = clickListener
    }
}
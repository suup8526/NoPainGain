package com.nopaingain.bouldereatout.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.network.model.restaurant.SimpleRestaurantModel
import com.nopaingain.bouldereatout.utils.loadImage

class RestaurantAdapter(
    private val restaurantList: ArrayList<SimpleRestaurantModel>,
    private val restaurantClickListener: OnRestaurantClickListener
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    companion object {
        private const val LOADING = 1
        private const val ITEM = 0
    }

    private var isLoadingAdded = false

    interface OnRestaurantClickListener {
        fun onRestaurantClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return when (viewType) {
            ITEM -> RestaurantViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_restaurant,
                    parent,
                    false
                )
            )
            else -> RestaurantViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = restaurantList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == restaurantList.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM) {
            holder.setData(restaurantList[position])
            holder.itemView.setOnClickListener {
                restaurantClickListener.onRestaurantClick(position)
            }
        }
    }

    fun getRestaurantList(): ArrayList<SimpleRestaurantModel> {
        return restaurantList
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
    }

    fun removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false
        }
    }

    inner class RestaurantViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private var restaurantIV: AppCompatImageView? = null
        private var nameTV: AppCompatTextView? = null
        private var cuisineTV: AppCompatTextView? = null
        private var locationTV: AppCompatTextView? = null
        private var ratingTV: AppCompatTextView? = null

        init {
            restaurantIV = itemView?.findViewById(R.id.ivItemDp)
            nameTV = itemView?.findViewById(R.id.tvItemName)
            cuisineTV = itemView?.findViewById(R.id.tvItemCuisine)
            locationTV = itemView?.findViewById(R.id.tvItemLoc)
            ratingTV = itemView?.findViewById(R.id.tvItemRatingValue)
        }


        fun setData(restaurant: SimpleRestaurantModel) {
            if (restaurant.imageUrl != null)
                restaurantIV?.loadImage(R.drawable.ic_restaurant, restaurant.imageUrl)
            else
                restaurantIV?.setImageResource(R.drawable.ic_restaurant)

            nameTV?.text = restaurant.name ?: ""
            if (restaurant.categories != null && restaurant.categories.isNotEmpty())
                cuisineTV?.text = restaurant.categories.joinToString(separator = ", ")
            locationTV?.text = restaurant.address ?: "Boulder"
            ratingTV?.text = (restaurant.rating ?: 0).toString()
        }
    }
}
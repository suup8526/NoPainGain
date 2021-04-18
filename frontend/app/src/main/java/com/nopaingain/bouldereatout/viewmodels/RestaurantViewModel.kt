package com.nopaingain.bouldereatout.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.nopaingain.bouldereatout.BoulderEatOutApplication
import com.nopaingain.bouldereatout.network.model.restaurant.SimpleRestaurantModel

class RestaurantViewModel : BaseViewModel() {

    private lateinit var restaurantListResponse: LiveData<ArrayList<SimpleRestaurantModel>>
    private var restaurantList: ArrayList<SimpleRestaurantModel> = ArrayList()

    fun getRestaurantListing(offset: Int,
                             limit: Int): LiveData<ArrayList<SimpleRestaurantModel>> {
        val hashMap = HashMap<String, String>()
        hashMap["offset"] = offset.toString()
        hashMap["limit"] = limit.toString()
        obProcessing.value = true
        restaurantListResponse =
            Transformations.map(
                BoulderEatOutApplication.getBoulderEatOutRepository().getRestaurantList(hashMap)
            ) {
                val restaurantList = ArrayList<SimpleRestaurantModel>()
                if (it?.responseData?.restaurants != null) {
                    for (restaurant in it.responseData?.restaurants!!) {
                        restaurantList.add(restaurant)
                    }
                } else {
                    obErrorMessage.postValue(it.errorResponse)
                }
                obProcessing.value = false
                return@map restaurantList
            }
        return restaurantListResponse
    }


    private fun addDummyData() {
        restaurantList.clear()
//        restaurantList.add(Sim(id="1", name = "The Sink", location = "Boulder", cusine = "American", rating = "4.4"))
//        restaurantList.add(Sim(id="2", name = "Pizzeria Locale", location = "Boulder", cusine = "American, Italian", rating = "4.5"))
//        restaurantList.add(Sim(id="3", name = "Le French Cafe", location = "Boulder", cusine = "French", rating = "4.7"))
//        restaurantList.add(Sim(id="4", name = "Hapa Sushi Grill", location = "Boulder", cusine = "Sushi", rating = "4.5"))
//        restaurantList.add(Sim(id="5", name = "The Yellow Deli", location = "Boulder", cusine = "Deli", rating = "4.4"))
//        restaurantList.add(Sim(id="6", name = "Verde", location = "Boulder", cusine = "Mexican", rating = "4.4"))
//        restaurantList.add(Sim(id="7", name = "Sherpa", location = "Boulder", cusine = "Nepalese", rating = "4.5"))
    }

}
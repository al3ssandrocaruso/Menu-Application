package com.francescapavone.menuapp.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RestaurantApi(private var context: Context) {
    fun getRestaurant(id: String,
                      onSuccess: (String) -> Unit,
                      onError: (VolleyError) -> Unit) {
        var url="https://raw.githubusercontent.com/al3ssandrocaruso/restaurantsappdata/main/restaurants/$id"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                onSuccess(it)
            },
            {
                onError(it)
            })
        queue.add(stringRequest)
    }
    fun getMenu(id: String,
                onSuccess: (String) -> Unit,
                onError: (VolleyError) -> Unit) {
        var url="https://raw.githubusercontent.com/al3ssandrocaruso/restaurantsappdata/main/menus/$id"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                onSuccess(it)
            },
            {
                onError(it)
            })
        queue.add(stringRequest)
    }
    fun listAllPreviews(onSuccess: (String) -> Unit,
                        onError: (VolleyError) -> Unit) {
        val url = "https://raw.githubusercontent.com/al3ssandrocaruso/restaurantsappdata/main/restaurants/allpreviews"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                onSuccess(it)
            },
            {
                onError(it)
            })
        queue.add(stringRequest)
    }

}
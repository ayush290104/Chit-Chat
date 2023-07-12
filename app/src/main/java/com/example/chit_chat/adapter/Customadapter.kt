package com.example.chit_chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chit_chat.R
import com.example.chit_chat.models.User

class CustomAdapter(private val dataSet: List<User>,private val context: FragmentActivity?) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var mListener:onButtonclicklistener
interface onButtonclicklistener{
    fun onButtonclick(Position:Int)
}
    fun setOnButtonClicklistener(Listener:onButtonclicklistener){
        mListener = Listener
    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View,listener:onButtonclicklistener) : RecyclerView.ViewHolder(view) {
        val customname: TextView
        val customimage:ImageView
        val custommail:TextView
        val addfriend:Button
        init {
            // Define click listener for the ViewHolder's View
            customname = view.findViewById(R.id.customname)
            customimage = view.findViewById(R.id.Customimage)
            custommail = view.findViewById(R.id.customemail)
            addfriend = view.findViewById(R.id.addfriend)


        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.column, viewGroup, false)

        return ViewHolder(view,mListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.customname.text = dataSet[position].Name
        viewHolder.custommail.text = dataSet[position].emailid
viewHolder.addfriend.setOnClickListener {

    if (viewHolder.addfriend.text=="Request Sent"){
     Toast.makeText(context,"Request sent already",Toast.LENGTH_SHORT).show()
    }
    else {
        mListener.onButtonclick(position)
        viewHolder.addfriend.text = "Request Sent"
    }
}
        Glide.with(viewHolder.itemView).load(dataSet[position].uri).into(viewHolder.customimage)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
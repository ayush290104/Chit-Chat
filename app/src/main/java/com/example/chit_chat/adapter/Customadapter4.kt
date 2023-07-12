package com.example.chit_chat.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chit_chat.R
import com.example.chit_chat.models.Chat
import com.example.chit_chat.models.User

class CustomAdapter4 (private val dataSet: List<Chat>) :
    RecyclerView.Adapter<CustomAdapter4.ViewHolder>() {


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val received: TextView
        val sent: TextView
         val cardsent:CardView
         val cardreceive:CardView
        init {
            // Define click listener for the ViewHolder's View
            received = view.findViewById(R.id.received)
            sent = view.findViewById(R.id.sent)
cardsent = view.findViewById(R.id.cardsent)
cardreceive = view.findViewById(R.id.cardreceive)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.column4, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.sent.text = dataSet[position].sent
        viewHolder.received.text = dataSet[position].received
        if(viewHolder.sent.text == ""){
            viewHolder.cardsent.visibility = View.GONE
            viewHolder.sent.visibility = View.GONE

        }
        if(viewHolder.received.text == ""){
            viewHolder.received.visibility = View.GONE
            viewHolder.cardreceive.visibility = View.GONE
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
package com.betrybe.trybnb.ui.viewmodels

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.betrybe.trybnb.R

class ReservationViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTextView: TextView = itemView.findViewById(R.id.name_item_reservation)
    val checkInTextView: TextView = itemView.findViewById(R.id.checkin_item_reservation)
    val checkOutTextView: TextView = itemView.findViewById(R.id.checkout_item_reservation)
    val additionalNeedsTextView: TextView = itemView.findViewById(
        R.id.additional_needs_item_reservation
    )
    val totalPriceTextView: TextView = itemView.findViewById(R.id.total_price_item_reservation)
}

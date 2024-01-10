package com.betrybe.trybnb.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.betrybe.trybnb.R
import com.betrybe.trybnb.data.models.ReservationItem

class ReservationAdapter(
    private val itemList: List<ReservationItem>
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_reservation,
            parent,
            false
        )
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(
            R.id.name_item_reservation
        )
        private val checkInTextView: TextView = itemView.findViewById(
            R.id.checkin_item_reservation
        )
        private val checkOutTextView: TextView = itemView.findViewById(
            R.id.checkout_item_reservation
        )
        private val additionalNeedsTextView: TextView = itemView.findViewById(
            R.id.additional_needs_item_reservation
        )
        private val totalPriceTextView: TextView = itemView.findViewById(
            R.id.total_price_item_reservation
        )
        private val depositPaidImageView: ImageView = itemView.findViewById(
            R.id.depositpaid_item_reservation
        )

        fun bind(reservationItem: ReservationItem) {
            nameTextView.text = reservationItem.firstname
            checkInTextView.text = reservationItem.bookingdates.checkin
            checkOutTextView.text = reservationItem.bookingdates.checkout
            additionalNeedsTextView.text = reservationItem.additionalneeds
            totalPriceTextView.text = reservationItem.totalprice.toString()

            val depositPaidDrawable = if (reservationItem.depositpaid) {
                R.drawable.ic_depositpaid_true
            } else {
                R.drawable.ic_depositpaid_false
            }
            depositPaidImageView.setImageResource(depositPaidDrawable)
        }
    }
}

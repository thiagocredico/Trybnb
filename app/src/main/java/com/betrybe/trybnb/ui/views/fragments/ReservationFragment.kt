package com.betrybe.trybnb.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betrybe.trybnb.R
import com.betrybe.trybnb.common.ApiIdlingResource
import com.betrybe.trybnb.data.api.RetrofitInstance
import com.betrybe.trybnb.data.models.BookingIdItem
import com.betrybe.trybnb.data.models.ReservationItem
import com.betrybe.trybnb.ui.adapters.ReservationAdapter
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

const val TWENTYFIVE = 25
class ReservationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val api = RetrofitInstance.create()

        val scope = CoroutineScope(Dispatchers.IO)

        val context = requireContext()

        val view = inflater.inflate(R.layout.fragment_reservation, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.reservation_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        scope.launch {
            val allBookings = loadAllBookings()

            val lastTwentyFiveBooking = allBookings.takeLast(TWENTYFIVE)

            val reservations = loadReservations(lastTwentyFiveBooking)

            val adapter = ReservationAdapter(reservations)
            withContext(Dispatchers.Main) {
                recyclerView.adapter = adapter
            }
        }

        return view
    }

    private suspend fun loadAllBookings(): List<BookingIdItem> {
        return withContext(Dispatchers.IO) {
            val api = RetrofitInstance.create()
            try {
                ApiIdlingResource.increment()
                val result = api.getAllBookings()
                ApiIdlingResource.decrement()
                result
            } catch (e: HttpException) {
                println("Error: ${e.message}")
                ApiIdlingResource.decrement()
                emptyList()
            } catch (e: IOException) {
                println("Error: ${e.message}")
                ApiIdlingResource.decrement()
                emptyList()
            }
        }
    }

    private suspend fun loadReservations(bookingIds: List<BookingIdItem>): List<ReservationItem> {
        return withContext(Dispatchers.IO) {
            val reservations = mutableListOf<ReservationItem>()

            val api = RetrofitInstance.create()
            try {
                ApiIdlingResource.increment()
                bookingIds.forEach { booking ->
                    val result = api.getBookingById(booking.bookingid)
                    reservations.add(result)
                }
                ApiIdlingResource.decrement()
                reservations
            } catch (e: HttpException) {
                println("Error: ${e.message}")
                ApiIdlingResource.decrement()
                emptyList()
            } catch (e: IOException) {
                println("Error: ${e.message}")
                ApiIdlingResource.decrement()
                emptyList()
            }
        }
    }
}

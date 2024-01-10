import com.betrybe.trybnb.data.models.BookingIdItem
import com.betrybe.trybnb.data.models.LoginRequest
import com.betrybe.trybnb.data.models.LoginResponse
import com.betrybe.trybnb.data.models.ReservationItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @POST("auth")
    suspend fun doLoginRequest(@Body loginRequest: LoginRequest): LoginResponse

    @GET("booking?checkin=2024-01-01&checkout=2024-12-21")
    suspend fun getAllBookings(): List<BookingIdItem>

    @GET("booking/{Id}")
    suspend fun getBookingById(
        @Path("Id") bookingId: String,
        @Header("Accept") accept: String = "application/json"
    ): ReservationItem

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @POST("booking")
    suspend fun createReservation(
        @Body reservationItem: ReservationItem
    ): Response<Any>
}

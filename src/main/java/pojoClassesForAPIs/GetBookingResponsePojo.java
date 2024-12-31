package pojoClassesForAPIs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetBookingResponsePojo {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    @Getter
    @Setter
    @ToString
    public static class BookingDates {
        private String checkin;
        private String checkout;
    }
}

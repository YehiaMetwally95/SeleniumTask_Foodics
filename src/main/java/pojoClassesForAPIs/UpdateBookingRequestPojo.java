package pojoClassesForAPIs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString
@Jacksonized
@Builder
public class UpdateBookingRequestPojo {

    //variables
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    @Getter
    @Setter
    @ToString
    @Jacksonized
    @Builder
    public static class BookingDates {
        private String checkin;
        private String checkout;
    }
}

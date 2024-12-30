package pojoClassesForAPIs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class WhoamiResponsePojo {

    //variables
    private String status;
    private String message;
    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private String fullName;
        private int userId;
        private String email;
        private String mobile;
        private String company;
        private String dateOfBirth;
    }
}

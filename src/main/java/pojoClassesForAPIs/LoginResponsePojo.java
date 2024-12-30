package pojoClassesForAPIs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponsePojo {

    //variables
    private String status;
    private String message;
    private String sessionId;
    private int userId;
}

package pojoClassesForAPIs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponsePojo {

    //variables
    private String token;
    private String reason;
}

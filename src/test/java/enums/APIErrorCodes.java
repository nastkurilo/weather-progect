package enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum APIErrorCodes {

    Q_PARAMETER_MISSED(400, 1003,"Parameter 'q' not provided."),
    API_REQUEST_INVALID(400,1005, "API request url is invalid"),
    NO_FOUND_LOCATION_Q(400, 1006, "No location found matching parameter 'q'"),
    API_KEY_DISABLED(403,2008, "API key has been disabled.");

    private final int statusCode;
    private final int errorCode;
    private final String description;

}

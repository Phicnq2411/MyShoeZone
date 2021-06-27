package com.philconnal.shoezone.common.exception;

import com.philconnal.shoezone.common.enums.APIStatus;

/**
 * @author DiGiEx
 */
public class ApplicationException extends RuntimeException {

    private APIStatus apiStatus;
    private Object data;

    public ApplicationException(APIStatus apiStatus) {
        super(apiStatus.getDescription());
        this.apiStatus = apiStatus;
    }

    /**
     * This constructor is builded only for handling BAD REQUEST exception
     * Careful when use it with other purpose ;)
     *
     * @param apiStatus
     * @param data
     */
    public ApplicationException(APIStatus apiStatus, Object data) {
        this(apiStatus);
        this.data = data;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(APIStatus apiStatus, String message) {
        super(message);
        this.apiStatus = apiStatus;
    }

    public APIStatus getApiStatus() {
        return apiStatus;
    }

    public Object getData() {
        return data;
    }


}

package com.philconnal.shoezone.common.validation;

import com.philconnal.shoezone.common.enums.APIStatus;
import com.philconnal.shoezone.common.exception.ApplicationException;
import com.philconnal.shoezone.common.exception.errors.MyExistedException;
import com.philconnal.shoezone.common.exception.errors.MyBadRequestException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

/**
 * @author DiGiEx
 */
public class Validator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);


    public static void mustNull(Object obj, String message) {

        if (obj != null) {

            throw new MyExistedException(message);
        }
    }

    /**
     * Use when validate {@code obj} must not be null. Throw
     * ApplicationException with {@code apiStatus} if {@code obj} is null
     *
     * @param obj
     * @param apiStatus APIStatus will throw when failed
     * @param message
     * @throws ApplicationException if {@code obj} is null
     */
    public static void notNull(Object obj, APIStatus apiStatus, String message) {

        if (obj == null) {
            throw new ApplicationException(apiStatus, message);
        }
    }

    public static void notNull(Object obj, APIStatus apiStatus) {

        if (obj == null) {
            throw new ApplicationException(apiStatus, "");
        }
    }

    /**
     * Validate list object not null & not empty
     *
     * @param obj
     * @param apiStatus
     * @param message
     */
    public static void notNullAndNotEmpty(List<?> obj, APIStatus apiStatus, String message) {

        if (obj == null || obj.isEmpty()) {
            throw new ApplicationException(apiStatus, message);
        }
    }

    /**
     * Validate object not null & not empty
     *
     * @param obj
     * @param apiStatus
     * @param message
     */
    public static void notNullAndNotEmpty(Object obj, APIStatus apiStatus, String message) {

        if (obj == null || "".equals(obj)) {
            throw new ApplicationException(apiStatus, message);
        }
    }

    /**
     * Validate list array not null & not empty
     *
     * @param arrays
     * @param apiStatus
     * @param message
     */
    public static void notNullAndNotEmpty(String[] arrays, APIStatus apiStatus, String message) {

        for (String str : arrays) {
            if (str == null || "".equals(str)) {
                throw new ApplicationException(apiStatus, message);
            }
        }
    }

    /**
     * Validate list object must null & must empty
     *
     * @param obj
     * @param apiStatus
     * @param message
     */
    public static void mustNullAndMustEmpty(List<?> obj, APIStatus apiStatus, String message) {
        if (obj != null && !obj.isEmpty()) {
            throw new ApplicationException(apiStatus, message);
        }
    }

    public static void mustEquals(String str1, String str2, APIStatus apiStatus, String message) {
        if (!str1.equals(str2)) {
            throw new ApplicationException(apiStatus, message);
        }
    }


    public static void mustNotEquals(String str1, String str2, APIStatus apiStatus, String message) {
        if (str1.equals(str2)) {
            throw new ApplicationException(apiStatus, message);
        }
    }

    /**
     * validate email format
     *
     * @param emailAddress
     */
    public static void validateEmail(String emailAddress) {
        boolean isEmailFormat = isEmailFormat(emailAddress);
        if (!isEmailFormat) {
            throw new MyBadRequestException("Invalid email format");
        }
    }

    public static boolean isEmailFormat(String valueToValidate) {
        // Regex
        String regexExpression = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        Pattern regexPattern = Pattern.compile(regexExpression);

        if (valueToValidate != null) {
            if (valueToValidate.indexOf("@") <= 0) {
                return false;
            }
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(valueToValidate);
            return matcher.matches();
        } else { // The case of empty Regex expression must be accepted
            Matcher matcher = regexPattern.matcher("");
            return matcher.matches();
        }
    }

    public static void mustContainString(String value, List<String> containList) {
        if (!containList.contains(value)) {
            throw new ApplicationException(APIStatus.BAD_REQUEST, "Value " + value + " was not in " + containList.toString());
        }
    }

}

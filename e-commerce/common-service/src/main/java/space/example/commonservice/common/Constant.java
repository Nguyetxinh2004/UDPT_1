package space.example.commonservice.common;

public class Constant {
    public static final class ErrorCode {
        public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
        public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";

        public static final String AUTH_MISSING_TOKEN= "AUTH_MISSING_TOKEN";

        public static final String NOT_FOUND = "NOT_FOUND_RESOURCE";

        public static final String ORDER_PUT_ITEM_FAILED = "ORDER_PUT_ITEM_FAILED";
        public static final String ORDER_NOT_FOUND = "ORDER_NOT_FOUND";

        public static final String DELIVERY_ALREADY_EXISTS = "DELIVERY_ALREADY_EXISTS";

        public static final String FOOD_EXISTS = "FOOD_EXISTS";
    }

    public static final class HeaderConstant {
        public static final String BEARER_PREFIX = "Bearer ";
    }
}

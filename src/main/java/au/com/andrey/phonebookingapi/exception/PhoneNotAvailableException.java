package au.com.andrey.phonebookingapi.exception;

import java.util.List;
import java.util.UUID;

public class PhoneNotAvailableException extends RuntimeException {
    private final List<UUID> phoneIds;

    public PhoneNotAvailableException(List<UUID> phoneIds) {
        super("Phones " + phoneIds + " not available");
        this.phoneIds = phoneIds;
    }
}

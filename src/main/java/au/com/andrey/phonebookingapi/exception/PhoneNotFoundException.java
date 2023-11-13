package au.com.andrey.phonebookingapi.exception;

import java.util.List;
import java.util.UUID;

public class PhoneNotFoundException extends RuntimeException {
    private final List<UUID> phoneIds;

    public PhoneNotFoundException(List<UUID> phoneIds) {
        super("Phones " + phoneIds + " not found");
        this.phoneIds = phoneIds;
    }

}

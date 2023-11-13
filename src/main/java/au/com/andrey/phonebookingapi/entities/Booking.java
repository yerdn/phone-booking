package au.com.andrey.phonebookingapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String bookedBy;

    @NotNull
    private OffsetDateTime bookingTime;

    @OneToMany(mappedBy = "booking")
    private List<Phone> phones;

    public Booking() {
        this.id = UUID.randomUUID();
    }

    public boolean hasBookings() {
        return phones != null && !phones.isEmpty();
    }
}

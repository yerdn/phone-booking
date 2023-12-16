package au.com.andrey.phonebookingapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Phone {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String name;

    @ManyToOne
    @JoinTable(name = "phone_booking",
            joinColumns = {@JoinColumn(name = "phone_id")},
            inverseJoinColumns = {@JoinColumn(name = "booking_id")})
    private Booking booking;

    public boolean isAvailable() {
        return booking == null;
    }

}

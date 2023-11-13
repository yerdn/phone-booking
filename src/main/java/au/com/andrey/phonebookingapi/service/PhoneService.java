package au.com.andrey.phonebookingapi.service;

import au.com.andrey.phonebookingapi.entities.Booking;
import au.com.andrey.phonebookingapi.entities.Phone;
import au.com.andrey.phonebookingapi.exception.PhoneNotAvailableException;
import au.com.andrey.phonebookingapi.exception.PhoneNotFoundException;
import au.com.andrey.phonebookingapi.repository.BookingRepository;
import au.com.andrey.phonebookingapi.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final BookingRepository bookingRepository;
    private final JmsTemplate jmsTemplate;

    public Page<Phone> findAll(Pageable pageable) {
        return phoneRepository.findAll(pageable);
    }

    @Transactional
    public List<Phone> bookPhones(List<UUID> phoneIds, String bookedBy) {
        List<Phone> phonesFound = phoneRepository.findAllById(phoneIds);
        if (phonesFound.isEmpty()) {
            throw new PhoneNotFoundException(phoneIds);
        }
        List<UUID> bookedPhones = phonesFound.stream().filter(phone -> !phone.isAvailable()).map(Phone::getId).toList();
        if (!bookedPhones.isEmpty()) {
            throw new PhoneNotAvailableException(bookedPhones);
        }

        Booking booking = new Booking();
        booking.setBookedBy(bookedBy);
        booking.setBookingTime(OffsetDateTime.now());
        bookingRepository.save(booking);
        phonesFound.forEach(phone -> phone.setBooking(booking));
        sendJmsMessage("Phones booked", phonesFound);

        return phoneRepository.saveAll(phonesFound);
    }

    @Transactional
    public List<Phone> returnPhones(List<UUID> phoneIds, String bookedBy) {
        List<Phone> phonesFound = phoneRepository.findAllById(phoneIds);
        if (phonesFound.isEmpty()) {
            throw new PhoneNotFoundException(phoneIds);
        }

        List<Phone> bookedPhones = phonesFound
                .stream()
                .filter(phone -> !phone.isAvailable())
                .filter(phone -> bookedBy.equals(phone.getBooking().getBookedBy()))
                .toList();
        if (bookedPhones.isEmpty()) {
            throw new PhoneNotAvailableException(phoneIds);
        }

        List<Booking> bookings = bookedPhones.stream().map(phone -> {
            Booking booking = phone.getBooking();
            phone.setBooking(null);
            booking.getPhones().remove(phone);
            return booking;
        }).toList();

        List<Phone> returnedPhones = phoneRepository.saveAll(bookedPhones);
        List<UUID> emptyBookings = bookings.stream().filter(booking -> !booking.hasBookings()).map(Booking::getId).toList();
        bookingRepository.deleteAllById(emptyBookings);
        sendJmsMessage("Phones returned", returnedPhones);

        return returnedPhones;
    }

    private void sendJmsMessage(String message, List<Phone> phones) {
        Map<String, Object> jmsMessage = Map.of("action", message, "phones", phones);
        jmsTemplate.convertAndSend(jmsMessage);
        log.info("Successfully published JMS message [{}] for phones {}", message, phones);
    }
}

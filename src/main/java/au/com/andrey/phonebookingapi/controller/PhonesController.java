package au.com.andrey.phonebookingapi.controller;

import au.com.andrey.phonebookingapi.entities.Phone;
import au.com.andrey.phonebookingapi.mapper.PhoneMapper;
import au.com.andrey.phonebookingapi.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.PhonesApi;
import org.openapitools.model.BookingRequestResource;
import org.openapitools.model.PhoneResource;
import org.openapitools.model.PhoneResponseList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PhonesController implements PhonesApi {
    private final PhoneService phoneService;
    private final PhoneMapper phoneMapper;

    @Override
    public ResponseEntity<PhoneResponseList> find(Pageable pageable) {
        Page<Phone> phonesPage = phoneService.findAll(pageable);
        List<PhoneResource> phoneResourceList = phoneMapper.toDTOFromEntity(phonesPage.getContent());

        return ResponseEntity.ok(
                new PhoneResponseList()
                        .data(phoneResourceList)
                        .page(
                                new org.openapitools.model.Page()
                                        .pageNumber(phonesPage.getNumber())
                                        .pageSize(phonesPage.getSize())
                                        .totalElements(phonesPage.getTotalElements())
                                        .totalPages(phonesPage.getTotalPages())
                        )
        );
    }

    @Override
    public ResponseEntity<PhoneResponseList> bookPhones(BookingRequestResource bookingRequestResource) {
        List<Phone> phones = phoneService.bookPhones(bookingRequestResource.getPhones(), bookingRequestResource.getBookedBy());
        List<PhoneResource> phoneResourceList = phoneMapper.toDTOFromEntity(phones);

        return ResponseEntity.ok(
                new PhoneResponseList()
                        .data(phoneResourceList)
                        .page(
                                new org.openapitools.model.Page()
                                        .pageNumber(0)
                                        .pageSize(phones.size())
                                        .totalElements((long) phones.size())
                                        .totalPages(1))
        );
    }

    @Override
    public ResponseEntity<PhoneResponseList> returnPhones(BookingRequestResource bookingRequestResource) {
        List<Phone> phones = phoneService.returnPhones(bookingRequestResource.getPhones(), bookingRequestResource.getBookedBy());
        List<PhoneResource> phoneResourceList = phoneMapper.toDTOFromEntity(phones);

        return ResponseEntity.ok(
                new PhoneResponseList()
                        .data(phoneResourceList)
                        .page(
                                new org.openapitools.model.Page()
                                        .pageNumber(0)
                                        .pageSize(phones.size())
                                        .totalElements((long) phones.size())
                                        .totalPages(1))
        );
    }
}

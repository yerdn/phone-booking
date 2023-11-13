package au.com.andrey.phonebookingapi.mapper;

import au.com.andrey.phonebookingapi.entities.Phone;
import org.mapstruct.Mapper;
import org.openapitools.model.PhoneResource;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
    List<PhoneResource> toDTOFromEntity(List<Phone> phones);

    PhoneResource toDTOFromEntity(Phone phone);

}

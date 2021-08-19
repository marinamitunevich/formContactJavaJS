package telran.mapper;



import telran.dto.ContactToAddDto;
import telran.dto.ContactToDisplayDto;
import telran.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toContact(ContactToAddDto contactToAddDto) {
        return new Contact(contactToAddDto.firstName, contactToAddDto.lastName, contactToAddDto.age);
    }
    public Contact toContact(ContactToDisplayDto contactToDisplayDto) {
        return new Contact(contactToDisplayDto.firstName, contactToDisplayDto.lastName, contactToDisplayDto.age);
    }
    public ContactToDisplayDto toDto(Contact contact) {
        return new ContactToDisplayDto(contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getAge());
    }
    public ContactToAddDto toDtoAdd(Contact contact) {
        return new ContactToAddDto(contact.getFirstName(), contact.getLastName(), contact.getAge());
    }

}

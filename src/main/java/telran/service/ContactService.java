package telran.service;



import telran.entity.Contact;
import telran.mapper.ContactMapper;
import telran.repo.IContactRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private  static final String CONTACT_NOT = "Contact not exist";
    private IContactRepo repo;


    public ContactService(IContactRepo repo, ContactMapper contactMapper) {
        this.repo = repo;


        repo.save(new Contact("Max", "Mustermann", 18));
        repo.save(new Contact("Anna", "Schmidt", 25));
        repo.save(new Contact("Mark", "Bauer", 30));
    }

    public List<Contact> getAll(){
      return (List<Contact>) repo.findAll();
    }

    public void save(String firstName, String lastName, int age) {
        Contact contact = new Contact(firstName,lastName,age);
        repo.save(contact);

    }

    public void edit(String firstName, String lastName, int age, int contactId) {
        Contact contact = repo.findById(contactId)
                .orElseThrow(()-> new ContactNotFoundExeption(CONTACT_NOT));
        if(firstName != null)
            contact.setFirstName(firstName);
        if(lastName != null)
            contact.setLastName(lastName);
        if(age > 0)
            contact.setAge(age);

        repo.save(contact);

    }

    public void delete(int contactId) {


        if(repo.existsById(contactId))
        repo.deleteById(contactId);
        else
            throw new ContactNotFoundExeption(CONTACT_NOT);
    }

    public Contact getById(int contactId) {

        Contact contact = repo.findById(contactId)
                .orElseThrow(()-> new ContactNotFoundExeption(CONTACT_NOT));

        return contact;

    }
    public List<Contact> searchByName(String name){
        return repo.findAllByFirstNameContainsIgnoreCase(name);
    }
}

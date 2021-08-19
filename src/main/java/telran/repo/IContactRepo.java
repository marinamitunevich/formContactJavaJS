package telran.repo;



import telran.entity.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IContactRepo extends CrudRepository<Contact,Integer> {

    List<Contact> findAllByFirstNameContainsIgnoreCase(String firstName);

    List<Contact> findAllByLastNameContains(String term);

    List<Contact> findAllByFirstName(String fistName);


}

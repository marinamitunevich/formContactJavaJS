package telran.controller;



import telran.dto.ContactToAddDto;
import telran.dto.ContactToDisplayDto;
import telran.entity.Contact;
import telran.mapper.ContactMapper;
import telran.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contacts")
public class ContactRestController {

    private ContactService service;
    private ContactMapper contactMapper;

    public ContactRestController(ContactService service, ContactMapper contactMapper) {
        this.service = service;
        this.contactMapper = contactMapper;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactToDisplayDto contactDetail(@PathVariable(name = "id") int contactId) {

        Contact contact = service.getById(contactId);
        ContactToDisplayDto contactToDisplayDto = contactMapper.toDto(contact);

        return contactToDisplayDto;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactToDisplayDto> contactsPage() {

        List<ContactToDisplayDto> contactToDisplayDtoList = service.getAll().stream()
                .map(contactMapper::toDto).collect(Collectors.toList());

        return contactToDisplayDtoList;

    }
//    @RequestMapping(value = "/form", method = RequestMethod.GET)
//    public  String contactForm(Model model){
//        model.addAttribute("contact", new Contact());
//        return "contact-form";
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void editContact(@PathVariable(name = "id") int contactId, @RequestBody ContactToAddDto contactToAddDto) {

        service.edit(contactToAddDto.firstName, contactToAddDto.lastName, contactToAddDto.age,
                contactId);

    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNewContact(@RequestBody  ContactToAddDto contactToAddDto) {

            service.save(contactToAddDto.firstName, contactToAddDto.lastName, contactToAddDto.age);

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable(name = "id") int contactId) {

        service.delete(contactId);

    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<ContactToDisplayDto> searchContact(@RequestParam(name = "name", required = true) String name) {

        List<ContactToDisplayDto> list = service.searchByName(name)
                .stream().map(contactMapper::toDto).collect(Collectors.toList());

        return list;

    }

}

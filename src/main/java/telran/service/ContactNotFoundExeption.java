package telran.service;

public class ContactNotFoundExeption extends RuntimeException {
    public ContactNotFoundExeption(String contactNot) {
        super(contactNot);
    }
}

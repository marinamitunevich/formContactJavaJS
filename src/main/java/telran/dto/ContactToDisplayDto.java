package telran.dto;

public class ContactToDisplayDto {
    public int id;

    public String firstName;
    public String lastName;
    public int age;

    public ContactToDisplayDto(int id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}

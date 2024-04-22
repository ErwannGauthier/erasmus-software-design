package people;

import java.util.UUID;

public class Person {
    private final UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;

    public Person(String name, String surname, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

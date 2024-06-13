import java.util.UUID;

public abstract class Person {
    private final UUID identifier;
    private String name;
    private String surname;
    private String phone;

    public Person(String name, String surname, String phone) {
        this.identifier = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "(" + this.getIdentifier() + ") " + this.getName() + " " + this.getSurname().toUpperCase();
    }
}

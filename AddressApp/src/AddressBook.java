import java.util.ArrayList;
import java.util.List;

public class AddressBook {

    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    public boolean removeContact(Contact contact) {
        return contacts.remove(contact);
    }
}
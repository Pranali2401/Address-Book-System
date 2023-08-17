import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class AddressBookApp extends Application {

    private AddressBook addressBook = new AddressBook();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Address Book");

        BorderPane layout = new BorderPane();
        VBox contactList = new VBox(10);
        Button addButton = new Button("Add Contact");
        Button searchButton = new Button("Search Contact");
        Button displayAllButton = new Button("Display All Contacts");
        Button removeButton = new Button("Remove Contact");
        TextArea displayArea = new TextArea();

        addButton.setOnAction(e -> {
            ContactDialog dialog = new ContactDialog();
            Contact contact = dialog.showAddDialog();
            if (contact != null) {
                if (isValidContact(contact)) {
                    addressBook.addContact(contact);
                    displayArea.appendText("Added: " + contact.toString() + "\n");
                } else {
                    displayArea.appendText("Invalid contact. Please provide valid information.\n");
                }
            }
        });

        searchButton.setOnAction(e -> {
            ContactDialog dialog = new ContactDialog();
            String nameToSearch = dialog.showSearchDialog();
            if (nameToSearch != null) {
                Contact foundContact = addressBook.searchContact(nameToSearch);
                if (foundContact != null) {
                    displayArea.setText("Found: " + foundContact.toString());
                } else {
                    displayArea.setText("Contact not found.");
                }
            }
        });

        displayAllButton.setOnAction(e -> {
            List<Contact> allContacts = addressBook.getAllContacts();
            if (allContacts.isEmpty()) {
                displayArea.setText("No contacts to display.");
            } else {
                displayArea.setText("All Contacts:\n");
                for (Contact contact : allContacts) {
                    displayArea.appendText(contact.toString() + "\n");
                }
            }
        });

        removeButton.setOnAction(e -> {
            ContactDialog dialog = new ContactDialog();
            String nameToRemove = dialog.showSearchDialog();
            if (nameToRemove != null) {
                Contact foundContact = addressBook.searchContact(nameToRemove);
                if (foundContact != null) {
                    boolean confirmed = dialog.showRemoveDialog(foundContact);
                    if (confirmed) {
                        if (addressBook.removeContact(foundContact)) {
                            displayArea.appendText("Removed: " + foundContact.toString() + "\n");
                        } else {
                            displayArea.appendText("Error removing contact.\n");
                        }
                    } else {
                        displayArea.appendText("Removal cancelled.\n");
                    }
                } else {
                    displayArea.appendText("Contact not found.\n");
                }
            }
        });

        HBox buttonBox = new HBox(10, addButton, searchButton, displayAllButton, removeButton);
        buttonBox.setPadding(new Insets(10));

        layout.setTop(buttonBox);
        layout.setLeft(contactList);
        layout.setCenter(displayArea);

        Scene scene = new Scene(layout, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValidContact(Contact contact) {
        return !contact.getName().isEmpty() && !contact.getPhoneNumber().isEmpty()
                && isValidEmail(contact.getEmailAddress());
    }

    private boolean isValidEmail(String email) {
        return !email.isEmpty() && email.contains("@") && email.indexOf(".") > email.indexOf("@");
    }
}
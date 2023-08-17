import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Pair;

public class ContactDialog {

    public Contact showAddDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Contact");
        dialog.setHeaderText("Enter contact information:");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        TextField phoneField = new TextField();
        TextField emailField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Phone:"), 0, 1);
        grid.add(phoneField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();

                if (isValidContactInfo(name, phone, email)) {
                    return new Pair<>(name, phone);
                } else {
                    showAlert("Invalid input. Please fill in all required fields correctly.");
                }
            }
            return null;
        });

        // Retrieve the result of the dialog
        return dialog.showAndWait().map(result -> {
            String name = result.getKey();
            String phone = result.getValue();
            String email = emailField.getText();
            return new Contact(name, phone, email);
        }).orElse(null);
    }

    public String showSearchDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Search Contact");
        dialog.setHeaderText("Enter name to search:");

        ButtonType searchButton = new ButtonType("Search", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(searchButton, ButtonType.CANCEL);

        TextField searchField = new TextField();
        dialog.getDialogPane().setContent(searchField);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == searchButton) {
                return searchField.getText();
            }
            return null;
        });

        // Retrieve the result of the dialog
        return dialog.showAndWait().orElse(null);
    }

    public boolean showRemoveDialog(Contact foundContact) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Remove Contact");
        alert.setHeaderText("Confirm Removal");
        alert.setContentText("Are you sure you want to remove this contact?\n" + foundContact.toString());

        ButtonType removeButton = new ButtonType("Remove", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(removeButton, ButtonType.CANCEL);

        // Retrieve the result of the dialog
        return alert.showAndWait().filter(buttonType -> buttonType == removeButton).isPresent();
    }

    private boolean isValidContactInfo(String name, String phone, String email) {
        return !name.isEmpty() && !phone.isEmpty() && isValidEmail(email);
    }

    private boolean isValidEmail(String email) {
        return !email.isEmpty() && email.contains("@") && email.indexOf(".") > email.indexOf("@");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
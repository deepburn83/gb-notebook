package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;

import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com;

        while (true) {
            String command = prompt("Введите команду: ").toUpperCase();
            com = Commands.valueOf(command);
            if (com == Commands.EXIT) return;
            switch (com) {
                case CREATE:
                    User u = createUser();
                    userController.saveUser(u);
                    break;
                case READ:
                    String id = prompt("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case LIST:
                    System.out.println(userController.readAll());
                    break;
                case UPDATE:
                    String userId = prompt("Идентификатор пользователя: ");
                    userController.updateUser(userId, createUser());
                    break;
                case DELETE:
                    String userIdToDelete = prompt("Идентификатор пользователя для удаления: ");
                    try {
                        Long userIdLong = Long.parseLong(userIdToDelete);
                        boolean deleted = userController.delete(userIdLong);
                        if (deleted) {
                            System.out.println("Пользователь успешно удален.");
                        } else {
                            System.out.println("Пользователь с указанным идентификатором не найден.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Некорректный формат идентификатора пользователя.");
                    }
                    break;
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private User createUser() {
        String firstName;
        do {
            firstName = prompt("Имя: ");
            if (firstName.trim().isEmpty()) {
                System.out.println("Имя не может быть пустым. Пожалуйста, введите имя.");
            }
        } while (firstName.trim().isEmpty());

        String lastName;
        do {
            lastName = prompt("Фамилия: ");
            if (lastName.trim().isEmpty()) {
                System.out.println("Фамилия не может быть пустой. Пожалуйста, введите фамилию.");
            }
        } while (lastName.trim().isEmpty());

        String phone;
        do {
            phone = prompt("Номер телефона: ");
            if (phone.trim().isEmpty()) {
                System.out.println("Номер телефона не может быть пустым. Пожалуйста, введите номер телефона.");
            }
        } while (phone.trim().isEmpty());

        return new User(firstName, lastName, phone);
    }
}

package notebook.util;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.model.repository.impl.UserRepository;
import notebook.view.UserView;

import java.util.ArrayList;
import java.util.List;

import static notebook.util.DBConnector.DB_PATH;
import static notebook.util.DBConnector.createDB;

public class ApplicationRunner {
    public static final List<User> HASH = new ArrayList<>();

    private ApplicationRunner() {
        createDB();
        GBRepository repository = new UserRepository(DB_PATH);
        UserController controller = new UserController(repository);
        HASH.addAll(controller.readAll());
        UserView view = new UserView(controller);
        view.run();
    }

    public static void run() {
        new ApplicationRunner();
    }
}
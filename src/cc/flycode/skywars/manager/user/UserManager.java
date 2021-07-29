package cc.flycode.skywars.manager.user;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by FlyCode on 11/06/2019 Package cc.flycode.skywars.manager.user
 */
public class UserManager {
    public static CopyOnWriteArrayList<User> Users;

    public UserManager() {
        Users = new CopyOnWriteArrayList<>();
    }

    public User getUser(UUID uuid) {
        for (User user : Users) {
            if (user.uuid == uuid || user.uuid.equals(uuid)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        if (!Users.contains(user)) {
            Users.add(user);
        }
    }

    public void removeUser(User user) {
        if (Users.contains(user)) {
            Users.remove(user);
        }
    }
    public List<User> getUsers() {
        return Users;
    }
}

package messenger.messenger.service;

import messenger.messenger.models.User;

import java.util.List;

public interface IUserService {

    public List<User> findConnectUsers();
    public void disconnect(User user);
    public void saveUser(User user);

}

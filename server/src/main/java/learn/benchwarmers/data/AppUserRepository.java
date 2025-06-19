package learn.benchwarmers.data;

import learn.benchwarmers.models.AppUser;

import java.util.List;

public interface AppUserRepository {
    List<AppUser> findAll();
    AppUser findByUsername(String username);
    AppUser add(AppUser appUser);
    boolean update(AppUser appUser, int roleId);
    boolean deleteById(int userId);
}

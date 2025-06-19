package learn.benchwarmers.security;

import learn.benchwarmers.data.AppUserRepository;
import learn.benchwarmers.domain.Result;
import learn.benchwarmers.models.AppUser;
import learn.benchwarmers.models.Role;
import learn.benchwarmers.models.Team;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    // Spring Framework
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    // Find all users
    public List<AppUser> findAll() {
        return repository.findAll();
    }

//    // Find user by ID
//    public AppUser findById(int userId) {
//        // Might Not Need this (Might Remove)
//        return repository.findAll().stream()
//                .filter(u -> u.getUserId() == userId)
//                .findFirst()
//                .orElse(null);
//    }

    // Find user by their username
    public AppUser findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public AppUser create(String username, String password, String email, String team) {
        validateUsername(username);
        validatePassword(password);

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, username, password, true, List.of("LEAGUE_MEMBER"));
        if(team == null || team.isBlank()) {
            appUser.setFavoriteTeam(Team.KNICKS);
        }else{
            appUser.setEmail(email);
        }

        if(email == null || email.isBlank()) {
            appUser.setEmail("temp@gmail.com");
        }else{
            appUser.setFavoriteTeam(Team.valueOf(team));
        }

        return repository.add(appUser);
    }

    // Add a user
    public Result<AppUser> add(AppUser user) {
        Result<AppUser> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addErrorMessage("User ID cannot be set for add operation.");
            return result;
        }

        AppUser existing = findByUsername(user.getUsername());
        if (existing != null) {
            result.addErrorMessage("Username already exists.");
            return result;
        }

        // Authentication
        String hashedPassword = encoder.encode(user.getPassword());
        AppUser userSave = new AppUser(
                0,
                user.getUsername(),
                hashedPassword,
                false,
                AppUser.convertAuthoritiesToRoles(user.getAuthorities())
        );
        userSave.setEmail(user.getEmail());
        userSave.setFavoriteTeam(user.getFavoriteTeam());

        userSave = repository.add(userSave);
        result.setPayload(userSave);
        return result;
    }

    // Update A User
    public Result<AppUser> update(String username, @RequestBody Map<String, String> parameters) {
        AppUser user = repository.findByUsername(username);
        Result<AppUser> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() <= 0) {
            result.addErrorMessage("User ID is required.");
            return result;
        }

        AppUser existing = findByUsername(user.getUsername());
        if (existing != null && existing.getUserId() != user.getUserId()) {
            result.addErrorMessage("Username already exists.");
            return result;
        }

        // If password is provided to update than we encode it
        if (parameters.get("password") != null && !parameters.get("password").startsWith("$2a$")) {
            String hashedPassword = encoder.encode(parameters.get("password"));
            // We need to create a new app user with the encoded password
            AppUser userToUpdate = new AppUser(
                    user.getUserId(),
                    user.getUsername(),
                    hashedPassword,
                    Boolean.parseBoolean(parameters.get("disabled")),
                    List.of(parameters.get("role"))
            );
            userToUpdate.setEmail(parameters.get("email"));
            userToUpdate.setFavoriteTeam(Team.valueOf(parameters.get("favorite_team")));

            boolean success = repository.update(userToUpdate, Role.valueOf(parameters.get("role")).getId());
            if (!success) {
                result.addErrorMessage("User not found.");
            }
            return result;
        }else{
            AppUser userToUpdate = new AppUser(
                    user.getUserId(),
                    user.getUsername(),
                    parameters.get("password"),
                    Boolean.parseBoolean(parameters.get("disabled")),
                    List.of(parameters.get("role"))
            );
            userToUpdate.setEmail(parameters.get("email"));
            userToUpdate.setFavoriteTeam(Team.valueOf(parameters.get("favorite_team")));
            boolean success = repository.update(user, Role.valueOf(parameters.get("role")).getId());
            if (!success) {
                result.addErrorMessage("User not found.");
            }
        }
        return result;
    }

    // Delete user by ID
    public boolean deleteById(int userId) {
        return repository.deleteById(userId);
    }

    // Validate a user
    public Result<AppUser> validate(AppUser user) {
        Result<AppUser> result = new Result<>();

        if (user == null) {
            result.addErrorMessage("User cannot be null.");
            return result;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addErrorMessage("Username is required.");
        }

        if (user.getUsername().length() < 1 || user.getUsername().length() > 250) {
            result.addErrorMessage("Username must be between 1 and 250 characters.");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            result.addErrorMessage("Email is required.");
        }

        if(!user.getEmail().contains("@")){
            result.addErrorMessage("Invalid email format.");
        }

        if(user.getPassword() == null || user.getPassword().isBlank()){
            if(user.getUserId() == 0){
                result.addErrorMessage("Password is required");
            }
        }

        if (user.getFavoriteTeam() == null) {
            result.addErrorMessage("Favorite team is required.");
        }

        return result;
    }

    // Helper method to check if a user has ADMIN role
    public boolean hasAdminRole(AppUser user) {
        return user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }


//    public boolean isLeagueAdmin(int leagueId, int userId) {
//        return true;
//    }

    private void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new ValidationException("username is required");
        }

        if (username.length() > 50) {
            throw new ValidationException("username must be less than 50 characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("password must be at least 8 characters");
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        if (digits == 0 || letters == 0 || others == 0) {
            throw new ValidationException("password must contain a digit, a letter, and a non-digit/non-letter");
        }
    }
}

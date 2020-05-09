package dmz.crud.controller;

import dmz.crud.model.Role;
import dmz.crud.model.User;
import dmz.crud.service.RoleService;
import dmz.crud.service.UserService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CrudRestController {

    private final UserService userService;
    private final RoleService roleService;


    public CrudRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("user")
    public void addNewUser(@Valid @RequestBody User user) { userService.addNew(user); }

    @GetMapping("users")
    public List<User> getAllUsers() {  return userService.getAll();  }

    @GetMapping("roles")
    public List<Role> getAllRoles() {
        return roleService.getAll();
    }

    @GetMapping("roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") Long roleId) {
        Role role = roleService.getOne(roleId).get();
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(role);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        User user = userService.getOne(userId).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @CachePut
    @PutMapping("users/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) {
        User user = userService.getOne(userId).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setName(userDetails.getName());
        user.setRoles(userDetails.getRoles());
        user.setPassword(userDetails.getPassword());
        user.setLogin(userDetails.getLogin());
        userService.update(user);
        return ResponseEntity.ok().body(userService.getOne(userId).get());
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userService.getOne(userId).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }


}

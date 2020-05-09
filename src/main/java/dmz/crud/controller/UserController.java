package dmz.crud.controller;

import dmz.crud.model.Role;
import dmz.crud.model.User;
import dmz.crud.service.RoleService;
import dmz.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private RoleService roleService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(String error, String logout, ModelAndView modelAndView) {
        if (error != null) {
            modelAndView.addObject("error", "Wong param(s) entered.");
        }
        if (logout != null) {
            modelAndView.addObject("message", "Logged out OK.");
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public ModelAndView startPageAdmin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject( "user", new User());
        modelAndView.addObject("users", this.userService.getAll());
        modelAndView.addObject("roles", this.roleService.getAll());
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public ModelAndView startPageUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject( "user", new User());
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping("admin/getAll")
    public String getAll(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @RequestMapping("admin/getOne")
    @ResponseBody
    public Optional<User> getOne(Long id)    {
        return userService.getOne(id);
    }

    @PostMapping(value="admin/addNew")
    public String addNew(User user, @RequestParam Long roleId) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add( roleService.getOne(roleId).get());
        user.setRoles(roleSet);
        userService.addNew(user);
        return "redirect:/admin/getAll";
    }

    @RequestMapping(value="admin/update", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(User user, @RequestParam Long roleId) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add( roleService.getOne(roleId).get());
        user.setRoles(roleSet);
        userService.update(user);
        return "redirect:/admin/getAll";
    }

    @RequestMapping(value="admin/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(User user, @RequestParam Long id) {
        userService.delete(id);
        return "redirect:/admin/getAll";
    }

}

package dmz.crud.service;

import dmz.crud.dao.UserRepository;
import dmz.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getOne(Long id) {
        return userRepository.findById(id);
    }

    public void addNew(User user) {  userRepository.save(user);  }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(Long Id) {
        userRepository.deleteById(Id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       List<User> uL = (List<User>) userRepository.findAll();
       Optional<User> usr = uL.stream().filter(user -> user.getLogin().equals(s)).findFirst();
       return (UserDetails) getOne(usr.get().getId()).get();
    }
}

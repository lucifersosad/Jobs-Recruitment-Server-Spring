package spring.api.uteating.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.api.uteating.entity.Role;
import spring.api.uteating.entity.User;
import spring.api.uteating.model.UserModel;
import spring.api.uteating.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.getUserByEmail(usernameOrEmail);

        if (user == null) {
            throw new UsernameNotFoundException("Cound not find user");
        }

        return new MyUserService(user);
    }

    public User getUser(String username) {
        return userRepository.getUserByEmail(username);
    }

    public UserModel getUserModel(Long idUser) {
        if (!existsById(idUser)) {
            return null;
        }
        User user = getById(idUser);
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        return userModel;
    }

    @Deprecated
    public User getById(Long aLong) {
        return userRepository.getById(aLong);
    }

    public boolean existsById(Long aLong) {
        return userRepository.existsById(aLong);
    }
}

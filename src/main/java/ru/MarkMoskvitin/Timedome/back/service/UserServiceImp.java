package ru.MarkMoskvitin.Timedome.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.MarkMoskvitin.Timedome.back.entity.Task;
import ru.MarkMoskvitin.Timedome.back.entity.User;
import ru.MarkMoskvitin.Timedome.back.models.Role;
import ru.MarkMoskvitin.Timedome.back.repo.TaskRepository;
import ru.MarkMoskvitin.Timedome.back.repo.UserRepository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PlatformTransactionManager transactionManager;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImp(TaskRepository taskRepository, UserRepository userRepository,
                           PlatformTransactionManager transactionManager, PasswordEncoder passwordEncoder)
    {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.transactionManager = transactionManager;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public boolean isFree(String username) {
        return userRepository.findByUsername(username) != null;
    }


    @Override
    public void deleteUser(String username)
    {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<Task> tasks = taskRepository.findByUser(username);
            for (Task task : tasks){
                taskRepository.delete(task);
            }

            User user = userRepository.findByUsername(username);
            userRepository.delete(user);
            transactionManager.commit(status);
        }
        catch (DataAccessException ex)
        {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),mapRoles(user.getRole()));
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public void updatePass(String username,String currentPassword, String newPassword) throws UsernameNotFoundException,BadCredentialsException{
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> mapRoles(Role role){
        List<SimpleGrantedAuthority> ls = new ArrayList<>();
        ls.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return ls;
    }
}
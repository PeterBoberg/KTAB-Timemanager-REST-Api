package se.karingotrafiken.timemanager.rest.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.LoginRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.LoginResponse;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.entitys.User;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;
import se.karingotrafiken.timemanager.rest.repository.UserRepository;
import se.karingotrafiken.timemanager.rest.security.JWTTokenProvider;
import se.karingotrafiken.timemanager.rest.service.employees.EmployeeService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private EmployeeService employeeService;

    @Override
    @Transactional
    public LoginResponse loginWith(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            User user = userRepository.findByUsername(loginRequest.getUsername());
            String token = jwtTokenProvider.createToken(user);
            return new LoginResponse(token, employeeService.getTranslator().translateFromEntity(user.getEmployee()));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new ApiException(ErrorMessageDTO.ErrorCode.BAD_CREDENTIALS, "Ogiltigt användarnamn eller lösenord");
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User: " + username + " not found");

        return user;
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenProvider(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

}

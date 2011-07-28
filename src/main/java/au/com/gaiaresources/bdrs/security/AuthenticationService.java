package au.com.gaiaresources.bdrs.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import au.com.gaiaresources.bdrs.model.user.User;
import au.com.gaiaresources.bdrs.model.user.UserDAO;


/**
 * Service for authenticating users.
 * @author Tim Carpenter
 */
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @SuppressWarnings("unused")
    private Logger log = Logger.getLogger(AuthenticationService.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        User u = userDAO.getUser(userName);
        if (u == null) {
            log.warn("User " + userName + " not found.");
            throw new UsernameNotFoundException("User " + userName + " not found.");
        }
        log.info("User " + userName + " found, id " + u.getId().toString() + ".");
        return new UserDetails(u);
    }


    public User getUserByRegistrationKey(String rego){
        User u = userDAO.getUserByRegistrationKey(rego);
        return u;
    }
}

package first.app.app1.security;

import first.app.app1.daos.UserDao;
import first.app.app1.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userDao.findByUsername(s);
        ArrayList<GrantedAuthority> grantedAuthorities= new ArrayList<GrantedAuthority>();
        if(user==null) {
            return new org.springframework.security.core.userdetails.User("","",grantedAuthorities);
        }
        else {
            setAuthority(user, grantedAuthorities);
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
        }

    }

    public void setAuthority(User user, ArrayList<GrantedAuthority> lista){
        if(!user.getRole().isEmpty()){
            if(user.getRole().equals(User.adminRole)){
                lista.add( new SimpleGrantedAuthority(User.adminRole));
                lista.add( new SimpleGrantedAuthority(User.customerRole));
            }
            else if(user.getRole().equals(User.customerRole)){
                lista.add( new SimpleGrantedAuthority(User.customerRole));
            }
        }
    }
}

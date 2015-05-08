package pw.spn.quizgame.config;

import java.util.Collections;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import pw.spn.quizgame.security.UserAuthenticationProvider;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware {
    private ApplicationContext context;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO logout
        http.authorizeRequests().antMatchers("/game").authenticated().and().authorizeRequests()
                .antMatchers("/register", "/addUser").permitAll().and().formLogin().defaultSuccessUrl("/game", true)
                .loginPage("/").permitAll().and().logout().logoutSuccessUrl("/").permitAll();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        UserAuthenticationProvider provider = context.getBean(UserAuthenticationProvider.class);
        return new ProviderManager(Collections.singletonList(provider));
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        super.setApplicationContext(context);
        this.context = context;
    }
}
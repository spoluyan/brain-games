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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import pw.spn.quizgame.security.UserAuthenticationProvider;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware {
    private ApplicationContext context;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/game/**", "/stat").authenticated().and().authorizeRequests()
                .antMatchers("/register", "/addUser").permitAll().and().authorizeRequests().antMatchers("/admin/**")
                .hasIpAddress("127.0.0.1").and().formLogin().defaultSuccessUrl("/game/", true)
                .loginPage("/").permitAll().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll();
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

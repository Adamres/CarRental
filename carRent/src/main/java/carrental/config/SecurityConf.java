package carrental.config;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    protected void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .roles("USER")
                .and()
                    .withUser("admin")
                    .password("admin")
                    .roles("ADMIN");

        // Definiáltam egy USER és egy ADMIN rolet
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers(HttpMethod.GET,"/").permitAll()
                        .antMatchers("/admin/vehicles").hasRole("ADMIN")
                        .antMatchers("/admin/reservations").hasRole("ADMIN")
                        .antMatchers("/admin/addcar").hasRole("ADMIN")
                        .antMatchers("/admin/modifycar").hasRole("ADMIN");

        /* Beállítom, hogy az összes admin művelethez szükség legyen ADMIN jogokra
            Az ADMIN role-t pedig az AdminController /admin pathen teszem bele a Granted Authoritiest-be
            ROLE_ANONYMUS-ról ROLE_ADMIN-ra demonstráció céljából.
            Természetesen éles környezetben nem adnék anonymus usereknek ADMIN roleokat */

        http.csrf().disable();
        /* A Spring dokumentáció szerint, fejlesztés közben érdemes kikapcsolni a CSRF funkciót.
        * A POST methodokra 403-as hibákat kaptam és ez a CSRF kikapcsolása után megszűnt */
    }
}



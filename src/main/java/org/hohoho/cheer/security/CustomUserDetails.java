package org.hohoho.cheer.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.hohoho.cheer.model.User;  // Oletetaan, että sinulla on User-malli, joka on tallennettu tietokantaan.

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));  // Oletetaan, että User-luokalla on rooli
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // Oletetaan, että User-luokalla on salasana
    }

    @Override
    public String getUsername() {
        return user.getUsername();  // Oletetaan, että User-luokalla on käyttäjänimi
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Voit tarkistaa käyttäjätilin vanhentumisen tilan tarvittaessa
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Voit tarkistaa käyttäjätilin lukituksen tilan tarvittaessa
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Voit tarkistaa käyttäjän tunnistetietojen vanhenemisen tilan tarvittaessa
    }

    @Override
    public boolean isEnabled() {
        return true;  // Voit tarkistaa, onko käyttäjä tilassa "aktiivinen"
    }

    // Getter for user object if needed elsewhere
    public User getUser() {
        return user;
    }
}

package ru.pcs.graduatework.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.pcs.graduatework.entities.ClientEntity;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class ClientUserDetails implements UserDetails {
    private final ClientEntity clientEntity;

    // это метод, который описывает права пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = clientEntity.getRole().name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return clientEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return clientEntity.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    // мы считаем, что аккаунт не заблокирован, если его state не равно BANNED
    @Override
    public boolean isAccountNonLocked() {
//        return !client.getState().equals(Client.State.BANNED);
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    // мы считаем, что аккаунт рабочий, если он "подтвержден по email"
    @Override
    public boolean isEnabled() {
//        return client.getState().equals(Client.State.CONFIRMED);
        return true;
    }


}

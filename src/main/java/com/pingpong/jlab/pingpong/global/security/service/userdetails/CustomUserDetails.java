package com.pingpong.jlab.pingpong.global.security.service.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pingpong.jlab.pingpong.domain.user.entity.User;

public class CustomUserDetails implements UserDetails{

	private User user;

	public CustomUserDetails(User user){
		this.user = user;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<GrantedAuthority>authorities = new ArrayList<>();
		authorities.add(() -> "ROLE_" + user.getRole());
        return authorities;
    }
    @Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUser_id();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
    
}

package com.readinglist.entity;

import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * Reader用了@Entity注解，所以这是一个JPA实体。此外，它的username字段上有@Id注解，
 * 表明这是实体的ID。这个选择无可厚非，因为username应该能唯一标识一个Reader。
 * Reader实现了UserDetails接口以及其中的方法，这样Reader就能代表
 * Spring Security里的用户了。getAuthorities()方法被覆盖过了，始终会为用户授予READER
 * 权限。isAccountNonExpired()、 isAccountNonLocked()、isCredentialsNonExpired()
 * 和isEnabled()方法都返回true，这样读者账户就不会过期，不会被锁定，也不会被撤销。
 */
@Entity
public class Reader implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    private String username;
    private String fullname;
    private String password;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    // UserDetails methods
    //授予READER权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("READER"));
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
package com.thiennth.taskmanager.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;

public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {

    @Override
    public UserDetails updatePassword(UserDetails arg0, @Nullable String arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePassword'");
    }
}

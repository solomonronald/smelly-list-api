package com.smellylist.api.auth;

import com.smellylist.api.auth.models.UserCredential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends CrudRepository<UserCredential, String> {

    UserCredential findByUsername(String username);

    UserCredential findByEmail(String email);
}

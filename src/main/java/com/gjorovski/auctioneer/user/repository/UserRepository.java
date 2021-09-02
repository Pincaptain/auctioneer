package com.gjorovski.auctioneer.user.repository;

import com.gjorovski.auctioneer.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String username);

    User findFirstByEmail(String email);
}

package com.gjorovski.auctioneer.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findFirstByName(String name);
}

package com.gjorovski.auctioneer.auth.repository;

import com.gjorovski.auctioneer.auth.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findFirstByName(String name);
}

package com.gjorovski.auctioneer.auth.repository;

import com.gjorovski.auctioneer.auth.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findFirstByName(String name);
}

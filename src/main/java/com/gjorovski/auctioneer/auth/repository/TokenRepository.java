package com.gjorovski.auctioneer.auth.repository;

import com.gjorovski.auctioneer.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findFirstByValue(String value);
}

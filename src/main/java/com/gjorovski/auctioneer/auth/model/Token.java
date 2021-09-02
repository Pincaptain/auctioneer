package com.gjorovski.auctioneer.auth.model;

import com.gjorovski.auctioneer.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "token", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value", nullable = false, unique = true)
    private String value;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Token(String value, User user) {
        this.value = value;
        this.user = user;
    }

    @PreRemove
    private void deleteTokenFromUser() {
        this.user.setToken(null);
    }
}

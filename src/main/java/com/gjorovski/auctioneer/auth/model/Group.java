package com.gjorovski.auctioneer.auth.model;

import com.gjorovski.auctioneer.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    @PreRemove
    public void deleteGroupFromUsers() {
        users.forEach(user -> user.getGroups().removeIf(group -> group.id.equals(id)));
    }
}

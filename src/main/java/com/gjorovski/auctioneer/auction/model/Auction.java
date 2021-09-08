package com.gjorovski.auctioneer.auction.model;

import com.gjorovski.auctioneer.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "auction", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "details", length = 512)
    private String details;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Lot> lots = new ArrayList<>();

    public void addLot(Lot lot) {
        lots.add(lot);
    }
}

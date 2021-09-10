package com.gjorovski.auctioneer.auction.model;

import com.gjorovski.auctioneer.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Inventory {
    private static final long DEFAULT_INITIAL_CURRENCY = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "currency")
    private double currency = DEFAULT_INITIAL_CURRENCY;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Slot> slots = new ArrayList<>();
}

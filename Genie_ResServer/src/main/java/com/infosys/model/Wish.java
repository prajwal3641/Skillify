package com.infosys.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wish_id;

    private String wish_name;
    private String description;

    private String status;

    private String location;


    @ManyToOne
    @JoinColumn(name = "wishmaker_id")
    @JsonIgnore
    private Wishmaker wishmaker;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "wish_user",
            joinColumns = @JoinColumn(name = "wish_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    @JsonIgnoreProperties("wishes")// Ignore the back-reference to prevent recursion
    // it Genie json will ignore the wishes field
    @JsonIgnoreProperties("wishes")
    private Set<Genie> registeredUsers = new HashSet<>();

}

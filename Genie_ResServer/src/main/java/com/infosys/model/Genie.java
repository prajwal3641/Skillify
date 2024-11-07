package com.infosys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Genie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
// Not using the pwd feild as we are storing it in user
//    private String pwd;

    @Column(name="phone_no")
    private String phoneNo;

    private String city;

    private String description;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    @JsonIgnore
    private String role;


    @ManyToMany(mappedBy = "registeredUsers",fetch = FetchType.EAGER)
    @JsonIgnoreProperties("registeredUsers")
    private Set<Wish> wishes = new HashSet<>();


}

package com.phonevalidator.data.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "customer")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String phone;
}

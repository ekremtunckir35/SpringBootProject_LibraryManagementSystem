package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Geçerli bir isim giriniz!")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Geçerli bir soyisim giriniz!")
    @Column(nullable = false)
    private String lastName;

    private String phoneNumber;

    @Email(message = "Geçerli bir email giriniz!")
    @Column(nullable = false,unique = true)
    private String email;


    @Setter(AccessLevel.NONE)
    private LocalDateTime registrationDate;

    @PrePersist//objeyi kaydetmeden hemen önce bu metod çağrılır
    public void setRegistrationDateAuto(){
        this.registrationDate=LocalDateTime.now();
    }
}
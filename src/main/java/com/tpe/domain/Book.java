package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "t_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Kitap ismi boşluk olamaz!!!")
    @NotNull(message = "Kitap ismi girilmelidir!!!")
    @Size(min = 2,max = 50,message = "Kitap ismi en az 2 karakter içermelidir!!!!")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Yazar ismi boşluk olamaz!!!")
    @Size(min = 2,max = 50,message = "Yazar ismi en az 2 karakter içermelidir!!!!")
    @Column(nullable = false)
    private String author;

    @NotBlank(message = "Lütfen yayın yılını giriniz!")
    @Column(nullable = false)
    private String publicationDate;

    @ManyToOne//fk ekler
    @JsonIgnore
    private Owner owner;



}
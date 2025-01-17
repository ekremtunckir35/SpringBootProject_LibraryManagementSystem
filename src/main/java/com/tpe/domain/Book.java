package com.tpe.domain;

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
    @Setter(value = AccessLevel.NONE)// id degeri set edilemez degistirilemez
    private Long id;

    @NotBlank(message = "Kitap isim bosluk olamaz!!!")
    @NotNull(message = "Kitap ismi girilmelidir !!!")
    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "Kitap ismi 2 ile 50 karakter arasinda olmalidir!!!")
    private String title;


    @NotBlank(message = "Yazar isim bosluk olamaz!!!")
    @Size(min = 2, max = 50, message = "Yazar ismi 2 ile 50 karakter arasinda olmalidir!!!")
    @Column(nullable = false)
    private String author;

    @NotBlank(message = "Yayin yilini giriniz!!!")
    @Column(nullable = false)
    private String publicationDate;





}

package com.tpe.dto;

import com.tpe.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {

    @NotBlank(message = "Kitap ismi boşluk olamaz!!!")
    @NotNull(message = "Kitap ismi girilmelidir!!!")
    @Size(min = 2,max = 50,message = "Kitap ismi en az 2 karakter içermelidir!!!!")
    private String title;

    @NotBlank(message = "Yazar ismi boşluk olamaz!!!")
    @Size(min = 2,max = 50,message = "Yazar ismi en az 2 karakter içermelidir!!!!")
    private String author;

    @NotBlank(message = "Lütfen yayın yılını giriniz!")
    private String publicationDate;

    //book-->bookDTO
    public BookDTO(Book book){
        this.title= book.getTitle();
        this.author=book.getAuthor();
        this.publicationDate=book.getPublicationDate();
    }
}
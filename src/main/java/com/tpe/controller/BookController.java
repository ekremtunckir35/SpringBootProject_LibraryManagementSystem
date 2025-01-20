package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.dto.BookDTO;
import com.tpe.exception.BookNotFoundException;
import com.tpe.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController//rest api, response:JSON
//@Controller dynamic app, return:ModelAndView veya String(viewname) //dinemik uygulama ön yüzü olan uygulama var
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    //CREATE
    //1-a : Save a Book & Response:Message
    // http://localhost:8080/books + POST + body(JOSN)
    /*
    {
        "id":1
        "title":"Martin Eden"
        "author":"Jack London"
        "publicationDate":"1875"
    }
    //DTO ile id yi almayız.
    */
    @PostMapping
    public ResponseEntity<String> createBook(@Valid @RequestBody BookDTO bookDTO) {//mesaj döndüriceğim icin Spring oluşturduk.
        bookService.saveBook(bookDTO);
        return  new ResponseEntity<>("Kitap başarıyla kayıt edildi", HttpStatus.CREATED);//201
    }

    //2-a : Get All Books
    // http://localhost:8080/books + GET
    //todo:ilerleyen derste return:List<BookDTO>
    @GetMapping
    private ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);//200

    }

    ///Ödev:5 tane kitap ekleyelim

    //3- Get a Book by its ID
    //http://localhost:8080/books/2 + GET
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") Long id){
        BookDTO bookDTO = bookService.getBookDTOById(id);
        return ResponseEntity.ok(bookDTO);//200
    }

    //4- Delete a Book by its ID
    //http://localhost:8080/books/2 + DELETE
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Kitap silme işlemi başarılı...");
    }

    @PostMapping("/morebooks")
    public ResponseEntity<String> createMoreStudent(@RequestBody List<Book> books){
        bookService.saveMoreBook(books);
        return new ResponseEntity<>("More book created",HttpStatus.CREATED);
    }

    //5- Get a Book by its ID with RequestParam-->Qeriy  parametresiyle yaptık
    //http://localhost:8080/books/q?id=2
    @GetMapping("/q")
    public ResponseEntity<?> getBookById(@RequestParam("id") Long id){
        try {//exception fılatılma ihtimali...
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(book);//200
        }catch (BookNotFoundException e){
            return new  ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    //6- Get a Book by its Title with RequestParam
    //http://localhost:8080/books/search?title=Atomic Habits + GET
    @GetMapping("/search")
    public ResponseEntity<List<Book>> filterBooksByTitle(@RequestParam("title") String title){
        List<Book> bookList=bookService.filterBooksByTitle(title);
        return ResponseEntity.ok(bookList);
    }

    //todo:ödevvvvv!!!!!!!!...
    ///ÖDEV:--> Get Books By Its Author and PublicationDate
    //--> http://localhost:8080/books/filter?author=Martin Eden&pubDate=1900
    //alternatif:http://localhost:8080/books/MartinEden/1900
    //findByAuthorandPubicationdate(author, pubDate) ikiside aynanda sağlansın istiyorsak and


    //7- Get Books With Page --x> sayfalandırmada coğunlukla Qery parametresi kullanılır.
    // http://localhost:8080/books/s?page=1&
    //                               size=2&
    //                               sort=publicationDate&
    //                               direction=ASC
    @GetMapping("/s")
    public ResponseEntity<Page<Book>> getBooksByPage(@RequestParam(value="page",defaultValue = "1") int page,
                                                     @RequestParam("size") int size,
                                                     @RequestParam("sort") String sortBy,
                                                     @RequestParam("direction")Sort.Direction direction){
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,sortBy));
        Page<Book> bookPage = bookService.getBookByPage(pageable);
        return ResponseEntity.ok(bookPage);//200
    }

    //8- Update a Book With Using DTO
// http://localhost:8080/books/update/2 + PUT(yer degistirme)/PATH + body(JSON)

    @PatchMapping("/update/{id}")//ben sadece bir alanı güncellemek istiyorum.

    public ResponseEntity<String>updateBook(@PathVariable("id") Long id,@RequestBody BookDTO bookDTO){


        bookService.updateBook(id,bookDTO);
        return ResponseEntity.ok("Kitap basari ile guncellendi...");

    }

    //9- Get a Book By Its Author Using JPQL
// http://localhost:8080/books/a?author=ABC
    @GetMapping("/a")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String author){
        List<Book> books=bookService.filterBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    //10- Get a Book By Its Title Using JPQL







}
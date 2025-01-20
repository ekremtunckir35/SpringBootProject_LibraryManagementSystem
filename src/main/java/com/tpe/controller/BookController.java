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

@RestController//rest api,return:JSON
//@Controller-dynamic app,return:ModelAndView veya String(Viewname)
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    //CREATE
    //1- Save a Book & Response:Message
    // http://localhost:8080/books + POST + body(JSON)
    /*
    {
      "title":"Martin Eden"
      "author":"Jack London"
      "publicationDate":"1875"
    }
    */
    @PostMapping
    public ResponseEntity<String> createBook(@Valid @RequestBody BookDTO bookDTO){

        bookService.saveBook(bookDTO);

        return new ResponseEntity<>("Kitap başarıyla kaydedildi", HttpStatus.CREATED);//201
    }

    //2- Get All Books
    // http://localhost:8080/books + GET
    //todo:ilerleyen derste return:List<BookDTO>
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books=bookService.findAllBooks();
        return ResponseEntity.ok(books);//200
    }

    //3- Get a Book by its ID
    // http://localhost:8080/books/2 + GET
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") Long id){
        BookDTO bookDTO=bookService.getBookDTOById(id);

        return ResponseEntity.ok(bookDTO);//200
    }

    //4- Delete a Book by its ID
    // http://localhost:8080/books/2 + DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Kitap silme işlemi başarılı...");
    }

    //5- Get a Book by its ID with RequestParam
    // http://localhost:8080/books/q?id=2 + GET
    @GetMapping("/q")
    public ResponseEntity<?> getBookById(@RequestParam("id") Long id){
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(book);//200
        }catch (BookNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    //6- Get a Book by its Title with RequestParam
    //http://localhost:8080/books/search?title=Atomic Habits + GET
    @GetMapping("/search")
    public ResponseEntity<List<Book>> filterBooksByTitle(@RequestParam("title") String title){
        List<Book> bookList=bookService.filterBooksByTitle(title);
        return ResponseEntity.ok(bookList);
    }

    //ÖDEV:--> Get Books By Its Author and PublicationDate
    //--> http://localhost:8080/books/filter?author=Martin Eden&pubDate=1900
    //alternatif:http://localhost:8080/books/Martin Eden/1900
    //findByAuthorAndPublicationDate(author,pubDate)


    //7- Get Books With Page
    // http://localhost:8080/books/s?page=1&
    //                               size=2&
    //                               sort=publicationDate&
    //                               direction=ASC  + GET
    @GetMapping("/s")
    public ResponseEntity<Page<Book>> getBooksByPage(@RequestParam(value = "page",defaultValue = "1") int page,
                                                     @RequestParam(value = "size",defaultValue = "2") int size,
                                                     @RequestParam("sort") String sortBy,
                                                     @RequestParam("direction")Sort.Direction direction){

        Pageable pageable= PageRequest.of(page-1,size,Sort.by(direction,sortBy));
        Page<Book> bookPage=bookService.getBooksByPage(pageable);

        return ResponseEntity.ok(bookPage);

    }

    //8- Update a Book With Using DTO
    // http://localhost:8080/books/update/2 + PUT(yer değiştirme)/PATCH(kısmi)
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id,@Valid @RequestBody BookDTO bookDTO){

        bookService.updateBook(id,bookDTO);

        return ResponseEntity.ok("Kitap başarıyla güncellendi...");
    }

    //9- Get a Book By Its Author Using JPQL
    // http://localhost:8080/books/a?author=AB + GET
    @GetMapping("/a")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String author){

        List<Book> books=bookService.filterBooksByAuthor(author);

        return ResponseEntity.ok(books);
    }

    //10- Add a Book to an Owner
    // http://localhost:8080/books/add?book=3&owner=1


    //todo:exceptionhandler
    //todo:idsi verilen hangi üyede?





}
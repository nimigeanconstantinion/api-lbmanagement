package com.example.api.controller;

import com.example.api.model.Book;
import com.example.api.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin
public class BookController {


    BookRepo bookRepo;
    List<Book> biblio=new ArrayList<>();
    @Autowired
    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<Book> getBooks() {
        biblio=bookRepo.findAll();
        return biblio;

    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        System.out.println(book);
        this.bookRepo.save(book);
        return book ;
    }

    @PutMapping("/{iid}")
    @ResponseStatus(HttpStatus.OK)

    public void updateBook(@RequestBody Book nbook,@PathVariable Long iid) throws Exception {
       this.bookRepo.findById(iid).map(book->{
            book.setYear(nbook.getYear());
            book.setAuthor(nbook.getAuthor());
            book.setGenre(nbook.getGenre());
            book.setTitle(nbook.getTitle());
            return bookRepo.save(book);

       }).orElseThrow(()->new Exception("BOOK NOT FOUND"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(
            code=HttpStatus.OK
    )
    public void deleteBook(@PathVariable Long id){
        this.bookRepo.deleteById(id);
    }

    @GetMapping("/filt1/{titlu}/{autor}")
    @ResponseStatus(
            code=HttpStatus.OK
    )
    public List<Book> filtBook(@PathVariable String titlu,@PathVariable String autor) throws Exception {
        return this.bookRepo.findAll().stream().filter(b->b.getTitle().contains(titlu)&&b.getAuthor().contains(autor)).collect(Collectors.toList());
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/book/{id}")
    public Book getById(@PathVariable Long id){
        return this.bookRepo.findById(id).get();
    }
}

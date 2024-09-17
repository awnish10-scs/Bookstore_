package com.bookstore.controller;

import com.bookstore.model.Cart;
import com.bookstore.model.Book;
import com.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartRepository.save(cart);
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}/add")
    public Cart addToCart(@PathVariable Long id, @RequestBody Book book) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart != null) {
            List<Book> books = cart.getBooks();
            if (books == null) {
                books = new ArrayList<>();
            }
            books.add(book);
            cart.setBooks(books);
            return cartRepository.save(cart);
        }
        return null;
    }

    @PutMapping("/{id}/remove")
    public Cart removeFromCart(@PathVariable Long id, @RequestBody Book book) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart != null) {
            List<Book> books = cart.getBooks();
            if (books != null) {
                books.remove(book);
                cart.setBooks(books);
                return cartRepository.save(cart);
            }
        }
        return null;
    }

    @PutMapping("/{id}/update")
    public Cart updateCart(@PathVariable Long id, @RequestBody List<Book> books) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart != null) {
            cart.setBooks(books);
            return cartRepository.save(cart);
        }
        return null;
    }
}

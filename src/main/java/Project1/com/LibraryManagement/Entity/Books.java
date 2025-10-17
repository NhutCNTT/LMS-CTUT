package Project1.com.LibraryManagement.Entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.NumberFormat;
@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bookCode", nullable = false)
    private String bookCode;
    @Column(name = "bookName", nullable = false)
    private String bookName;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "ISBN", nullable = false)
    private String ISBN;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "price", nullable = false)
    @NumberFormat(pattern = "###.##")
    private double price;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "image",nullable = false)
    private String image;
    @Column(name = "description",nullable = false)
    private String description;


    public Books(){}

    public Books(String author, String bookCode, String bookName, String category, String description, String image, String ISBN, double price, int quantity, String status) {
        this.author = author;
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.category = category;
        this.description = description;
        this.image = image;
        this.ISBN = ISBN;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

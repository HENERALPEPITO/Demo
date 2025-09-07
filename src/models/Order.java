package models;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Date date;
    private String reference; // 5 random uppercase letters or alphanum
    private String productName;
    private BigDecimal productPrice;
    private int qty;
    private BigDecimal total;
    private String status; // FOR_DELIVERY or DELIVERED

    public Order() {}

    public Order(Date date, String reference, String productName, BigDecimal productPrice, int qty, BigDecimal total, String status) {
        this.date = date;
        this.reference = reference;
        this.productName = productName;
        this.productPrice = productPrice;
        this.qty = qty;
        this.total = total;
        this.status = status;
    }

    // getters / setters
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

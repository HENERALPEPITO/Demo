package ui;

import models.*;
import services.*;
import services_impl.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new AuthServiceImpl();
    private static final ProductService productService = new ProductServiceImpl();
    private static final OrderService orderService = new OrderServiceImpl();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy"); // matches sample

    public static void main(String[] args) {
        while (true) {
            showWelcome();
            String choice = prompt("What do you want to do? : ");
            if (choice.equals("1")) {
                loginScreen();
            } else if (choice.equals("0")) {
                System.out.println("Thank you for using our services!");
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private static void showWelcome() {
        System.out.println();
        System.out.println("***********************");
        System.out.println("* Welcome to My Shop! *");
        System.out.println("***********************");
        System.out.println("1 - Login");
        System.out.println(".......................");
        System.out.println("0 - Exit");
    }

    private static void loginScreen() {
        System.out.println();
        System.out.println("***********************");
        System.out.println("* LOGIN *");
        System.out.println("***********************");
        String username = prompt("Username : ");
        String password = prompt("Password : ");

        LoginModel lm = authService.authenticate(username, password);
        if (lm == null) {
            System.out.println("ERROR : Invalid credentials.");
            pressEnter();
            return;
        }

        if ("admin".equalsIgnoreCase(lm.getRole())) {
            adminHome();
        } else {
            customerHome();
        }
    }

    // ------------------ ADMIN -------------------
    private static void adminHome() {
        while (true) {
            System.out.println();
            System.out.println("***********************");
            System.out.println("* ADMINISTRATOR *");
            System.out.println("***********************");
            System.out.println("1 - Manage Products");
            System.out.println("2 - Manage Orders");
            System.out.println("........................");
            System.out.println("0 - Logout");
            String choice = prompt("What do you want to do : ");
            if (choice.equals("1")) manageProductsAdmin();
            else if (choice.equals("2")) manageOrdersAdmin();
            else if (choice.equals("0")) return;
            else System.out.println("Invalid input.");
        }
    }

    private static void manageProductsAdmin() {
        while (true) {
            System.out.println();
            System.out.println("***********************");
            System.out.println("* PRODUCTS *");
            System.out.println("***********************");
            List<Product> all = productService.getAll();
            if (all.isEmpty()) {
                System.out.println("No products found.");
            } else {
                System.out.println("ID Name Price");
                for (Product p : all) {
                    System.out.println(p.getId() + " " + p.getName() + " " + p.getPrice());
                }
            }
            System.out.println(".......................");
            System.out.println("1 - Add New Product");
            System.out.println("2 - Remove Product");
            System.out.println("0 - Back");
            String choice = prompt("What do you want to do : ");
            if (choice.equals("1")) addProduct();
            else if (choice.equals("2")) removeProduct();
            else if (choice.equals("0")) return;
            else System.out.println("Invalid input.");
        }
    }

    private static void addProduct() {
        String name;
        while (true) {
            name = prompt("Name : ");
            if (name == null || name.trim().isEmpty()) {
                System.out.println("ERROR: Invalid input.");
            } else if (productService.existsByName(name)) {
                // Advance requirement: reject duplicated name
                System.out.println("ERROR: Invalid product name.");
            } else break;
        }

        BigDecimal price;
        while (true) {
            String p = prompt("Price : ");
            if (p == null || p.trim().isEmpty()) {
                System.out.println("ERROR: Invalid input.");
                continue;
            }
            try {
                price = new BigDecimal(p.trim());
                break;
            } catch (NumberFormatException ex) {
                System.out.println("ERROR: Invalid number.");
            }
        }

        while (true) {
            String confirm = prompt("Are you sure you want to add this product (Y/N) : ");
            if ("Y".equalsIgnoreCase(confirm)) {
                productService.addProduct(name, price);
                System.out.println("Product added successfully!");
                pressEnter();
                return;
            } else if ("N".equalsIgnoreCase(confirm)) {
                System.out.println("Action canceled.");
                pressEnter();
                return;
            } else {
                System.out.println("ERROR: Invalid character.");
            }
        }
    }

    private static void removeProduct() {
        String idStr;
        int id;
        while (true) {
            idStr = prompt("Product ID : ");
            if (idStr == null || idStr.trim().isEmpty()) {
                System.out.println("ERROR: Invalid input.");
                continue;
            }
            try {
                id = Integer.parseInt(idStr.trim());
                break;
            } catch (NumberFormatException ex) {
                System.out.println("ERROR: Invalid number.");
            }
        }

        while (true) {
            String confirm = prompt("Are you sure you want to remove this product (Y/N) : ");
            if ("Y".equalsIgnoreCase(confirm)) {
                boolean removed = productService.removeProduct(id);
                if (removed) {
                    System.out.println("Product removed successfully!");
                } else {
                    System.out.println("Product not found.");
                }
                pressEnter();
                return;
            } else if ("N".equalsIgnoreCase(confirm)) {
                System.out.println("Action canceled.");
                pressEnter();
                return;
            } else {
                System.out.println("ERROR: Invalid character.");
            }
        }
    }

    // ------------------ ADMIN ORDERS -------------------
    private static void manageOrdersAdmin() {
        while (true) {
            System.out.println();
            System.out.println("***********************");
            System.out.println("* ORDERS *");
            System.out.println("***********************");
            List<Order> all = orderService.getAll();
            if (all.isEmpty()) {
                System.out.println("No orders found.");
                System.out.println(".......................");
                System.out.println("1 - Mark Order As Delivered");
                System.out.println("0 - Back");
                String choice = prompt("What do you want to do : ");
                if (choice.equals("1")) {
                    System.out.println("ERROR: Cannot proceed with action. No orders found.");
                } else if (choice.equals("0")) return;
                else System.out.println("Invalid input.");
            } else {
                System.out.println("Date Reference Name Price Qty Total Status");
                for (Order o : all) {
                    System.out.println(sdf.format(o.getDate()) + " " + o.getReference() + " " + o.getProductName() + " " +
                                       o.getProductPrice() + " " + o.getQty() + " " + o.getTotal() + " " + o.getStatus());
                }
                System.out.println(".......................");
                System.out.println("1 - Mark Order As Delivered");
                System.out.println("0 - Back");
                String choice = prompt("What do you want to do : ");
                if (choice.equals("1")) updateOrderStatus();
                else if (choice.equals("0")) return;
                else System.out.println("Invalid input.");
            }
        }
    }

    private static void updateOrderStatus() {
        String ref = prompt("Order Reference : ");
        if (ref == null || ref.trim().isEmpty()) {
            System.out.println("ERROR: Invalid input.");
            return;
        }
        Order o = orderService.findByReference(ref.trim());
        if (o == null) {
            System.out.println("ERROR: Order does not exist.");
            pressEnter();
            return;
        }

        while (true) {
            String confirm = prompt("Are you sure you want to mark this order as delivered (Y/N) : ");
            if ("Y".equalsIgnoreCase(confirm)) {
                o.setStatus("DELIVERED");
                System.out.println("Order updated successfully.");
                pressEnter();
                return;
            } else if ("N".equalsIgnoreCase(confirm)) {
                System.out.println("Action canceled.");
                pressEnter();
                return;
            } else {
                System.out.println("ERROR: Invalid character.");
            }
        }
    }

    // ------------------ CUSTOMER -------------------
    private static void customerHome() {
        while (true) {
            System.out.println();
            System.out.println("***********************");
            System.out.println("* CUSTOMER *");
            System.out.println("***********************");
            System.out.println("1 - Shop");
            System.out.println("2 - My Orders");
            System.out.println("........................");
            System.out.println("0 - Logout");
            String choice = prompt("What do you want to do : ");
            if (choice.equals("1")) shopScreen();
            else if (choice.equals("2")) myOrdersScreen();
            else if (choice.equals("0")) return;
            else System.out.println("Invalid input.");
        }
    }

    private static void shopScreen() {
        while (true) {
            System.out.println();
            System.out.println("***********************");
            System.out.println("* SHOP *");
            System.out.println("***********************");
            List<Product> all = productService.getAll();
            if (all.isEmpty()) {
                System.out.println("No products found.");
                System.out.println("........................");
                System.out.println("0 - Back");
                String choice = prompt("What do you want to order : ");
                if (choice.equals("0")) return;
                else System.out.println("ERROR: Cannot proceed with action. No products found.");
            } else {
                System.out.println("ID Name Price");
                for (Product p : all) {
                    System.out.println(p.getId() + " " + p.getName() + " " + p.getPrice());
                }
                System.out.println("........................");
                System.out.println("0 - Back");
                String choice = prompt("What do you want to order : ");
                if (choice.equals("0")) return;
                if (choice == null || choice.trim().isEmpty()) {
                    System.out.println("ERROR: Invalid input.");
                    continue;
                }
                try {
                    int id = Integer.parseInt(choice.trim());
                    Product p = productService.findById(id);
                    if (p == null) {
                        System.out.println("ERROR: Invalid input. Product does not exist.");
                        continue;
                    }
                    placeOrderScreen(p);
                } catch (NumberFormatException ex) {
                    System.out.println("ERROR: Invalid input. Product does not exist.");
                }
            }
        }
    }

    private static void placeOrderScreen(Product p) {
        System.out.println();
        System.out.println("***********************");
        System.out.println("* PLACE ORDER *");
        System.out.println("***********************");
        System.out.println("Name : " + p.getName());
        System.out.println("Price : Php " + p.getPrice());
        String qtyStr;
        int qty;
        while (true) {
            qtyStr = prompt("How many do you want : ");
            if (qtyStr == null || qtyStr.trim().isEmpty()) {
                System.out.println("ERROR: Invalid input.");
                continue;
            }
            try {
                qty = Integer.parseInt(qtyStr.trim());
                break;
            } catch (NumberFormatException ex) {
                System.out.println("ERROR: Invalid number.");
            }
        }

        BigDecimal total = p.getPrice().multiply(new BigDecimal(qty));
        while (true) {
            System.out.println("That would be Php " + total + ".");
            String confirm = prompt("Proceed with your order (Y/N) : ");
            if ("Y".equalsIgnoreCase(confirm)) {
                Order o = new Order(new Date(), generateReference(), p.getName(), p.getPrice(), qty, total, "FOR_DELIVERY");
                orderService.add(o);
                System.out.println("Order placed successfully.");
                pressEnter();
                return;
            } else if ("N".equalsIgnoreCase(confirm)) {
                System.out.println("Action canceled.");
                pressEnter();
                return;
            } else {
                System.out.println("ERROR: Invalid character.");
            }
        }
    }

    private static void myOrdersScreen() {
        System.out.println();
        System.out.println("***********************");
        System.out.println("* MY ORDERS *");
        System.out.println("***********************");
        List<Order> all = orderService.getAll();
        if (all.isEmpty()) {
            System.out.println("No orders found.");
            pressEnter();
            return;
        } else {
            System.out.println("Date Reference Name Price Qty Total Status");
            for (Order o : all) {
                System.out.println(sdf.format(o.getDate()) + " " + o.getReference() + " " + o.getProductName() + " " +
                                   o.getProductPrice() + " " + o.getQty() + " " + o.getTotal() + " " + o.getStatus());
            }
            pressEnter();
        }
    }

    // ------------------ utilities -------------------
    private static String prompt(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }

    private static void pressEnter() {
        System.out.print("Press \"ENTER\" to continue...");
        try {
            System.in.read();
            // consume rest of line if any
            scanner.nextLine();
        } catch (Exception e) {
            // ignore
        }
    }

    private static String generateReference() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) sb.append(chars.charAt(r.nextInt(chars.length())));
        return sb.toString();
    }
}

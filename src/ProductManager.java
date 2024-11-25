import java.io.*;
import java.util.Scanner;

public class ProductManager {
    private static final String FILE_NAME = "products.dat";
    public static void addProduct(Product product) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME, true))) {
            oos.writeObject(product);
            System.out.println("Product added: " + product.getProductName());
        } catch (IOException e) {
            System.out.println("Error adding Product: " + e.getMessage());
        }
    }
    public static void showAllProducts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            while (true) {
                Product product = (Product) ois.readObject();
                System.out.println(product);
            }
        } catch (EOFException e) {
            //Do nothing ai the end of file
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading product: " + e.getMessage());
        }
    }
    public static void searchProductByCode(String productCode) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            boolean found = false;
            while (true) {
                Product product = (Product) ois.readObject();
                if (product.getProductCode().equalsIgnoreCase(productCode)) {
                    System.out.println("Product found: " + product);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Cannot find product with code: " + productCode);
            }
        } catch (EOFException e) {
            System.out.println("Cannot find product with code: " + productCode);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error finding product: " + e.getMessage());
        }
    }

    // Phương thức để nhập thông tin sản phẩm từ người dùng
    public static Product inputProductInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter code: ");
        String code = sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter price: ");
        double price = sc.nextDouble();
        sc.nextLine();  // Đọc bỏ newline
        System.out.print("Enter manufacturer: ");
        String manufacturer = sc.nextLine();
        System.out.print("Enter description: ");
        String description = sc.nextLine();
        return new Product(code, name, price, manufacturer, description);
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("----- Product management -----");
            System.out.println("1. Add Product");
            System.out.println("2. Display All Products");
            System.out.println("3. Find product by Code");
            System.out.println("4. Exit");
            System.out.print("Your selection: ");
            int choice = sc.nextInt();
            sc.nextLine();  // Đọc bỏ newline
            switch (choice) {
                case 1:
                    Product product = inputProductInfo();
                    addProduct(product);
                    break;
                case 2:
                    showAllProducts();
                    break;
                case 3:
                    System.out.print("Enter product code: ");
                    String code = sc.nextLine();
                    searchProductByCode(code);
                    break;
                case 4:
                    System.out.println("Exit.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

package Model;

public class Order_temp_list {
    private String productname;
    private String quantity;
    private String subtotal;

    public Order_temp_list(String productname, String quantity, String subtotal) {
        this.productname = productname;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public String getProductname() {
        return productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSubtotal() {
        return subtotal;
    }
}



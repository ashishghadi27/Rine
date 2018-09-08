package Model;

public class Orders_list {
    private String order_id;
    private String date;
    private String status;
    private String total;
    private String product;
    private String quantity;
    private String subtotal;
    private String payment_method;
    private String address_1;
    private String address_2;
    private String city;
    private String postcode;

    public Orders_list(String order_id, String date, String status, String total, String product, String quantity, String subtotal, String payment_method, String address_1, String address_2, String city, String postcode) {
        this.order_id = order_id;
        this.date = date;
        this.status = status;
        this.total = total;
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.payment_method = payment_method;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.city = city;
        this.postcode = postcode;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getTotal() {
        return total;
    }

    public String getProduct() {
        return product;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public String getAddress_1() {
        return address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }
}

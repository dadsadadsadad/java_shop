public class Fruits
{
    public String fruitname;
    public double fruitprice;
    public int fruitquantity;

    public Fruits(String name, double price, int quantity)
    {
        this.fruitname = name;
        this.fruitprice = price;
        this.fruitquantity = quantity;
    }

    public String getName()
    {
        return fruitname;
    }

    public void setName(String name)
    {
        this.fruitname = name;
    }

    public double getPrice()
    {
        return fruitprice;
    }

    public void setPrice(double price)
    {
        this.fruitprice = price;
    }

    public int getQuantity()
    {
        return fruitquantity;
    }

    public void setQuantity(int quantity)
    {
        this.fruitquantity = quantity;
    }
}

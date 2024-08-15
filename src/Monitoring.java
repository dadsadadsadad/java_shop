import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Monitoring
{
    static String FP = "inventory.txt";

    public void load(ArrayList<Fruits> fruit, int fruitnum)
    {

        File InFile = new File(FP);
        String name;
        double price = 0;
        int quantity = 0;

        if (InFile.length() != 0)
        {
            fruit.clear();
            try
            {
                Scanner scan = new Scanner(InFile);
                while (scan.hasNextLine())
                {
                    name = scan.nextLine();
                    price = Double.parseDouble(scan.nextLine());
                    quantity = Integer.parseInt(scan.nextLine());

                    Fruits fruits = new Fruits(name, price, quantity);
                    fruit.add(fruits);
                }
                System.out.println("|Inventory loaded successfully.");
            }
            catch (IOException e)
            {
                System.out.println("Failed to load.");
            }
        }
    }

    public void display(ArrayList<Fruits> fruit, int fruitnum)
    {
        System.out.println("|-------------------------------");
        System.out.println("|Current Inventory:            ");
        System.out.println("|-------------------------------");
        for (int i = 0; i < fruitnum; i++)
        {
            Fruits fruits = fruit.get(i);
            System.out.print(fruits.getQuantity());
            if (fruits.getQuantity() == 1)
            {
                System.out.print(" pc ");
            }
            else
            {
                System.out.print(" pcs ");
            }
            System.out.print("of " + fruits.getName());
            System.out.println(" ₱" + fruits.getPrice() + "/pc");
            System.out.println("|-------------------------------");
        }
    }

    public void update(ArrayList<Fruits> fruit, ArrayList<Integer> fruitindex, ArrayList<Integer> buyamount,int fruitnum)
    {
        int index = 0;
        for (int i : fruitindex)
        {
            Fruits fruits = fruit.get(i);
            if (i >= 0 && i < fruitnum && fruits.getQuantity() > 0)
            {
                fruits.setQuantity(fruits.getQuantity() - buyamount.get(index));
                save(fruit, fruitnum);
                index++;
            }
            else
            {
                System.out.println("Invalid input.");
            }
        }
        System.out.println("|Inventory updated successfully.");
    }

    public void save(ArrayList<Fruits> fruit, int fruitnum)
    {
        try
        {
            FileWriter Wr = new FileWriter(FP);
            for (int i = 0; i < fruitnum; i++)
            {
                Fruits fruits = fruit.get(i);
                Wr.write(fruits.getName() + "\n" + fruits.getPrice() + "\n" + fruits.getQuantity() + "\n");
            }
            Wr.close();
            System.out.println("|--------------------------------");
            System.out.println("|Inventory saved to file: " + FP);
        }
        catch (IOException e)
        {
            System.out.println("\nFailed to save.");
        }
    }

    public void lowstocks(ArrayList<Fruits> fruit, int fruitnum)
    {
        System.out.println("\n|Low Stock Items:");
        System.out.println("|-------------------------------");
        for (int i = 0; i < fruitnum; i++)
        {
            Fruits fruits = fruit.get(i);
            if (fruits.fruitquantity <= 20)
            {
                System.out.println("|" + fruits.fruitname + ": " + fruits.fruitquantity);
                System.out.println("|-------------------------------");
            }
        }
    }
}
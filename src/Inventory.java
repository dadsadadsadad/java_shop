import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Inventory
{
    Monitoring monitoring = new Monitoring();

    static String ADMIN = "admin.txt";
    static String CASH = "cash.txt";
    static String STOCKS = "stocks.txt";
    ArrayList<String> FRUITS = new ArrayList<>();
    ArrayList<Double> PRICE = new ArrayList<>();

    public double loadmoney()
    {
        double adminmoney = 0;
        File InFile = new File(ADMIN);

        if (InFile.length() != 0)
        {
            try
            {
                Scanner scan = new Scanner(InFile);
                String content = scan.nextLine();
                adminmoney = Double.parseDouble(content.trim());
                System.out.println("|Inventory loaded successfully.");
            }
            catch (IOException e)
            {
                System.out.println("Failed to load.");
            }
        }
        return adminmoney;
    }

    public void savemoney(double adminmoney)
    {
        try
        {
            FileWriter Wr = new FileWriter(ADMIN);
            Wr.write(" " + adminmoney);
            Wr.close();
            System.out.println("Money saved.");
        }
        catch (IOException e)
        {
            System.out.println("\nFailed to save.");
        }
    }

    public void savestockshop(ArrayList<Fruits> fruit, int fruitnum)
    {
        try
        {
            FileWriter Wr = new FileWriter(STOCKS);
            for (int i = 0; i < fruitnum; i++)
            {
                Fruits fruits = fruit.get(i);
                Wr.write(fruits.getName() + "\n" + (fruits.getPrice() - 10) + "\n");
            }
            Wr.close();
            System.out.println("|--------------------------------");
            System.out.println("|Inventory saved to file: " + STOCKS);
        }
        catch (IOException e)
        {
            System.out.println("\nFailed to save.");
        }
    }

    public void loadstockshop(int fruitnum)
    {
        File InFile = new File(STOCKS);
        if (InFile.length() != 0)
        {
            try
            {
                Scanner scan = new Scanner(InFile);
                for (int i = 0; i < fruitnum; i++)
                {
                    FRUITS.add(scan.nextLine());
                    PRICE.add(Double.parseDouble(scan.nextLine()));
                }
                scan.close();
            }
            catch (IOException e)
            {
                System.out.println("Failed to load.");
            }
        }
    }

    public void buystocks(ArrayList<Fruits> fruit, int fruitnum, double adminmoney)
    {
        loadstockshop(fruitnum);

        Scanner scan = new Scanner(System.in);
        int choice = 0, amount = 0;
        double lowest = PRICE.get(0), total = 0;
        String restart;

        do
        {
            for (int i = 0; i < fruitnum; i++)
            {
                System.out.println((i + 1) + ". " + FRUITS.get(i) + " ₱" + PRICE.get(i) + "/pc");
            }

            for (int i = 0; i < fruitnum; i++)
            {
                if (lowest > PRICE.get(i))
                {
                    lowest = PRICE.get(i);
                }
            }

            if (adminmoney < lowest)
            {
                System.out.println("You don't have enough money to buy stocks.");
                return;
            }

            do
            {
                try
                {
                    System.out.println("Which item would you like to buy?");
                    choice = scan.nextInt();
                    choice -= 1;

                    if (choice >= fruitnum || choice < 0)
                    {
                        System.out.println("Invalid choice.");
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println("Invalid choice.");
                    scan.next();
                }
            } while (choice >= fruitnum || choice < 0);

            Fruits fruits = fruit.get(choice);

            do
            {
                try
                {
                    System.out.println("How many would you like to buy?");
                    amount = scan.nextInt();

                    total = (PRICE.get(choice) * amount);

                    if (adminmoney < total) {
                        System.out.println("You do not have enough money for this amount of stocks.");
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println("Invalid choice.");
                    scan.next();
                }
            } while (adminmoney < total);

            fruits.setQuantity(fruits.getQuantity() + amount);
            adminmoney -= (total);
            savemoney(adminmoney);
            savecashout(amount, choice, total);

            scan.nextLine();
            do
            {
                System.out.println("Buy more? (YES / NO)");
                restart = scan.nextLine();

                if (!restart.equalsIgnoreCase("yes") && !restart.equalsIgnoreCase("no"))
                {
                    System.out.println("Yes or No only.");
                }
            } while (!restart.equalsIgnoreCase("yes") && !restart.equalsIgnoreCase("no"));

        } while (restart.equalsIgnoreCase("YES"));

        totalcash(adminmoney);
        monitoring.save(fruit, fruitnum);
    }

    public void addnewitem(ArrayList<Fruits> fruit)
    {
        Scanner scan = new Scanner(System.in);
        String restart, name;
        double price;

        do
        {
            System.out.println("What fruit would you like to add?");
            name = scan.nextLine();

            System.out.println("Enter price:");
            price = scan.nextDouble();

            Fruits newFruit = new Fruits(name, price, 0);
            fruit.add(newFruit);

            scan.nextLine();
            do
            {
                System.out.println("Add more? (YES / NO)");
                restart = scan.nextLine();

                if (!restart.equalsIgnoreCase("yes") && !restart.equalsIgnoreCase("no"))
                {
                    System.out.println("Yes or No only.");
                }
            } while (!restart.equalsIgnoreCase("yes") && !restart.equalsIgnoreCase("no"));
        } while (restart.equalsIgnoreCase("YES"));
    }

    public void savecashin(ArrayList<Fruits> fruit, ArrayList<Integer> buyamount, ArrayList<Integer> fruitindex, int index, int i)
    {
        try
        {
            File cash = new File("cash.txt");
            FileWriter cashier = new FileWriter(cash, true);

                Fruits fruits = fruit.get(index);
                cashier.write("- ");
                cashier.write(Integer.toString(buyamount.get(i)));
                if (buyamount.get(i) == 1)
                {
                    cashier.write(" pc ");
                }
                else
                {
                    cashier.write(" pcs ");
                }
                cashier.write("of " + fruits.getName());

                cashier.write("\t + ₱" + (buyamount.get(i) * fruits.getPrice()) + "\n\n");
                i++;
                cashier.close();
        }
        catch (IOException e)
        {
            System.out.println("Failed to load.");
        }
    }

    public void savecashout(int amount, int index, double total)
    {
        try
        {
            File cash = new File(CASH);
            FileWriter cashier = new FileWriter(cash, true);

            cashier.write("+ ");
            cashier.write(Integer.toString(amount));
            if (amount == 1)
            {
                cashier.write(" pc ");
            }
            else
            {
                cashier.write(" pcs ");
            }
            cashier.write("of " + FRUITS.get(index));

            cashier.write("\t - ₱" + total + "\n\n");
            cashier.close();
        }
        catch (IOException e)
        {
            System.out.println("Failed to load.");
        }
    }

    public void totalcash(double adminmoney)
    {
        try
        {
            File cash = new File(CASH);
            FileWriter cashier = new FileWriter(cash, true);

            cashier.write("TOTAL :" + adminmoney + "\n\n");
            cashier.close();
        }
        catch (IOException e)
        {
            System.out.println("Failed to load.");
        }
    }

    public void displaycash()
    {
        String temp;
        File InFile = new File(CASH);

        System.out.println();
        if (InFile.length() != 0)
        {
            try
            {
                Scanner scan = new Scanner(InFile);

                while (scan.hasNextLine())
                {
                    temp = scan.nextLine();
                    System.out.println(temp);
                }
                scan.close();

            }
            catch (IOException e)
            {
                System.out.println("Failed to load.");
            }
        }
    }
}
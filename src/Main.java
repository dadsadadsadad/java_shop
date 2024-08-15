import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        Login log = new Login();
        POS pos = new POS();
        Inventory inventory = new Inventory();
        Monitoring monitoring = new Monitoring();

        ArrayList<Fruits> fruits = new ArrayList<>();
        ArrayList<Integer> fruitindex = new ArrayList<>();
        ArrayList<Integer> buyamount = new ArrayList<>();
        String username, password, restart;
        int fruitnum = 0, numorder = 0, loginchoice = 0, menuchoice = 0, index = 0;
        double total = 0, tax = 0, discount = 0, finalprice = 0, adminmoney = 0;
        boolean loggedin = false, isadmin = false;

        fruits.add(new Fruits("Apple", 50, 100));
        fruits.add(new Fruits("Banana", 30, 50));
        fruits.add(new Fruits("Mango", 40, 65));
        fruits.add(new Fruits("Pineapple", 50, 80));
        fruits.add(new Fruits("Watermelon", 60, 20));

        monitoring.load(fruits, fruitnum);
        fruitnum = fruits.size();
        adminmoney = inventory.loadmoney();
        monitoring.save(fruits, fruitnum);

        do
        {
            System.out.println("1. Login");
            System.out.println("2. Create new user");
            System.out.println("3. Login as admin");
            loginchoice = scan.nextInt();
            scan.nextLine();
            switch (loginchoice)
            {
                case 1:
                    System.out.println("Enter your username: ");
                    username = scan.nextLine();
                    System.out.println("Enter your password: ");
                    password = scan.nextLine();
                    loggedin = log.login(username, password);
                    break;
                case 2:
                    do
                    {
                        System.out.println("Enter your username: ");
                        username = scan.nextLine();
                        System.out.println("Enter your password: ");
                        password = scan.nextLine();
                        loggedin = log.createnew(username, password);
                        if (!loggedin)
                        {
                            System.out.println("A username like that already exists, choose a new one.");
                        }
                    } while (!loggedin);
                    break;
                case 3:
                    System.out.println("Enter your username: ");
                    username = scan.nextLine();
                    System.out.println("Enter your password: ");
                    password = scan.nextLine();
                    if (username.equals("admin") && password.equals("admin1"))
                    {
                        System.out.println("Welcome admin!");
                        loggedin = true;
                        isadmin = true;
                        break;
                    }
                    else
                    {
                        System.out.println("Wrong username or password.");
                        break;
                    }
                default:
                    System.out.println("Choose 1 - 3 only.");
                    break;
            }
        } while (!loggedin);

        if (isadmin)
        {
            do
            {
                System.out.println("1. VIEW STOCKS");
                System.out.println("2. BUY STOCKS");
                System.out.println("3. ADD NEW PRODUCT");
                System.out.println("4. VIEW CASH IN/OUT");
                System.out.println("5. EXIT");

                menuchoice = scan.nextInt();
                scan.nextLine();

                inventory.savestockshop(fruits, fruitnum);

                switch (menuchoice)
                {
                    case 1:
                        monitoring.display(fruits, fruitnum);
                        restart = restartchoice();
                        break;
                    case 2:
                        inventory.buystocks(fruits, fruitnum, adminmoney);
                        restart = restartchoice();
                        break;
                    case 3:
                        inventory.addnewitem(fruits);
                        fruitnum = fruits.size();
                        inventory.savestockshop(fruits, fruitnum);
                        monitoring.save(fruits, fruitnum);
                        restart = restartchoice();
                        break;
                    case 4:
                        inventory.displaycash();
                        restart = restartchoice();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Choose 1 - 5 only.");
                        restart = restartchoice();
                        break;
                }
            } while(restart.equalsIgnoreCase("yes"));
        }
        else
        {
            monitoring.load(fruits, fruitnum);
            fruitnum = fruits.size();

            pos.ShopMenu(fruits, fruitnum, fruitindex, buyamount, numorder);

            for (int i : fruitindex)
            {
                Fruits fruita = fruits.get(i);
                total += (fruita.getPrice() * buyamount.get(index));
                index++;
            }

            if (pos.isPWD())
            {
                discount = total * 0.2;
            }
            else
            {
                tax = total * 0.1;
            }

            finalprice = total + (tax - discount);

            pos.ShowReceipt(fruits, buyamount, fruitindex, tax, discount, total, finalprice);
            adminmoney += pos.Payment(finalprice);

            pos.ReceiptFile(fruits, buyamount, fruitindex, tax, discount, total, finalprice, adminmoney);
            monitoring.lowstocks(fruits, fruitnum);

            monitoring.update(fruits, fruitindex, buyamount, fruitnum);
            inventory.savemoney(adminmoney);
        }
    }

    public static String restartchoice()
    {
        String choice;
        Scanner scan = new Scanner(System.in);

        do
        {
            System.out.println("Go back to the menu? (YES / NO)");
            choice = scan.nextLine();
        }
        while (!choice.equalsIgnoreCase("YES") && !choice.equalsIgnoreCase("NO"));

        return choice;
    }
}
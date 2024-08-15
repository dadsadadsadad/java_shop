import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class POS
{

    public void ShopMenu(ArrayList<Fruits> fruit, int fruitnum, ArrayList<Integer> fruitindex, ArrayList<Integer> buyamount, int numorder)
    {
        Scanner scan = new Scanner(System.in);
        String buyagain;
        int index = -1, input = 0, temp = 0;

        System.out.println("\n\n\n\n\n________________________________");
        System.out.println("Available fruits:");
        for (int i = 0; i < fruitnum; i++)
        {
            Fruits fruits = fruit.get(i);
            System.out.print((i + 1) + ". ");//
            System.out.print(fruits.getQuantity());
            if (fruits.getQuantity() == 1)
            {
                System.out.print(" pc ");
            }
            else
            {
                System.out.print(" pcs ");
            }
            System.out.println("of " + fruits.getName() + " -  ₱" + fruits.getPrice() + "/pc");
        }

        do
        {
            boolean isValid = false;
            System.out.println("________________________________");
            do
            {
                try
                {
                    System.out.print("Enter fruit number: ");
                    input = scan.nextInt() - 1;
                    if (input < fruitnum && input >= 0)
                    {
                        Fruits fruits = fruit.get(input);
                        if (fruits.getQuantity() > 0)
                        {
                            fruitindex.add(input);
                            isValid = true;
                            index++;
                        }
                        else
                        {
                            System.out.println("There is not enough stock of this product.");
                        }
                    }
                    else
                    {
                        System.out.println("Choose an appropriate number.");
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println("Invalid choice.");
                    scan.next();
                }
            } while (!isValid);

            Fruits fruits = fruit.get(fruitindex.get(index));
            do
            {
                try
                {
                    System.out.print("Enter quantity: ");
                    temp = scan.nextInt();
                }
                catch (InputMismatchException e)
                {
                    System.out.println("Invalid choice.");
                    scan.next();
                }
            } while (temp > fruits.getQuantity());

            buyamount.add(temp);
            numorder++;
            System.out.println("________________________________");
            do
            {
                System.out.println("Purchase another fruit? (YES/NO): ");
                buyagain = scan.next();
                if (!buyagain.equalsIgnoreCase("yes") && !buyagain.equalsIgnoreCase("no"))
                {
                    System.out.println("Yes or No only.");
                }
            } while (!buyagain.equalsIgnoreCase("yes") && !buyagain.equalsIgnoreCase("no"));
        } while (buyagain.equalsIgnoreCase("yes"));
    }

    public boolean isPWD()
    {
        Scanner scan = new Scanner(System.in);
        String PWD;

        do
        {
            System.out.println("Does this person have a disability? (PWD)? (Yes/No) ");
            PWD = scan.nextLine();

            if (PWD.equalsIgnoreCase("yes"))
            {
                return true;
            }
            else if (PWD.equalsIgnoreCase("no"))
            {
                return false;
            }
            else
            {
                System.out.println("Yes or No only.");
            }
        } while (!PWD.equalsIgnoreCase("yes") && !PWD.equalsIgnoreCase("no"));
        return false;
    }

    public double Payment(double finalprice)
    {
        Scanner scan = new Scanner(System.in);
        int MOP = 0;
        double amountofmoney = 0, change = 0, adminmoney = 0;

        do
        {
            try
            {
                System.out.println("Select mode of payment: ");
                System.out.println("1. Cash");
                System.out.println("2. Card");
                System.out.println("3. Gcash");

                MOP = scan.nextInt();

                if (MOP > 3 || MOP <= 0)
                {
                    System.out.println("Invalid choice.");
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("Invalid choice.");
                scan.next();
            }
        } while (MOP > 3 || MOP <= 0);

        switch (MOP)
        {
            case 1:
                System.out.println("********************************");
                do
                {
                    try
                    {
                        System.out.print("Enter amount :  ₱");
                        amountofmoney = scan.nextDouble();
                        if (amountofmoney < finalprice)
                        {
                            System.out.println("Insufficient amount.");
                        }
                        else
                        {
                            change = amountofmoney - finalprice;
                            System.out.println("Change:  ₱" + change);
                            System.out.println("********************************");
                            adminmoney = finalprice;
                        }
                    }
                    catch (InputMismatchException e)
                    {
                        System.out.println("Invalid.");
                        scan.next();
                    }
                } while (amountofmoney < finalprice);
                break;
            case 2:
                System.out.println("********************************");
                System.out.println("Card payment processed.");
                System.out.println("********************************");
                break;
            case 3:
                System.out.println("********************************");
                System.out.println("Gcash payment processed.");
                System.out.println("********************************");
                break;
        }
        return adminmoney;
    }

    public void ShowReceipt(ArrayList<Fruits> fruit, ArrayList<Integer> buyamount, ArrayList<Integer> fruitindex, double tax, double discount, double total, double finalprice)
    {
        int i = 0;
        for (int index : fruitindex)
        {
            Fruits fruits = fruit.get(index);
            System.out.println("********************************");
            System.out.println("Receipt:");
            System.out.print(buyamount.get(i));
            if (buyamount.get(i) == 1)
            {
                System.out.print(" pc ");
            }
            else
            {
                System.out.print(" pcs ");
            }
            System.out.print("of " + fruits.getName());
            System.out.println(" ₱" + fruits.getPrice() + "/pc");
            System.out.println("Total: ₱" + (buyamount.get(i) * fruits.getPrice()));
            i++;
        }
        System.out.println("********************************");
        System.out.println("Total Price:  ₱" + total);
        System.out.println("Tax:  ₱" + tax);
        System.out.println("Discount: ₱" + discount);
        System.out.println("Final Price:  ₱" + finalprice);
        System.out.println("********************************");
    }

    public void ReceiptFile(ArrayList<Fruits> fruit, ArrayList<Integer> buyamount, ArrayList<Integer> fruitindex, double tax, double discount, double total, double finalprice, double adminmoney)
    {
        Inventory inventory = new Inventory();
        try
        {
            int i = 0;
            File receipt = new File("receipt.txt");
            FileWriter resibo = new FileWriter(receipt, true);
            for (int index : fruitindex)
            {
                Fruits fruits = fruit.get(index);
                resibo.write("********************************\n");
                resibo.write("Receipt:\n");
                resibo.write(Integer.toString(buyamount.get(i)));
                if (buyamount.get(i) == 1)
                {
                    resibo.write(" pc ");
                }
                else
                {
                    resibo.write(" pcs ");
                }
                resibo.write("of " + fruits.getName());
                resibo.write(" ₱" + fruits.getPrice() + "/pc\n" );
                resibo.write("Total: ₱" + (buyamount.get(i) * fruits.getPrice() + "\n"));
                inventory.savecashin(fruit, buyamount, fruitindex, index, i);
                i++;
            }
            inventory.totalcash(adminmoney);
            resibo.write("********************************\n");
            resibo.write("Total Price:  ₱" + total + "\n");
            resibo.write("Tax:  ₱" + tax + "\n");
            resibo.write("Discount: ₱" + discount + "\n");
            resibo.write("Final Price:  ₱" + finalprice + "\n");
            resibo.write("********************************\n\n");
            resibo.close();
            System.out.println("\n|--------------------------------");
            System.out.println("|Receipt saved to file: " + receipt.getAbsolutePath());
        }
        catch (IOException e)
        {
            System.out.println("\nFailed to save receipt to file.");
        }
    }
}
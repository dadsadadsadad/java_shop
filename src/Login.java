import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Login
{
    String LogFile = "login.txt";

    String username;
    String password;

    public void readlogfile()
    {
        try
        {
            File InFile = new File(LogFile);
            Scanner scan = new Scanner(InFile);
            if (scan.hasNextLine())
            {
                username = scan.next();
                if (scan.hasNextLine())
                {
                    password = scan.next();
                    System.out.println("|Inventory loaded successfully.");
                }
            }
            else
            {
                System.out.println("Login data not found.");
            }
            scan.close();
        }
        catch (IOException e)
        {
            System.out.println("Failed to load.");
        }
    }

    public void savelogfile(String user, String pass)
    {
        try
        {
            FileWriter FW = new FileWriter(LogFile);
            FW.write(user + "\n");
            FW.write(pass);
            FW.close();
            System.out.println("|--------------------------------");
            System.out.println("|Login data saved to file: " + LogFile);
        }
        catch (IOException e)
        {
            System.out.println("\nFailed to save.");
        }
    }

    public boolean login(String user, String pass)
    {
        String tempuser, temppass;
        boolean duplicate = false;

        try
        {
            File Log = new File(LogFile);
            Scanner scan = new Scanner(Log);
            while (scan.hasNextLine())
            {
                tempuser = scan.nextLine();
                temppass = scan.nextLine();
                if (tempuser.equals(user) && temppass.equals(pass))
                {
                    duplicate = true;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while saving the data: " + e.getMessage());
        }

        if (duplicate)
        {
            System.out.println("Login successful.");
            return true;
        }
        else
        {
            System.out.println("Incorrect username or password.");
            return false;
        }
    }

    public boolean createnew(String user, String pass)
    {
        String tempuser, temppass;
        boolean duplicate = false;

        try
        {
            File Log = new File(LogFile);
            Scanner scan = new Scanner(Log);
            while (scan.hasNextLine())
            {
                tempuser = scan.nextLine();
                temppass = scan.nextLine();
                if (tempuser.equals(user) || user.equals("admin1"))
                {
                    duplicate = true;
                }
            }
            scan.close();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while saving the data: " + e.getMessage());
        }

        if (!duplicate)
        {
            try
            {
                FileWriter writer = new FileWriter(LogFile, true);
                writer.write(user + "\n");
                writer.write(pass + "\n");
                writer.close();
                System.out.println("New user created.");
                return true;
            }
            catch (IOException e)
            {
                System.out.println("An error occurred while saving the data: " + e.getMessage());
            }
        }
        return false;
    }
}

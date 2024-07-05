package views;

import dao.userDAO;
import model.User;
import service.GenerateOTP;
import service.sendOTP;
import service.userService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen(){

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("WELCOME TO THE APP!");
        System.out.println("PRESS 1 TO LOGIN");
        System.out.println("PRESS 2 TO SIGNUP");
        System.out.println("PRESS 0 TO EXIT");
        int choice = 0;
        try{
            choice = Integer.parseInt(br.readLine());
        }catch (IOException e){
            e.printStackTrace();
        }
        switch (choice){
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }
    }

    public void login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER YOUR EMAIL : ");
        String email = sc.nextLine();
        try{
            if(userDAO.doesExist(email)){
                String OTP = GenerateOTP.getOTP();
                sendOTP.sendOTP(email, OTP);
                System.out.println("ENTER THE OTP : ");
                String sentOtp = sc.nextLine();
                if(sentOtp.equals(OTP)){
                    new userView(email).home();

                }
                else{
                    System.out.println("WRONG OTP.");
                }
            }
            else{
                System.out.println("USER DOESN'T EXIST!");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void signUp(){
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER NAME");
        String name = sc.nextLine();
        System.out.println("ENTER EMAIL");
        String email = sc.nextLine();
        String OTP = GenerateOTP.getOTP();
        sendOTP.sendOTP(email, OTP);
        System.out.println("ENTER THE OTP : ");
        String SignUpOtp = sc.nextLine();
        if(SignUpOtp.equals(OTP)){
            User user = new User(name, email);
            Integer response = userService.saveUser(user);
            switch (response){
                case 0 : System.out.println("USER REGISTERED!");
                break;
                case 1 : System.out.println("USER ALREADY EXISTS!");
                break;
            }
        }
        else{
            System.out.println("WRONG OTP.");
        }
    }
}

package views;

import dao.dataDAO;
import model.Data;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class userView {
    private String email;
    userView(String email){
        this.email = email;
    }
    public void home(){
        do {
            System.out.println("WELCOME "+ this.email +"!");
            System.out.println("PRESS 1 TO SHOW HIDDEN FILES.");
            System.out.println("PRESS 2 TO HIDE A NEW FILE.");
            System.out.println("PRESS 3 TO UN-HIDE A FILE");
            System.out.println("PRESS 0 TO EXIT.");
            Scanner sc = new Scanner(System.in);
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1 -> {
                    try{
                        List<Data> files = dataDAO.getAllFiles(this.email);
                        System.out.println("ID - FILE NAME");
                        for(Data file : files){
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("ENTER THE PATH :");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        dataDAO.hideFile(file);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    List<Data> files = null;
                    try {
                        files = dataDAO.getAllFiles(this.email);
                    System.out.println("ID - FILE NAME");
                    for(Data file : files){
                        System.out.println(file.getId() + " - " + file.getFileName());
                    }
                    System.out.println("ENTER FILE ID TO UNHIDE.");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean isValidId = false;
                    for(Data file : files){
                        if(file.getId()==id){
                            isValidId = true;
                            break;
                        }
                    }
                    if(isValidId){
                        dataDAO.unHide(id);
                    }
                    else{
                        System.out.println("INVALID ID");
                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                case 0 -> {
                    System.exit(0);
                }
            }
        }
        while(true);
    }
}

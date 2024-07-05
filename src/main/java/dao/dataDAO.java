package dao;

import db.MyConnection;
import model.Data;

import javax.xml.transform.Result;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class dataDAO {
     public static List<Data> getAllFiles(String email) throws Exception
     {
         Connection connection = MyConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement("SELECT * FROM DATA WHERE EMAIL = ?");
         ps.setString(1, email);
         ResultSet rs = ps.executeQuery();
         List<Data> files = new ArrayList<>();
         while(rs.next()){
             int id = rs.getInt(1);
             String name = rs.getString(2);
             String path = rs.getString(3);
             files.add(new Data(id, name, path));
         }
         return files;
     }

     public static int hideFile(Data file)throws Exception{
         Connection connection = MyConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement("INSERT INTO DATA(FILE_NAME, PATH, EMAIL, BIN_DATA ) VALUES(?,?,?,?)");
         ps.setString(1, file.getFileName());
         ps.setString(2, file.getPath());
         ps.setString(3, file.getEmail());
         File f = new File(file.getPath());
         FileReader fr = new FileReader(f);
         ps.setCharacterStream(4, fr, f.length());
         int ans = ps.executeUpdate();
         fr.close();
         f.delete();
         return ans;
     }


     public static void unHide(int id ) throws Exception {
         Connection connection = MyConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement("SELECT PATH, BIN_DATA FROM DATA WHERE ID = ?");
         ps.setInt(1, id);
         ResultSet rs = ps.executeQuery();
         rs.next();
         String path = rs.getString("path");
         Clob clob = rs.getClob("bin_data");

         Reader r = clob.getCharacterStream();
         FileWriter fw = new FileWriter(path);
         int i;
         while((i=r.read())!=-1){
             fw.write((char)i);
         }
         fw.close();
         ps = connection.prepareStatement("DELETE FROM DATA WHERE ID = ?");
         ps.setInt(1, id);
         ps.executeUpdate();
         System.out.println("FILE IS NOW UNHIDDEN!");
     }
}

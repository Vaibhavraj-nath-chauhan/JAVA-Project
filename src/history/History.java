package history;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class History extends Applet implements ActionListener{
    TextField name;
    Button button;
    Label un;
    TextArea area;

    public void init(){
        setSize(500,500);
        un=new Label("UserName:",Label.CENTER);
        name=new TextField(20);
        area = new TextArea("",25,60);
        Font font1 = new Font("SansSerif", Font.BOLD, 12);
        button=new Button("submit");

        add(un);
        add(name);
        add(area);
        add(button);
        area.setFont(font1);
        un.setBounds(-10,90,90,60);
        name.setBounds(280,100,90,20);
        button.setBounds(100,260,70,40);
        button.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae){
        String un_1;
        un_1 = name.getText();
        area.setBackground(Color.white);
        area.setText("");
        if(un_1.isEmpty()){
            name.setText("Fill Details");
        }else {
            history_database(un_1);
        }
    }

    public void history_database(String username){
        try {
            // 1. Load the data access driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. Connect to the data "library"
            String url = "jdbc:mysql://localhost/Instagram";
            String user = "root";
            String passwd = "vaibhav";
            Connection conn = DriverManager.getConnection(url, user, passwd);
            //3. Build SQL commands
            Statement state = conn.createStatement();
            String query = "select NonFollower from Instainfo where UserName = "+"\""+username+"\"";
            ResultSet results =state.executeQuery(query);
            while (results.next()) {
                area.setBackground(Color.lightGray);
                String data = results.getString(1);
                area.append("Result " +results.getRow() + ":\n" + data+"\n\n");
            }
        }catch (Exception e) {
            area.append("Something Wet Wrong");

        }
    }

}
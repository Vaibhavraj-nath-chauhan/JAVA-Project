package history;

//Applet
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

//Jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Selenium
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

//SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

//History



public class EYE extends Applet implements ActionListener{
    /* -------------------- Creating Applet Body -------------------- */
    TextField name,pass;
    Button button;
    String un_1,ps_1;
    TextArea lb;
    Font font1;
    public void init()
    {

        setSize(500,500);
        Label un=new Label("UserName:",Label.CENTER);
        font1 = new Font("SansSerif", Font.BOLD, 18);
        lb = new TextArea("",28,60,TextArea.SCROLLBARS_BOTH);
        Label ps=new Label("password:",Label.CENTER);
        name=new TextField(20);
        pass=new TextField(20);
        pass.setEchoChar('*');
        button=new Button("submit");
        //adding elements to applet
        add(un);
        add(name);
        add(ps);
        add(pass);
        add(button);
        add(lb);
        un.setBounds(70,90,90,60);
        ps.setBounds(70,130,90,60);
        name.setBounds(280,100,90,20);
        pass.setBounds(200,140,90,20);
        button.setBounds(100,260,70,40);
        button.addActionListener(this);

    }
    //Button Setting
    public void actionPerformed(ActionEvent ae){
        lb.setText("");
        un_1 = name.getText();
        ps_1 = pass.getText();
        if(un_1.isEmpty() || ps_1.isEmpty()){
            name.setText("Fill Both Details");
            pass.setText("");
        }else {
            name.setText("Wait for few Minutes");
            pass.setText("");
            Scrapper(un_1,ps_1);
        }
    }

    public void Scrapper(String un_2,String ps_2) {

        String html, Above,URL;
        int loop = 0;

        /* -------------------- Opening Web Driver -------------------- */
        // Setting Chrome Driver (Latest and Stable)
        System.setProperty("webdriver.chrome.driver", "D:\\Learning\\College Section\\Setup\\Drivers\\chromedriver_win32\\chromedriver.exe");
        // Only for my system setting Brave as browser
        ChromeOptions option = new ChromeOptions().setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        //Opening Driver
        WebDriver driver = new ChromeDriver(option);
        //Full Screen
        driver.manage().window().maximize();
        //Creating javascript setup
        JavascriptExecutor js = (JavascriptExecutor) driver;


        /* -------------------- Login process -------------------- */
        try {
            //Opening instagram url
            driver.get("https://www.instagram.com/");
            //waiting for the page to load
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            //finding the element where we will put username (By Xpath)
            driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div/div[1]/div/label/input")).sendKeys(un_2);
            //finding the element where we will put password (By Xpath)
            driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div/div[2]/div/label/input")).sendKeys(ps_2, Keys.ENTER);
            // Method 2--> //driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div/div[3]/button")).click();

            /* -------------------- Directing to Main Profile -------------------- */
            Thread.sleep(5000);
            URL = "https://www.instagram.com/" + un_2;
            driver.get(URL);

            /* -------------------- PopUp Closing --------------------
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/div/div/button")).click();



             -------------------- Directing to Main Profile --------------------
            driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[3]/div/div[5]")).click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[3]/div/div[5]/div[2]/div[2]/div[2]/a[1]")).click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            */

            /* -------------------- Fetching Followers Number -------------------- */
            Above = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[2]/a/span")).getText();
            String[] In = new String[Integer.parseInt(Above)];
            /* -------------------- Fetching Followers Section -------------------- */
            driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[2]/a")).click();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            //Loop for scrolling page
            for (int i = 0; i < Integer.parseInt(Above)*10; i++) {
                js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", driver.findElement(By.className("isgrP")));

            }

            // Fetching innerHtml
            html = driver.findElement(By.className("isgrP")).getAttribute("innerHTML");
            //   * --- Passing it to Jsoup from extraction --- *   //
            Document doc = Jsoup.parse(html);
            // Fetching tags with the help of class name
            Elements follower = doc.getElementsByClass("_0imsa");

            for (Element el : follower) {
                In[loop] = el.text();
                loop += 1;
            }



//Going to the main menu
            driver.get(URL);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

            /* -------------------- Fetching Following Number -------------------- */
            Above = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[3]/a/span")).getText();
            String[] Out = new String[Integer.parseInt(Above)];

            /* -------------------- Fetching Following Section -------------------- */
            driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[3]/a")).click();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            //loop for scrolling page
            for (int i = 0; i < Integer.parseInt(Above)*10; i++) {
                js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", driver.findElement(By.className("isgrP")));
            }
            //Fetching innerHtml
            html = driver.findElement(By.className("isgrP")).getAttribute("innerHTML");
            //   * --- Passing it to Jsoup from extraction --- *   //
            doc = Jsoup.parse(html);
            // Fetching tags with the help of class name
            Elements following = doc.getElementsByClass("_0imsa");

            /* -------------------- checking List -------------------- */
            List<String> fol = Arrays.asList(In);
            int up = 0;
            for (Element el : following) {
                if (!fol.contains(el.text())) {
                    Out[up] = el.text();
                    up += 1;
                }
            }

            /*
            //Going to the main menu
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div/div[2]/button")).click();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);



            // -------------------- LogOut --------------------
            //setting menu
            driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/div[1]/div[2]/button")).click();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            //Logout button
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div/button[9]")).click();
            */
            driver.close();
            //Function calling
            getting(Out);

        }catch (Exception e){
            name.setText("Something Went Wrong");
            lb.setText("\t\t\tReasons of Error\n\n1. Wrong Username Password\n\n2. Low BandWidth\n\n3. Session Times Out ");
            driver.close();
        }
    }


    /* -------------------- Showing Output on Applet -------------------- */
    public void getting(String[] Out){
        name.setText("Person Not Following you");
        String out ="";
        for(int i=0;i< Out.length;i++){
            if(Out[i] !=null){
                out += (i+1)+". "+Out[i]+"\n";
            }else{
                break;
            }
        }
        lb.setBackground(Color.lightGray);
        lb.setFont(font1);
        lb.setText(out);
        Database(Out);
    }


    /* -------------------- dataBase Connection -------------------- */
    public void Database(String[] Out){
        try {

            String out ="";
            for (String value : Out) {
                if (value != null) {
                    out += value + ",";
                } else {
                    break;
                }
            }
            // 1. Load the data access driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. Connect to the data "library"
            String url = "jdbc:mysql://localhost/Instagram";
            String user = "root";
            String passwd = "vaibhav";
            Connection conn = DriverManager.getConnection(url, user, passwd);
            //3. Build SQL commands
            Statement state = conn.createStatement();
            String s = "insert into InstaInfo values("+"'" + un_1 + "','" + out + "')";
            state.executeUpdate(s);
        } catch (Exception e) {
            name.setText("Something Went Wrong");
        }
    }
}
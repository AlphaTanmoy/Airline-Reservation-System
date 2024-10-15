package Servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class registration extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        System.out.println("-------------------------------INSIDE REGISTRATION ----------------------");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "AIRRESERVE";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password1 = "";
        try {
            int count=0;
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password1);
            System.out.println("connected!.....");
            String fullname = request.getParameter("fullname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String countryCode = request.getParameter("countryCode");
            String phno = request.getParameter("phno");
            String email_address = request.getParameter("email_address");
            String state = request.getParameter("state");
            String city = request.getParameter("city");
            String pincode = request.getParameter("pincode");
            String mobileno = "+"+countryCode+" "+phno;
            
            HttpSession session = request.getSession();
            session.setAttribute("fullname", fullname);
            
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM user_details WHERE username='"+username+"'");
            while(rs.next()){
                count++;
            }
            if(count>0){
                RequestDispatcher view = request.getRequestDispatcher("registration_failed.jsp");
                view.forward(request, response);
                System.out.print("Record NOt Inserted");
            }
            else{
                int i=st.executeUpdate("INSERT INTO user_details (fullname, username, password, mobileno, email_address, city, state, pincode) values('" + fullname + "','" + username + "','" +password+ "', '" +mobileno+ "', '"+email_address+"', '"+city+"', '"+state+"', "+pincode+")");
                RequestDispatcher view = request.getRequestDispatcher("registration_scs.jsp");
                view.forward(request, response);
                System.out.print("Record Inserted");
            }
        }
        catch(IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException | ServletException e){
            System.out.print(e);
        }  
        System.out.println("-------------------------------INSIDE REGISTRATION ----------------------");
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

package Servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class search_flight_by_fno_u extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        PrintWriter out = response.getWriter();
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "AIRRESERVE";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        Statement st=null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("connected!.....");
            String flight_no = request.getParameter("flight_no");
            if(flight_no == null || flight_no.equals("")){
                RequestDispatcher error = request.getRequestDispatcher("error_u.jsp");
                error.forward(request, response);
                conn.close();
                System.out.println("Disconnected!");
            }
            else{
                ArrayList al = null;
            ArrayList pid_list = new ArrayList();
            String query = "select * from flight_details";
            if(flight_no!=null && !flight_no.equals("")){
                query = "select * from flight_details where flight_no='" + flight_no + "' ";
            }
            
            System.out.println("query " + query);
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                al = new ArrayList();

                al.add(rs.getString(1));
                al.add(rs.getString(2));
                al.add(rs.getString(3));
                al.add(rs.getString(4));
                al.add(rs.getString(5));
                al.add(rs.getString(6));
                al.add(rs.getString(7));
                al.add(rs.getString(8));
                al.add(rs.getString(9));
                al.add(rs.getString(10));
                al.add(rs.getString(11));
                al.add(rs.getString(12));
                al.add(rs.getString(13));
                al.add(rs.getString(14));
                al.add(rs.getString(15));
                al.add(rs.getString(16));
                al.add(rs.getString(17));
                al.add(rs.getString(18));
                al.add(rs.getString(19));
                al.add(rs.getString(20));
                al.add(rs.getString(21));
                System.out.println("al :: " + al);
                pid_list.add(al);
            }

            request.setAttribute("piList", pid_list);
            RequestDispatcher view = request.getRequestDispatcher("searc_filter_flights_result_u.jsp");
            view.forward(request, response);
            conn.close();
            System.out.println("Disconnected!");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

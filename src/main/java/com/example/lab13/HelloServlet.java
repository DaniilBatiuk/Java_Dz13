package com.example.lab13;

import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static java.lang.System.out;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    private Connection connection;
    Statement statement;
    public void init() {
        message = "Hello World!";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/agencydb","root","");
            String sql = "CREATE TABLE IF NOT EXISTS Country (id INT AUTO_INCREMENT PRIMARY KEY," +
                    "country_name VARCHAR(255) NOT NULL,capital_city VARCHAR(255) NOT NULL,population INT);";
            String sql1 = "CREATE TABLE IF NOT EXISTS City(id INT AUTO_INCREMENT PRIMARY KEY,city_name VARCHAR(255) NOT NULL," +
                    "country_id INT,FOREIGN KEY (country_id) REFERENCES Country(id))";
            statement = connection.createStatement();
            statement.executeUpdate(sql);




            //String sqlFilling = "INSERT INTO MyCountries (country_name, capital_city, population) " +
            //        "VALUES('Україна', 'Київ', 44000000),('Польща', 'Варшава', 38000000)," +
            //        "('Німеччина', 'Берлін', 83000000)";

//            String sqlFilling1 = "INSERT INTO MyCities (city_name, country_id) " +
//                    "VALUES('Львів', 1),('Краків', 2),('Берлін', 3)";
            //statement.executeUpdate(sqlFilling);

           statement.executeUpdate(sql1);
//            statement.executeUpdate(sqlFilling1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String sql = "SELECT * FROM Country";

        int IdExample = 1;

        String sql2 = "SELECT * FROM City WHERE country_id = " + IdExample;

        String sql3 = "SELECT capital_city FROM Country";

        String sql4 = "SELECT capital_city FROM Country WHERE id = " + IdExample;

        String sql5 = "SELECT C.country_name, COUNT(CI.id) AS city_count FROM Country C LEFT JOIN City CI ON " +
                "C.id = CI.country_id GROUP BY C.country_name ORDER BY city_count DESC LIMIT 3";


        String sql6 = "SELECT C.country_name, SUM(C.population) AS total_population FROM Country C GROUP BY " +
                "C.country_name ORDER BY total_population DESC LIMIT 3";

        String sql7 = "SELECT C.country_name, SUM(C.population) AS total_population FROM Country C " +
                "GROUP BY C.country_name ORDER BY total_population ASC LIMIT 3";

        String sql8 = "SELECT C.country_name, AVG(C.population) AS avg_city_population FROM Country C " +
                "LEFT JOIN City CI ON C.id = CI.country_id " +
                "WHERE C.country_name = 'Україна' GROUP BY C.country_name";

        String sql9 = "SELECT city_name, COUNT(DISTINCT country_name) AS country_count FROM City CI LEFT JOIN " +
                "Country C ON CI.country_id = C.id GROUP BY city_name HAVING COUNT(DISTINCT country_name) > 1";

        String sql10 = "SELECT city_name FROM City CI GROUP BY city_name HAVING COUNT(DISTINCT country_id) > 1";

        String sql11 = "SELECT C.country_name, COUNT(CI.id) AS city_count FROM Country C LEFT JOIN City CI " +
                "ON C.id = CI.country_id GROUP BY C.country_name HAVING city_count BETWEEN 0 AND 1";

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h2>Всі Country</h2>");
            while(resultSet.next()){
                String country_name = resultSet.getString("country_name");
                String capital_city = resultSet.getString("capital_city");
                String population = resultSet.getString("population");
                out.println("<p>" + country_name + " " + capital_city + " " + population + "</p>");

            }

            ResultSet resultSet2 = statement.executeQuery(sql2);
            out.println("<h2>Відображення всіх міст конкретної країни</h2>");
            while(resultSet2.next()){
                String city_name = resultSet2.getString("city_name");
                out.println("<p>" + city_name + "</p>");

            }


            ResultSet resultSet3 = statement.executeQuery(sql3);
            out.println("<h2>Відображення всіх столиць</h2>");
            while(resultSet3.next()){
                String capital_city = resultSet3.getString("capital_city");
                out.println("<p>" + capital_city + "</p>");
            }

            ResultSet resultSet4 = statement.executeQuery(sql4);
            out.println("<h2>Відображення столиці конкретної країни</h2>");
            while(resultSet4.next()){
                String capital_city = resultSet4.getString("capital_city");
                out.println("<p>" + capital_city + "</p>");

            }

            ResultSet resultSet5 = statement.executeQuery(sql5);
            out.println("<h2>Показати три країни з найбільшою кількістю міст</h2>");
            while(resultSet5.next()){
                String country_name = resultSet5.getString("country_name");
                String city_count = resultSet5.getString("city_count");
                out.println("<p>" + country_name + "</p>");
                out.println("<p>" + city_count + "</p>");
            }

            ResultSet resultSet6 = statement.executeQuery(sql6);
            out.println("<h2>Показати три країни з найбільшою кількістю жителів</h2>");
            while(resultSet6.next()){
                String country_name = resultSet6.getString("country_name");
                String total_population = resultSet6.getString("total_population");
                out.println("<p>" + country_name + "</p>");
                out.println("<p>" + total_population + "</p>");
            }


            ResultSet resultSet7 = statement.executeQuery(sql7);
            out.println("<h2>Показати три країни з найменшою кількістю жителів</h2>");
            while(resultSet7.next()){
                String country_name = resultSet7.getString("country_name");
                String total_population = resultSet7.getString("total_population");
                out.println("<p>" + country_name + "</p>");
                out.println("<p>" + total_population + "</p>");
            }

            ResultSet resultSet8 = statement.executeQuery(sql8);
            out.println("<h2>Показати середню кількість жителів у місті для вказаної країни</h2>");
            while(resultSet8.next()){
                String country_name = resultSet8.getString("country_name");
                String avg_city_population = resultSet8.getString("avg_city_population");
                out.println("<p>" + country_name + "</p>");
                out.println("<p>" + avg_city_population + "</p>");
            }

            ResultSet resultSet9 = statement.executeQuery(sql9);
            out.println("<h2>Показати кількість міст з однаковою назвою в різних країнах</h2>");
            while(resultSet9.next()){
                String city_name = resultSet9.getString("city_name");
                String country_count = resultSet9.getString("country_count");
                out.println("<p>" + city_name + "</p>");
                out.println("<p>" + country_count + "</p>");
            }

            ResultSet resultSet10 = statement.executeQuery(sql10);
            out.println("<h2>Показати унікальні назви міст із різних країн</h2>");
            while(resultSet10.next()){
                String city_name = resultSet10.getString("city_name");
                out.println("<p>" + city_name + "</p>");
            }

            ResultSet resultSet11 = statement.executeQuery(sql11);
            out.println("<h2> Показати країни з кількістю міст в указаному діапазоні</h2>");
            while(resultSet11.next()){
                String country_name = resultSet11.getString("country_name");
                String city_count = resultSet11.getString("city_count");
                out.println("<p>" + country_name + "</p>");
                out.println("<p>" + city_count + "</p>");
            }


            out.println("</body></html>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String countryName = request.getParameter("countryName");
        String capitalCity = request.getParameter("capitalCity");
        int population = Integer.parseInt(request.getParameter("population"));
        String sql = "INSERT INTO Country (country_name, capital_city, population) VALUES (?, ?, ?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/agencydb", "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, countryName);
            preparedStatement.setString(2, capitalCity);
            preparedStatement.setInt(3, population);

            int rowsInserted = preparedStatement.executeUpdate();
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            if (rowsInserted > 0) {
                out.println("<p>Success</p>");
            } else {
                out.println("<p>Error</p>");
            }
            out.println("</body></html>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void destroy() {
    }
}
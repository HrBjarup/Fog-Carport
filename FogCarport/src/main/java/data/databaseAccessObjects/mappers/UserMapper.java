package data.databaseAccessObjects.mappers;

import data.exceptions.DataException;
import data.exceptions.UserException;
import data.models.CustomerModel;
import data.models.EmployeeModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author
 */
public class UserMapper
{

//    private DatabaseConnector dbc = new DatabaseConnector(); Old way we did it.
    private DataSource ds;
    

    public void setDataSource(DataSource ds)
    {
//        dbc.setDataSource(ds); Old way we did it.
        this.ds = ds;
    }

    /* CUSTOMER */
    // <editor-fold desc="Log in a customer">
    /**
     * Login Method.
     *
     * Pulls a User entity from the SQL if the User input correct info into the
     * form. Else throws an exception and returns User to the index page.
     *
     * @param email Users email
     * @param password Users password
     * @return User entity
     * @throws UserException Custom Exception. Caught in FrontController. Sends
     * User back to index.jsp.
     */
    public CustomerModel login(String email, String password) throws UserException
    {
        String SQL = "SELECT customer_name, id_customer, phone, registered FROM customers WHERE email=? AND password=?;";
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(SQL))
        {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                boolean registered = rs.getBoolean("registered");
                if (registered == true)
                {
                    int id = rs.getInt("id_customer");
                    CustomerModel customer = new CustomerModel();
                    customer.setRegistered(registered);
                    customer.setPhone(rs.getInt("phone"));
                    customer.setName(rs.getString("customer_name"));
                    customer.setId(id);
                    customer.setEmail(email);
                    customer.setPassword(password);
                    return customer;
                } else
                {
                    throw new UserException("Kunden eksisterer ikke i databasen.");
                }
            } else
            {
                throw new UserException("Kunden eksisterer ikke i databasen.");
            }

        } catch (SQLException ex)
        {
            throw new UserException("Fejl i forbindelse til databasen.");
        }
    }
    // </editor-fold>

    // <editor-fold desc="Get a customer">
    /**
     * Get a Customer.
     *
     * @param id of the Order.
     * @return OrderModel
     * @throws DataException
     */
    public CustomerModel getCustomer(int id) throws DataException
    {
        String SQL = "SELECT * FROM carportdb.customers WHERE id_customer = ?;";

        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(SQL))
        {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                CustomerModel customer = new CustomerModel();
                customer.setId(id);

                customer.setName(rs.getString("customer_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getInt("phone"));
                customer.setPassword(rs.getString("password"));
                if (!customer.getPassword().equals(""))
                {
                    customer.setRegistered(true);
                }
                return customer;
            } else
            {
                throw new DataException("Kunne ikke skaffe kunde-info.");
            }
        } catch (SQLException ex)
        {
            throw new DataException("Fejl i forbindelse til databasen.");
        }
    }
    // </editor-fold>

    // <editor-fold desc="Create customer">
    /**
     * Create Customer Method.
     *
     * Inputs a Customer into the SQL database.
     *
     * @param customer
     * @throws UserException Custom Exception. Caught in FrontController. Sends
     * User back to index.jsp.
     */
    public void createCustomer(CustomerModel customer) throws UserException
    {
        // Doing this registered thing, because we use tinyint to represent boolean. 1 for true, 0 for false.
        int registered;
        if (customer.isRegistered())
        {
            registered = 1;
        } else
        {
            registered = 0;
        }

        String SQL = "INSERT INTO `carportdb`.`customers` "
                + "(`customer_name`, `phone`, `email`, `password`, `registered`) VALUES (?, ?, ?, ?, ?);";
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
        {

            ps.setString(1, customer.getName());
            ps.setInt(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            ps.setInt(5, registered);
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next())
            {
                customer.setId(resultSet.getInt(1));
            }
        } catch (SQLException ex)
        {
            throw new UserException("Kunden eksisterer allerede i databasen.");
        }
    }
    //</editor-fold>

    /* EMPLOYEE */
    // <editor-fold desc="Create Employee">
    /**
     * Create Employee Method.
     *
     * Inputs a Employee into the SQL database.
     *
     * @param employee
     * @throws UserException Custom Exception. Caught in FrontController. Sends
     * User back to index.jsp.
     */
    public void createEmployee(EmployeeModel employee) throws UserException
    {
        String SQL = "INSERT INTO `carportdb`.`employees`\n"
                + "(`emp_email`,\n"
                + "`id_role`)\n"
                + "VALUES\n"
                + "(?,\n"
                + "?);";
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS))
        {

            ps.setString(1, employee.getEmail());
            ps.setInt(2, employee.getId_role());
            ps.executeUpdate();
            try (ResultSet ids = ps.getGeneratedKeys())
            {
                ids.next();
                int id = ids.getInt(1);
                employee.setId(id);
            }
        } catch (SQLException ex)
        {
            throw new UserException("Den ansatte eksisterer allerede i databasen.");
        }
    }
    //</editor-fold>

    // <editor-fold desc="Get an employee">
    /**
     * Get an Order.
     *
     * @param id of the Order.
     * @return OrderModel
     * @throws data.exceptions.UserException
     */
    public EmployeeModel getEmployee(int id) throws UserException
    {

        String SQL = "SELECT `employees`.`emp_email`, `roles`.`role` "
                + "FROM `carportdb`.`employees` "
                + "INNER JOIN `carportdb`.`roles` "
                + "ON `employees`.`id_role` = `roles`.`id_role` "
                + "WHERE `employees`.`id_employee` = ?;";

        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(SQL))
        {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                EmployeeModel employee = new EmployeeModel();
                employee.setId(id);
                employee.setEmail(rs.getString("emp_email"));
                employee.setRole(rs.getString("role"));
                return employee;
            } else
            {
                throw new UserException("Kunne ikke skaffe den ansatte fra databasen.");
            }

        } catch (SQLException ex)
        {
            throw new UserException("Kunne ikke skaffe den ansatte fra databasen.");
        }

    }
    // </editor-fold>

    // <editor-fold desc="Log in an employee">
    public EmployeeModel empLogin(String email, String password) throws UserException
    {

        String SQL = "SELECT id_employee, id_role FROM employees where emp_email=? AND password=?;";
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(SQL))
        {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                int id_emp = rs.getInt("id_employee");
                int id_role = rs.getInt("id_role");
                EmployeeModel employee = new EmployeeModel();
                employee.setEmail(email);
                employee.setId_role(id_role);
                employee.setId(id_emp);
                return employee;
            } else
            {
                throw new UserException("Kunne ikke validere brugeren.");
            }
        } catch (SQLException ex)
        {
            throw new UserException("Der er opstået en fejl i forbindelsen til databasen.");
        }
    }
    // </editor-fold>
}

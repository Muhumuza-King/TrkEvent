
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class User {

    int id;
    String name;
    String email;
    String status;
    String date;
    String manager;
    String narration;
    String businessname;
    String clientaddress;
    String category;
    String referrer;
    String appointmentdate;
    String lead;

    ArrayList usersList;
    private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    Connection connection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getClientaddress() {
        return clientaddress;
    }

    public void setClientaddress(String clientaddress) {
        this.clientaddress = clientaddress;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getAppointmentdate() {
        return appointmentdate;
    }

    public void setAppointmentdate(String appointmentdate) {
        this.appointmentdate = appointmentdate;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

// Used to establish connection  
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
// Used to fetch all records  

    public ArrayList usersList() {
        try {
            usersList = new ArrayList();
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getString("status"));
                user.setDate(rs.getString("date"));
                user.setManager(rs.getString("manager"));
                user.setNarration(rs.getString("narration"));
                user.setBusinessname(rs.getString("businessname"));
                user.setClientaddress(rs.getString("clientaddress"));
                user.setCategory(rs.getString("category"));
                user.setReferrer(rs.getString("referrer"));
                user.setAppointmentdate(rs.getString("appointmentdate"));
                user.setLead(rs.getString("lead"));

                usersList.add(user);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;
    }
// Used to save user record  

    public String save() {
        int result = 0;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into users(name,email,status,date,manager,narration,businessname,clientaddress,category,referrer,appointmentdate,lead) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, status);
            stmt.setString(4, date);
            stmt.setString(5, manager);
            stmt.setString(6, narration);
            stmt.setString(7, businessname);
            stmt.setString(8, clientaddress);
            stmt.setString(9, category);
            stmt.setString(10, referrer);
            stmt.setString(11, appointmentdate);
            stmt.setString(12, lead);

            result = stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (result != 0) {
            return "index.xhtml?faces-redirect=true";
        } else {
            return "create.xhtml?faces-redirect=true";
        }
    }
// Used to fetch record to update  

    public String edit(int id) {
        User user = null;
        System.out.println(id);
        try {
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from users where id = " + (id));
            rs.next();
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setStatus(rs.getString("status"));
            user.setDate(rs.getString("date"));
            user.setManager(rs.getString("manager"));
            user.setNarration(rs.getString("narration"));
            user.setBusinessname(rs.getString("businessname"));
            user.setClientaddress(rs.getString("clientaddress"));
            user.setCategory(rs.getString("category"));
            user.setReferrer(rs.getString("referrer"));
            user.setAppointmentdate(rs.getString("appointmentdate"));
            user.setLead(rs.getString("lead"));

            sessionMap.put("editUser", user);
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/edit.xhtml?faces-redirect=true";
    }
// Used to update user record  

    public String update(User u) {
//int result = 0;  
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "update users set name=?,email=?,status=?,date=?,manager=?,narration=?,businessname=?,clientaddress=?,category=?,referrer=?,appointmentdate=?,lead=? where id=?");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getStatus());
            stmt.setString(4, u.getDate());
            stmt.setString(5, u.getManager());
            stmt.setString(6, u.getNarration());
            stmt.setString(7, u.getBusinessname());
            stmt.setString(8, u.getClientaddress());
            stmt.setString(9, u.getCategory());
            stmt.setString(10, u.getReferrer());
            stmt.setString(11, u.getAppointmentdate());
            stmt.setString(12, u.getLead());

            stmt.setInt(13, u.getId());
            stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/index.xhtml?faces-redirect=true";
    }
// Used to delete user record  

    public void delete(int id) {
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("delete from users where id = " + id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//Used to get Status Name
    public String getStatusName(String status) {

        if (status.equals("Complete")) {
            return "Complete";
        } else if (status.equals("Pending")) {
            return "Pending";
        } else if (status.equals("Scheduled")) {
            return "Scheduled";
        } else if (status.equals("In Progress")) {
            return "In Progress";
        } else if (status.equals("Other")) {
            return "Other";
        } else {
            return "";
        }

    }
//Used to get Business Category
    public String getBusinessCategory(String category) {

        if (category.equals("Supermarket")) {
            return "Supermarket";
        } else if (category.equals("Pharmacy")) {
            return "Pharmacy";
        } else if (category.equals("Boutique")) {
            return "Boutique";
        } else if (category.equals("Hardware")) {
            return "Hardware";
        } else if (category.equals("Restuarant")) {
            return "Restuarant";
        } else if (category.equals("Bar")) {
            return "Bar";
        } else if (category.equals("Manufacturing Company")) {
            return "Manufacturing Company";
        } else if (category.equals("Other")) {
            return "Other";
        } else {
            return "";
        }
    }

}

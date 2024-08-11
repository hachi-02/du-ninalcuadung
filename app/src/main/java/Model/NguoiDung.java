package Model;

public class NguoiDung {
public String username,password,role;
public NguoiDung(String username, String password,String role){
    this.username=username;
    this.password=password;
    this.role=role;
}
    public NguoiDung(String username, String password){
        this.username=username;
        this.password=password;
        this.role = "borrower";
    }
}

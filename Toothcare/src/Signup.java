import java.util.ArrayList;

public class Signup {
    private static Signup instance;
    private ArrayList<User> users;
    
    public Signup(){
        users = new ArrayList<>();
    }
    
    public static Signup getInstance(){
        if(instance == null){
            instance = new Signup();
        }
        return instance;
    }
    
    public void registerUser(String username, String password){
        User newUser = new User(username, password);
        users.add(newUser);
        System.out.println();
        System.out.println("User Registered Successfully!");

    }
    
    public ArrayList<User> getUsers(){
        return users;
    }
}

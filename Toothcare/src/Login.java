import java.util.ArrayList;

public class Login {
    private Signup signup;
    private boolean loggedIn;
    
    public Login(Signup signup){
        this.signup = signup;
        loggedIn = false;
    }
    
    public boolean authenticateUser(String username, String password){
        for(User user : signup.getUsers()){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                loggedIn = true;
                return true;
            }
        }
        return false;
    }
    
    public boolean isLoggedIn(){
        return loggedIn;
    }
    
    public void logout(){
        loggedIn = false;
    }
}

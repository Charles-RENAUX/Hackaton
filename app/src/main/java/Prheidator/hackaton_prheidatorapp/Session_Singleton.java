package Prheidator.hackaton_prheidatorapp;


public class Session_Singleton  {
    private Session session;

    private static final Session_Singleton ourInstance = new Session_Singleton();

    public static Session_Singleton getInstance(){
        return ourInstance;
    }

    private Session_Singleton(){
        this.session=new Session();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

}

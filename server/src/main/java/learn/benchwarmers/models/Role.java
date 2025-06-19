package learn.benchwarmers.models;

public enum Role {
    ADMIN(1, "admin"),
    LEAGUE_MEMBER(2, "league member");

    //constructor
    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //fields
    private int id;
    private String name;

    //getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

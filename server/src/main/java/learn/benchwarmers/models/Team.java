package learn.benchwarmers.models;

public enum Team {
    HAWKS(1, "Atlanta Hawks", "ATL", "Eastern", "Southeast"),
    CELTICS(2, "Boston Celtics", "BOS", "Eastern", "Atlantic"),
    NETS(3, "Brooklyn Nets", "BKN", "Eastern", "Atlantic"),
    HORNETS(4, "Charlotte Hornets", "CHA", "Eastern", "Southeast"),
    BULLS(5, "Chicago Bulls", "CHI", "Eastern", "Central"),
    CAVALIERS(6, "Cleveland Cavaliers", "CLE", "Eastern", "Central"),
    MAVERICKS(7, "Dallas Mavericks", "DAL", "Western", "Southwest"),
    NUGGETS(8, "Denver Nuggets", "DEN", "Western", "Northwest"),
    PISTONS(9, "Detroit Pistons", "DET", "Eastern", "Central"),
    WARRIORS(10, "Golden State Warriors", "GSW", "Western", "Pacific"),
    ROCKETS(11, "Houston Rockets", "HOU", "Western", "Southwest"),
    PACERS(12, "Indiana Pacers", "IND", "Eastern", "Central"),
    CLIPPERS(13, "Los Angeles Clippers", "LAC", "Western", "Pacific"),
    LAKERS(14, "Los Angeles Lakers", "LAL", "Western", "Pacific"),
    GRIZZLIES(15, "Memphis Grizzlies", "MEM", "Western", "Southwest"),
    HEAT(16, "Miami Heat", "MIA", "Eastern", "Southeast"),
    BUCKS(17, "Milwaukee Bucks", "MIL", "Eastern", "Central"),
    TIMBERWOLVES(18, "Minnesota Timberwolves", "MIN", "Western", "Northwest"),
    PELICANS(19, "New Orleans Pelicans", "NOP", "Western", "Southwest"),
    KNICKS(20, "New York Knicks", "NYK", "Eastern", "Atlantic"),
    THUNDER(21, "Oklahoma City Thunder", "OKC", "Western", "Northwest"),
    MAGIC(22, "Orlando Magic", "ORL", "Eastern", "Southeast"),
    SIXERS(23, "Philadelphia 76ers", "PHI", "Eastern", "Atlantic"),
    SUNS(24, "Phoenix Suns", "PHX", "Western", "Pacific"),
    BLAZERS(25, "Portland Trail Blazers", "POR", "Western", "Northwest"),
    KINGS(26, "Sacramento Kings", "SAC", "Western", "Pacific"),
    SPURS(27, "San Antonio Spurs", "SAS", "Western", "Southwest"),
    RAPTORS(28, "Toronto Raptors", "TOR", "Eastern", "Atlantic"),
    JAZZ(29, "Utah Jazz", "UTA", "Western", "Northwest"),
    WIZARDS(30, "Washington Wizards", "WAS", "Eastern", "Southeast");

    //constructor
    Team(int teamId, String name, String abbreviation, String conference, String division) {
        this.teamId = teamId;
        this.name = name;
        this.abbreviation = abbreviation;
        this.conference = conference;
        this.division = division;
    }

    //fields
    private int teamId;
    private String name;
    private String abbreviation;
    private String conference;
    private String division;

    //Getters
    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getConference() {
        return conference;
    }

    public String getDivision() {
        return division;
    }

    public int getTeamId() {
        return teamId;
    }
}

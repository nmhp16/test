import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private List<Showtime> showtimes = new ArrayList<>();
    private boolean isSoldOut;

    // Constructors
    public Movie () {}
    public Movie(int movieId, String title, String genre) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
    }

    // Methods
    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }

    public void listGenres() {
        // This could be replaced with a dynamic list if genres are managed separately
        System.out.println("Available genres: Action, Comedy, Drama, Sci-Fi, etc.");
    }

    public void selectGenre(String genre) {
        this.genre = genre;
        System.out.println("Selected genre: " + genre);
    }

    public void listShowtimes() {
        for (Showtime showtime : showtimes) {
            System.out.println("Showtime ID: " + showtime.getShowtimeId() + ", Time: " + showtime.getTime());
        }  
    }

    public Showtime selectShowtime(int showtimeId) throws ShowtimeNotFoundException {
        for (Showtime showtime : showtimes) {
            if (showtime.getShowtimeId() == showtimeId) {
                return showtime;
            }
        }
        throw new ShowtimeNotFoundException("Showtime not found with ID: " + showtimeId);
    }
    
    public void checkSeatOccupancy() {
        for (Showtime showtime : showtimes) {
            System.out.println("Showtime ID: " + showtime.getShowtimeId() + ", Available Seats: " + showtime.getAvailableSeats());
        };
    }

    // Getters and Setters
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public List<Showtime> getShowtimes() { return showtimes; }
    public void setShowtimes(List<Showtime> showtimes) { this.showtimes = showtimes; }

    public boolean isSoldOut() {return isSoldOut;}
    public void setSoldOut(boolean soldOut) {isSoldOut = soldOut;}
}

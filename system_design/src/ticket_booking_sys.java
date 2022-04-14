import java.util.*;
public class ticket_booking_sys {
    public enum Language {
        HINDI, ENGLISH;
    }

    public enum Genre {
        ACTION, ROMANCE, COMEDY, HORROR;
    }

    public class Movie {
        private String name;
        private float ratings = 0.0f;
        private Language language;
        private Genre genre;

        public Movie(String name, Language language, Genre genre) {
            this.name = name;
            this.language = language;
            this.genre = genre;
        }

        public String getName() {
            return name;
        }

        public float getRatings() {
            return ratings;
        }

        public Language getLanguage() {
            return language;
        }

        public Genre getGenre() {
            return genre;
        }
    }
    public class Theater {
        private static int idCounter=0;
        private int id;
        private String name;
        private String location;
        private int capacity;
        private ArrayList<Show> shows;

        public Theater(String name, String location, int capacity) {
            idCounter += 1;
            this.id = idCounter;
            this.name = name;
            this.location = location;
            this.capacity = capacity;
            this.shows = new ArrayList<>();
        }

        public void updateShow(Show oldShow, Show newShow){

        }
        public ArrayList<Show> getShows(){
            return shows;
        }

        public String getName() {
            return name;
        }

        public int getCapacity() {
            return capacity;
        }
    }
    public class Show {
        private static int idCounter=0;
        private int id;
        private Date showTime;
        private Movie movie;
        private Theater theater;
        private int availableSeats;

        public Show(Date showTime, Movie movie, Theater theater) {
            idCounter += 1;
            this.id = idCounter;
            this.showTime = showTime;
            this.movie = movie;
            this.theater = theater;
            this.availableSeats = theater.getCapacity();
            theater.getShows().add(this);
        }

        public Movie getMovie() {
            return movie;
        }
        public void setTheater(Theater theater) {
            this.theater = theater;
        }
        public void setAvailableSeats(int availableSeats) {
            this.availableSeats = availableSeats;
        }
        public int getAvailableSeats() {
            return availableSeats;
        }
        public void updateShow(){
        }
        public synchronized Ticket bookTicket(RegisteredUser user, int seats){
            if(availableSeats >= seats && seats >0){
                Ticket ticket = new Ticket();
                availableSeats -= seats;
                ticket.setOwner(user.getName());
                ticket.setBookedShow(this);
                ticket.setBookingTime(new Date());
                ticket.setNumberOfSeats(seats);
                System.out.println("Successfully booked");
                user.bookingHistory.add(ticket);
                return ticket;
            }
            else{
                System.out.println("Seats not Available");
                return null;
            }
        }
        @Override
        public String toString() {
            return "Show{" +
                    "id=" + id +
                    ", showTime=" + showTime +
                    ", movie=" + movie.getName() +
                    ", theater=" + theater.getName() +
                    ", availableSeats=" + availableSeats +
                    '}';
        }
    }
    public class Ticket {
        private static int idCounter=0;
        private int id;
        private String owner;
        private Date bookingTime;
        private int numberOfSeats;
        private Show bookedShow;

        public Ticket() {
            idCounter += 1;
            this.id = idCounter;
        }

        public String getTicketInfo(){
            return null;
        }
        public int cancelTicket(){
            return 0;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Date getBookingTime() {
            return bookingTime;
        }

        public void setBookingTime(Date bookingTime) {
            this.bookingTime = bookingTime;
        }

        public int getNumberOfSeats() {
            return numberOfSeats;
        }

        public void setNumberOfSeats(int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
        }

        @Override
        public String toString() {
            return "Ticket{" +  "owner='" + owner + '`' +
            ", bookingTime=" + bookingTime +
                    ", numberOfSeats=" + numberOfSeats +
                    ", bookedShow=" + bookedShow +
                    '}';
        }

        public Show getBookedShow() {
            return bookedShow;
        }

        public void setBookedShow(Show bookedShow) {
            this.bookedShow = bookedShow;
        }
    }
    public abstract class User {
        private static int idCounter=0;
        private int id;
        private String name;

        public User(String name) {
            idCounter += 1;
            this.id = idCounter;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    public class GuestUser extends User {
        public GuestUser(String name) {
            super(name);
        }

        public void register(String username, String password){

        }
    }
    public class RegisteredUser extends User {
        public ArrayList<Ticket> bookingHistory;

        public RegisteredUser(String name) {
            super(name);
            this.bookingHistory = new ArrayList<>();
        }
        public void cancelTicket(Ticket ticket){

        }
    }
    public class TicketBookingThread extends Thread {
        private Show show;
        private RegisteredUser user;
        private int numberOfSeats;
        private Ticket ticket;

        public TicketBookingThread(Show show, RegisteredUser user, int numberOfSeats) {
            this.show = show;
            this.user = user;
            this.numberOfSeats = numberOfSeats;
        }

        @Override
        public void run() {
            this.ticket = show.bookTicket(user,numberOfSeats);
        }

        public Ticket getTicket() {
            return ticket;
        }
    }

}

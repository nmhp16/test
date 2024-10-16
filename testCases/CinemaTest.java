// Use VS Code Terminal to run
// javac -cp "lib/*;." src/*.java testCases/*.java
// java -cp "lib/*;.;src;testCases" org.junit.runner.JUnitCore testCases.CinemaTest

package testCases;

import org.junit.Before;
import org.junit.Test;
import src.Cinema;
import src.Theater;
import src.TheaterNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CinemaTest {

    private Cinema cinema;

    @Before
    public void setUp() {
        Cinema.resetCinemaCount();

        cinema = new Cinema(); // Initialize a new Cinema instance before each test
        System.out.println("Cinema setup completed.");
    }

    @Test
    public void testAddTheater() {
        Theater theater = new Theater(1, "123 Main St");
        cinema.addTheater(theater);
        assertEquals(1, cinema.getTotalTheaters()); // Ensure theater count is correct
        System.out.println("Theater added successfully. Total theaters: " + cinema.getTotalTheaters());
    }

    @Test
    public void testSelectTheaterValid() throws TheaterNotFoundException {
        Theater theater = new Theater(1, "123 Main St");
        cinema.addTheater(theater);
        Theater selected = cinema.selectTheater(1);
        assertEquals(theater, selected); // Ensure the selected theater is the same
        System.out.println("Theater selected successfully: " + selected.getTheaterId());
    }

    @Test(expected = TheaterNotFoundException.class)
    public void testSelectTheaterInvalid() throws TheaterNotFoundException {
        Theater theater = new Theater(1, "123 Main St");
        cinema.addTheater(theater);
        cinema.selectTheater(2); // Attempt to select a theater that does not exist
    }

    @Test
    public void testGetTotalTheaters() {
        assertEquals(0, cinema.getTotalTheaters()); // No theaters added yet
        Theater theater1 = new Theater(1, "123 Main St");
        Theater theater2 = new Theater(2, "456 Elm St");
        cinema.addTheater(theater1);
        cinema.addTheater(theater2);
        assertEquals(2, cinema.getTotalTheaters()); // Two theaters should be added
        System.out.println("Total theaters: " + cinema.getTotalTheaters());
    }

    @Test
    public void testIsValidTheaterTrue() {
        Theater theater = new Theater(1, "123 Main St");
        cinema.addTheater(theater);
        assertTrue(cinema.isValidTheater(1)); // Should return true for valid theater ID
        System.out.println("Valid theater ID check passed for: " + theater.getTheaterId());
    }

    @Test
    public void testIsValidTheaterFalse() {
        Theater theater = new Theater(1, "123 Main St");
        cinema.addTheater(theater);
        assertFalse(cinema.isValidTheater(2)); // Should return false for invalid theater ID
        System.out.println("Invalid theater ID check passed for ID: 2");
    }

    @Test
    public void testListTheaters() {
        // Redirect output to a ByteArrayOutputStream to capture print statements
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Save original output stream
        System.setOut(new PrintStream(outContent)); // Redirect output

        Theater theater1 = new Theater(1, "123 Main St");
        Theater theater2 = new Theater(2, "456 Elm St");
        cinema.addTheater(theater1);
        cinema.addTheater(theater2);
        cinema.listTheaters();

        // Expected output
        String expectedOutput = "Theater ID: 1, Address: 123 Main St\n" +
                "No movies currently available in this theater.\n\n" +
                "Theater ID: 2, Address: 456 Elm St\n" +
                "No movies currently available in this theater.\n\n";

        String actualOutput = outContent.toString().replace("\r\n", "\n"); // Normalize line endings

        // Debugging: Print both outputs for comparison
        System.out.println("Expected Output:\n" + expectedOutput);
        System.out.println("Actual Output:\n" + actualOutput);

        // Trim both outputs to remove any leading/trailing whitespace and compare
        assertEquals(expectedOutput.trim(), actualOutput.trim());

        // Reset the output stream
        System.setOut(originalOut);
        System.out.println("List theaters method tested successfully.");
    }

    @Test
    public void testCinemaCreationLimit() {
        List<Cinema> cinemas = new ArrayList<>();

        // Create 100 Cinema instances successfully
        for (int i = 0; i < 98; i++) { // + 1 from set up
            cinemas.add(new Cinema());
        }

        // Ensure we can still create the 100th transaction
        Cinema hundredthCinema = new Cinema();
        assertNotNull("100th Cinema should be created successfully", hundredthCinema);
    }

    @Test
    public void testCinemaInstanceLimit() {
        List<Cinema> cinemas = new ArrayList<>();

        // Create 100 Cinema instances successfully
        for (int i = 0; i < 99; i++) { // + 1 from set up
            cinemas.add(new Cinema());
        }

        // Try to create the 101st Cinema instance and expect an IllegalStateException
        try {
            new Cinema();
            fail("Expected IllegalStateException for creating more than 100 Cinema instances.");
        } catch (IllegalStateException e) {
            assertEquals("Maximum number of Cinema instances (100) reached.", e.getMessage());
        }
    }
}

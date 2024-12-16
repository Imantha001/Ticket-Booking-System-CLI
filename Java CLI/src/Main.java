import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    static volatile boolean systemRunning = true; // Flag to control threads
    private static Logger logger;  // Logger instance for logging events
    private static String eventName;
    private static int totalTickets;
    private static int ticketReleaseRate;
    private static int customerRetrievalRate;
    private static int maxTicketCapacity;
    private static double ticketPrice; // Add ticket price variable

    // Main method to start the ticket system
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        logger = new Logger("Ticket_Details.txt"); // Initialize logger

        while (true) {
            displayMainMenu(); // Display main menu
            int choice = -1; // Default invalid value

            // Validate menu choice input
            while (true) {
                try {
                    String input = scanner.nextLine();
                    choice = Integer.parseInt(input); // Parse input as an integer
                    break; // Exit loop if successful
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid numeric (between 1 and 3) choice:"); // Prompt for valid input
                }
            }

            // Perform action based on users choice
            switch (choice) {
                case 1:
                    // Prompt user for configuration
                    System.out.println("Enter total tickets (greater than 0):");
                    totalTickets = validateInput(scanner); // Validate & get total tickets input

                    System.out.println("Enter ticket release rate (greater than 0):");
                    ticketReleaseRate = validateInput(scanner); // Validate & get ticket release rate input

                    System.out.println("Enter customer retrieval rate (greater than 0):");
                    customerRetrievalRate = validateInput(scanner); // Validate & get customer retrieval rate input

                    System.out.println("Enter maximum ticket capacity (greater than 0 and total tickets):");
                    maxTicketCapacity = validateInput(scanner, totalTickets); // Validate & get maximum ticket capacity input

                    System.out.println("Enter ticket price:");
                    ticketPrice = validatePriceInput(scanner); // Validate & get ticket price input

                    // Ask for event name
                    System.out.println("Enter event name:");
                    eventName = scanner.nextLine(); // Read event name input

                    // Log system configuration
                    logEventDetails();

                    startSystem(); // Start the ticket system
                    break;
                case 2:
                    showTicketPoolStatus(); // Display ticket pool status
                    break;
                case 3:
                    System.out.println("Exiting the system...");
                    logger.log("System exited.");
                    logger.log("---------------------------------------------------------------------");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }


    // Display main menu options`
    private static void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Start system");
        System.out.println("2. View ticket pool status");
        System.out.println("3. Exit system");
        System.out.print("Enter your choice: ");
    }

    // Method to start the ticket system
    private static void startSystem() {
        System.out.println("Starting the system...");

        // Start the ticket system
        TicketPool ticketPool = new TicketPool(maxTicketCapacity, totalTickets, logger);

        // Create and start vendor and customer threads
        Thread vendor1 = new Thread(new Vendor(ticketPool, ticketReleaseRate), "Vendor");
        Thread customer1 = new Thread(new Customer(ticketPool, customerRetrievalRate), "Customer");

        vendor1.start();
        customer1.start();

        // Wait for threads to complete
        try {
            vendor1.join();
            customer1.join();
        } catch (InterruptedException e) {
            logger.log("System interrupted: " + e.getMessage()); // Log the exception
        }

        // Log final summary
        logger.log("Final Summary:");
        logger.log("Tickets Sold: " + ticketPool.getTicketsSold());
        logger.log("Remaining Tickets: " + ticketPool.getRemainingTickets());

        System.out.println("Ticket system stopped.");
        logger.log("Ticket system stopped.");
    }

    // Method to display ticket pool status
    private static void showTicketPoolStatus() {
        System.out.println("\nTicket Pool Status:");

        boolean eventFound = false; // Flag to check if an event is found
        boolean anyEventFound = false; // Flag to check if any events have been found

        // Read the ticket details file
        try (BufferedReader reader = new BufferedReader(new FileReader("Ticket_Details.txt"))) {
            String line;

            // Read the log file and display the relevant details
            while ((line = reader.readLine()) != null) {
                if (line.contains("##__Tickets__##:")) {
                    eventFound = true;
                    anyEventFound = true;
                    System.out.println(); // Add a blank line between events
                }
                if (eventFound) {
                    // Remove the date and time from the line
                    String detail = line.substring(line.indexOf("] ") + 2);

                    // Print relevant event details without date and time
                    if (detail.startsWith("##__Tickets__##:") ||
                            detail.startsWith("Event Name:") ||
                            detail.startsWith("Total Tickets:") ||
                            detail.startsWith("Ticket Release Rate:") ||
                            detail.startsWith("Customer Retrieval Rate:") ||
                            detail.startsWith("Maximum Ticket Capacity:") ||
                            detail.startsWith("Ticket Price:") ||
                            detail.startsWith("Final Summary:") ||
                            detail.startsWith("Tickets Sold:") ||
                            detail.startsWith("Remaining Tickets:")) {
                        System.out.println(detail); // Print the detail line
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Failed to read the details file: " + e.getMessage()); // Handle file read error
        }

        if (!anyEventFound) {
            System.out.println("No event details found."); // Display message if no events are found
        }
    }

    // Log the entered event details to the file
    private static void logEventDetails() {
        logger.log("##__Tickets__##:");
        logger.log("Event Name: " + eventName);  // Log event name
        logger.log("Total Tickets: " + totalTickets);
        logger.log("Ticket Release Rate: " + ticketReleaseRate);
        logger.log("Customer Retrieval Rate: " + customerRetrievalRate);
        logger.log("Maximum Ticket Capacity: " + maxTicketCapacity);
        logger.log("Ticket Price: " + ticketPrice); // Log ticket price
    }

    // Validate user input for ticket counts
    private static int validateInput(Scanner scanner) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine()); // Parse input as an integer
                if (input > 0) break;
                else System.out.println("Please enter a value greater than 0:");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value greater than 0:");
            }
        }
        return input;
    }

    // Validate user input for total tickets
    private static int validateInput(Scanner scanner, int totalTickets) {
        int input;
        while (true) {
            input = validateInput(scanner);
            if (input > totalTickets) break;
            else System.out.println("Please enter a value greater than total tickets:");
        }
        return input;
    }

    // Validate ticket price input
    private static double validatePriceInput(Scanner scanner) {
        double input;
        while (true) {
            try {
                input = Double.parseDouble(scanner.nextLine()); // Parse input as a double
                if (input > 0) break;
                else System.out.println("Please enter a price greater than 0:");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value greater than 0:");
            }
        }
        return input;
    }
}
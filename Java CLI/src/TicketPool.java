public class TicketPool {
    private final int capacity; // Maximum capacity of the ticket pool
    private final int totalTickets; // Total number of tickets
    private int currentTickets = 0; // Remaining tickets in the pool
    private int totalTicketsAdded = 0; // Total tickets that have been added to the pool
    private int ticketsSold = 0; // Total tickets sold
    private final Logger logger; // Logger instance for logging events

    // Constructor to initialize the ticket pool with capacity, total tickets, and logger
    public TicketPool(int capacity, int totalTickets, Logger logger) {
        this.capacity = capacity;
        this.totalTickets = totalTickets;
        this.logger = logger;
    }

    // Method to add tickets to the pool
    synchronized void addTickets(int count) {
        if (totalTicketsAdded >= totalTickets) { // Check if all tickets have been added
            Main.systemRunning = false; // Stop the system if all tickets are added
            return;
        }

        int ticketsToAdd = Math.min(count, capacity - currentTickets); // Calculate tickets to add based on capacity
        if (ticketsToAdd + totalTicketsAdded > totalTickets) { // Check if the total tickets added exceeds the total tickets
            ticketsToAdd = totalTickets - totalTicketsAdded; // Only add remaining tickets
        }

        currentTickets += ticketsToAdd; // Update the current ticket count
        totalTicketsAdded += ticketsToAdd; // Update the total tickets added count
        logger.log(Thread.currentThread().getName() + " added " + ticketsToAdd + " tickets. Total added: " + totalTicketsAdded);

        // Log the new total (remaining tickets)
        logger.log(Thread.currentThread().getName() + " Current Tickets in Pool: " + currentTickets);
    }

    // Method to retrieve tickets from the pool
    synchronized void retrieveTickets(int count) {
        for (int i = 0; i < count; i++) {
            if (currentTickets > 0) {
                currentTickets--; // Decrease the ticket count as it's sold
                ticketsSold++; // Increment tickets sold count
                logger.log(Thread.currentThread().getName() + " retrieved a ticket. Remaining: " + currentTickets);
            } else {
                logger.log(Thread.currentThread().getName() + " tried to retrieve a ticket, but none are available.");
                break;
            }
        }
    }

    // Getter methods to retrieve ticket pool details
    public int getTicketsSold() {
        return ticketsSold;
    }

    // Getter method to retrieve the remaining tickets in the pool
    public int getRemainingTickets() {
        return currentTickets;
    }
}

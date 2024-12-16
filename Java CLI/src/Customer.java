public class Customer implements Runnable {
    private final TicketPool ticketPool; // Reference to the ticket pool
    private final int customerRetrievalRate; // Number of tickets to retrieve

    // Constructor to initialize the ticket pool and the number of tickets to retrieve
    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        while (Main.systemRunning) { // Continue while the system is running
            ticketPool.retrieveTickets(customerRetrievalRate); // Retrieve tickets from the pool
            try {
                Thread.sleep(500); // Pause for a short time before the next retrieval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }
        }
    }
}
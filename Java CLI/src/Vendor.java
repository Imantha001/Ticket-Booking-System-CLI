public class Vendor implements Runnable {
    private final TicketPool ticketPool; // Reference to the ticket pool
    private final int ticketReleaseRate; // Number of tickets to release

    // Constructor to initialize the ticket pool and the number of tickets to release
    public Vendor(TicketPool ticketPool, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        while (Main.systemRunning) { // Continue while the system is running
            ticketPool.addTickets(ticketReleaseRate); // Add tickets to the pool
            try {
                Thread.sleep(500); // Pause for a short time before the next release
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }
        }
    }
}
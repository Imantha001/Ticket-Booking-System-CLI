# Ticket Management System

This project is a Java-based command-line application for managing ticket sales for events. It simulates a ticketing system where vendors add tickets to a pool, and customers retrieve tickets from the pool. The system is multithreaded, with vendors and customers running as separate threads.

## Features

- **Multithreaded System**: Vendors and customers operate concurrently.
- **Dynamic Ticket Management**: Vendors add tickets to the pool at a specified rate, and customers retrieve tickets at their own rate.
- **Event Configuration**: Users can configure event details such as total tickets, ticket release rate, customer retrieval rate, maximum ticket capacity, and ticket price.
- **Logging**: All system activities are logged to a file (`Ticket_Details.txt`) with timestamps.
- **Event History**: Users can view the status of past events from the log file.


## How It Works

1. **Start the System**:
   - The user is prompted to configure the event details:
     - Total tickets
     - Ticket release rate
     - Customer retrieval rate
     - Maximum ticket capacity
     - Ticket price
     - Event name
   - The system starts with a vendor and a customer thread.

2. **Vendor**:
   - Adds tickets to the pool at the specified release rate.
   - Stops adding tickets once the total ticket count is reached.

3. **Customer**:
   - Retrieves tickets from the pool at the specified retrieval rate.
   - Stops retrieving tickets when no tickets are available.

4. **Logging**:
   - All activities (e.g., tickets added, tickets retrieved, final summary) are logged to `Ticket_Details.txt`.

5. **View Event History**:
   - Users can view the status of past events from the log file.

## How to Run

1. Clone the repository or download the source code.
2. Open the project in an IDE (e.g., IntelliJ IDEA or VS Code).
3. Compile and run the `Main.java` file.
4. Follow the on-screen instructions to configure and start the system.

## Example Log Output

Here is an example of the log output in `Ticket_Details.txt`:

[2024-12-01 13:40:51] ##Tickets##: [2024-12-01 13:40:51] Event Name: Spandana [2024-12-01 13:40:51] Total Tickets: 100 [2024-12-01 13:40:51] Ticket Release Rate: 5 [2024-12-01 13:40:51] Customer Retrieval Rate: 2 [2024-12-01 13:40:51] Maximum Ticket Capacity: 120 [2024-12-01 13:40:51] Ticket Price: 250.0 [2024-12-01 13:40:51] Vendor added 5 tickets. Total added: 5 [2024-12-01 13:40:51] Vendor Current Tickets in Pool: 5 [2024-12-01 13:40:51] Customer retrieved a ticket. Remaining: 4 ... [2024-12-01 13:41:02] Final Summary: [2024-12-01 13:41:02] Tickets Sold: 42 [2024-12-01 13:41:02] Remaining Tickets: 58 [2024-12-01 13:41:02] Ticket system stopped.


## Requirements

- Java 8 or higher
- A text editor or IDE for Java development

## License

This project is licensed under the MIT License.

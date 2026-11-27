import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


/**
 * Manages the sailing club's fleet of boats.
 * Handles fleet operations and file persistence.
 */
public class FleetManagement {

    private static final String FLEET_FILE = "FleetData.db";

    /**
     * Starts the fleet management program.
     *
     * @param args command-line arguments, optionally containing a CSV filename
     */
    public static void main(String[] args) {

        ArrayList<Boat> fleet;
        Scanner keyboard;

        try {
            if (args.length > 0) {
                fleet = loadCSV(args[0]);
            } else {
                fleet = loadFleet();
            }
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("Error loading fleet: "
                    + exception.getMessage());
            System.exit(1);
            return;
        }

        keyboard = new Scanner(System.in);


        System.out.println("Welcome to the Fleet Management System");
        System.out.println("--------------------------------------");
        System.out.println();

        runMenu(fleet, keyboard);

        keyboard.close();

        try {
            saveFleet(fleet);
        } catch (IOException exception) {
            System.out.println("Error saving fleet: "
                    + exception.getMessage());
            System.exit(1);
            return;
        }

        System.out.println("Exiting the Fleet Management System");

    }

    /**
     * Converts a CSV record into a Boat object.
     *
     * @param csvLine the comma-separated boat information
     * @return the Boat object created from the record
     */
    private static Boat parseBoat(String csvLine) {
        String[] fields;
        BoatType type;
        String name;
        int year;
        String makeModel;
        int length;
        double purchasePrice;
        Boat boat;

        fields = csvLine.split(",");

        type = BoatType.valueOf(fields[0]);
        name = fields[1];
        year = Integer.parseInt(fields[2]);
        makeModel = fields[3];
        length = Integer.parseInt(fields[4]);
        purchasePrice = Double.parseDouble(fields[5]);

        boat = new Boat(type, name, year, makeModel,
                length, purchasePrice);

        return boat;
    }

    /**
     * Loads boat information from a CSV file into a fleet.
     *
     * @param filename the name of the CSV file
     * @throws IOException if the CSV file cannot be read
     * @return the fleet containing the loaded boats
     *
     */
    private static ArrayList<Boat> loadCSV(String filename)
            throws IOException {

        ArrayList<Boat> fleet;
        String line;

        fleet = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filename))) {

            line = reader.readLine();

            while (line != null) {
                fleet.add(parseBoat(line));
                line = reader.readLine();
            }
        }

        return fleet;
    }

    /**
     * Safely saves the fleet to a serialized database file.
     *
     * @param fleet the fleet to save
     * @throws IOException if the fleet cannot be saved
     */
    private static void saveFleet(ArrayList<Boat> fleet)
            throws IOException {

        Path databasePath;
        Path temporaryPath;

        databasePath = Path.of(FLEET_FILE).toAbsolutePath();
        temporaryPath = Files.createTempFile(
                databasePath.getParent(), "FleetData-", ".tmp");

        try {
            try (ObjectOutputStream output =
                         new ObjectOutputStream(
                                 Files.newOutputStream(temporaryPath))) {

                output.writeObject(fleet);
            }

            Files.move(temporaryPath, databasePath,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);

        } finally {
            Files.deleteIfExists(temporaryPath);
        }
    }

    /**
     * Loads the fleet from the serialized database file.
     *
     * @throws IOException if the database cannot be read
     * @throws ClassNotFoundException if a serialized class cannot be found
     * @return the restored fleet of boats
     */
    private static ArrayList<Boat> loadFleet() throws IOException, ClassNotFoundException {
        ArrayList<Boat> fleet;

        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(FLEET_FILE))) {

            fleet = (ArrayList<Boat>) input.readObject();

        }

        return fleet;

    }

    /**
     * Displays and processes the fleet management menu.
     *
     * @param fleet the fleet being managed
     * @param keyboard the Scanner used to read user input
     */
    private static void runMenu(ArrayList<Boat> fleet,  Scanner keyboard) {
        String choice;

        do {
            System.out.print("(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it : ");

            choice = keyboard.nextLine().trim().toUpperCase();

            switch (choice) {
                case "P":
                    printFleet(fleet);
                    break;

                case "A":
                    addBoat(fleet, keyboard);
                    break;

                case "R":
                    removeBoat(fleet, keyboard);
                    break;

                case "E":
                    requestExpense(fleet, keyboard);
                    break;

                case "X":
                    System.out.println();
                    break;

                default:
                    System.out.println("Invalid menu option, try again");

            }


        } while (!choice.equals("X"));

    }

    /**
     * Prints the fleet inventory and its financial totals.
     *
     * @param fleet the fleet to display
     */
    private static void printFleet(ArrayList<Boat> fleet) {
        double totalPurchasePrice;
        double totalExpenses;
        Boat boat;
        int index;

        totalPurchasePrice = 0.0;
        totalExpenses = 0.0;

        System.out.println("\nFleet report:");

        for (index = 0; index < fleet.size(); index++) {

            boat = fleet.get(index);
            System.out.println("    " + boat);

            totalPurchasePrice += boat.getPurchasePrice();
            totalExpenses += boat.getExpenses();

        }

        System.out.printf(
                "    %-50s: Paid $ %8.2f : Spent $ %8.2f%n",
                "Total", totalPurchasePrice, totalExpenses);
        System.out.println();


    }

    /**
     * Adds a new boat to the fleet using CSV input.
     *
     * @param fleet the fleet to modify
     * @param keyboard the Scanner used to read user input
     */
    private static void addBoat(ArrayList<Boat> fleet, Scanner keyboard) {
        String csvLine;
        Boat boat;

        System.out.print("Please enter the new boat CSV data          : ");

        csvLine = keyboard.nextLine();

        boat = parseBoat(csvLine);

        fleet.add(boat);

        System.out.println();

    }

    /**
     * Searches the fleet for a boat by name, ignoring case.
     *
     * @param fleet the fleet to search
     * @param name  the name of the requested boat
     * @return the matching Boat, or null if not found
     */
    private static Boat findBoat(ArrayList<Boat> fleet, String name) {
        Boat boat;
        int index;

        for (index = 0; index < fleet.size(); index++) {
            boat = fleet.get(index);
            if (boat.getName().equalsIgnoreCase(name)) {
                return boat;
            }
        }

        return null;
    }

    /**
     * Removes a boat from the fleet by name.
     *
     * @param fleet the fleet to modify.
     * @param keyboard the Scanner used to read user input
     */
    private static void removeBoat(ArrayList<Boat> fleet, Scanner keyboard) {
        String name;
        Boat boat;

        System.out.print("Which boat do you want to remove?           : ");

        name = keyboard.nextLine().trim();

        boat = findBoat(fleet, name);

        if (boat == null) {
            System.out.println("Cannot find boat " + name);
        } else {
            fleet.remove(boat);
        }

        System.out.println();


    }

    /**
     * Requests authorization for a boat maintenance expense.
     *
     * @param fleet the fleet being managed
     * @param keyboard the Scanner used to read user input
     */
    private static void requestExpense(ArrayList<Boat> fleet, Scanner keyboard) {
        String name;
        Boat boat;
        double amount;
        double remaining;

        System.out.print("Which boat do you want to spend on?         : ");

        name = keyboard.nextLine().trim();

        boat = findBoat(fleet, name);

        if (boat == null) {
            System.out.println("Cannot find boat " + name);
        } else {
            System.out.print("How much do you want to spend?              : ");

            amount = Double.parseDouble(keyboard.nextLine());

            if (boat.authorizeExpense(amount)) {
                System.out.printf("Expense authorized, $%.2f spent.%n",
                        boat.getExpenses());
            } else {
                remaining = boat.getPurchasePrice() - boat.getExpenses();
                System.out.printf(
                        "Expense not permitted, only $%.2f left to spend.%n",
                        remaining);
            }

        }

        System.out.println();

    }


}

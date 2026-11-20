import java.io.Serializable;


/**
 * Represents an individual boat in the fleet.
 * Stores boat information and tracks maintenance expenses.
 */
public class Boat implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double INITIAL_BOAT_EXPENSE = 0.0;

    private final BoatType type;
    private final String name;
    private final int year;
    private final String makeModel;
    private final int length;
    private final double purchasePrice;
    private double expenses;


    /**
     * Constructs a boat with its initial information.
     * Maintenance expenses are initialized to zero.
     *
     * @param type          the type of boat
     * @param name          the name of the boat
     * @param year          the year of manufacture
     * @param makeModel     the make and model of the boat
     * @param length        the length of the boat in feet
     * @param purchasePrice the original purchase price
     */
    public Boat(BoatType type, String name, int year,
                String makeModel, int length, double purchasePrice) {
        this.type = type;
        this.name = name;
        this.year = year;
        this.makeModel = makeModel;
        this.length = length;
        this.purchasePrice = purchasePrice;
        this.expenses = INITIAL_BOAT_EXPENSE;
    }

    /**
     * Gets the name of this boat.
     *
     * @return the boat's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the original purchase price of the boat.
     *
     * @return purchase price.
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Gets the amount of expenses.
     *
     * @return expenses of boat.
     */
    public double getExpenses() {
        return expenses;
    }

    /**
     * Attempts to authorize a maintenance expense.
     *
     * @param amount the requested maintenance expense
     * @return true if authorized, false otherwise
     */
    public boolean authorizeExpense(double amount) {
        if (amount >= 0 && expenses + amount <= purchasePrice) {
            expenses += amount;
            return true;
        }

        return false;
    }

    /**
     * Returns the formatted information for this boat.
     *
     * @return the boat's formatted inventory record
     */
    @Override
    public String toString() {
        return String.format(
                "%-7s %-20s %4d %-10s %3d' : Paid $ %8.2f : Spent $ %8.2f",
                type, name, year, makeModel, length,
                purchasePrice, expenses);
    }
}

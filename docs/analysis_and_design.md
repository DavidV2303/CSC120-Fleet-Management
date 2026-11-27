David Vega Alvarado
11/27/2026

# Fleet Management System
CSC 120
Project #2 — Object-Oriented Analysis and Design

---

# 1. OBJECT-ORIENTED ANALYSIS

## 1.1 Domain

**Problem Description**

The Commodore of the Coconut Grove Sailing Club needs a program to manage the club's fleet of sailing and power boats. The program must store each boat's information, purchase price, and maintenance expenses. Users must be able to view the fleet inventory, add and remove boats, and request maintenance expenses. To control expenditure, the Commodore has established a policy that the total maintenance expenses for any individual boat cannot exceed its purchase price.

**Entities**

1. Boat — An individual sailing or power boat owned by the club.
2. Fleet — The collection of boats owned by the club.
3. Boat Type — The classification of a boat as SAILING or POWER.
4. Club User — The person operating the Fleet Management System.
5. Fleet Data Files — Files used to initialize and preserve fleet information.

**Actors**

1. Club User — Interacts with the system by selecting menu options, entering boat information, removing boats, and requesting maintenance expenses.
2. Fleet Management System — Processes user requests, maintains the fleet records, enforces spending restrictions through individual Boat objects, and manages persistent storage.

**Non-Actors**

1. Boat — Stores its individual information and maintenance expenses.
2. Fleet — Represents the collection of Boat objects.
3. Boat Type — Identifies whether a boat is SAILING or POWER.
4. FleetData.csv — Provides initial boat information.
5. FleetData.db — Stores serialized fleet information between program executions.

## 1.2 Function Points

**Functions Initiated by the Club User**

1. Print the fleet inventory.
2. Add a boat using CSV-formatted input.
3. Remove a boat by name.
4. Request maintenance expenditure for a specific boat.
5. Exit the program.

**Functions Performed by the Fleet Management System**

1. Display the welcome message and menu.
2. Accept menu selections without regard to letter case.
3. Read initial fleet records from a CSV file when a filename is supplied as a command-line argument.
4. Restore previously saved fleet information from FleetData.db when no command-line argument is supplied.
5. Convert CSV records into Boat objects.
6. Store an arbitrary number of Boat objects in an ArrayList.
7. Display individual boat records and calculate fleet totals.
8. Locate boats by name without regard to letter case.
9. Add or remove Boat objects from the fleet.
10. Request expense authorization from the selected Boat object.
11. Save the fleet to FleetData.db using object serialization and temporary-file replacement when exiting.
12. Terminate with an error if an I/O or class-restoration failure prevents the fleet from loading, or if an I/O failure prevents it from saving.

**Functions Performed by Individual Boat Objects**

1. Store boat attributes and maintenance expenses.
2. Provide access to required information through accessor methods.
3. Determine whether a requested expense is within the purchase-price limit.
4. Update maintenance expenses when a request is authorized.
5. Provide a formatted string representing the boat's information.

## 1.3 Scenarios

**Scenario 1 — Initial Program Execution**

1. The user starts FleetManagement with a CSV filename as a command-line argument.
2. The program opens the specified CSV file.
3. Each CSV record is read and parsed into the appropriate data types.
4. A Boat object is created for each record, with maintenance expenses initialized to 0.0.
5. Each Boat object is added to the fleet's ArrayList.
6. After successful loading, main creates the Scanner and displays the welcome message.
7. The program displays the menu and waits for a selection.

**Scenario 2 — Printing the Inventory**

1. The user selects P.
2. The program displays the fleet report heading.
3. The program iterates through the ArrayList.
4. Each Boat provides its formatted information.
5. The program accumulates purchase prices and maintenance expenses.
6. The program displays the fleet totals.
7. The menu is displayed again.

**Scenario 3 — Adding a Boat**

1. The user selects A.
2. The program requests a CSV-formatted boat record.
3. The user enters the boat type, name, year, make/model, length, and purchase price.
4. The program parses the record and constructs a new Boat object.
5. The new Boat object begins with expenses of 0.0.
6. The program adds the Boat to the ArrayList.
7. The program prints a blank line and displays the menu again.

**Scenario 4 — Removing a Boat**

1. The user selects R.
2. The program requests the name of the boat.
3. The program searches the ArrayList using a case-insensitive comparison.
4. If the boat is found, it is removed from the ArrayList.
5. If the boat is not found, an appropriate message is displayed.
6. The menu is displayed again.

**Scenario 5 — Requesting Maintenance Expenditure**

1. The user selects E.
2. The program requests the name of the boat.
3. The program searches for the matching Boat object without regard to letter case.
4. If no matching boat exists, the program displays an error message.
5. Otherwise, the program requests the amount to spend.
6. The Boat object converts the purchase price, current expenses, and requested amount to integer cents using Math.round(). It checks that the request is finite and nonnegative and that it fits within the remaining budget in cents.
7. If the request is permitted, the Boat adds the requested cents to its accumulated expenses, converts the result back to dollars, and returns true.
8. Otherwise, the expenses remain unchanged and the Boat returns false.
9. The program displays the authorization result.
10. The menu is displayed again.

**Scenario 6 — Exiting and Saving**

1. The user selects X.
2. runMenu finishes and returns control to main.
3. main calls saveFleet with the current ArrayList of Boat objects.
4. saveFleet creates a temporary file in the same directory as FleetData.db and serializes the entire ArrayList in one operation.
5. After the output stream closes successfully, saveFleet requests an atomic move to replace FleetData.db with the temporary file.
6. The temporary file is deleted if it still exists.
7. If saving succeeds, main displays the exit message and terminates. If an IOException occurs, main displays an error and exits with status 1 instead.

**Scenario 7 — Subsequent Program Execution**

1. The user starts FleetManagement without command-line arguments.
2. The program reads FleetData.db and deserializes the saved ArrayList.
3. Previously added boats, removed boats, and recorded expenses are preserved.
4. After successful loading, main creates the Scanner and displays the welcome message.
5. The program displays the menu, and the user can continue managing the fleet.

**Scenario 8 — Unsuccessful Fleet Loading**

1. The user starts the program with a missing or unreadable CSV file, or attempts to restore a missing or corrupt database.
2. The loading method propagates an IOException or, when deserializing, a ClassNotFoundException to main.
3. main displays an error message and exits with status 1.
4. The menu is not started and saveFleet is not called, so the existing database is not overwritten by an empty or incomplete fleet.

---

# 2. OBJECT-ORIENTED DESIGN

## 2.1 Classes and Objects

**Class: Boat**

Represents one boat belonging to the Coconut Grove Sailing Club. Each instance stores its own information and controls its maintenance expenses.

Example objects: Moon Glow, Osita, Big Brother.

The Boat class implements Serializable to allow its instances to be saved and restored.

**Class: FleetManagement**

Contains the main method and controls the entire application. It manages the fleet collection, menu, keyboard input, CSV parsing, and file operations.

The fleet is stored as a local `ArrayList<Boat>` variable in main and passed to other methods as needed. A single Scanner is created in main and passed to the menu and the methods that read user input.

**Enum: BoatType**

Defines the two possible boat types:

- SAILING
- POWER

**Class Relationships**

- FleetManagement uses and manages zero or more Boat objects.
- Each Boat has one BoatType value.
- Boat implements the Serializable interface.
- FleetManagement uses an `ArrayList<Boat>` to store the fleet.

## 2.2 Data of Objects and Classes

### Boat — Instance Data

All Boat attributes are private.

| Attribute | Data Type | Initial Value | Access and Mutability |
|---|---|---|---|
| type | BoatType | Constructor parameter | Read through formatted output; not changed after creation |
| name | String | Constructor parameter | Read through getName(); not changed after creation |
| year | int | Constructor parameter | Read through formatted output; not changed after creation |
| makeModel | String | Constructor parameter | Read through formatted output; not changed after creation |
| length | int | Constructor parameter | Read through formatted output; not changed after creation |
| purchasePrice | double | Constructor parameter | Read through getPurchasePrice(); not changed after creation |
| expenses | double | 0.0 | Read through getExpenses(); modified only through authorizeExpense() |

The first six instance attributes are declared final because they do not change after the Boat is constructed. The expenses attribute cannot be final because its value changes when maintenance expenditure is authorized.

The spending policy prevents other classes from modifying expenses directly.

### Boat — Class Data

| Constant | Data Type | Value | Purpose |
|---|---|---|---|
| serialVersionUID | long | 1L | Identifies the serialized version of the Boat class |
| INITIAL_BOAT_EXPENSE | double | 0.0 | Initial maintenance expenses for a new boat |
| CENTS_PER_DOLLAR | int | 100 | Converts between dollars and integer cents |

All three constants are private static final. The monetary instance fields remain double so that the existing serialized Boat structure is retained.

### FleetManagement — Class and Local Data

- FLEET_FILE: String — Private static final constant containing "FleetData.db".
- fleet: `ArrayList<Boat>` — Local variable in main containing the fleet's Boat objects.
- keyboard: Scanner — Local variable in main, initialized after a successful load and passed to runMenu, addBoat, removeBoat, and requestExpense. Only FleetManagement creates or uses the Scanner.
- choice: String — Local variable in runMenu used to process user selections.
- databasePath, temporaryPath: Path — Local variables in saveFleet used to identify the database and its temporary replacement file.
- Other local variables — Used for searching, parsing CSV records, calculating totals, and handling user input.

Ordinary local variables are declared at the beginning of their respective methods. File resources are declared in try-with-resources statements.

### BoatType — Enumeration Data

SAILING and POWER are fixed enum constants.

## 2.3 Methods of Objects and Classes

### Boat Class

**Constructor**

`+ Boat(type: BoatType, name: String, year: int, makeModel: String, length: int, purchasePrice: double)`

Initializes the six supplied attributes and sets expenses to 0.0.

**Accessors**

`+ getName(): String`
Returns the boat's name.

`+ getPurchasePrice(): double`
Returns the original purchase price.

`+ getExpenses(): double`
Returns the accumulated maintenance expenses.

**Controlled Mutator**

`+ authorizeExpense(amount: double): boolean`

Checks whether the requested maintenance expense is permitted.

The method converts purchasePrice, expenses, and amount into long integer cent values using Math.round(value * CENTS_PER_DOLLAR). This avoids floating-point comparison errors such as incorrectly denying a $0.20 expense after spending $0.10 on a $0.30 boat.

If the original amount is finite and nonnegative and amountInCents is less than or equal to purchasePriceInCents minus expensesInCents, the method adds amountInCents to expensesInCents. It converts the resulting total back to double dollars and stores it in expenses before returning true.

Otherwise, the method leaves expenses unchanged and returns false.

**Other Method**

`+ toString(): String`

Overrides Object's toString method and returns a formatted record containing the boat's type, name, year, make/model, length, purchase price, and maintenance expenses.

The Boat class does not read keyboard input or perform file operations.

### FleetManagement Class

**Main Method**

`+ main(args: String[]): void`

Selects the loading procedure based on the command-line arguments. If loading succeeds, creates the Scanner, displays the welcome message, and calls runMenu(fleet, keyboard). After runMenu returns, main calls saveFleet. It displays the normal exit message only when saving succeeds.

main catches IOException and ClassNotFoundException during loading and IOException during saving. In either handled failure case, it displays the error and terminates with exit status 1.

**File Handling Methods**

`- loadCSV(filename: String): ArrayList<Boat>`

Opens the CSV file using a BufferedReader in a try-with-resources statement, reads each record, creates Boat objects using parseBoat, and returns the populated ArrayList. An IOException is propagated to main.

`- loadFleet(): ArrayList<Boat>`

Reads FleetData.db using an ObjectInputStream in a try-with-resources statement, deserializes the saved ArrayList, and returns the restored fleet. IOException and ClassNotFoundException are propagated to main.

`- saveFleet(fleet: ArrayList<Boat>): void`

Creates a temporary file in the same directory as FleetData.db. Serializes the entire ArrayList in a single writeObject call using an ObjectOutputStream in a try-with-resources statement. After the stream closes successfully, requests an atomic move of the temporary file to the database path with replacement. A finally block deletes the temporary file if it still exists. An IOException is propagated to main, including when the requested atomic move is unsupported or fails.

**CSV Parsing Method**

`- parseBoat(csvLine: String): Boat`

Splits a CSV-formatted record into six fields, converts each field to its appropriate data type, constructs a Boat object, and returns it.

This method is used when loading CSV data and when adding new boats.

**Menu Method**

`- runMenu(fleet: ArrayList<Boat>, keyboard: Scanner): void`

Displays the menu repeatedly, reads case-insensitive selections using the supplied Scanner, and calls the appropriate methods. When the user selects X, the loop ends and control returns to main, which is responsible for saving the fleet.

**Fleet Operations**

`- printFleet(fleet: ArrayList<Boat>): void`

Prints all boat records and calculates the fleet's total purchase prices and maintenance expenses.

`- addBoat(fleet: ArrayList<Boat>, keyboard: Scanner): void`

Reads a CSV-formatted record using the supplied Scanner, constructs a new Boat using parseBoat, adds it to the ArrayList, and prints a blank line.

`- removeBoat(fleet: ArrayList<Boat>, keyboard: Scanner): void`

Requests a boat name using the supplied Scanner, finds the matching object, and removes it from the ArrayList if found. Otherwise, displays a boat-not-found message.

`- findBoat(fleet: ArrayList<Boat>, name: String): Boat`

Searches the ArrayList for a matching boat name using equalsIgnoreCase(). Returns the Boat object if found, or null if no match exists.

`- requestExpense(fleet: ArrayList<Boat>, keyboard: Scanner): void`

Requests a boat name using the supplied Scanner and locates the boat. If found, reads the expense amount and calls its authorizeExpense method. If authorized, displays the accumulated expenses; otherwise, displays the remaining allowance (purchase price minus expenses, formatted to two decimal places). If the boat is not found, displays an error message.

**Method Access**

Boat's constructor, accessors, authorization method, and toString are public.

FleetManagement's methods are static because the application is controlled from main and no FleetManagement instance is required.

The main method is public static. All other FleetManagement methods are private static. The file-handling methods propagate the checked exceptions described above to main, which controls termination. Try-with-resources automatically closes the file streams.

## 2.4 Overall Program Control

The program uses a menu-driven control structure within the FleetManagement class.

**Initialization**

1. Declare an `ArrayList<Boat>` variable and a Scanner variable in main.
2. If a command-line filename is supplied, initialize the fleet from the CSV file.
3. Otherwise, load the serialized fleet from FleetData.db.
4. If loading throws IOException or ClassNotFoundException, display the error and terminate with status 1 without saving.
5. After a successful load, initialize the Scanner and display the welcome message.
6. Call runMenu with the loaded ArrayList and the Scanner.

**Menu Processing**

Repeat until the user selects X:

- P — Call printFleet.
- A — Call addBoat with the fleet and Scanner.
- R — Call removeBoat with the fleet and Scanner.
- E — Call requestExpense with the fleet and Scanner.
- X — End the menu loop and return to main.
- Otherwise — Display an invalid-option message and prompt again.

Menu selections are case-insensitive.

**Data Management**

The ArrayList stores references to the individual Boat objects. Each Boat manages its own maintenance expenses. FleetManagement performs collection-level operations such as adding, removing, searching, and calculating totals.

**Program Termination**

When the user selects X, runMenu returns to main. main calls saveFleet to serialize the current fleet to a temporary file and replace FleetData.db after serialization and stream closure succeed. This preserves the fleet information for subsequent executions. If saving throws an IOException, main displays an error and terminates with status 1 rather than printing the normal exit message.

---

# 3. DESIGN CONSTRAINTS AND ASSUMPTIONS

1. The program supports an arbitrary number of boats through ArrayList.
2. Boat types are restricted to SAILING and POWER using an enum.
3. Boat lengths must not exceed 100 feet.
4. Boat purchase prices are less than $1,000,000.
5. All initial CSV records are assumed to be correctly formatted.
6. Numeric input is assumed to be syntactically correct. Purchase prices and maintenance expenses are assumed to be ordinary monetary amounts expressed to the nearest cent; the authorization calculation rounds values to cents. Purchase prices are assumed to be finite and nonnegative.
7. Menu options and boat-name searches are case-insensitive.
8. Only FleetManagement uses a Scanner. A single Scanner is created in main and passed to the methods that need keyboard input.
9. All file handling is performed in FleetManagement.
10. Boat objects implement Serializable.
11. The fleet is saved in FleetData.db using object serialization and a temporary file. The filesystem must support the requested atomic replacement for saving to succeed. The program reports a save failure rather than falling back to a non-atomic move.
12. All classes and methods are to contain appropriate Javadoc documentation.
13. The complete project and design documentation will be maintained in GitHub.
14. The initializing run uses FleetData.csv as its command-line argument; subsequent runs use no arguments. Both IntelliJ configurations should use the project root as their working directory.
15. Malformed CSV records and invalid numeric text are outside the assumed valid input format; the program does not provide full validation or recovery for these inputs.

---

# 4. IMPLEMENTATION PLAN

1. Create BoatType.java with the SAILING and POWER enum values.
2. Implement Boat.java with private attributes, constructor, getters, integer-cent expense authorization, and toString.
3. Implement FleetManagement.java with the main method, local fleet ArrayList, and a local Scanner passed to the input methods.
4. Implement reusable CSV parsing and initial CSV loading.
5. Implement the menu and fleet operations.
6. Implement serialization and deserialization using try-with-resources, checked exception propagation, and temporary-file replacement for safe saving.
7. Configure IntelliJ for initialization and subsequent runs.
8. Test against the professor's sample interactions, exact-budget expense requests, persistence across runs, missing or corrupt input files, and save failures.
9. Generate Javadoc and review programming style.
10. Commit the completed implementation and documentation to GitHub.
11. Prepare the required Canvas submissions.

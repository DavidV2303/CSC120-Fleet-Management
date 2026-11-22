David Vega Alvarado
11/16/2026

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
11. Save the fleet to FleetData.db using object serialization when exiting.

**Functions Performed by Individual Boat Objects**

1. Store boat attributes and maintenance expenses.
2. Provide access to required information through accessor methods.
3. Determine whether a requested expense is within the purchase-price limit.
4. Update maintenance expenses when a request is authorized.
5. Provide a formatted string representing the boat's information.

## 1.3 Scenarios

**Scenario 1 — Initial Program Execution**

1. The user starts FleetManagement with a CSV filename as a command-line argument.
2. The program displays the welcome message.
3. The program opens the specified CSV file.
4. Each CSV record is read and parsed into the appropriate data types.
5. A Boat object is created for each record, with maintenance expenses initialized to 0.0.
6. Each Boat object is added to the fleet's ArrayList.
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
7. The menu is displayed again.

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
6. The Boat object determines whether its current expenses plus the requested amount exceed its purchase price.
7. If the request is permitted, the Boat updates its expenses and returns true.
8. Otherwise, the expenses remain unchanged and the Boat returns false.
9. The program displays the authorization result.
10. The menu is displayed again.

**Scenario 6 — Exiting and Saving**

1. The user selects X.
2. The program serializes the fleet's ArrayList of Boat objects.
3. The serialized data is written to FleetData.db.
4. The program displays the exit message and terminates.

**Scenario 7 — Subsequent Program Execution**

1. The user starts FleetManagement without command-line arguments.
2. The program reads FleetData.db and deserializes the saved ArrayList.
3. Previously added boats, removed boats, and recorded expenses are preserved.
4. The program displays the menu.
5. The user can continue managing the fleet.

---

# 2. OBJECT-ORIENTED DESIGN

## 2.1 Classes and Objects

**Class: Boat**

Represents one boat belonging to the Coconut Grove Sailing Club. Each instance stores its own information and controls its maintenance expenses.

Example objects: Moon Glow, Osita, Big Brother.

The Boat class implements Serializable to allow its instances to be saved and restored.

**Class: FleetManagement**

Contains the main method and controls the entire application. It manages the fleet collection, menu, keyboard input, CSV parsing, and file operations.

The fleet is stored as a local ArrayList<Boat> variable in main and passed to other methods as needed.

**Enum: BoatType**

Defines the two possible boat types:

- SAILING
- POWER

**Class Relationships**

- FleetManagement uses and manages zero or more Boat objects.
- Each Boat has one BoatType value.
- Boat implements the Serializable interface.
- FleetManagement uses an ArrayList<Boat> to store the fleet.

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

The first six instance attributes can be declared final because they do not change after the Boat is constructed. The expenses attribute cannot be final because its value changes when maintenance expenditure is authorized.

The spending policy prevents other classes from modifying expenses directly.

### FleetManagement — Class and Local Data

- keyboard: Scanner — Private static final field used to read keyboard input. Only the main class contains a Scanner.
- FLEET_FILE: String — Private static final constant containing "FleetData.db".
- fleet: ArrayList<Boat> — Local variable in main containing the fleet's Boat objects.
- menuOption: String — Local variable used to process user selections.
- Other local variables — Used for searching, parsing CSV records, calculating totals, and handling user input.

All local variables will be declared at the beginning of their respective methods.

### BoatType — Enumeration Data

SAILING and POWER are fixed enum constants.

## 2.3 Methods of Objects and Classes

### Boat Class

**Constructor**

+ Boat(type: BoatType, name: String, year: int, makeModel: String, length: int, purchasePrice: double)

Initializes the six supplied attributes and sets expenses to 0.0.

**Accessors**

+ getName(): String
Returns the boat's name.

+ getPurchasePrice(): double
Returns the original purchase price.

+ getExpenses(): double
Returns the accumulated maintenance expenses.

**Controlled Mutator**

+ authorizeExpense(amount: double): boolean

Checks whether the requested maintenance expense is permitted.

If expenses + amount is less than or equal to purchasePrice, the method updates expenses and returns true.

Otherwise, the method leaves expenses unchanged and returns false.

The request should be nonnegative. The program will not intentionally authorize negative expenses.

**Other Method**

+ toString(): String

Overrides Object's toString method and returns a formatted record containing the boat's type, name, year, make/model, length, purchase price, and maintenance expenses.

The Boat class does not read keyboard input or perform file operations.

### FleetManagement Class

**Main Method**

+ main(args: String[]): void

Displays the welcome message, initializes the fleet, selects the appropriate loading procedure, and starts the menu.

**File Handling Methods**

+ loadCSV(filename: String): ArrayList<Boat>

Opens the CSV file, reads each record, creates Boat objects using parseBoat, and returns the populated ArrayList.

+ loadFleet(): ArrayList<Boat>

Reads FleetData.db, deserializes the saved ArrayList, and returns the restored fleet.

+ saveFleet(fleet: ArrayList<Boat>): void

Serializes the fleet's ArrayList and writes it to FleetData.db.

**CSV Parsing Method**

+ parseBoat(csvLine: String): Boat

Splits a CSV-formatted record into six fields, converts each field to its appropriate data type, constructs a Boat object, and returns it.

This method is used when loading CSV data and when adding new boats.

**Menu Method**

+ runMenu(fleet: ArrayList<Boat>): void

Displays the menu repeatedly, accepts case-insensitive selections, calls the appropriate methods, and saves the fleet when the user exits.

**Fleet Operations**

+ printFleet(fleet: ArrayList<Boat>): void

Prints all boat records and calculates the fleet's total purchase prices and maintenance expenses.

+ addBoat(fleet: ArrayList<Boat>): void

Reads a CSV-formatted record from the keyboard, constructs a new Boat using parseBoat, and adds it to the ArrayList.

+ removeBoat(fleet: ArrayList<Boat>): void

Requests a boat name, finds the matching object, and removes it from the ArrayList if found.

+ findBoat(fleet: ArrayList<Boat>, name: String): Boat

Searches the ArrayList for a matching boat name using equalsIgnoreCase(). Returns the Boat object if found, or null if no match exists.

+ requestExpense(fleet: ArrayList<Boat>): void

Requests a boat name and expense amount, locates the boat, calls its authorizeExpense method, and displays whether the request was permitted.

**Method Access**

Boat's constructor, accessors, authorization method, and toString are public.

FleetManagement's methods are static because the application is controlled from main and no FleetManagement instance is required.

Appropriate helper methods may be declared private once implementation begins. The final visibility of FleetManagement methods will be determined during implementation.

File handling methods will manage the necessary Java I/O exceptions. Detailed exception handling will be finalized during coding.

## 2.4 Overall Program Control

The program uses a menu-driven control structure within the FleetManagement class.

**Initialization**

1. Display the welcome message.
2. Declare an ArrayList<Boat> variable in main.
3. If a command-line filename is supplied, initialize the fleet from the CSV file.
4. Otherwise, load the serialized fleet from FleetData.db.
5. Call runMenu with the loaded ArrayList.

**Menu Processing**

Repeat until the user selects X:

- P — Call printFleet.
- A — Call addBoat.
- R — Call removeBoat.
- E — Call requestExpense.
- X — Save the fleet and exit.
- Otherwise — Display an invalid-option message and prompt again.

Menu selections are case-insensitive.

**Data Management**

The ArrayList stores references to the individual Boat objects. Each Boat manages its own maintenance expenses. FleetManagement performs collection-level operations such as adding, removing, searching, and calculating totals.

**Program Termination**

When the user selects X, saveFleet serializes the current fleet to FleetData.db. This preserves the fleet information for subsequent executions.

---

# 3. DESIGN CONSTRAINTS AND ASSUMPTIONS

1. The program supports an arbitrary number of boats through ArrayList.
2. Boat types are restricted to SAILING and POWER using an enum.
3. Boat lengths must not exceed 100 feet.
4. Boat purchase prices are less than $1,000,000.
5. All initial CSV records are assumed to be correctly formatted.
6. Numeric input is assumed to be syntactically correct.
7. Menu options and boat-name searches are case-insensitive.
8. Only FleetManagement contains a Scanner.
9. All file handling is performed in FleetManagement.
10. Boat objects implement Serializable.
11. The fleet is saved in FleetData.db using object serialization.
12. All classes and methods will contain appropriate Javadoc documentation.
13. The complete project and design documentation will be maintained in GitHub.

---

# 4. IMPLEMENTATION PLAN

1. Create BoatType.java with the SAILING and POWER enum values.
2. Implement Boat.java with private attributes, constructor, getters, expense authorization, and toString.
3. Implement FleetManagement.java with the main method and fleet ArrayList.
4. Implement reusable CSV parsing and initial CSV loading.
5. Implement the menu and fleet operations.
6. Implement serialization and deserialization.
7. Configure IntelliJ for initialization and subsequent runs.
8. Test against the professor's sample interactions and error cases.
9. Generate Javadoc and review programming style.
10. Commit the completed implementation and documentation to GitHub.
11. Prepare the required Canvas submissions.
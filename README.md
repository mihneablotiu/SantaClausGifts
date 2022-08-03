Bloțiu Mihnea-Andrei - 323CA
Proiect - Etapa 1 - Etapa 1 - Santa Claus is coming to ACS students
09.01.2022

As a starting point for this project, I used the ”GiftsDatabase” class which
is used as the beginning point for each year of simulation. To be more precise
we will use the „giftsDistribution” method from it.

This class contains:
 - The list with all the children;
 - The information that santa needs such as:
	* The number of years we are simulating for;
	* The budget;
	* The sum of the average grades of each child;
	* The budget unit.
- The initial data santa has such as:
	* The initial children list;
	* The initial gifts list.
- The list with „numberOfYears” annual changes;
- The JSONArray used for writing the answer.

All this information is parsed by the Main class with is the entry point of this
project that works as follows. The implementation of the input-output files follows
as much as possible the model that was given in the first homework:

* Main class:

main method:
 - Considers the directory where the input files are;
 - Creates the output directory and deletes every file that was already there before
by calling the „deleteFile” method;
 - Sorts the input files in ascending order by their number because otherwise it would
have taken the files in the next order (1, 10, 11, ..., 2, 20, 21...3, 4, ...) and then
the output would not have matched;
 - Crates the output file and applies the „action” method just for those files that contain
”test” string in them as a patch for VMChecker as I have uploaded different versions of the
code and I saw that there are other files in the input folder except from those that should
be there and would have given errors;
 - Calculates the final score and the checkstyle.

deleteFile method:
 - Deletes every existing file from a given directory.

action method:
 - For those valid files determined by the main method, it reads the input file and
creates an Input object with all the information needed for the simulation;
 - Creates a new list of Child entities that contain the same information as the input
given ones (”ChildData”) but with the additional information we need for the simulation
such as: his age category, his average score, the strategy applied for him in order to
calculate the average score, his budget and his received gifts;
 - Creates a Santa object that has the given information such as: the number of years we
are doing the simulation for, his budget for this year, but also the sum of the average 
score of all the children or the budget unit;
 - Creates the database we are going to work on in the future;
 - Writes the answer in the file and closes the file.

In order to get this Input object that we have in the action method mentioned before, I worked
on the „readersandwriters” package that follows the model in the homework in order to parse the
input files and store them into different objects.

That being said, I divided the input as follows:
 - SantaGeneralData - Contains the general data of the simulations such as the number of years
we are doing the simulation for and the budget we have in the beginning;
 - InitialData - Contains the two lists of information we get in the beginning: one list with
the information of the children and one list with the information of the gifts;
 - ChildData and GiftData - Each of those represents one object of the coresponding type and
contains the given data such as: id, lastname, firstname, etc. for the child and the productname,
the price and the category for the gifts.
 - A list of AnnualChangesData: Contains more AnnualChangesData that perform on the given
number of years;
 - AnnualChangesData: Contains a new budget, a new list of children and a new list of gifts but
also a new list of ChildrenUpdatesData;
 - ChildrenUpdatesData: Contains the ID of the child we are reffering to, his new niceScore and
also his new list of gifts preferences.

All these are parsed in the readData method of the InputLoader class following the example in the
homework. While parsing the input files we also used three other methods that convert from a
JSON Array in an ArrayList, from a String to a City and from a String to a Category as
mentioned and explained in the JavaDoc.

In the end, with all these information, we created our Input object that has the general data, 
the initial data and the list of annual changes and which we are going to use next.

As metioned in the action method: with the input, we created the dataBase and giftDistribution
method.

giftDistribution method:
-  Repeats the simulation for numberOfYears + 1 times. At the beginning of each year we are
deleting from the list those children that became an „Young Adult” as they are not longer
going to receive a gift;
- Then we delete their received gifts as we are starting a new year and we are calling the
distribution method for this year;
- After everything was distributed for each child, we are adding the result to the JSON Array,
and we check if we finished the simulation years. If not, we increment each child age with one
and consider the annual changes for that year as follows:
- We firstly get the new budget;
- We add the new children and the new gifts in their list;
- We take into consideration the children updates by giving them a new nice score and changing
or adding new preferences. We are also careful not to have duplicates in the final prefferences
list;
- We repeat the operations in the first round (done in the action method) such as determining
their age category, their average score, the santa's sum average and budget unit and also
the budget of each child.

childDistribution method:
 - In order to distribute the gifts to every child we just go through his list of preferences
and we search the minimum price in the list of the santa's gifts that matches the category
and we are also careful to still have budget for that gift;
 - If we found a gift that matches these requests we decrease its value from the budget
and we add it in the received gifts list of that child;
 - In the end, we just follow an output example in order to write all the information needed
in the outputfile by using JSONObjects and JSONArrays.
 - We also use two methods to convert cities into strings and categories into strings. They
are the reverse methods from the InputLoader ones.

* Santa class:
 - Contains the general data and the sum average and the budget unit;
 - The sum average is determined with the getSumAverage method by just adding together
the averageScore of each child;
 - The budgetUnit is determined as the total budget divided by the sum average.

* Test class:
 - Contains the main method that allows the tester to test just one input file
at a time with the exact same model from the homework and writes the answer in the out.txt
file.

* Child class:
 - Contains all the "ChildData" information parsed from the input files plus:
	* AgeCategory = A string determined by the child's age;
	* NiceScoreList = A list with the history of all niceScores;
	* AverageScore = His average score determined depending on the strategy;
	* AverageStrategy = The strategy used to determine the score;
	* Budget = averageScore * budgetUnit;
	* ReceivedGifts = The received gifts in a specific year.

* averagedetermination package:

In order to determine which strategy is going to be applied for each child I made a Factory design
pattern that is a singleton as we should have just one factory that returns a specific strategy
depending on the ageCategory each child is in.

In order to generate the average strategies I used the strategy design pattern as follows:
- I created one interface called: "AverageStrategy" with two methods: the one that returns
the average score and the one that returns the description of the strategy in order to understand what
each strategy is doing.
- I crated 4 classes that extend the interface and implement the two methods differently according
to the subject: 10 for the babies, average for the kids, ponderate average for the teens and null for
the young adults as they don't get gifts anymore.

Bloțiu Mihnea-Andrei - 323CA
Proiect - Etapa 2 - Santa Claus is coming to ACS students
15.01.2022

! IMPORTANT !: Am următoarea problemă cu VMChecker: Pe local primesc 60 de puncte
pentru toate testele rezolvate corect însă pe VMChecker doar 45. Am menționat
această problemă și cu o săptămână înainte de deadline pe forum însă nu am primit
nicio soluție. 

Precizări:

- Am ultima versiune de checker și de teste + ref de pe github, postată
pe data de 10.01.2022 la ora 12:29 pe GitHub;
- Am tesat pe două calculatoare, atât Windows cât și Linux (aceeași problemă);
- Am vorbit cu Narcis Căroi, asistentul de laborator de la semigrupa mea,
știe despre această problemă, i-am prezentat live situația; 
- De asemenea a testat și el implementarea mea pe calculatorul său și primește
tot punctaj maxim.

! IMPORTANT !: Vă mulțumesc mult pentru înțelegere și aștept o soluție în acest
sens. Știu că este vorba doar de 15p însă nu cred că ar fi corect să le pierd
deoarece eu consider că am făcut tot ce imi stătea în putință și am anunțat problema
cu o săptămână înainte (nu mai am alte idei). Mulțumesc pentru timpul acordat.

As a starting point for this second stage of this project, I used the classes
implemented in the first stage, that are detailed in the first README. In the
next lines, I will give a short resume about what was implemented in the first
stage.

There are numberOfYears + 1 simulation years done as follows:

- The „readersandwriters” package is responsible for parsing the input and 
writing the final output;
- The main method is responsible for creating the output files and directory;
- The action method reads the input and creates a database with which we are
going to work in the future;
- The giftDistribution method from the gift database is responsible for giving
each child their gift and updating the information for the next year.
- The childDistribution method from the gift database is responsible for the
algorithm of giving the gifts, updating the budget and so on.
- The Santa Class contains the general data, the sum average and the budget
unit.
- The Child Class contains all the data necesary for a child such as his
age category, the niceScoreList, his/her average score etc.
- The averagedetermination package = Factory design pattern that returns
a specific strategy based on the ageCategory each child is in.


Updates for the second stage:

First of all I updated the input parsing classes as we now have to read more
information from the input tests. That being said, I have also taken into
consideration the fact that now each child is going to receive a niceScoreBonus
and a specific elf. Also, each gift from the initial list is going to receive
a quantity.

Additionally, in the annualChanges, the new gifts and the new children should have
the same format so I updated the reading methods for those as well. Also, the elf
for a child cand be changed every year so I also updated the way of reading the 
childrenUpdates of each year. Every year can now receive a different way o giving
the gifts so I also read the strategy input from each annual change.

All these mentioned before were done in the InputLoader class from the
”readers and writers package” as well as updating the corespondent information from
each implied class such as:
- The giftData class is now having a quantity field;
- The childrenUpdatesData class is now having an elf field;
- The ChildData class is now having a niceScoreBonus field and an elf field;
- The AnnualChangesData class is now having a new strategy field.

* Main class:

Now having the correct input, the only difference in the main class which is the
starting point of this project is that when calculating the budget of a child
we now have to take into consideration his initial elf. I am doing that by
calling the method „blackPinkElf” for each child.

* GiftsDatabase class:

giftsDistribution method:

- The difference from the first stage of the project is that now, we have to consider
which strategy has to be applied each year when distributing the gifts.
- Depending on the strategy we are sorting the children list in a different order
(id = in ascending order of the ids, niceScore = in descending order of their
average score if they have the same average score in ascending order of their id,
niceScoreCity = in descending order of their city's average, then on their city's
name and then on their id's).
- In order to calculated the average of each city, I used the getNiceScoreCity
method that is going to be explained as follows in the santa's class.
- No matter the strategy we are having to simulate this year, after sorting
the children list, we are calling the childDistribution method which is identical
with the one from the first stage. The only difference is that now, when a gift
is given we have also to update the quantity we have left from that gift.
- After finishing the distribution we have to sort the list back on the original
order because we have to write the output in the order of the ids. 
- At the end we are calling the yellowElf efect for those children that had a
yellow left and then we are writing the output in the file for that year.
- I now separated the writing method from the gift distribution one.
- After finishing the distribution for one year I updated the information for the
next year exactly as in the first stage but also with the new information from
the input such as: the new elf, the new niceScoreBonus or the new strategy.

yellowElf method:
- Is taking into consideration only those childrent that had a yellow elf
on a specific year, and their receivedGifts list is empty but their
preferences list is not.
- Iterates through the santa's list of gifts in order to find one with
the same category as the child's first preference and with a quantity
grater that 0. If it finds one, it offers the gift to the child and
decreases it's quantity.

* Child class:

determineAverageScore method:
- When determining the average score of a child, now I have to take into
consideration if he has a nice score bonus or not. If it has, we are using
a builder desing pattern to build a niceScoreBonus with the niceScore
given from the input. Otherwise we are considering it as being 0.
- We are updating the average score with the formula from the task and if
the total is over 10, we just truncate it to 10.

blackPinkElf method:
- It is the method we are always calling when we are recalculating the 
budget of one child;
- It just takes the budget from the first stage of a child and increases
the budget with 30 percent for the pink elf and decreases the budget with
30 percent for the black elf.

* bulder package:
- The builder pattern implemented for desinging the niceScoreBonus.

* Santa Class:

getNiceScoreCity method:
- This method is called whenever the strategy we are having in one year is
based on the average of one city;
- We are having a hashmap in which we are storing a list of the average
grades each child has from a specific city;
- Another hashmap in which we are storing each city with it's average based
on the list mentioned above.

The additional flow of the second stage:
1. Determine the average score of each child taking into consideration
the niceScoreBonus;
2. Determining the budget considering the black and the pink elf;
3. Distribution of the gifts depending on the strategy;
4. Appling the yellow elf's effect.


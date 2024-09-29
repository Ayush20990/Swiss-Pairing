# Swiss-Pairing
#Problem Approach
Class Structure
Reading the problem statement the first thought that came to my
mind was to make a class for teams containing their ratings, wins and
language they are using so that the language constraints can be
applied.
Second I thought of overriding the compareTo function of the class so
that I can use the inbuilt sorting function to directly sort the teams
for the leaderboard. Also, while thinking for pairing of the teams I
thought that priorityQueue can be used to pair the teams with equal
rating together so overriding the compareTo function also helped
with priorityQueue.
Main Function :
I created a array of 50 Team objects to store the teams.I first entered
the info of 50 teams manually and then used scanner class to read
from txt file which I created by copy pasting the table of spreadsheet.
For the simulation of rounds, a for loop is run 10 times which calls
the function pairing, match, updateleaderboard and then finally
prints the leaderboard
matchResult function
Inputs: Two teams between whom match is
happening
Then I wrote the code of the matchResult function and applied all the
conditions accordingly the inputs of the function contains two paired
teams. For the time of the match and noise generation of ratings I
used random number generator and also for the probability of
winning I used randomInt generator to find a int number between 0-

100 and applied probability conditions on the different rating cases.
At the end I return the winning team.
Pairing and pairingHelper function:
Inputs: Pairing(Array of teams)
pairingHelper(Team 1 for which pair is req , PriorityQueue of the
team 1 language)
While implementing the pairing function, to prevent from pairing two
teams more then one time I thought of including HashSet of the
previous opponents a team has played, So while pairing I used a
helper function which removes Teams one by one from the
priorityQueue and if it does not contain to the set pOpp(previous
opp) then the pairing is done and and the selected team is returned.
Also I created two separate queues for different languages. And for
teams that already team one played also needs to be added again to
PQ so I used another set to store the teams that were removed and
added them to PQ again at the end.
Match function
Input: Array of teams
In match function I first repeat the same code of paring function to
pair the teams(basically pairing function can be removed. It is just
there to show the pairing done before the results of the matches as
match functions shows the results simultaneously) and then call the
matchResult function and based on the result update the score(i.e
wins) and rating of the teams. I also included a cap of rating at first
but then commented it out after someone said it on the group that
rating can be anything just initial ratings are between 60-100

UpdateLeaderBoard

Inputs: Array of teams
Just used inbuilt sort to sort the teams based on the given method

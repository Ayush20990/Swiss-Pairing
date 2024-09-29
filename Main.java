import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;

public class Main {
    static class Team implements  Comparable<Team>{
        int wins;
        String name;
        int rating;
        //int byes=0;
        String lang;
        HashSet<Team> pOpp=new HashSet<>();
        int[] tM=new int[2];
        //constructor for the team
        public Team(String n,int r,String lang){
            this.name=n;
            this.rating=r;
            this.lang=lang;
            this.wins=0;
        }
        //overriding the compareTo function to be used for comparing two teams
        @Override
        public int compareTo(Team t2) {
            //first compare wins
            // if wins same then compare average rating of opponents
            if (this.wins == t2.wins) {
                //Oth index contains the time of match where the team lost and 1st idx contains time of match where the team won
                //so the team which loses in more time or the one which wins in less should be rated higher hence the return statement
                int Time1 = -this.tM[0] + this.tM[1], Time2 = -t2.tM[0] + t2.tM[1];
                if(Time1==Time2) {
                    //if time is equal then we can compare average rating of the opponents
                    int averageRatingOfOpponentFaced1 = 0, aROPF2 = 0;
                    Iterator<Team> it = this.pOpp.iterator();
                    Iterator<Team> it2 = t2.pOpp.iterator();
                    while (it.hasNext()) {
                        averageRatingOfOpponentFaced1 += it.next().rating;
                    }
                    while (it2.hasNext()) {
                        aROPF2 += it2.next().rating;
                    }
                    if (this.pOpp.size() > 0 && t2.pOpp.size() > 0) {
                        averageRatingOfOpponentFaced1 /= this.pOpp.size();
                        aROPF2 /= this.pOpp.size();
                        if (aROPF2 == averageRatingOfOpponentFaced1) {
                            //if this is first round then teams can be sorted on basis of rating
                            return -this.rating + t2.rating;
                        } else {
                            return -averageRatingOfOpponentFaced1 + aROPF2;
                        }
                    } else {
                        return -this.rating + t2.rating;
                    }
                }
                else{
                    return Time1-Time2;
                }
                } else {
                    return -this.wins + t2.wins;
                }
            }
    }
    public static Team pairingHelper(Team t1,PriorityQueue<Team> pq) {
        //HashSet for again adding the team with which current team has already played to the priorityQueue back
        HashSet<Team> done=new HashSet<>();
        Team t2=null;
        //finding suitable opponent and removing it from the queue
        while(!pq.isEmpty()){
            t2=pq.remove();
            if(t1.pOpp.contains(t2)){
                done.add(t2);
                t2=null;
            }
            else{
                // if t2 is not in previous opponents then t2 is the selected opponent
                break;
            }
        }//adding already played opponents back to the pq
        for(Team t:done){
            pq.add(t);
        }
        return t2;
    }
    public static void match(Team[] teams){
        //pairs the teams and find the match result
        PriorityQueue<Team> cpp=new PriorityQueue<>();
        PriorityQueue<Team> py=new PriorityQueue<>();
        for(int i=0;i<teams.length;i++){
            if(teams[i].lang.equals("C++")){
                cpp.add(teams[i]);
            }
            else{
                py.add(teams[i]);
            }
        }
        while(!cpp.isEmpty()){
            Team t1=cpp.remove();
            Team t2=pairingHelper(t1,cpp);
            //updating the ratings after the match result
            if(t2==null){
                t1.wins++;
                t1.rating+=2;
                t1.rating=Math.min(100,t1.rating);
                System.out.println(t1.name+" wins bye .");
            }
            else{
                t1.pOpp.add(t2);
                t2.pOpp.add(t1);
                if(t1.rating>t2.rating){
                    if(t1==matchResult(t1,t2)){
                        System.out.println(t1.name+" wins against "+t2.name);
                        t1.rating+=2; t1.wins++;
                        t2.rating-=2;
                    }
                    else{
                        System.out.println(t2.name+" wins against "+t1.name);
                        t1.rating-=5; t2.wins++;
                        t2.rating+=5;
                    }
                }
                else{
                    if(t2==matchResult(t1,t2)){
                        System.out.println(t2.name+" wins against "+t1.name);
                        t2.rating+=2; t2.wins++;
                        t1.rating-=2;
                    }
                    else{
                        if(t1.rating==t2.rating){System.out.println(t1.name+" wins against "+t2.name);
                            t2.rating-=2; t1.rating+=2; t1.wins+=1;
                        }
                        else{
                        System.out.println(t1.name+" wins against "+t2.name);
                        t2.rating-=5;
                        t1.rating+=5; t1.wins++;
                    }}
                }
                //since rating is between range 60-100
//                t1.rating=Math.max(60,t1.rating);
//                t1.rating=Math.min(100,t1.rating);
//                t2.rating=Math.max(60,t2.rating);
//                t2.rating=Math.min(100, t2.rating);
            }
        }
        while(!py.isEmpty()) {
            Team t1 = py.remove();
            Team t2 = pairingHelper(t1,py);
            if (t2 == null) {
                t1.rating += 2;
                t1.wins++;
                t1.rating=Math.min(100,t1.rating);
                System.out.println(t1.name+" wins bye.");
            } else {
                t1.pOpp.add(t2);
                t2.pOpp.add(t1);
                if (t1.rating > t2.rating) {
                    if (t1 == matchResult(t1, t2)) {
                        System.out.println(t1.name+" wins against "+t2.name);
                        t1.rating += 2;
                        t1.wins=1+t1.wins;
                        t2.rating -= 2;
                    } else {
                        System.out.println(t2.name+" wins against "+t1.name);
                        t1.rating -= 5;
                        t2.rating += 5;
                        t2.wins=1+t2.wins;
                    }
                } else {
                    if (t2 == matchResult(t1, t2)) {
                        System.out.println(t2.name+" wins against "+t1.name);
                        t2.rating += 2;
                        t2.wins=1+t2.wins;
                        t1.rating -= 2;
                    } else {
                        System.out.println(t1.name+" wins against "+t2.name);
                        t2.rating -= 5;
                        t1.rating += 5;
                        t1.wins=1+t1.wins;
                    }
                }
//                t1.rating=Math.max(60,t1.rating);
//                t1.rating=Math.min(100,t1.rating);
//                t2.rating=Math.max(60,t2.rating);
//                t2.rating=Math.min(100,t2.rating);

            }
        }
    }
    public static void pairing(Team[] teams){
        // code for swiss pairing;
        PriorityQueue<Team> cpp=new PriorityQueue<>();
        PriorityQueue<Team> py=new PriorityQueue<>();
        for(int i=0;i<teams.length;i++){
            if(teams[i].lang.equals("C++")){
                cpp.add(teams[i]);
            }
            else{
                py.add(teams[i]);
            }
        }
        while(!cpp.isEmpty()){
            Team t1=cpp.remove();
            Team t2=pairingHelper(t1,cpp);
            if(t2==null){
                //System.out.println(t1.byes);
                System.out.println(t1.name+" is allotted a bye victory");
            }
            else {
                System.out.println(t1.name + " is paired with " + t2.name);
            }
        }
        while(!py.isEmpty()) {
            Team t1 = py.remove();
            Team t2 = pairingHelper(t1,py);
            if(t2==null){
               // System.out.println(t1.byes);
                System.out.println(t1.name+" is allotted a bye victory");
            }
            else {
                System.out.println(t1.name + " is paired with " + t2.name);
            }
            }
        }
    public static void updateLeaderBoard(Team[] teams){
        //just sorting the teams to get the leaderboard
        Arrays.sort(teams);
    }
    public static Team matchResult(Team T1,Team T2){
        //changing the seeds continuously so that randomness if more
        Random generator = new Random(System.nanoTime());
        //generating the noise
        int rating1=-4+generator.nextInt(9);
        int rating2=-4+generator.nextInt(9);

        rating1+=T1.rating;
        rating2+=T2.rating;
        //time for the match
        int time=generator.nextInt(6)+5;
        int winner=-1;
        //using random numbers to determine the probability
        int p=generator.nextInt(100);
        if(Math.abs(rating1-rating2)<5){
            if(p>50){
                winner=1;
            }
            else{
                winner=0;
            }
        }
        else if(Math.abs(rating1-rating2)>=5 && Math.abs(rating1-rating2)<=10){
            if(rating1>rating2){
                if(p>=65){
                    winner=1;
                }
                else{
                    winner=0;
                }
            }
            else{
                if(p>=65){
                    winner=0;
                }
                else {
                    winner = 1;
                }
            }
        }
        else{
            if(rating1>rating2){
                if(p>=90){
                    winner=1;
                }
                else {
                    winner = 0;
                }
            }
            else{
                if(p>=90){
                    winner=0;
                }
                else{
                    winner=1;
                }
            }
        }
        if(winner==0){
            T1.tM[1]+=time;
            T2.tM[0]+=time;
            return T1;
        }
        else{
            T2.tM[1]+=time;
            T1.tM[0]+=time;
            return T2;
        }
    }
    public static void printLeaderBoard(Team[] teams){
        for(int i=0;i<teams.length;i++){
            int timeDifference=teams[i].tM[0]-teams[i].tM[1];
            //negative time difference means the time to win is more for the team
            System.out.println("Name "+teams[i].name+" Score "+teams[i].wins+" Rating "+teams[i].rating+" TimeRate "+(teams[i].pOpp.size()==0?0:timeDifference/teams[i].pOpp.size()));

        }
    }
    public static void main(String[] args) {
        Team[] teams=new Team[50];
        File file = new File("/Users/miral/Downloads/players.txt");
        try {
            Scanner scan = new Scanner(file);
            for(int i=0;i<50;i++){
                String a= scan.next();
                int rating=scan.nextInt();
                String lang=scan.next();
                teams[i]=new Team(a,rating,lang);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());

        }
//       teams[0]=new Team("A01",77,"C++");
//       teams[1]=new Team("A02",60,"P");
//       teams[2]=new Team("A03",83,"P");
//       teams[3]=new Team("A04",84,"P");
//       teams[4]=new Team("A05",71,"P");
//       teams[5]=new Team("A06",66,"C++");
//        teams[6]=new Team("A07",70,"P");
//        teams[7]=new Team("A08",82,"C++");
//        teams[8]=new Team("A09",80,"C++");
//        teams[9]=new Team("A10",77,"P");
//        teams[10]=new Team("A11",67,"P");
//        teams[11]=new Team("A12",89,"P");
//        teams[12]=new Team("A13",77,"P");
//        teams[13]=new Team("A14",66,"C++");
//        teams[14]=new Team("A15",81,"P");
//        teams[15]=new Team("A16",92,"P");
//        teams[16]=new Team("A17",89,"P");
//        teams[17]=new Team("A18",79,"P");
//        teams[18]=new Team("A19",69,"P");
//        teams[19]=new Team("A20",93,"C++");
//        teams[20]=new Team("A21",75,"P");
//        teams[21]=new Team("A22",97,"C++");
//        teams[22]=new Team("A23",86,"P");
//        teams[23]=new Team("A24",92,"P");
//        teams[24]=new Team("A25",96,"P");
//        teams[25]=new Team("A26",89,"P");
//        teams[26]=new Team("A27",75,"P");
//        teams[27]=new Team("A28",96,"C++");
//        teams[28]=new Team("A29",96,"P");
//        teams[29]=new Team("A30",62,"P");
//        teams[30]=new Team("A31",93,"P");
//        teams[31]=new Team("A32",92,"P");
//        teams[32]=new Team("A33",70,"P");
//        teams[33]=new Team("A34",62,"P");
//        teams[34]=new Team("A35",60,"C++");
//        teams[35]=new Team("A36",72,"P");
//        teams[36]=new Team("A37",79,"P");
//        teams[37]=new Team("A38",74,"C++");
//        teams[38]=new Team("A39",93,"C++");
//        teams[39]=new Team("A40",81,"P");
//        teams[40]=new Team("A41",89,"P");
//        teams[41]=new Team("A42",85,"P");
//        teams[42]=new Team("A43",94,"C++");
//        teams[43]=new Team("A44",69,"P");
//        teams[44]=new Team("A45",92,"P");
//        teams[45]=new Team("A46",65,"P");
//        teams[46]=new Team("A47",88,"C++");
//        teams[47]=new Team("A48",93,"P");
//        teams[48]=new Team("A49",99,"P");
//        teams[49]=new Team("A50",62,"P");
        updateLeaderBoard(teams);
        printLeaderBoard(teams);
        for(int i=0;i<10;i++){
            System.out.println("Round "+(i+1));
            pairing(teams);
            match(teams);
            updateLeaderBoard(teams);
            printLeaderBoard(teams);
        }
        System.out.println();

    }
}
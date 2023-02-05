import java.util.*;

class Game {
  private String gameName;
  private String personName;

  // constructor
  public Game(String Gname, String Pname) {
    setGameName(Gname);
    setPersonName(Pname);
    System.out.println(this);
  }

  // set&get
  public void setGameName(String Gname) {
    gameName = Gname;
  }

  public String getGameName() {
    return gameName;
  }

  public void setPersonName(String Pname) {
    personName = Pname;
  }

  public String getPersonName() {
    return personName;
  }

  public String toString() {
    return String.format("Hello %s in %s", getPersonName(), getGameName());
  }

}// end game class

class GuessGame extends Game {
  enum Status {
    WIN, LOSE
  };

  Status playerStatus = null;// enumeration

  private int score;
  private int attempts;

  // constructor of GuessGame class
  public GuessGame(String Gname, String Pname, int sc, int at) {
    super(Gname, Pname); // constructor game name
    setScore(sc);
    setAttempts(at);
  }// end con

  public void setScore(int sc) {
    score = sc;
  }

  public int getScore() {
    return score;
  }

  public void setAttempts(int at) {
    attempts = at;
  }

  public int getAttempts() {
    return attempts;
  }

  // main guess game method
  public void guess() {
    Random randomnumber = new Random();
    // if the player doesn't won, by defualt he lost
    playerStatus = playerStatus.LOSE;
    // Game rules and instructions for the user
    System.out.println(
        "\nGame Rules : \n -your intial score is 50 \n -guess a number from 1 to 10\n -for each round you have 5 guesses\n -if you guess right number from the first time you get +20 points,and you will lose 10 point for each wrong guess.\n");

    Scanner input = new Scanner(System.in);

    int answer = 1 + randomnumber.nextInt(10);
    int userScore = score;

    for (int i = attempts; i > 0; i--) {
      System.out.printf("\nAttempts lefts: %d", i);
      System.out.println("\nPlease enter your guess:");
      int userGuess = input.nextInt();

      if (userGuess > 10 || userGuess < 0) {
        throw new IllegalArgumentException("number out of range , number between 1-10");
      }

      if (userGuess == answer) {
        playerStatus = playerStatus.WIN;
        break;// exit the for loop
      } else if (userGuess > answer) {
        System.out.println("Your guess is higher.");
        userScore -= 10;
      } else if (userGuess < answer) {
        System.out.println("Your guess is lower.");
        userScore -= 10;
      }

    } // end for loop

    // we will see if the player won from first try / not first try / lost:

    // FIRST try condtion, the player did not lose any points
    // and that's why his total points are equal to the original points.
    if (playerStatus == playerStatus.WIN && userScore == score) {
      System.out.println("You win from frist try! You will get +20 points");
      System.out.printf("your scores now: %d\n", userScore + 20);
    }
    // SECOND try condtion, the player won but not from the first try,
    // that means he lost a points from his wrong gusses,
    // so his total points doesn't equal the original points.
    else if (playerStatus == playerStatus.WIN && userScore != score) {
      System.out.println("You win but not from frist try!");
      System.out.printf("your scores : %d\n", userScore);
    }
    // LOSE condtion, if the attempts end and the player doesn't answer:
    // the players Status we REMAIN "lose" as we initialized it befor.
    else {
      System.out.println("unfortunately you lose :(");
      System.out.printf("your scores now: %d\n", userScore);
    }
  } // end guess method
}// end GuessGame class

// this class like a list of games, we aspire for this list
// in the future to combunes several games.
// this class is for running each game by calling the main method
// through an object from the same game class.
class PlayGame {

  private GuessGame GgObj; // compistion - GuessGame object
  private String gameName;
  private String personName;

  // constructor
  public PlayGame(String gameName, String personName) {
    // we called the explicitly constructor for GuessGame class
    GgObj = new GuessGame(gameName, personName, 50, 5); // fixed attemps & scores
  }

  public void setGameName(String Gname) {
    gameName = Gname;
  }

  public String getGameName() {
    return gameName;
  }

  public void setPersonName(String Pname) {
    personName = Pname;
  }

  public String getPersonName() {
    return personName;
  }

  public void setGgObj(GuessGame obj) {
    GgObj = obj;
  }

  public GuessGame getGgObj() {
    return GgObj;
  }

  // the method that will start Guess game
  public void play() {
    if (GgObj.getGameName() == "GuessGame")
      GgObj.guess(); // will go to GuessGame class and start "guess" method
  }

  public String toString() {
    return String.format("%s \n score:%d   attempts:%d", getGgObj(), GgObj.getScore(), GgObj.getAttempts());

  }
}// end playGame class

public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.println("Please enter your name:");
    String userName = input.nextLine();

    PlayGame obj = new PlayGame("GuessGame", userName);// PlayGame object

    obj.play();
  }
}
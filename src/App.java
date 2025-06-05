import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.nio.file.Paths;
import processing.core.*;

public class App extends PApplet {
    ArrayList<Hats> hats;
    Magic magic;
    int rabbitsSummoned = 0;
    double highScore;
    Hats newHat1;
    Hats newHat2;
    String highScoreFile = "highscore.txt";
    SceneManager sceneManager;
    PImage wandCursor;
    Random rand = new Random(); // chat gpt method

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        hats = new ArrayList<>();
        readHighScore(); // sets up high score
        hats.add(new Hats(this, 100, 200)); // adds initial hats
        hats.add(new Hats(this, 500, 200));
        hats.add(new Hats(this, 900, 300));
        newHat1 = new Hats(this, 300, 300); // sets up new hats added in later scenes
        newHat2 = new Hats(this, 700, 300);
        wandCursor = loadImage("magic wand.png"); // loads custom image for cursor
        wandCursor.resize(80, 95);
        noCursor();
        // initializes logic in game
        magic = new Magic(hats);
        sceneManager = new SceneManager(hats, newHat1, newHat2, magic);
    }

    public void settings() {
        size(1200, 600);
    }

    public void draw() {
        if (sceneManager.getScene() == 0) {
            drawHomeScreen();
            return;
        }
        background(48, 25, 52);
        for (Hats hat : hats) {
            hat.draw();
        }
        int scene = sceneManager.getScene();
        if (scene >= 1 && scene <= 8) {
            displayRabbitsSummoned();
            sceneManager.update(frameCount, millis()); // used chatGPT to figure this out
        } else if (scene == 9) {
            drawEndScreen();
        }
        // image for cursor, displays highscore on screen
        image(wandCursor, mouseX, mouseY);
        fill(255, 215, 0);
        textSize(24);
        textAlign(RIGHT, BOTTOM);
        text("HIGHSCORE: " + (int) highScore + " RABBITS", width - 20, height - 20);
    }

    public void keyPressed() {
        for (Hats hat : hats) {
            hat.isMoving(); // starts hats moving when key pressed (begins game)
        }
        // start from home screen
        if (sceneManager.getScene() == 0) {
            rabbitsSummoned = 0;
            sceneManager.startGame(frameCount);
        }
    }

    public void mousePressed() {
        // reset to home when click on end sceren
        if (sceneManager.getScene() == 9) {
            sceneManager.reset();
            rabbitsSummoned = 0;
        }
        for (Hats hat : hats) { // checks if magic hat is clicked
            if (hat.isClicked(mouseX, mouseY) && hat == magic.getMagicHat() && hat.isMagicHat()) {
                hat.showRabbit();
                rabbitsSummoned = rabbitsSummoned + 1;
                if (rabbitsSummoned > highScore) { // writes new high score if previous one is beaten
                    highScore = rabbitsSummoned;
                    writeHighScore();
                }

            }
        }
    }

    void drawHomeScreen() { // draws the home screen
        background(120, 0, 0);
        fill(255, 215, 0);
        textAlign(CENTER); // chat gpt for this
        textSize(64);
        text("RABBITS", width / 2, height / 3);
        textSize(32);
        text("TAP THE GOLDEN HATS TO SUMMON THE RABBITS", width / 2, height / 2);
        text("PRESS ANY KEY TO START", width / 2, height / 2 + 100);
    }

    // void triggerMagicHat() {
    // magic.triggerMagicHat();
    // hasMagic = true;
    // choosing = false;
    // magicStartertime = millis();
    // }

    // void removeMagicHatWhenExpired(int durationMillis) { // used chatgpt to
    // figure out millis part
    // if (hasMagic && millis() - magicStartertime > durationMillis) {
    // magic.clearMagicHat();
    // hasMagic = false;
    // }
    // }

    void drawEndScreen() { // draws the ending screen based on how well the player did
        background(120, 0, 0);
        fill(255, 215, 0);
        textAlign(CENTER);
        textSize(64);
        if (rabbitsSummoned <= 3) {
            text("YOU SUCK AT MAGIC.", width / 2, height / 3);
            textSize(32);
            text("YOU SUMMONED " + rabbitsSummoned + " RABBITS!", width / 2, height / 3 + 60);
            text("CLICK TO PLAY AGAIN", width / 2, height / 3 + 90);
        } else if (rabbitsSummoned <= 5) {
            text("YOU'RE OKAY AT MAGIC.", width / 2, height / 3);
            textSize(32);
            text("YOU SUMMONED " + rabbitsSummoned + " RABBITS!", width / 2, height / 3 + 60);
            text("CLICK TO PLAY AGAIN", width / 2, height / 3 + 90);
        } else if (rabbitsSummoned <= 8) {
            text("WHAT A MAGICIAN!", width / 2, height / 3);
            textSize(32);
            text("YOU SUMMONED " + rabbitsSummoned + " RABBITS!", width / 2, height / 3 + 60);
            text("CLICK TO PLAY AGAIN", width / 2, height / 3 + 90);
        }
    }

    public void readHighScore() { //reads the high score from the file
        try (Scanner scanner = new Scanner(Paths.get(highScoreFile))) {
            if (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                highScore = Double.valueOf(row);
            } else {
                highScore = 0;
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            highScore = 0;
        }
    }

    void writeHighScore() { //writes high score to file
        try {
            saveStrings(highScoreFile, new String[] { String.valueOf((int) highScore) });
        } catch (Exception e) {
            System.out.println("Error writing high score: " + e.getMessage());
        }
    }

    void displayRabbitsSummoned() { //displays how many rabbits the player summoned
        fill(255, 215, 0);
        textSize(32);
        textAlign(CENTER, TOP);
        text("RABBITS SUMMONED: " + rabbitsSummoned + "/8", width / 2, 20);
    }
}

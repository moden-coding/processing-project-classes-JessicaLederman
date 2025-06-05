import processing.core.PApplet;
import processing.core.PImage;

public class Hats {
    private PApplet canvas;
    private PImage photorabbit;
    private PImage photohat;
    private PImage photogoldhat;

    private boolean isMagicHat = false;

    private int xhat;
    private int yhat;
    private int xrabbit;
    private int yrabbit;

    private int speedx = 15; 
    private int speedy = 13; 

    private boolean isMoving = true;
    private boolean rabbitvisible = false;

    public Hats(PApplet j, int startX, int startY) { //initialize hat and rabbit images, set starting postions
        canvas = j;
        photorabbit = canvas.loadImage("rabbit.png");
        photohat = canvas.loadImage("cs hat.png");
        photogoldhat = canvas.loadImage("csgoldhat.png");

        xhat = startX;
        yhat = startY;
        xrabbit = xhat + 18;
        yrabbit = yhat - 100;

    }

    void draw() { //draws hat and rabbit, handles bouncing and moving animation
        if (isMoving) {
            xhat = xhat + speedx;
            yhat = yhat + speedy;
            xrabbit = xhat;
            yrabbit = yhat - 150;

            // to make it bounce off walls
            if (xhat < 0 || (xhat + photohat.width) > canvas.width) {
                speedx = speedx * -1;
            }
            if (yhat < 0 || (yhat + photohat.height) > canvas.height) {
                speedy = speedy * -1; // cahnges speed direction
            }
        }
        if (rabbitvisible) {
            canvas.image(photorabbit, xrabbit, yrabbit);
        }
        if (isMagicHat) {
            canvas.image(photogoldhat, xhat, yhat);
        } else {
            canvas.image(photohat, xhat, yhat);
        }
        photohat.resize(210, 210); //resize photos to fit screen better
        photogoldhat.resize(210, 210);
        photorabbit.resize(210, 210);
    }

    public void isMoving() { //allows this hat to start moving
        isMoving = true;
    }

    public void showRabbit() { //makes rabbit image visible
        rabbitvisible = true;
    }

    public boolean isClicked(int mouseX, int mouseY) { // checks if mouse click was within this hat
        if (mouseX >= xhat && mouseX <= xhat + photohat.width && mouseY >= yhat && mouseY <= yhat + photohat.height) {
            return true;
        } else {
            return false;
        }
    }

    public void decideMagicHat(boolean value) { //sets this hat as a golden magic hat, I asked chatGPT for the boolean value part
        isMagicHat = value; 
    }

    public void hideRabbit() { //hides the rabbit image
        rabbitvisible = false;
    }

    public boolean isMagicHat() { //returns true if this hat is currently the goldne magic hat
        return isMagicHat;
    }
}

import java.util.ArrayList;
public class SceneManager {
     private int scene = 0;
    private int[] sceneStartFrames = new int[10];
    private boolean choosing = true;
    private boolean hasMagic = false;
    private double magicStartTime;

    private Hats newHat1;
    private Hats newHat2;
    private ArrayList<Hats> hats;
    private Magic magic;

    public SceneManager(ArrayList<Hats> hats, Hats newHat1, Hats newHat2, Magic magic) { //gets references to logic from other classes
        this.hats = hats;
        this.newHat1 = newHat1;
        this.newHat2 = newHat2;
        this.magic = magic;
    }

    public void startGame(int frameCount) { //starts game from home screen
        scene = 1;
        sceneStartFrames[1] = frameCount;
        choosing = true;
        hasMagic = false;
    }

    public void update(int frameCount, int millis) { //controls scene transitions, timing, magic hat behavior
        if (scene >= 1 && scene <= 8) {
            int start = sceneStartFrames[scene];

            if (frameCount - start >= 180 && choosing) { //after delay, trigger magic hat
                magic.triggerMagicHat();
                hasMagic = true;
                choosing = false;
                magicStartTime = millis;
            }

            if (frameCount - start >= 330) {
                scene = scene +1;
                if (scene <= 8) sceneStartFrames[scene] = frameCount;
                choosing = true;
                hasMagic = false;
            }

            if (hasMagic && millis - magicStartTime > 750) {
                magic.clearMagicHat();
                hasMagic = false; //hide magic hat after timeout
            }

            if (scene == 4 && !hats.contains(newHat1)) hats.add(newHat1); //extra hats in later scenes
            if (scene == 5 && !hats.contains(newHat2)) hats.add(newHat2);
        }
    }public void reset() { //resets to home screen
        scene = 0;
        choosing = true;
        hasMagic = false;
    }

    public int getScene() { //returns current scene number
        return scene;
    }
}


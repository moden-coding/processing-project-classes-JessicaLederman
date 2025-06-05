import java.util.ArrayList;
import java.util.Random;

public class Magic {
    private Hats magichat;
    private ArrayList<Hats> hats;
    private Random rand;
    private boolean hasMagic = false;

    public Magic(ArrayList<Hats> hats) {
        this.hats = hats;
        this.rand = new Random();
    }

    public void triggerMagicHat() { //randomly selects a hat to become the golden magic hat
        if (hasMagic) {
            return;
        }
        int index = rand.nextInt(hats.size());
        magichat = hats.get(index);
        magichat.decideMagicHat(true);
        hasMagic = true;
    }

    public void clearMagicHat() { //clears the current magic hat and hides its rabbit
        if (magichat != null) {
            magichat.decideMagicHat(false);
            magichat.hideRabbit();
            magichat = null;
        }
        hasMagic = false;
    }

    public Hats getMagicHat() { //returns the current golden magic hat
        return magichat;
    }

    public boolean hasMagic() { //returns true if a magic hat is currently active
        return hasMagic;
    }
}
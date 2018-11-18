import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class KeyboardListener implements KeyListener {

    public KeyboardListener() {
        // do nothing
    }


    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
            GUI.generateAndUpdate(0);
        }
        if (arg0.getKeyCode() == KeyEvent.VK_UP) {
            GUI.generateAndUpdate(1);
        }
        if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
            GUI.generateAndUpdate(2);
        }
        if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
            GUI.generateAndUpdate(3);
        }
    }


    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    // override all the methods of KeyListener interface.
}

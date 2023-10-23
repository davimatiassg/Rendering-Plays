package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;

import model.Rendering.Camera;
import model.Geometrical.*;

public class RenderApplication extends JPanel {
	private Camera mainCamera;
    private Scene currentScene;
    
    public Camera getMainCamera() {
		return mainCamera;
	}


	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}


	public Scene getCurrentScene() {
		return currentScene;
	}


	public void setCurrentScene(Scene currentScene) {
		this.currentScene = currentScene;
	}

    
    public RenderApplication()
    {
    	super();
    	mainCamera = new Camera();

    }
   

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
    }

 
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tela com Pixel Móvel em Java");
        RenderApplication screen = new RenderApplication();
        //frame.add(screen);
        frame.add(screen.mainCamera);
        frame.setSize(new Dimension(1920, 1080));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        while(true) 
        {
        	try {
            	screen.setCurrentScene(InputReader.readScene("..\\input\\defaultScene.rend"));
    			InputReader.configCamera(screen.mainCamera, "..\\input\\defaultCamera.rend");
                Thread.sleep(20); // Espera 0.02 segundos
                screen.mainCamera.repaint(); // Move a linha para (200, 200)
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
    			screen.currentScene = new Scene();
    			e.printStackTrace();
    		}
        }
        
        
        
        
    }
}
package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import geometrical.*;
import rendering.Camera;

import java.util.ArrayList;

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
		this.mainCamera.setCurrentScene(currentScene);
	}

    
    public RenderApplication()
    {
    	super();
    	mainCamera = new Camera(currentScene);
    	//mainCamera.setCurrentScene(currentScene);

    }
   

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
    }

 
    
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Tela com Pixel M�vel em Java");
        RenderApplication screen = new RenderApplication();
        //frame.add(screen);
        frame.add(screen.mainCamera);
        screen.setCurrentScene(InputReader.readScene("/home/davi.genuino.018/Área de Trabalho/Rendering-Plays-main/input/defaultScene.rend"));
		InputReader.configCamera(screen.mainCamera, "../input/defaultCamera.rend");
        frame.setSize(new Dimension(screen.mainCamera.getViewportSize()[0], screen.mainCamera.getViewportSize()[1]));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        while(true) 
        {
        	try {
            	screen.setCurrentScene(InputReader.readScene("../input/defaultScene.rend"));
    			InputReader.configCamera(screen.mainCamera, "../input/defaultCamera.rend");
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
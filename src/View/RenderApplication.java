package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;

import Rendering.Camera;
import Geometrical.*;

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
    	Integer[] viewportsize = {800, 600};
    	mainCamera.setViewportSize(viewportsize);
    	currentScene = new Scene();
    }
   

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        ArrayList<Integer[]> renderedPixels = mainCamera.render(currentScene);
        
        if(renderedPixels.size() <= 0) { return; }
        
        Integer[] pixel = {0, 0};
        
        for(int i = 0; i < renderedPixels.size(); i++)
        {
        	pixel = renderedPixels.get(i);
            g.setColor(Color.WHITE);
            g.drawLine(pixel[0]+5, pixel[1]-5, pixel[0]-5, pixel[1]+5);
            g.drawLine(pixel[0]-5, pixel[1]-5, pixel[0]+5, pixel[1]+5);
            
            if(i < renderedPixels.size()-1)
            {
            	Integer[] pixel2 = renderedPixels.get(i+1);
            	g.drawLine(pixel[0], pixel[1], pixel2[0], pixel2[1]);
            }
        }
        Integer[] pixel2 = renderedPixels.get(0);
        g.drawLine(pixel[0], pixel[1], pixel2[0], pixel2[1]);
        	
    }
/*
    // Método para atualizar as coordenadas da linha
    public void updateLinePosition(int newX, int newY) {
        xCoordinate = newX;
        yCoordinate = newY;
        repaint(); // Redesenha a tela para exibir a nova posição da linha
    }*/

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tela com Pixel Móvel em Java");
        RenderApplication screen = new RenderApplication();


        GeoVector v = new GeoVector(3);
        GeoVector g = new GeoVector(3);
        GeoVector a = new GeoVector(3);
        Quaternion q = new Quaternion();
        q.setAxis(0, 0.94f);
        q.setAxis(1, 0.11f);
        q.setAxis(2, 0.17f);
        q.setAxis(3, -0.28f);
        
        screen.mainCamera.setRotation(q);
        v.setAxis(0, -500f);
        v.setAxis(1, 330f);
        g.setAxis(1, 50f);
        g.setAxis(2, 30f);
        a.setAxis(2, 50f);
        a.setAxis(0, 30f);
        ArrayList<GeoVector> vertices = new ArrayList<GeoVector>();
        vertices.add(v);
        vertices.add(g);
        vertices.add(a);
        Mesh m = new Mesh(vertices);

        screen.getCurrentScene().addMesh(m);


        frame.add(screen);
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        /*
        try {
            Thread.sleep(2000); // Espera 2 segundos
            tela.updateLinePosition(200, 200); // Move a linha para (200, 200)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
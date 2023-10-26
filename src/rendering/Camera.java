package rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JPanel;

import geometrical.*;

public class Camera extends JPanel {

	private Integer[] viewportSize = {360, 270};
	private Float fov; 			//Distance from viewport to camera focal point

	private Quaternion rotation;
	private GeoVector position;

	// Constraints
	private GeoVector up;
	private GeoVector right;
	private GeoVector forward;
	
	private Scene currentScene;


	public Camera()
	{
		this.fov = 70f;
		this.rotation = new Quaternion();
		this.rotation.setAxis(0, 1f);
		this.position = GeoVector.scale(GeoVector.forward(), -1f);
		
		this.up = Quaternion.Rotate(rotation, GeoVector.up());
		this.right = Quaternion.Rotate(rotation, GeoVector.right());
		this.forward = Quaternion.Rotate(rotation, GeoVector.forward());
		
		this.currentScene = null;
	}
	
	public Camera(Scene scene)
	{	
		this.fov = 70f;
		this.rotation = new Quaternion();
		this.rotation.setAxis(0, 1f);
		this.position = GeoVector.scale(GeoVector.forward(), -1f);
		
		this.up = Quaternion.Rotate(rotation, GeoVector.up());
		this.right = Quaternion.Rotate(rotation, GeoVector.right());
		this.forward = Quaternion.Rotate(rotation, GeoVector.forward());
		
		this.currentScene = scene;
	}

	public Scene getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(Scene currentScene) {
		this.currentScene = currentScene;
	}

	public GeoVector getPosition() {
		return position;
	}

	public void setPosition(GeoVector position) {
		this.position = position;
	}

	public Quaternion getRotation() {
		return rotation;
	}

	public void setRotation(Quaternion rotation) {
		this.rotation = rotation.unitary();
		//System.out.println("Rotation SET: " + this.rotation.toString());
		this.up = Quaternion.Rotate(rotation, GeoVector.up());
		this.right = Quaternion.Rotate(rotation, GeoVector.right());
		this.forward = Quaternion.Rotate(rotation, GeoVector.forward());
	}

	public Float getFov() {
		return fov;
	}

	public void setFov(Float fov) {
		this.fov = fov;
	}

	public Integer[] getViewportSize() {
		return viewportSize;
	}

	public void setViewportSize(Integer[] viewportSize) {
		this.viewportSize = viewportSize;
	}

	public GeoVector getDirection()
	{
		return this.forward;
	}


	// Relevant Methods
	public LinkedList<Mesh> selectMeshes(Scene scene)
	{
		LinkedList<Mesh> meshes = new LinkedList<Mesh>();
		LinkedList<Float> distances = new LinkedList<Float>();
		GeoVector forward = this.getDirection();
		for(Mesh mesh : scene.getMeshes())
		{
			//Float center_distance = (fov/f_axis_distance) * ( GeoVector.subtract(point, GeoVector.scale(forward, f_axis_distance)) ).magnitude;
			Float distance = GeoVector.scalarProduct(GeoVector.subtract(mesh.getCenter(), position), forward);
			if(distance > 0 )//& center_distance <= viewportSize[])
			{
				System.out.println("Achou um geovector de centro bom.");
				ListIterator ms = meshes.listIterator(0);
				ListIterator ds = distances.listIterator(0);
				while(ds.hasNext() && ms.hasNext())
				{
					ms.next();
					if(distance < (Float) ds.next()) 
					{
						ds.previous();
						ds.add(distance);
						ms.add(mesh);
					}
				}
				if(!ds.hasNext()) 
				{
					ds.add(distance);
					ms.add(mesh);
				}
			}
		}
		
		return meshes;
	}

	private GeoVector projectVertex(GeoVector point)
	{
		GeoVector point_to_camera = GeoVector.subtract(point, position);
		GeoVector screen_point = new GeoVector(2);
		screen_point.setAxis(0, GeoVector.scalarProduct(point_to_camera, up));
		screen_point.setAxis(1, GeoVector.scalarProduct(point_to_camera, right));
		return screen_point;
	}


	public ArrayList<Mesh> projectVertices(Scene scene)
	{
		LinkedList<Mesh> meshes = selectMeshes(scene);
		ArrayList<Mesh> projectedMeshes = new ArrayList<Mesh>();
		for(int i = 0; i < meshes.size(); i++)
		{
			ArrayList<GeoVector> projectedPoints = new ArrayList<GeoVector>();
			for(GeoVector point : meshes.get(i).getVertices())
			{
				projectedPoints.add(projectVertex(point));
			}
			Mesh m = new Mesh(projectedPoints, meshes.get(i).getTriangles());
			projectedMeshes.add(m);
		}
		return projectedMeshes;
	}

	public int[][] renderTriangle(Mesh mesh2D, int i)
	{	
		int[][] pixels = new int[2][mesh2D.getTriangle(i).length];
		
		int p = 0;
		
		for(Integer vertexIndex : mesh2D.getTriangle(i))
		{
			pixels[0][p] = mesh2D.getVertex(vertexIndex).getAxis(0).intValue() + viewportSize[0]/2;
			pixels[1][p] = mesh2D.getVertex(vertexIndex).getAxis(1).intValue() + viewportSize[1]/2;
			p ++;
		}
		//System.out.println("");
		
		return pixels;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        //System.out.println(this.get);
        ArrayList<Mesh> v_projections = projectVertices(currentScene);
        
        System.out.println(v_projections.size());
        if(v_projections.size() <= 0) { return; }

        for(Mesh m : v_projections) 
        {
        	int faces =  m.getTriangles().size();
        	for(int i = 0; i < faces; i++)
        	{
        		int[][] pixels = renderTriangle(m, i);
        		g.fillPolygon(pixels[0], pixels[1], pixels[0].length);
        	}
        }
        
       
    }
}

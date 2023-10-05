package Rendering;

import Geometrical.*;
import java.util.ArrayList;

public class Camera {

	private Integer[] viewportSize = {360, 270};
	private Float fov; 			//Distance from viewport to camera focal point

	private Quaternion rotation;
	private GeoVector position;

	// Constraints
	private GeoVector up;
	private GeoVector right;
	private GeoVector forward;
	


	public Camera()
	{
		this.fov = 70f;
		this.rotation = new Quaternion();
		this.rotation.setAxis(0, 1f);
		this.position = GeoVector.scale(GeoVector.forward(), -1f);
		
		this.up = Quaternion.Rotate(rotation, GeoVector.up());
		this.right = Quaternion.Rotate(rotation, GeoVector.right());
		this.forward = Quaternion.Rotate(rotation, GeoVector.forward());
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
		this.rotation = rotation;
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
	public ArrayList<Mesh> selectMeshes(Scene scene)
	{
		ArrayList<Mesh> selection = new ArrayList<Mesh>();
		GeoVector forward = this.getDirection();
		for(Mesh mesh : scene.getMeshes())
		{
			//Float center_distance = (fov/f_axis_distance) * ( GeoVector.subtract(point, GeoVector.scale(forward, f_axis_distance)) ).magnitude;
			if(GeoVector.scalarProduct(GeoVector.subtract(mesh.getCenter(), position), forward) > 0 )//& center_distance <= viewportSize[])
			{
				selection.add(mesh);
			}
		}
		return selection;
	}

	private GeoVector projectVertex(GeoVector point)
	{
		GeoVector point_to_camera = GeoVector.subtract(point, position);
		GeoVector screen_point = new GeoVector(2);
		screen_point.setAxis(0, GeoVector.scalarProduct(point_to_camera, up));
		screen_point.setAxis(1, GeoVector.scalarProduct(point_to_camera,  right));
		return screen_point;
	}


	public ArrayList<GeoVector> projectVertices(Scene scene)
	{
		ArrayList<GeoVector> points = new ArrayList<GeoVector>();
		ArrayList<Mesh> meshes = selectMeshes(scene);
		for(Mesh mesh : meshes)
		{
			for(GeoVector point : mesh.getVertices()) 
			{
				points.add(projectVertex(point));
			}
		}
		return points;
	}



	public ArrayList<Integer[]> render(Scene scene)
	{
		ArrayList<Integer[]> pixels = new ArrayList<Integer[]>();

		ArrayList<GeoVector> v_projections = projectVertices(scene);
		//TODO code

		for(GeoVector vertex : v_projections)
		{
			Integer[] pixel = {vertex.getAxis(0).intValue() + viewportSize[0]/2, vertex.getAxis(1).intValue() + viewportSize[1]/2};
			pixels.add(pixel);
		}
		return pixels;
	}
}

package Geometrical;
import java.util.ArrayList;
import Rendering.Camera;

public class Scene {
	
	private ArrayList<Mesh> meshes;

	public Scene()
	{
		meshes = new ArrayList<Mesh>();
	}

	public ArrayList<Mesh> getMeshes()
	{
		return meshes;
	}

	public void addMesh(Mesh mesh)
	{
		meshes.add(mesh);
	}
}

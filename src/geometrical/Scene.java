package geometrical;

import java.util.ArrayList;

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

package Geometrical;

import java.util.Collection;
import java.util.ArrayList;

public class Mesh {
	private ArrayList<GeoVector> vertices;
	private GeoVector center;

	public Mesh(ArrayList<GeoVector> vertices)
	{	
		this.vertices = vertices;
		center = new GeoVector(vertices.get(0).getDimensions());

		for(int j = 0; j < center.getDimensions(); j++)
		{
			Float sum = 0f;
			for(int i = 0; i < vertices.size(); i++)
			{
				sum += getVertex(i).getAxis(j);
			}
			center.setAxis(j, sum/center.getDimensions());
		}
	}

	public ArrayList<GeoVector> getVertices()
	{
		return vertices;
	}

	public void setVertices(ArrayList<GeoVector> vertices)
	{
		this.vertices = (ArrayList<GeoVector>) vertices.clone();
	}

	public GeoVector getVertex(int i)
	{
		return vertices.get(i);
	}

	public void setVertexices(int i, GeoVector vertex)
	{
		this.vertices.set(i, vertex);
	}

	public GeoVector getCenter()
	{
		return center;
	}
}

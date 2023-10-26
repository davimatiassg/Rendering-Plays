package geometrical;

import java.util.ArrayList;

public class Mesh {
	private ArrayList<GeoVector> vertices;
	private GeoVector center;
	private ArrayList<Integer[]> triangles;

	public Mesh(ArrayList<GeoVector> vertices, ArrayList<Integer[]> triangles)
	{
		
		this.vertices = vertices;
		this.triangles = triangles;
		//System.out.println(this.toString());
		this.center = calculateCenter();
	}
	
	private GeoVector calculateCenter() 
	{
		GeoVector center = new GeoVector(vertices.get(0).getDimensions());
		for(int j = 0; j < center.getDimensions(); j++)
		{
			Float sum = 0f;
			for(int i = 0; i < vertices.size(); i++)
			{
				sum += getVertex(i).getAxis(j);
			}
			center.setAxis(j, sum/center.getDimensions());
		}
		return center;
	}

	public ArrayList<GeoVector> getVertices()
	{
		return vertices;
	}

	public void setVertices(ArrayList<GeoVector> vertices)
	{
		this.vertices = (ArrayList<GeoVector>) vertices.clone();
		this.center = calculateCenter();
	}

	public ArrayList<Integer[]> getTriangles() {
		return triangles;
	}

	public void setTriangles(ArrayList<Integer[]> triangles) {
		this.triangles = triangles;
	}

	public GeoVector getVertex(int i)
	{
		return vertices.get(i);
	}

	public void setVertex(int i, GeoVector vertex)
	{
		this.vertices.set(i, vertex);
		this.center = calculateCenter();
	}
	
	public Integer[] getTriangle(int i)
	{
		return triangles.get(i);
	}

	public void setTriangle(int i, Integer[] triangle)
	{
		this.triangles.set(i, triangle);
	}

	public GeoVector getCenter()
	{
		return center;
	}

	@Override
	public String toString() {
		return "Mesh [vertices=" + vertices + ", center=" + center + ", triangles=" + triangles + "]";
	}
	
	
}

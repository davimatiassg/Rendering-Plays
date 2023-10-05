package Geometrical;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.Math;

public class GeoVector implements Collection{
	protected ArrayList<Float> axes;
	protected int dimensions;

	
	public GeoVector(int dimensions)
	{
		axes = new ArrayList<Float>();
		for(int i = 0; i < dimensions; i++)
		{
			axes.add(0f);
		}
		this.setDimensions(dimensions);
	}
	
	public GeoVector(ArrayList<Float> axes)
	{
		this.axes = new ArrayList<Float>(axes);
		for (Iterator<? extends Float> iterator = axes.iterator(); iterator.hasNext();) {
			iterator.next();
			dimensions += 1;
		}
	}

	public ArrayList<Float> getAxes()
	{
		return axes;
	}

	public void setAxes(ArrayList<Float> axes)
	{
		this.axes = axes;
	}

	public Float getAxis(int i)
	{
		return axes.get(i);
	}

	public void setAxis(int i, Float axis)
	{
		this.axes.set(i, axis);
	}

	public int getDimensions() {
		return dimensions;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}

	public Float sqrMagnitude()
	{
		Float sqrMag = 0f;
		for(Float axis : axes)
		{
			sqrMag += axis*axis;
		}
		return sqrMag;
	}

	public Float magnitude()
	{
		return new Float(Math.sqrt(sqrMagnitude()));
	}

	public static GeoVector add(GeoVector v1, GeoVector v2)
	{
		GeoVector result = new GeoVector(v1.getAxes());

		for(int i = 0; i < result.getDimensions(); i++)
		{
			result.setAxis(i, v1.getAxis(i) + v2.getAxis(i));
		}

		return result;
	}

	public static GeoVector scale(GeoVector v1, Float v)
	{
		GeoVector result = new GeoVector(v1.getAxes());

		for(int i = 0; i < result.getDimensions(); i++)
		{
			result.setAxis(i, v1.getAxis(i) * v);
		}

		return result;
	}

	public static GeoVector subtract(GeoVector v1, GeoVector v2)
	{
		GeoVector result = new GeoVector(v1.getAxes());

		for(int i = 0; i < result.getDimensions(); i++)
		{
			result.setAxis(i, v1.getAxis(i) - v2.getAxis(i));
		}

		return result;
	}

	public static Float scalarProduct(GeoVector v1, GeoVector v2)
	{
		Float product = 0f;
		for(int i = 0; i< v1.getDimensions(); i++)
		{
			product += v1.getAxis(i) * v2.getAxis(i);
		}
		return product;
	}

	public static GeoVector up()
	{
		ArrayList<Float> up = new ArrayList<Float>();
		up.add(0f);
		up.add(1f);
		up.add(0f);
		return new GeoVector(up);
	}

	public static GeoVector forward()
	{
		ArrayList<Float> forward = new ArrayList<Float>();
		forward.add(0f);
		forward.add(0f);
		forward.add(1f);
		return new GeoVector(forward);
	}

	public static GeoVector right()
	{
		ArrayList<Float> right = new ArrayList<Float>();
		right.add(0f);
		right.add(0f);
		right.add(1f);
		return new GeoVector(right);
	}

	/// Calculates the Orthogonal Projection of v over this.
	public Float projectionOf(GeoVector v)
	{
		return scalarProduct(this, v)/this.magnitude();
	}

	@Override
	public boolean add(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		for(Float axis : axes)
		{
			axis = 0.0f;
		}
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return dimensions;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return this.axes.toArray();
	}

	@Override
	public Object[] toArray(Object[] arg0) {
		// TODO Auto-generated method stub
		return this.axes.toArray(arg0);
	}
	
	public String toString()
	{
		String s = "";
		for(Float f : axes) 
		{
			s = s + f + " ";
		}
		return s;
	}
}

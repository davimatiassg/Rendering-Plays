package Geometrical;

import java.util.ArrayList;
import java.util.Collection;

public class Quaternion extends GeoVector{
	
	public Quaternion(){ 
		super(4);
	}
	
	public Quaternion(ArrayList<Float> axes)
	{
		super(axes);
		for (int i = dimensions - 1; i >= 4; i--)
		{
		    this.axes.remove(i);
		}
	}

	// Copy constructior
	public Quaternion(GeoVector original)
	{
		super(original.getAxes());
	}

	// Copy constructior
	public Quaternion(Quaternion original)
	{
		super(original.getAxes());
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

	public boolean isUnitary()
	{
		Float sum = 0f;
		for(int i = 0; i < dimensions; i ++) 
		{
			Float a = getAxis(i);
			sum += a*a;
		}
		return sum == 1f;
	}

	public Quaternion unitary()
	{

		Quaternion q = new Quaternion(getAxes());
		if(isUnitary()){ return q; }
		Float magnitude = ((GeoVector)q).magnitude();
		for(int i = 0; i < dimensions; i ++) 
		{
			q.setAxis(i, getAxis(i)/magnitude);
		}
		return q;
	}
	
	public Quaternion conjugate()
	{
		Quaternion q = new Quaternion(getAxes());
		for(int i = 1; i < dimensions; i ++) 
		{
			q.setAxis(i, -1*getAxis(i));
		}
		return q;
	}

	public static Quaternion multiply(Quaternion q1, Quaternion q2)
	{
		ArrayList<Float> newQuart = new ArrayList<Float>();
		
		newQuart.add(q1.getAxis(0)*q2.getAxis(0) - q1.getAxis(1)*q2.getAxis(1) - q1.getAxis(2)*q2.getAxis(2) - q1.getAxis(3)*q2.getAxis(3));
		newQuart.add(q1.getAxis(0)*q2.getAxis(1) + q1.getAxis(1)*q2.getAxis(0) + q1.getAxis(2)*q2.getAxis(3) - q1.getAxis(3)*q2.getAxis(2));
		newQuart.add(q1.getAxis(0)*q2.getAxis(2) + q1.getAxis(2)*q2.getAxis(0) + q1.getAxis(3)*q2.getAxis(1) - q1.getAxis(1)*q2.getAxis(3)); 
		newQuart.add(q1.getAxis(0)*q2.getAxis(3) + q1.getAxis(3)*q2.getAxis(0) + q1.getAxis(1)*q2.getAxis(2) - q1.getAxis(2)*q2.getAxis(1));
		
		return new Quaternion(newQuart);
	}

	public static GeoVector Rotate(Quaternion q, GeoVector v)
	{
		ArrayList<Float> quat_axes = new ArrayList<Float>();
		quat_axes.add(0f);
		quat_axes.addAll(v.getAxes());
		Quaternion quat_v = new Quaternion(quat_axes);
		Quaternion unit_q = q.unitary();
		Quaternion conj_q = unit_q.conjugate();
		quat_axes = ( Quaternion.multiply(Quaternion.multiply(unit_q, quat_v), conj_q) ).getAxes();
		quat_axes.remove(0);
		return new GeoVector(quat_axes);
	}
}

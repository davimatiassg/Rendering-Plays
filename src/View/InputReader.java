package View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.util.ArrayList;

import Geometrical.*;
import Rendering.Camera;

public class InputReader {

	public static void reader(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String line = "";
		while (true) {
			if (line != null) {
				System.out.println(line);

			} else
				break;
			line = buffRead.readLine();
		}
		buffRead.close();
	}
	public static GeoVector readPoint(String declaration, String values) throws IOException
	{
		GeoVector vertex = new GeoVector(Integer.parseInt(declaration.split(":")[1]));
		int counter = 0;
		for(String s : values.split(" ")) 
		{
			vertex.setAxis(counter, Float.valueOf(values.split(" ")[counter]));
			counter++;
		}
		return vertex;
	}
	public static Mesh readMesh(BufferedReader buffRead) throws IOException
	{
		ArrayList<GeoVector> vertices = new ArrayList<GeoVector>();
		String line = buffRead.readLine();
		if(line != null) {
			while(!line.contains("###"))
			{
				vertices.add(readPoint(line, buffRead.readLine()));
				line = buffRead.readLine();
			}
		}
		return new Mesh(vertices);
	}
	
	public static Scene readScene(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		Scene scene = new Scene();
		String line = "";
		
		while(line != null){
			line = buffRead.readLine();
			scene.addMesh(readMesh(buffRead));
			line = buffRead.readLine();
			
		}
		buffRead.close();
		
		return scene;
	}
	
	public static void configCamera(Camera cam, String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		
		buffRead.readLine();
		String[] buff = buffRead.readLine().split(" ");
		Integer[] viewport = {Integer.parseInt(buff[0]), Integer.parseInt(buff[1])};
		cam.setViewportSize(viewport);
		
		buffRead.readLine();
		buff = buffRead.readLine().split(" ");
		System.out.println("Lendo Position: " + buff[0] + " " + buff[1] + " " + buff[2] + " ");
		ArrayList<Float> floatCoords = new ArrayList<Float>();
		for(String s : buff) 
		{
			floatCoords.add(Float.parseFloat(s));
		}
		GeoVector v = new GeoVector(floatCoords);
		cam.setPosition(v);
		floatCoords.clear();
		
		buffRead.readLine();
		buff = buffRead.readLine().split(" ");
		System.out.println("Lendo Rotation: " + buff[0] + " " + buff[1] + " " + buff[2] + " " + buff[3]);
		for(String s : buff) 
		{
			floatCoords.add(Float.parseFloat(s));
			System.out.print(Float.parseFloat(s));
			System.out.print(" ");
		}
		Quaternion q = new Quaternion(floatCoords);
		cam.setRotation(q);
		buffRead.close();
	}
	/*
	public static void writer(String path) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
		String line = "";
		Scanner in = new Scanner(System.in);
		System.out.println("Escreva algo: ");
		line = in.nextLine();
		buffWrite.append(line + "\n");
		buffWrite.close();
	}*/

}
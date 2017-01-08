package models;

public class NginxUpload {
	public String name; // the name of the file on the client side
	public String contentType;
	public String path; // the abs path of the tmp file to hold the content
	public long size; // the size of the file;
	
	public String toString(){
		return "An Nginx Upload. name: " + name + "; path: " + path + "; size: " + size;
	}
}

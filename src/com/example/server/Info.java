package com.example.server;


public class Info {
	private String Fname;

	private String Nadd;

	private String Badd;
	
	private byte[] Bitmap;
	

	public void setFname(String Fname) {
		this.Fname = Fname;
	}

	public String getFname() {
		return this.Fname;
	}

	public void setNadd(String Nadd) {
		this.Nadd = Nadd;
	}

	public String getNadd() {
		return this.Nadd;
	}

	public void setBadd(String Badd) {
		this.Badd = Badd;
	}

	public String getBadd() {
		return this.Badd;
	}
	public void setBitmap(byte[] Bitmap) {
		this.Bitmap = Bitmap;
	}

	public byte[] getBitmap() {
		return this.Bitmap;
	}

}
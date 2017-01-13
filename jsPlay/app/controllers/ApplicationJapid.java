package controllers;

import play.*;
import play.mvc.*;

import java.io.File;
import java.util.*;

import models.*;


public class ApplicationJapid extends cn.bran.play.JapidController {

    // the views are in the "japidroot" directory
	// access url: /ApplicationJapid/index
    public static void index() {
    	render("Bing", 33);
    }
    
	public static void uploadFile(String comment, File attachment) {
		System.out.println("in controller");
		String info = comment + ". " + attachment.getAbsolutePath() + ":" + attachment.length();
		System.out.println(info);
		if (attachment.length() > 0) {
			attachment.delete();
			renderText(info);
		}
		else
			renderText(attachment.exists() + "");
			
	}
}
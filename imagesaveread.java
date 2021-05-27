import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

import javax.imageio.stream.*;

public class imagesaveread {	
	public void imageoutput(String name,String savepath) {
		File f=new File(savepath);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://192.168.0.105/images","zdr","5461863");
			Statement stmt=conn.createStatement();
			String query="select sources from images where name=?";
			PreparedStatement ps=conn.prepareStatement(query);
			ps.setString(1, name);
			ResultSet rs=ps.executeQuery();
			rs.next();
			//Ð´ÈëÎÄ¼þ
			InputStream fi=rs.getBinaryStream(1);
			FileOutputStream fos=new FileOutputStream(f);
			int length=0;
			byte[] b=new byte[1024];
			while((length=fi.read(b))!=-1) {
				fos.write(b,0,length);
			}
			fos.flush();
			
			conn.close();
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void imageinput(String readpath) {
		File f=new File(readpath);
		try {
			FileInputStream fi=new FileInputStream(f);
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn=DriverManager.getConnection("jdbc:mysql://192.168.0.105/images","zdr","5461863");
				String query="INSERT INTO images (name, sources) VALUES (?,?)";
				PreparedStatement ps=conn.prepareStatement(query);
				ps.setString(1, f.getName());
				ps.setBinaryStream(2, fi);
				ps.executeUpdate();				
				conn.close();
				ps.close();			
			}catch(Exception e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
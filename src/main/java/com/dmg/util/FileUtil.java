package com.dmg.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;


public class FileUtil {

	private static FileUtil fileUtil = new FileUtil();

	public static void moveFile(File sourceFile, String path) {

		File desFile = new File(path + File.separator + sourceFile.getName());
		try {
			copyFile(sourceFile, desFile);
			boolean deleted = sourceFile.delete();
			if (!deleted) {
				System.out.println("Delete faild for the file" + sourceFile.getName());
			}
		} catch (Exception e) {
			Logger.error(fileUtil, "Error in moving file:" + sourceFile.getPath(), e);
		}

	}

	public static boolean copyFile(String source, String dest) {

		File sourceFile = new File(source);
		File destFile = new File(dest);

		return copyFile(sourceFile, destFile);
	}

	public static boolean copyFile(File sourceFile, File destFile) {

		if (!sourceFile.exists()) {
			Logger.warn("COPYFILE", "Error in copyFile: file does not exsist: " + sourceFile.getAbsolutePath());
			return false;
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			if (!destFile.exists()) {
				destFile.createNewFile();
			}

			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
			return true;
		} catch (FileNotFoundException e) {
			Logger.error(fileUtil, "Error in Copieing file:" + sourceFile.getPath(), e);
			return false;
		} catch (IOException e) {
			Logger.error(fileUtil, "Error in Copieing file:" + sourceFile.getPath(), e);
			return false;
		} finally {
			if (source != null) {
				try {
					source.close();
				} catch (IOException e) {
					Logger.error(fileUtil, "Error in moving file:" + sourceFile.getPath(), e);
					throw new UtilException(e);
				}
			}
			if (destination != null) {
				try {
					destination.close();
				} catch (IOException e) {
					Logger.error(fileUtil, "Error in moving file:" + sourceFile.getPath(), e);
					throw new UtilException(e);
				}
			}
		}

	}

	public static void copyFile(File sourceFile, String destPath) {
		File destFile = new File(destPath);
		copyFile(sourceFile, destFile);

	}

	public static boolean loadFile(String urlPath, String des) {

		try {
	
			URL url = new URL(urlPath);
			url.openConnection();
			InputStream reader = url.openStream();


			FileOutputStream writer = new FileOutputStream(des);
			byte[] buffer = new byte[15360];
//			int totalBytesRead = 0;
			int bytesRead = 0;

			while ((bytesRead = reader.read(buffer)) > 0) {
				writer.write(buffer, 0, bytesRead);
				buffer = new byte[15360];
//				totalBytesRead += bytesRead;
			}

	
			writer.close();
			reader.close();
			return true;
		} catch (MalformedURLException e) {
			Logger.error(fileUtil,"Error on loading file from: " +urlPath + "  to: "+ des, e);
			return false;
		} catch (IOException e) {
			Logger.error(fileUtil,"Error on loading file from: " +urlPath + "  to: "+ des, e);
			return false;
		}

	}
	
	
	public static String getFileContent(String fullFilePath) {
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		try {
			fstream = new FileInputStream(fullFilePath);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			StringBuffer buffer = new StringBuffer();
			while ((strLine = br.readLine()) != null) {
				buffer.append(strLine);
			}
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			if (fstream != null) {
				try {
					fstream.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}


	public static void deleteFolder(String foldername) {
		
		File dir = new File(foldername);
		File[] listFiles = dir.listFiles();
		
		if(listFiles!= null ){
			
			for (File file : listFiles) {
				file.delete();
			}
		}
		
		dir.delete();
	}
	
	public static void saveFile(File file, String content) throws Exception {
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			fw.write(content);
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
			if (fw != null) {
				try {
					fw.flush();
					fw.close();
				} catch (IOException e) {
					throw new Exception(e);
				}
			}
		}
	}

}

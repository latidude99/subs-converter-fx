/**Copyright (C) 2017  Piotr Czapik.
*  @author Piotr Czapik
*  @version 2.0
* 
*  This file is part of Subs Converter.
*  Subs Converter is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, either version 3 of the License, or
*  (at your option) any later version.
* 
*  Subs Converter is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
* 
*  You should have received a copy of the GNU General Public License
*  along with Subs Converter.  If not, see <http://www.gnu.org/licenses/>
*  or write to: latitude1001101@gmail.com
*/

package com.meridian99.subsconverter.app;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class MainController implements Initializable{
	
	@FXML
    private TextArea contentText;
    @FXML
    private Button buttonReplace;
    @FXML
    private Button buttonClear;
    @FXML
    private Label labelTitle;
    @FXML
    private TextField pathOpenFile;
    @FXML
    private Button buttonOpenFile;
    @FXML
    private TextField pathSaveFile;
    @FXML
    private Button buttonSaveFile;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextField textReplace;
    @FXML
    private Label labelReplace;
    @FXML
    private TextField textWith;
    @FXML
    private Label labelWith;
    @FXML
    private Label labelSaved;
    @FXML
    private Label labelDone;
    @FXML
    private Button buttonApply;
    @FXML
    private RadioButton radioInUnicode;
    @FXML
    private RadioButton radioInUTF8;
    @FXML
    private RadioButton radioIn1250;
    @FXML
    private RadioButton radioInFile;
    @FXML
    private RadioButton radioOutUnicode;
    @FXML
    private RadioButton radioOutUTF8;
    @FXML
    private RadioButton radioOut1250;
    @FXML
    private RadioButton radioOutFile;
    @FXML
    private ToggleGroup radioInGroup;
    @FXML
    private ToggleGroup radioOutGroup;
    @FXML
    private Button buttonApplyContent;
    @FXML
    private Label labelDoneContent;
   // @FXML
   // private Button buttonApplyContent;
   // @FXML
   // private Label labelDoneContent;

    
    private File fileOut = null;
    private File fileIn = null;
        
    private ArrayList<String> textIn =null;
    private ArrayList<String> textOut =null;
    
    Charset chIn;
	Charset chOut;
    
    private FileChooser fileChooser = new FileChooser();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		configureIO();
		configureProcessing();
		configureMouseOnText();
		
		
	}
	
	
	public void configureIO(){
		//final Desktop desktop = Desktop.getDesktop();
		
		buttonOpenFile.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				labelDoneContent.setText("");
				labelDone.setText("");
				labelSaved.setText("");
				textReplace.clear();
				textWith.clear();
				fileIn = fileChooser.showOpenDialog(null);
				fileChooser.setTitle("Open subtitles file (.txt, .srt, .sub");
				//fileChooser.setInitialDirectory(File.listRoots()[0]);
				if(fileIn != null){
					pathOpenFile.setText(fileIn.getAbsolutePath());
					try {
						getFileEncoding(fileIn);
					} catch (IOException e1) {
						MessageBox.show("Error reading encoding type", "Error");
					}
					contentText.setText(textInToContent(readFile(fileIn)));
				}
			}
		});
		
		buttonSaveFile.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				labelDoneContent.setText("");
				labelDone.setText("");
				textReplace.clear();
				textWith.clear();
				fileChooser.setTitle("Choose location for converted subtitles");
				//fileChooser.setInitialDirectory(File.listRoots()[0]);
				if(fileIn != null){
					String name = fileIn.getName();
					fileChooser.setInitialFileName(name.substring(0, name.lastIndexOf(".")) + "_replaced"
									+ "." + name.substring(name.lastIndexOf(".")+1, name.length()));
					//while(fileOut == null){
						fileOut = fileChooser.showSaveDialog(null);
					//}
					saveFile(fileOut);
				} else {
					fileChooser.setInitialFileName("_replaced.txt");
					saveFile(fileOut);
				}
				fileOut = null;
				
			}
		});
	}	
	
	private void configureProcessing() {
		
		buttonApply.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				labelDoneContent.setText("");
				labelSaved.setText("");
				//try{
				contentToTextIn();
					contentText.clear();
					if(textIn != null){
						replaceSingle(textIn);
						contentText.setText(textInToContent(textIn));
					}
					
				//}catch (NullPointerException e){
				//	MessageBox.show("Load a file first", "Entry data error");
				//}
				
			}
		});
		
		buttonReplace.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				//if(contentText.getText() != null){
				contentToTextIn();
				if(textIn != null){
					labelDoneContent.setText("");
					labelSaved.setText("");
					contentToTextIn();
					contentText.clear();
					contentText.setText(textInToContent(replaceAll(textIn)));
					labelDone.setText("Replaced!");
				}
				//}else{
				//	MessageBox.show("No text to process", "Entry data error");
				//}
			}
		});
		
		buttonClear.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				contentText.clear();
				labelSaved.setText("");
				labelDone.setText("");
				labelDoneContent.setText("");
				textReplace.clear();
				textWith.clear();
				pathOpenFile.clear();
				pathSaveFile.clear();
			}
		});
		
		buttonApplyContent.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				labelSaved.setText("");
				labelDone.setText("");
				contentToTextIn();
				contentText.clear();
				if(textIn != null)
					contentText.setText(textInToContent(textIn));
			}
		});
	}
	
	private void configureMouseOnText(){
		
		pathOpenFile.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event){
				clear();
			}
		});
		
		pathSaveFile.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event){
				clear();
			}
		});
		
		contentText.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event){
				clear();
			}
		});
		
	}
	
	private void clear(){
		labelDone.setText("");
		labelDoneContent.setText("");
		labelSaved.setText("");
		textReplace.clear();
		textWith.clear();
		
	}
		
	private ArrayList<String> readFile(File file){
			textIn = new ArrayList<String>();
			chIn = Charset.forName(getInEncoding());
			try(InputStream is = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(is, chIn));) {
				
					String line = null;
					while((line = br.readLine()) != null){
						textIn.add(line);
						//System.out.println(line);
					}
							
			}catch(UnsupportedEncodingException e){
				MessageBox.show("Encoding not supported", "Error");
			}catch(IOException e){
				MessageBox.show("e.getMessage()", "Input Error");
			}catch(Exception e){
				MessageBox.show("e.getMessage()", "Input Error");
			}
			return textIn;
	}
	
	private void saveFile(File file) {
		if(textIn == null){
			MessageBox.show("Nothing to save!", "Output error");
			return;
		}
		chOut = Charset.forName(getOutEncoding());
		try(OutputStream os = new FileOutputStream(file);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, chOut));) {
				for(String str: textIn){
					bw.write(str);
					bw.newLine();
				}
				pathSaveFile.setText(file.getAbsolutePath());
				labelSaved.setText("File saved!");
			} catch (Exception ex){
				MessageBox.show("File not saved", "Output error");
			}
	}
	
	private ArrayList<String> replaceAll(ArrayList<String> list){
		//contentToTextIn();
		if(textIn == null)
			return null;
		textIn = (ArrayList<String>) list.stream()
			.map(s-> s.replaceAll("ą", "a").replaceAll("ę", "e")
			.replaceAll("ó", "o").replaceAll("ł", "l")
			.replaceAll("ń", "n").replaceAll("ż", "z")
			.replaceAll("ź", "z").replaceAll("ć", "c")
			.replaceAll("ś", "s").replaceAll("Ł", "L")
			.replaceAll("Ż", "Z").replaceAll("Ź", "Z")
			.replaceAll("Ć", "C").replaceAll("Ś", "S")
			.replaceAll("Ó", "O"))
			//.peek(s-> System.out.println(s))
			.collect(Collectors.toList());	
		return textIn;
	}
	
	private void replaceSingle(ArrayList<String> list){
		//contentToTextIn();
		if(textIn != null)
		textIn = (ArrayList<String>) list.stream()
			.map(s-> s.replaceAll(textReplace.getText(), textWith.getText()))
			//.peek(s-> System.out.println(s))
			.collect(Collectors.toList());
		labelDone.setText("Done!");
	}
			
	private String textInToContent(List<String> list){
		StringBuilder content = new StringBuilder();
		for(String s: list){
			content.append(s);
			content.append("\n");
		}
		labelDoneContent.setText("Done!");
		return content.toString();
	}
		
	private void contentToTextIn(){
		String newline = System.getProperty("line.separator");
		String content = contentText.getText();
		boolean hasNewLine = content.contains(newline);
		if((content.trim().length() > 0) && (!hasNewLine)){
			String[] lines = contentText.getText().split("\n");
			textIn = new ArrayList<String>(Arrays.asList(lines));
		} else {
			MessageBox.show("The text area is empty!", "Input error");
		}
	}
		
	public void getFileEncoding(File file) throws IOException{
		FileInputStream fis = null;
		InputStreamReader isr = null;
		String ch = null;
		try{
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			ch = isr.getEncoding();
			radioInFile.setText(ch);
			radioOutFile.setText(ch);
		}catch(IOException e){
			MessageBox.show("e.getMessage()", "Encoding reading error");
		}catch(Exception e){
			MessageBox.show("e.getMessage()", "Encoding reading error");
		}finally{
			fis.close();
			isr.close();
		}
	}
	
	private String getInEncoding(){
		if(radioInGroup.getSelectedToggle() == null)
			return "UTF-8";
		RadioButton radioInSelected = (RadioButton) radioInGroup.getSelectedToggle();
		return radioInSelected.getText();
	}
	
	private String getOutEncoding(){
		if(radioOutGroup.getSelectedToggle() == null)
			return getInEncoding();
		RadioButton radioOutSelected = (RadioButton) radioOutGroup.getSelectedToggle();
		return radioOutSelected.getText();
	}
		
}	


/* characters's codes
												.map(s-> s.replaceAll("\u0105", "\u0061").replaceAll("\u0119", "\u0065")
												.replaceAll("\u00F3", "\u006F").replaceAll("\u0142", "\u006C")
												.replaceAll("\u0144", "\u006E").replaceAll("\u017C", "\u007A")
												.replaceAll("\u017A", "\u007A").replaceAll("\u0107", "\u0063")
												.replaceAll("\u015B", "\u0073").replaceAll("\u0141", "\u004C")
												.replaceAll("\u017B", "\u005A").replaceAll("\u0179", "\u005A")
												.replaceAll("\u0106", "\u0043").replaceAll("\u015A", "\u0053")
												.replaceAll("\u00D3", "\u004F"))
												.peek(s-> System.out.println(s))
												.collect(Collectors.toList());	
ą \u0105   a \u0061
ę \u0119   e \u0065
ó \u00F3   o \u006F
ł \u0142   l \u006C
ń \u0144   n \u006E
ż \u017C   z \u007A
ź \u017A   z \u007A
ć \u0107   c \u0063
ś \u015B   s \u0073

Ł \u0141   L \u004C
Ż \u017B   Z \u005A
Ź \u0179   Z \u005A
Ć \u0106   C \u0043
Ś \u015A   S \u0053
Ó \u00D3   O \u004F

Ę \u0118   E \u0045
Ą \u0104   A \u0041
Ń \u0143   N \u004E


*/










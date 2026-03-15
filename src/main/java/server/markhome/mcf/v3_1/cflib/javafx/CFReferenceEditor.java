/*
 *	Mark's Code Fractal CFLib JavaFX 3.1 Widget and Support Library
 *
 *	Copyright 2016-2026 Mark Stephen Sobkow
 *
 *	These files are part of Mark's Code Fractal CFLib.
 *
 *	Mark's Code Fractal CFLib is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later with static linking exception.
 *
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the LGPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-LGPLv3-compatibly licensed code.  That said,
 *	code which does not rely on this library is free to specify whatever license its
 *	authors decide to use. Mark Sobkow specifically rejects the infectious nature of
 *	the LGPLv3, and considers the mere act of including LGPLv3 modules in an
 *	executable to be perfectly reasonable given tools like modern Java's single-jar
 *	deployment options.
 *
 *	Mark's Code Fractal CFLib is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Mark's Code Fractal CFLib is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU Library General Public License for more details.
 *
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFLib.  If not, see &lt;https://www.gnu.org/licenses/&gt;.
 *
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 */

package server.markhome.mcf.v3_1.cflib.javafx;

import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;

public class CFReferenceEditor
extends AnchorPane
{
	public final static String PICK_ICON_RESOURCE = "/server.markhome.mcf.v3_1.cflib/javafx.images/PickReference.gif";
	public final static String VIEW_ICON_RESOURCE = "/server.markhome.mcf.v3_1.cflib/javafx.images/ViewReference.gif";
	protected ICFLibAnyObj referencedObject = null;
	protected TextField textFieldQualifiedName = null;
	protected Button buttonPickReference = null;
	protected Button buttonViewReference = null;
	protected boolean customDisable = false;
	
	protected String fieldNameInzTag = null;
	
	public String getFieldNameInzTag() {
		return( fieldNameInzTag );
	}
	
	public void setFieldNameInzTag( String value ) {
		if( ( value == null ) || ( value.length() <= 0 ) ) {
			fieldNameInzTag = null;
		}
		else {
			fieldNameInzTag = value;
		}
	}

	public interface ICFReferenceCallback {
		void chose( server.markhome.mcf.v3_1.cflib.ICFLibAnyObj value );
		void view( server.markhome.mcf.v3_1.cflib.ICFLibAnyObj value );
	}
	
	protected ICFReferenceCallback callback = null;
	
	public CFReferenceEditor( ICFReferenceCallback argCallback ) {
		super();
		final String S_ProcName = "construct";
		if( argCallback == null ) {
			throw new CFLibNullArgumentException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argCallback" );
		}
		callback = argCallback;
		referencedObject = null;

		setMinWidth( 300 );
		setPrefWidth( 400 );
		setMaxWidth( 800 );
		setMinHeight( 25 );
		setMaxHeight( 25 );
		setPrefHeight( 25 );
		
		textFieldQualifiedName = new TextField();
		textFieldQualifiedName.setMinHeight( 25 );
		textFieldQualifiedName.setMaxHeight( 25 );
		textFieldQualifiedName.setPrefHeight( 25 );
		textFieldQualifiedName.setEditable( false );

		buttonPickReference = new Button();
		buttonPickReference.setGraphic( new ImageView( getPickIcon() ) );
		buttonPickReference.setMinHeight( 25 );
		buttonPickReference.setMaxHeight( 25 );
		buttonPickReference.setPrefHeight( 25 );
		buttonPickReference.setMinWidth( 25 );
		buttonPickReference.setMaxWidth( 25 );
		buttonPickReference.setPrefWidth( 25 );
		buttonPickReference.setOnAction( new EventHandler<ActionEvent>() {
			@Override public void handle( ActionEvent e ) {
				if( callback != null ) {
					callback.chose( referencedObject );
				}
			}
		});

		buttonViewReference = new Button();
		buttonPickReference.setGraphic( new ImageView( getViewIcon() ) );
		buttonViewReference.setMinHeight( 25 );
		buttonViewReference.setMaxHeight( 25 );
		buttonViewReference.setPrefHeight( 25 );
		buttonViewReference.setMinWidth( 25 );
		buttonViewReference.setMaxWidth( 25 );
		buttonViewReference.setPrefWidth( 25 );
		buttonViewReference.setOnAction( new EventHandler<ActionEvent>() {
			@Override public void handle( ActionEvent e ) {
				if( callback != null ) {
					callback.view( referencedObject );
				}
			}
		});
		
		getChildren().addAll( textFieldQualifiedName, buttonPickReference, buttonViewReference );

		setLeftAnchor( textFieldQualifiedName, 0.0 );
		setRightAnchor( textFieldQualifiedName, 50.0 );
		setRightAnchor( buttonPickReference, 25.0 );
		setRightAnchor( buttonViewReference, 0.0 );
	}

	public ICFLibAnyObj getReferencedObject() {
		return( referencedObject );
	}
	
	public void setReferencedObject( ICFLibAnyObj value ) {
		referencedObject = value;
		TextField textField = getTextFieldQualifiedName();
		if( referencedObject != null ) {
			String qualifiedName = referencedObject.getObjQualifiedName();
			if( qualifiedName != null ) {
				textField.setText( qualifiedName );
			}
			else {
				textField.setText( "" );
			}
		}
		else {
			textField.setText( "" );
		}
		textField.selectEnd();
		textField.deselect();
	}
	
	public Image getPickIcon() {
		final String S_ProcName="getPickIcon";
		InputStream stream = CFReferenceEditor.class.getResourceAsStream( PICK_ICON_RESOURCE );
		if( stream == null ) {
			throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"Resource " + PICK_ICON_RESOURCE );
		}
		Image icon = new Image( stream );
		return( icon );
	}
	
	public Image getViewIcon() {
		final String S_ProcName="getViewIcon";
		InputStream stream = CFReferenceEditor.class.getResourceAsStream( VIEW_ICON_RESOURCE );
		if( stream == null ) {
			throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"Resource " + VIEW_ICON_RESOURCE );
		}
		Image icon = new Image( stream );
		return( icon );
	}

	public TextField getTextFieldQualifiedName() {
		return( textFieldQualifiedName );
	}

	public Button getButtonPickReference() {
		return( buttonPickReference );
	}

	public Button getButtonViewReference() {
		return( buttonViewReference );
	}
	
	public void setCustomDisable( boolean value ) {
		setDisable( false );
		customDisable = value;
		if( customDisable ) {
			buttonPickReference.setDisable( true );
			buttonViewReference.setDisable( false );
			textFieldQualifiedName.setDisable( true );
		}
		else {
			buttonPickReference.setDisable( false );
			buttonViewReference.setDisable( false );
			textFieldQualifiedName.setDisable( false );
		}
	}
	
	public boolean getCustomDisable() {
		return( customDisable );
	}
}

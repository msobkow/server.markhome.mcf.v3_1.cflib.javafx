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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import server.markhome.mcf.v3_1.cflib.*;

public class CFBoolEditor
extends Button
{
	public final static String FALSE_ICON_RESOURCE = "/server.markhome.mcf.v3_1.cflib/javafx.images/BoolFalse.gif";
	public final static String TRUE_ICON_RESOURCE = "/server.markhome.mcf.v3_1.cflib/javafx.images/BoolTrue.gif";
	public final static String NULL_ICON_RESOURCE = "/server.markhome.mcf.v3_1.cflib/javafx.images/BoolNull.gif";
	public final static Boolean S_FALSE = Boolean.valueOf( false );
	public final static Boolean S_TRUE = Boolean.valueOf( true );
	
	protected Boolean value = S_FALSE;
	protected boolean isNullable = false;

	protected Image falseImage = null;
	protected Image trueImage = null;
	protected Image nullImage = null;

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
	
	public Image getFalseImage() {
		final String S_ProcName="getFalseImage";
		if( falseImage == null ) {
			InputStream stream = CFBoolEditor.class.getResourceAsStream( FALSE_ICON_RESOURCE );
			if( stream == null ) {
				throw new CFLibNullArgumentException( getClass(),
						S_ProcName,
						0,
						"Resource " + FALSE_ICON_RESOURCE );
			}
			falseImage = new Image( stream );
		}
		return( falseImage );
	}

	public Image getTrueImage() {
		final String S_ProcName="getTrueImage";
		if( trueImage == null ) {
			InputStream stream = CFBoolEditor.class.getResourceAsStream( TRUE_ICON_RESOURCE );
			if( stream == null ) {
				throw new CFLibNullArgumentException( getClass(),
						S_ProcName,
						0,
						"Resource " + TRUE_ICON_RESOURCE );
			}
			trueImage = new Image( stream );
		}
		return( trueImage );
	}

	public Image getNullImage() {
		final String S_ProcName="getNullImage";
		if( nullImage == null ) {
			InputStream stream = CFBoolEditor.class.getResourceAsStream( NULL_ICON_RESOURCE );
			if( stream == null ) {
				throw new CFLibNullArgumentException( getClass(),
						S_ProcName,
						0,
						"Resource " + NULL_ICON_RESOURCE );
			}
			nullImage = new Image( stream );
		}
		return( nullImage );
	}
	
	public CFBoolEditor() {
		super();
		value = S_FALSE;
		setGraphic( new ImageView( getFalseImage() ) );
		setMinWidth( 25 );
		setMaxWidth( 25 );
		setPrefWidth( 25 );
		setMinHeight( 25 );
		setMaxHeight( 25 );
		setPrefHeight( 25 );
		setOnAction( new EventHandler<ActionEvent>() {
			@Override public void handle( ActionEvent e ) {
				if( value == null ) {
					if( isNullable == false ) {
						value = S_FALSE;
					}
				}
				if( value == null ) {
					value = S_FALSE;
					setGraphic( new ImageView( getFalseImage() ) );
				}
				else if( value.booleanValue() == false ) {
					value = S_TRUE;
					setGraphic( new ImageView( getTrueImage() ) );
				}
				else if( isNullable ) {
					value = null;
					setGraphic( new ImageView( getNullImage() ) );
				}
				else {
					value = S_FALSE;
					setGraphic( new ImageView( getFalseImage() ) );
				}
			}
		});
	}
	
	public boolean getIsNullable() {
		return( isNullable );
	}
	
	public void setIsNullable( boolean val ) {
		isNullable = val;
		if( ( ! isNullable ) && ( value == null ) ) {
			value = S_FALSE;
			setGraphic( new ImageView( getFalseImage() ) );
		}
	}
	
	public boolean hasValue() {
		return( true );
	}
	
	public void setBooleanValue( Boolean val ) {
		if( val == null ) {
			if( isNullable ) {
				value = null;
				setGraphic( new ImageView( getNullImage() ) );
			}
			else {
				value = S_FALSE;
				setGraphic( new ImageView( getFalseImage() ) );
			}
		}
		else if( val.booleanValue() == false ) {
			value = S_FALSE;
			setGraphic( new ImageView( getFalseImage() ) );
		}
		else {
			value = S_TRUE;
			setGraphic( new ImageView( getTrueImage() ) );
		}
	}

	public Boolean getBooleanValue() {
		if( ( ! isNullable ) && ( value == null ) ) {
			value = S_FALSE;
		}
		return( value );
	}
}

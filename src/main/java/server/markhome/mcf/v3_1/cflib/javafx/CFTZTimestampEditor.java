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

import java.time.*;
import javafx.geometry.Pos;

import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;

public class CFTZTimestampEditor
extends CFTextField
{
	public final static LocalDateTime MIN_VALUE = CFLibXmlUtil.parseTimestamp("0000-00-00 00:00:00");
	public final static LocalDateTime MAX_VALUE = CFLibXmlUtil.parseTimestamp("9999-11-31 23:59:59" );

	protected LocalDateTime curValue = null;

	protected LocalDateTime minValue = MIN_VALUE;
	protected LocalDateTime maxValue = MAX_VALUE;

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

	public CFTZTimestampEditor() {
		super();
		setAlignment( Pos.CENTER_LEFT );
		setMinWidth( 200 );
		setMaxWidth( 200 );
		setPrefWidth( 200 );
		setMinHeight( 25 );
		setMaxHeight( 25 );
		setPrefHeight( 25 );
	}

	public LocalDateTime getMinValue() {
		return( minValue );
	}
	
	public void setMinValue( LocalDateTime value ) {
		minValue = value;
	}
	
	public LocalDateTime getMaxValue() {
		return( maxValue );
	}
	
	public void setMaxValue( LocalDateTime value ) {
		maxValue = value;
	}
	
	
	public boolean isEditValid() {
		String text = getText();
		if( ( text == null ) || ( text.length() <= 0 ) ) {
			curValue = null;
			return( true );
		}
		curValue = null;
		curValue = CFLibXmlUtil.parseTZTimestamp( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag), text );
		return( true );
	}

	public void setTZTimestampValue( LocalDateTime value ) {
		curValue = value;
		if( curValue != null ) {
			String str = CFLibXmlUtil.formatTZTimestamp( value );
			setText( str );
		}
		else {
			setText( "" );
		}
	}
	
	public boolean hasValue() {
		boolean retval;
		String text = getText();
		if( ( text == null ) || ( text.length() <= 0 ) ) {
			retval = false;
		}
		else {
			retval = true;
		}
		return( retval );
	}

	public LocalDateTime getTZTimestampValue() {
		final String S_ProcName = "getTZTimestampValue";
		if( ! hasValue() ) {
			return( null );
		}
		else if( isEditValid() ) {
			return( curValue );
		}
		else {
			throw new CFLibInvalidArgumentException( Inz.x(fieldNameInzTag),
				Inz.s(fieldNameInzTag),
				S_ProcName,
				0,
				"text",
				getText(),
				getText() );
		}
	}
}

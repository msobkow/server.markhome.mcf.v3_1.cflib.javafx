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

import javafx.geometry.Pos;

import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;

public class CFDbKeyHash384Editor
extends CFTextField
{
	protected final static int MAX_LEN = CFLibDbKeyHash384.HASH_LENGTH_STRING;

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

	public CFDbKeyHash384Editor() {
		super();
		setAlignment( Pos.CENTER_LEFT );
		setMinHeight( 25 );
		setPrefHeight( 25 );
		setMaxHeight( 250 );
		if( MAX_LEN > 58 ) {
			setPrefWidth( 660 );
		}
		else if( MAX_LEN > 0 ) {
			setPrefWidth( ( MAX_LEN + 2 ) * 11 );
		}
	}
	
	public int getMaxLen() {
		return( MAX_LEN );
	}
	
	public boolean hasValue() {
		boolean retval;
		String text = getText();
		if( ( text == null ) || ( text.length() != MAX_LEN ) ) {
			retval = false;
		}
		else {
			retval = true;
		}
		return( retval );
	}
	
	public boolean isEditValid() {
		String str = getText();
		if( str == null ) {
			return( true );
		}
		int len = str.length();
		if( len != MAX_LEN ) {
			return( false );
		}
		else {
			for (int i = 0; i < len; i++ ) {
				switch (str.charAt(i)) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
					case 'a':
					case 'b':
					case 'c':
					case 'd':
					case 'e':
					case 'f':
					case 'A':
					case 'B':
					case 'C':
					case 'D':
					case 'E':
					case 'F':
						break;
					default:
						return( false );
				}
			}
			return( true );
		}
	}
	
	public void setDbKeyHash384Value( CFLibDbKeyHash384 value ) {
		if( value != null ) {
			setText( value.toString() );
		}
		else {
			setText( "" );
		}
	}

	public CFLibDbKeyHash384 getDbKeyHash384Value() {
		final String S_ProcName = "getDbKeyHash384Value";
		String text = getText();
		if( text == null || text.isEmpty() ) {
			return( null );
		}
		else if( text.length() > MAX_LEN ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
					S_ProcName,
					0,
					"MaxLen",
					text.length(),
					MAX_LEN );
		}
		else if( text.length() < MAX_LEN ) {
			throw new CFLibArgumentUnderflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
					S_ProcName,
					0,
					"MinLen",
					text.length(),
					MAX_LEN );
		}
		else {
			try {
				CFLibDbKeyHash384 retval = CFLibDbKeyHash384.fromHex(text);
				return( retval );
			}
			catch( Throwable th ) {
				throw new CFLibInvalidArgumentException(Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
					S_ProcName, 
					th.getMessage(), th.getLocalizedMessage());
			}
		}
	}
}

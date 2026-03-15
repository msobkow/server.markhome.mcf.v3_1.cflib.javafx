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

import java.util.Calendar;
import javafx.scene.control.TextArea;

import server.markhome.mcf.v3_1.cflib.*;

public class CFConsole
extends TextArea
{
	protected static boolean logExceptionsToSystem = true;
	protected static CFConsole sharedConsole = null;
	
	public CFConsole() {
		super();
		setPrefWidth( 660 );
		setMinHeight( 50 );
		setPrefHeight( 50 );
		setEditable( false );
		setWrapText( true );
		if( sharedConsole == null ) {
			sharedConsole = this;
		}
	}

	public static boolean getLogExceptionsToSystem() {
		return( logExceptionsToSystem );
	}
	
	public static void setLogExceptionsToSystem( boolean value ) {
		logExceptionsToSystem = value;
	}
	
	protected void logMessage( String msg ) {
		if( msg == null ) {
			return;
		}
		StringBuffer buff = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		String stamp = String.format( "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL ", cal );
		buff.append( stamp );
		buff.append( msg );
		if( ! msg.endsWith( "\n" ) ) {
			buff.append( "\n" );
		}
		appendText( buff.toString() );
		positionCaret( getLength() );
	}
	
	public static void message( String msg ) {
		if( sharedConsole != null ) {
			sharedConsole.logMessage( msg );
		}
	}
	
	protected void logFormException( String formName, String actionName, Throwable t ) {
		String useFormName = ( formName != null ) ? formName : "?";
		String useActionName = ( actionName != null ) ? actionName : "?";
		if( t == null ) {
			return;
		}
		String msg = t.getMessage();
		if( msg == null ) {
			msg = "";
		}
		String className = t.getClass().getName();
		int lastDot = className.lastIndexOf( '.' );
		String strippedClassName = className.substring( lastDot + 1 );
		StringBuffer buff = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		String stamp = String.format( "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL ", cal );
		buff.append( stamp );
		buff.append( useFormName + "." + useActionName + " caught " + strippedClassName + " - " + msg );
		buff.append( "\n" );
		String buffString = buff.toString();
		appendText( buffString );
		positionCaret( getLength() );
		CFLib.beep();
		if( logExceptionsToSystem ) {
			System.out.print( buffString );
			t.printStackTrace( System.out );
		}
	}

	public static void formException( String formName, String actionName, Throwable t ) {
		if( sharedConsole != null ) {
			sharedConsole.logFormException( formName, actionName, t );
		}
	}
}

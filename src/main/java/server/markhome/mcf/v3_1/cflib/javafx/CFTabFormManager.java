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

import java.util.LinkedList;
import javafx.scene.Node;

import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.javafx.CFTab;

/**
 *	The ICFDesktopManager specifies the interface from an application's
 *	forms to the desktop display manager in charge of that facet of forms.
 */
public abstract class CFTabFormManager
extends CFTab
implements ICFFormManager
{
	protected LinkedList<Node> formStack = new LinkedList<Node>();

	public CFTabFormManager() {
		super();
	}
	
	/**
	 *	Sometimes you'll want to check to see what the current node
	 *	form being displayed is.
	 */
	public Node getCurrentForm() {
		if( formStack.isEmpty() ) {
			return( null );
		}
		Node retval = formStack.getLast();
		return( retval );
	}
	
	/**
	 *	Set the currently displayed form, clearing the form stack
	 *	and making the specified the root form for processing.
	 */
	public void setRootForm( Node value ) {
		final String S_ProcName = "setRootForm";
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"value" );
		}
		if( ! ( value instanceof ICFForm ) ) {
			throw new CFLibUnsupportedClassException(getClass(),
				S_ProcName,
				"value",
				value,
				"ICFForm" );
		}
		while( ! formStack.isEmpty() ) {
			Node top = formStack.getLast();
			ICFForm form = (ICFForm)top;
			try {
				form.forceCancelAndClose();
			}
			catch( RuntimeException e ) {
			}
			if( ! formStack.isEmpty() ) {
				if( top == formStack.getLast() ) {
					// Close wasn't called like it should have been
					formStack.removeLast();
				}
			}
		}
		formStack.add( value );
		setContent( value );
	}
	
	/**
	 *	Push a form on the stack.  The specified form becomes the current
	 *	displayed form.  You should never push the same set of nodes
	 *	more than once to the form manager, and you should never push
	 *	a root form on the stack.
	 */
	public void pushForm( Node value ) {
		final String S_ProcName = "pushForm";
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"value" );
		}
		if( ! ( value instanceof ICFForm ) ) {
			throw new CFLibUnsupportedClassException(getClass(),
				S_ProcName,
				"value",
				value,
				"ICFForm" );
		}
		formStack.add( value );
		setContent( value );
	}
	
	/**
	 *	Close the current form.  If you're paranoid, you can use
	 *	getCurrentForm() to make sure you're the current form before
	 *	invoking this method.
	 *
	 *	If the stack is empty and you are logged in, calling
	 *	closeCurrentForm() will cause showRootMainForm() to be
	 *	invoked.
	 */
	public void closeCurrentForm() {
		if( formStack.isEmpty() ) {
			return;
		}
		formStack.removeLast();
		if( formStack.isEmpty() ) {
			showRootMainForm();
		}
		else {
			setContent( formStack.getLast() );
		}
	}

	/**
	 *	Show the root main form for the facet.
	 */
	public abstract void showRootMainForm();
}

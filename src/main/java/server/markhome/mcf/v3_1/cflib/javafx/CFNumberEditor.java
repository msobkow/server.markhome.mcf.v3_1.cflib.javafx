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

import java.math.*;
import javafx.geometry.Pos;

import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;

public class CFNumberEditor
extends CFFormattedTextField
{
	protected int digits;
	protected int precis;
	protected BigDecimal minValue = null;
	protected BigDecimal maxValue = null;

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

	public CFNumberEditor( int argDigits, int argPrecis ) {
		super();
		final String S_ProcName = "construct";
		setAlignment( Pos.CENTER_RIGHT );
		if( argDigits < 1 ) {
			throw new CFLibArgumentUnderflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
					S_ProcName,
					1,
					"argDigits",
					argDigits,
					1 );
		}
		else if( argDigits > CFLibBigDecimalUtil.MAX_DIGITS ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argDigits",
				argDigits,
				CFLibBigDecimalUtil.MAX_DIGITS );
		}
		if( argPrecis < 0 ) {
			throw new CFLibArgumentUnderflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argPrecis",
				argPrecis,
				0 );
		}
		else if( argPrecis > CFLibBigDecimalUtil.MAX_PRECISION ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argPrecis",
				argPrecis,
				CFLibBigDecimalUtil.MAX_PRECISION );
		}
		if( argPrecis >= argDigits ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argPrecis",
				argPrecis,
				argDigits - 1 );
		}
		minValue = null;
		maxValue = null;
		digits = argDigits;
		precis = argPrecis;
		int width = ( argDigits + argPrecis + 2 ) * 10;
		if( width > 500 ) {
			width = 500;
		}
		setMinWidth( width );
		setMaxWidth( width );
		setPrefWidth( width );
		setMinHeight( 25 );
		setMaxHeight( 25 );
		setPrefHeight( 25 );
	}

	public int getDigits() {
		return( digits );
	}

	public void setDigits( int argDigits ) {
		final String S_ProcName = "setDigits";
		if( argDigits < 1 ) {
			throw new CFLibArgumentUnderflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
					S_ProcName,
					1,
					"argDigits",
					argDigits,
					1 );
		}
		else if( argDigits > CFLibBigDecimalUtil.MAX_DIGITS ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argDigits",
				argDigits,
				CFLibBigDecimalUtil.MAX_DIGITS );
		}
		digits = argDigits;
		minValue = null;
		maxValue = null;
	}
	
	public int getPrecision() {
		return( precis );
	}

	public void setPrecision( int argPrecis ) {
		final String S_ProcName = "setPrecision";
		if( argPrecis < 0 ) {
			throw new CFLibArgumentUnderflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argPrecis",
				argPrecis,
				0 );
		}
		else if( argPrecis > CFLibBigDecimalUtil.MAX_PRECISION ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argPrecis",
				argPrecis,
				CFLibBigDecimalUtil.MAX_PRECISION );
		}

		if( argPrecis >= digits ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argPrecis",
				argPrecis,
				digits - 1 );
		}

		precis = argPrecis;
		minValue = null;
		maxValue = null;
	}

	public void setDigitsAndPrecision( int argDigits, int argPrecis ) {
		final String S_ProcName = "setDigitsAndPrecision";
		if( argDigits < 1 ) {
			throw new CFLibArgumentUnderflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
					S_ProcName,
					1,
					"argDigits",
					argDigits,
					1 );
		}
		else if( argDigits > CFLibBigDecimalUtil.MAX_DIGITS ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argDigits",
				argDigits,
				CFLibBigDecimalUtil.MAX_DIGITS );
		}
		if( argPrecis < 0 ) {
			throw new CFLibArgumentUnderflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argPrecis",
				argPrecis,
				0 );
		}
		else if( argPrecis > CFLibBigDecimalUtil.MAX_PRECISION ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argPrecis",
				argPrecis,
				CFLibBigDecimalUtil.MAX_PRECISION );
		}
		if( argPrecis >= argDigits ) {
			throw new CFLibArgumentOverflowException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"argPrecis",
				argPrecis,
				argDigits - 1 );
		}
		digits = argDigits;
		precis = argPrecis;
		
		if( minValue != null ) {
			BigDecimal newMinValue = CFLibBigDecimalUtil.getAbsoluteMinValue( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag), argDigits, argPrecis );
			if( minValue.compareTo( newMinValue ) < 0 ) {
				minValue = newMinValue;
			}
		}
		
		if( maxValue != null ) {
			BigDecimal newMaxValue = CFLibBigDecimalUtil.getAbsoluteMaxValue( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag), argDigits, argPrecis );
			if( maxValue.compareTo( newMaxValue ) > 0 ) {
				maxValue = newMaxValue;
			}
		}
		
		BigDecimal numberValue = getNumberValue();
		setNumberValue( numberValue );
	}

	public BigDecimal getMinValue() {
		if( minValue == null ) {
			minValue = CFLibBigDecimalUtil.getAbsoluteMinValue( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag), digits, precis );
		}
		return( minValue );
	}

	public void setMinValue( BigDecimal value ) {
		final String S_ProcName = "setMinValue";
		
		if( value == null ) {
			throw new CFLibNullArgumentException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"value" );
		}
		
		BigDecimal coerced = CFLibBigDecimalUtil.coerce( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag), digits, precis, value );

		minValue = coerced;
	}

	public BigDecimal getMaxValue() {
		if( maxValue == null ) {
			minValue = CFLibBigDecimalUtil.getAbsoluteMaxValue( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag), digits, precis );
		}
		return( maxValue );
	}

	public void setMaxValue( BigDecimal value ) {
		final String S_ProcName = "setMaxValue";
		
		if( value == null ) {
			throw new CFLibNullArgumentException( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag),
				S_ProcName,
				1,
				"value" );
		}
		
		BigDecimal coerced = CFLibBigDecimalUtil.coerce( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag), digits, precis, value );

		maxValue = coerced;
	}
	
	public void setNumberValue( BigDecimal value ) {
		if( value == null ) {
			setText( "" );
		}
		else {
			String formatted = CFLibBigDecimalUtil.format( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag), digits, precis, value );
			setText( formatted );
		}
	}

	public BigDecimal getNumberValue() {
		String text = getText();
		BigDecimal retval;
		if( ( text == null ) || ( text.length() <= 0 ) ) {
			retval = null;
		}
		else {
			retval = CFLibBigDecimalUtil.parse( Inz.x(fieldNameInzTag), Inz.s(fieldNameInzTag), digits, precis, text );
		}
		return( retval );
	}
}

/*
jAlarms A simple Java library to enable server apps to send alarms to sysadmins.
Copyright (C) 2009 Enrique Zamudio Lopez

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/
package com.solab.util;

import java.io.ByteArrayOutputStream;

/** A Simple utility to encode byte arrays into Base64 strings and decode Base64 strings
 * into byte arrays.
 * 
 * @author Enrique Zamudio
 */
public class Base64 {

	static final char[] B64_CHARS = new char[64];

	static {
		for (int i = 0; i < 26; i++) {
			B64_CHARS[i] = (char)(i + 65);
		}
		for (int i = 26; i < 52; i++) {
			B64_CHARS[i] = (char)(i + 71);
		}
		for (int i = 52; i < 63; i++) {
			B64_CHARS[i] = (char)(i - 4);
		}
		B64_CHARS[62] = '+';
		B64_CHARS[63] = '/';
	}

	/** Decodifica una cadena de base 64.
	 * @param b64 La cadena codificada. Puede estar separada en renglones.
	 * @return El arreglo de bytes resultante de haber decodificado la cadena.
	 */
	public static byte[] base64Decode(String b64) {

		String input = b64.trim();
		if (input.length() % 4 != 0) {
			throw new IllegalArgumentException("Incomplete data");
		}
		ByteArrayOutputStream bout = new ByteArrayOutputStream(input.length() * 3 / 4 + 1);

		//Recorremos los caracteres de entrada.
		int pos = 0;

		while (pos < input.length()) {
			int b1, b2;

			//Obtener el primer caracter que no sea espacio en blanco.
			char c = input.charAt(pos++);
			while (Character.isWhitespace(c)) {
				c = input.charAt(pos++);
			}
			//los seis bits los recorremos 2 a la izquierda
			b1 = indice(c) << 2;

			//Obtener el segundo caracter
			c = input.charAt(pos++);
			while (Character.isWhitespace(c)) {
				c = input.charAt(pos++);
			}
			//lo recorremos para tomar los primeros 2 bits
			b2 = indice(c) >> 4;
			bout.write(b1 | b2);

			//tomamos los ultimos 4 bits y los movemos a su lugar
			b1 = (indice(c) & 0x0f) << 4;

			//Obtener el tercer caracter
			c = input.charAt(pos++);
			while (Character.isWhitespace(c)) {
				c = input.charAt(pos++);
			}
			//tomamos los primeros 4 bits
			b2 = indice(c) >> 2;
			if (c == '=') {
				pos = input.length() + 1;
				break;
			}
			bout.write(b1 | b2);

			//tomamos los primeros 2 bits
			b1 = indice(c) << 6;

			//Obtener el cuarto caracter
			c = input.charAt(pos++);
			while (Character.isWhitespace(c)) {
				c = input.charAt(pos++);
			}
			if (c == '=') {
				pos = input.length() + 1;
				break;
			}
			b2 = indice(c);
			bout.write(b1 | b2);
		}
		return bout.toByteArray();
	}

	/** Codifica un arreglo de bytes a base 64. No se hace separacion
	 * de renglones ni nada; la cadena devuelta esta compuesta solamente
	 * de los caracteres de base 64 con los cuales fue codificada la entrada.
	 * @param input El arreglo de bytes a codificar.
	 * @param start La posicion inicial donde se comienza a codificar
	 * @param len El numero de bytes a codificar.
	 * @return El arreglo de bytes codificados en base 64, como una cadena.
	 */
	public static String base64Encode(byte[] input, int start, int len) {
		StringBuilder buf = new StringBuilder((input.length * 4) / 3);

		//Leemos de tres en tres, escribimos de cuatro en cuatro
		int end = start + len;
		for (int i = start; i < end; i += 3) {
			int indice = -1;
			int uno = 0, dos = 0, tres = 0;
			uno = input[i];

			//convertir a positivo en caso que sea negativo
			if (uno < 0) {
				uno |= 0x80;
			}

			//tomar los primeros seis bits del primer byte
			indice = (uno & 0xfc) >> 2;
			buf.append(B64_CHARS[indice]);

			//Leer el segundo byte y pasarlo a positivo
			if (i + 1 < input.length) {
				dos = input[i + 1];
				if (dos < 0) {
					dos |= 0x80;
				}
				//Los ultimos 2 bits del primero y los primeros 4 del segundo
				indice = ((uno & 0x03) << 4) | ((dos & 0xf0) >> 4);
				buf.append(B64_CHARS[indice]);
			} else {
				indice = (uno & 0x03) << 4;
				buf.append(B64_CHARS[indice]);
			}

			//Leer el tercer byte y pasarlo a positivo
			if (i + 2 < input.length) {
				tres = input[i + 2];
				if (tres < 0) {
					tres |= 0x80;
				}
				//Los ultimos 4 del segundo y los primeros 2 del tercero
				indice = ((dos & 0x0f) << 2) | ((tres & 0xC0) >> 6);
				buf.append(B64_CHARS[indice]);
				//los ultimos 6 del tercero
				indice = tres & 0x3f;
				buf.append(B64_CHARS[indice]);
			} else if (i+2 == input.length) {
				indice = (dos & 0x0f) << 2;
				buf.append(B64_CHARS[indice]);
				buf.append("=");
			}
			if (i + 1 >= input.length) {
				buf.append("==");
			} else if (i >= input.length) {
			    buf.append("=");
			}
		}
		return buf.toString();
	}

	/** Devuelve el indice de un caracter, en el alfabeto de base 64,
	 * o -1 si dicho caracter no forma parte del alfabeto.
	 * @param c El caracter cuya posicion se desea saber.
	 * @return El indice del caracter en el alfabeto base64, o -1 si no esta.
	 */
	private static int indice(char c) {
		for (int i = 0; i < B64_CHARS.length; i++) {
			if (B64_CHARS[i] == c) {
				return i;
			}
		}
		return -1;
	}

}

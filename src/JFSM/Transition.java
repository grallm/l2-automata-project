/**
 * 
 * Copyright (C) 2017 Emmanuel DESMONTILS
 * 
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 * 
 * 
 * 
 * E-mail:
 * Emmanuel.Desmontils@univ-nantes.fr
 * 
 * 
 **/

package JFSM;

/**
 * Transition.java
 *
 *
 * Created: 2017-08-25
 *
 * @author Emmanuel Desmontils
 * @version 1.0
 */

public class Transition implements Cloneable {
	public String name;
	public String source, cible;
	public String symbol;

	public Transition(String s, String symbol, String c) throws JFSMException {
		if (symbol==null) throw new JFSMException("Un symbole ne peut pas être absent");
		if ((symbol.equals(""))||(symbol.equals("\u03b5"))) 
			throw new JFSMException("Un symbole ne peut pas être vide ou \u03b5");
		this.source = s;
		this.cible = c;
		this.symbol = symbol;
		this.name = s+"-"+symbol+"->"+c;
	}

	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return o;
	}

	public String toString(){return name;}

	/** 
	* Indique si la transition peut être appliquée depuis cet état sur ce symbole.  
	* @param etat L'état courant
	* @param symbol Le symbol courant
	* @return booléen à vrai si la transition est applicable, faux sinon
	*/
	public boolean candidate(String etat, String symbol) {
		return etat.equals(source) && symbol.equals(this.symbol);
	}

	/** 
	* Applique la transition.  
	* @return le nouvel état
	*/
	public String appliquer() {
		return cible ;
	}

}

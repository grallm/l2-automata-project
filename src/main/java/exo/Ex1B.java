package exo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import JFSM.AFD;
import JFSM.AFN;
import JFSM.Automate;
import JFSM.Etat;
import JFSM.JFSMException;
import JFSM.Transition;

public class Ex1B {
   public static void main(String argv []) throws JFSMException {
	   
	   // Automate standard
	 /*  	Set<String> A = new HashSet<String>();
	      A.add("GA");

	      Set<Etat> Q = new HashSet<Etat>();
	      Q.add(new Etat("1"));Q.add(new Etat("2"));

	      Set<Transition> mu = new HashSet<Transition>();

	      mu.add(new Transition("1","BU","2"));

	      Set<String> F = new HashSet<String>();
	      F.add("2");

	      Set<String> I = new HashSet<String>();
	      I.add("1");
	      Automate afn = new AFN(A, Q, I, F, mu);*/
	   
	   // Automate non standard
	      Set<String> A = new HashSet<String>();
	      A.add("MEU");A.add("GA");A.add("ZO");A.add("BU");
	      
	      Set<Etat> Q = new HashSet<Etat>();
	      Q.add(new Etat("1"));Q.add(new Etat("2"));Q.add(new Etat("3"));
	      Q.add(new Etat("4"));Q.add(new Etat("5"));Q.add(new Etat("6"));
	      Q.add(new Etat("7"));Q.add(new Etat("8"));Q.add(new Etat("9"));

	      Set<String> F = new HashSet<String>();
	      F.add("7");
	      F.add("8");
	      F.add("9");
	      
	      Set<String> I = new HashSet<String>();
	      I.add("1");
	      I.add("2");
	      I.add("3");
	      
	      Set<Transition> mu = new HashSet<Transition>();
	      mu.add(new Transition("1","ZO","1"));
	      mu.add(new Transition("1","GA","4"));
	      mu.add(new Transition("1","BU","5"));
	      mu.add(new Transition("2","MEU","1"));
	      mu.add(new Transition("2","BU","5"));
	      mu.add(new Transition("2","ZO","6"));
	      mu.add(new Transition("3","GA","3"));
	      mu.add(new Transition("3","MEU","2"));
	      mu.add(new Transition("3","ZO","6"));
	      mu.add(new Transition("4","GA","7"));
	      mu.add(new Transition("4","ZO","5"));
	      mu.add(new Transition("4","BU","8"));
	      mu.add(new Transition("5","BU","8"));
	      mu.add(new Transition("5","ZO","9"));
	      mu.add(new Transition("5","GA","6"));
	      mu.add(new Transition("6","GA","6"));
	      mu.add(new Transition("6","ZO","9"));
	      mu.add(new Transition("7","MEU","7"));
	      mu.add(new Transition("8","MEU","7"));
	      mu.add(new Transition("9","MEU","8"));
	      mu.add(new Transition("9","BU","9"));

	      Automate afn = new AFN(A, Q, I, F, mu);

	      System.out.println(afn.standardiser().estStandard());
//	      System.out.println(afn);
//	      
//	      List<String> l = new ArrayList<String>();
//	      // MEUMEUBUZOBUMEU
//	      l.add("MEU");l.add("MEU");l.add("BU");l.add("ZO");l.add("BU");l.add("MEU");
//	      System.out.println(afn.run(l) + "\n");
//	      
//	      List<String> l2 = new ArrayList<String>();
//	      // GABUZOMEU
//	      l2.add("GA");l2.add("BU");l2.add("ZO");l2.add("MEU");
//	      System.out.println(afn.run(l2));
	      
//	      List<String> l = new ArrayList<String>();
//	      l.add("MEUMEUBUZOBUMEU");l.add("GABUZOMEU");l.add("ZOZOGAZOGAGAZO");l.add("BUGAZOMEU");
//	      System.out.println(afn.run(l));

	   }
	}

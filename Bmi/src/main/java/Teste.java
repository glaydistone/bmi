import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.com.tecnotran.bmi.model.gestor.MovtoFrota;
import br.com.tecnotran.bmi.repository.AccessDAO;

public class Teste implements Serializable {

	public static void main(String[] args) throws Exception {
		for (int i=0; i<20; i++) {
		System.out.println(getAleatorio());
		}

		/*
		 * AccessDAO dao = new AccessDAO(); EntityManagerFactory factory =
		 * Persistence.createEntityManagerFactory("BoletimAcc"); EntityManager manager =
		 * factory.createEntityManager();
		 * 
		 * EntityTransaction trx = manager.getTransaction(); trx.begin(); //
		 * List<MovtoCustosOperac> CustosOperacionais =
		 * dao.findAll(MovtoCustosOperac.class); // System.out.println(manager.
		 * createNativeQuery("SELECT Movto_Frota.[cod-empresa] FROM Movto_Frota").
		 * getResultList().get(0)); // List<MovtoFrota> frt =
		 * manager.createQuery("from MovtoFrota order by codEmpresa, mesAno").
		 * getResultList(); // for (MovtoFrota m : frt) { //
		 * System.out.println(m.getMesAno()); //
		 * 
		 * 
		 * 
		 * 
		 * DateFormat sdf = new SimpleDateFormat("ddmmyyyy"); java.util.Date dtRef =
		 * sdf.parse("01042021"); String dtAcc = "#2021-04-01#"; //List<Objects[]> frt =
		 * manager.createNativeQuery("Select * from Movto_Frota where [mes-ano] = "
		 * +dtAcc).getResultList(); List<MovtoFrota> frt = dao.recuperaFrotaMes(dtRef);
		 * //for (int i=0; i< frt.size(); i++) { // Object[] obj = (Object[])
		 * frt.get(i); // System.out.println(obj[1]); // }
		 * 
		 * trx.commit(); System.out.println("Testando git");
		 */
	}

	public static BigDecimal getAleatorio() {
		BigDecimal rnd = new BigDecimal(Math.random() * 0.20);
		BigDecimal retorno = new BigDecimal("1.10");
		retorno = retorno.subtract(rnd, MathContext.DECIMAL32);
		return retorno;
	}
}

/*
 * 
 * public static Object clone(Object o) { Object clone = null;
 * 
 * try { clone = o.getClass().newInstance(); } catch (InstantiationException e)
 * { e.printStackTrace(); } catch (IllegalAccessException e) {
 * e.printStackTrace(); }
 * 
 * // Walk up the superclass hierarchy for (Class obj = o.getClass();
 * !obj.equals(Object.class); obj = obj.getSuperclass()) { Field[] fields =
 * obj.getDeclaredFields(); for (int i = 0; i < fields.length; i++) {
 * fields[i].setAccessible(true); try { // for each class/suerclass, copy all
 * fields // from this object to the clone fields[i].set(clone,
 * fields[i].get(o)); } catch (IllegalArgumentException e){} catch
 * (IllegalAccessException e){} } } return clone; }
 *
 ********
 *
 * import java.io.*; import java.util.*; import java.awt.*; public class Cloner
 * { // so that nobody can accidentally create an ObjectCloner object private
 * Cloner(){} // returns a deep copy of an object static public Object
 * deepCopy(Object oldObj) throws Exception { ObjectOutputStream oos = null;
 * ObjectInputStream ois = null; try { ByteArrayOutputStream bos = new
 * ByteArrayOutputStream(); // A oos = new ObjectOutputStream(bos); // B //
 * serialize and pass the object oos.writeObject(oldObj); // C oos.flush(); // D
 * ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray()); // E
 * ois = new ObjectInputStream(bin); // F // return the new object return
 * ois.readObject(); // G } catch(Exception e) {
 * System.out.println("Exception in ObjectCloner = " + e); throw(e); } finally {
 * oos.close(); ois.close(); } }
 * 
 * }
 * 
 * class Test{
 * 
 * public static void main(String[] a) {
 * 
 * Pessoa p = new Pessoa("Original");
 * 
 * try{ Pessoa p1 = (Pessoa) Cloner.deepCopy(p); p1.n = "Clone";
 * System.out.println(p.n); System.out.println(p1.n); } catch(Exception e) {
 * e.printStackTrace(); } } }
 * 
 * class Pessoa implements Serializable { String n;
 * 
 * public Pessoa(String name) { n = name; } }
 */

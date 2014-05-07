/*
	//FileReader
	private FileInputStream fStream=new FileInputStream("savedata.txt");
	private BufferedReader inPut=new BufferedReader(FileReader(fStream));
	private String strLine;
	while(strLine=inPut.readLine())!=null){
		//Print the content on the console
		System.out.println(strLine);
	}
	//Close the input stream
	inPut.close();
	*/
	
	private List<String> data=new ArrayList<String>();
	data.add("2014.01.01,10,2014.01.01,12,sampletext");
	data.add("2014.01.01,12,2014.01.01,14,sampletext2");
	
	Iterator iter=data.iterator();
	while(iter.hasNext()){
		String value=(String)iter.next();
	}
	
	/*
	String separates=„,”;
	StringTokenizer st=new StringTokenizer(sampleText, separates);
	
	while (st.hasMoreToken()) {
		String currText=st.nextToken();
	}
	*/
	package bin;

/**
 * A candy osztaly segitsegevel hozunk letre cukrot a programban.
 * 
 * @author Szatmari Koppany Gergo
 */

public class candy{

	/**
	 * A cukorka szine.
	 */
	private int szin;
	/**
	 * A cukorka tipusa.
	 */
	private int tip;
	
	/**
	 * A szin lekerese.
	 * @return a szin erteket adja vissza
	 */
	public int getSzin() {
		return this.szin;
	}
	/**
	 * A tipus lekerese.
	 * @return a tipus erteket adja vissza
	 */
	public int getTip() {
		return this.tip;
	}
	/**
	 * A szin beallitasa.
	 * @param szin beallitando szin
	 */
	public void setSzin(int szin) {
		this.szin=szin;
	}
	/**
	 * A tipus beallitasa.
	 * @param tip beallitando tipus
	 */
	public void setTip(int tip) {
		this.tip=tip;
	}
	
	/**
	 * Cukorka kerese.
	 * @param szin lekerendo szin
	 * @param tip lekerendo tipus
	 */
	public candy(int szin, int tip){
		this.szin=szin;
		this.tip=tip;
	}

	/**
	 * A lekert cukor szinekor visszaadando adat.
	 * K:kek, Z:zold, P:piros, E:ures
	 * @return visszaadando adat
	 */
	public String szinbetu(){
		switch (this.getSzin()) {
			case 0: return "K";
			case 1: return "Z";
			case 2: return "P";
			default: return "E";
		}
	}
	
	/**
	 * A lekert cukor tipusakor visszaadando adat.
	 * S:sima cukor, V:vizszintes cukorfalo, F:fuggoleges cukorfalo, X:szomszedos cukorfalo, E:ures
	 * @return visszaadando adat
	 */
	public String tipbetu(){
		switch (this.getTip()) {
			case 0: return "S";
			case 1: return "V";
			case 2: return "F";
			case 3: return "X";
			default: return "E";
		}
	}

	/**
	 * A cukor osztaly toString metodusa.
	 * @return A szin lekeresekor visszadando adat es a tipus lekeresekor visszaadando adat kiadasa.
	 */
	public String toString() {
		return szinbetu()+tipbetu();
	}  
}
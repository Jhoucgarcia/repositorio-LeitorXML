package xml;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotaFiscal implements Comparable<NotaFiscal> {
	
	boolean dest = false;
	String id;
	String modelo;
	String dataString;
	Date date;
	String emitente;
	String destinatario;
	double valorFinal;
	long dataLong;
	String dataXML;
	Date dataFormatada;
	String data;
	
	public int compareTo(NotaFiscal outraNota) {
		if (this.dataLong < outraNota.dataLong) {
			return -1;
		}
		if (this.dataLong < outraNota.dataLong) {
			return 1;
		}
		return 0;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public NotaFiscal(String id, String modelo, String data, String emitente, String destinatário, double valorFinal) {
		super();
		this.id = id;
		this.modelo = modelo;
		this.dataString = data;
		this.emitente = emitente;
		this.destinatario = destinatário;
		this.valorFinal = valorFinal;
	}

	public NotaFiscal() {

	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getData() {
		return dataString;
	}

	public void setData(String data) {
		this.dataString = data;
	}

	public String getEmitente() {
		return emitente;
	}

	public void setEmitente(String emitente) {
		this.emitente = emitente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(double valorFinal) {
		this.valorFinal = valorFinal;
	}

	public long getDataLong() {
		return dataLong;
	}

	public void setDataLong(long dataLong) {
		this.dataLong = dataLong;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void imprimirNota() {
		System.out.println("ID: " + this.getId());
		System.out.println("Modelo: " + this.getModelo());
		System.out.println("Data emissão: " + this.getData());
		System.out.println("Emitente: " + this.getEmitente());
		System.out.println("Destinatário: " + this.getDestinatario());
		System.out.println("Valor final: " + this.getValorFinal());
		System.out.println("====================================================");
	}

	public static Date formataData(String data) throws Exception {
		if (data == null || data.equals(""))
			return null;
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (java.util.Date) formatter.parse(data);
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}

}

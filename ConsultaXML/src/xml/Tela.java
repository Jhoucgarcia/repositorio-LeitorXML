package xml;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Tela extends JFrame {

	private JPanel contentPane;
	private FileDialog fsalvar, fabrir;
	private JTextField valorCaminho;
	private String caminhoArquivos;

	// INFORMAÇÕES DAS NOTAS
	// ----------------------------------
	boolean dest = false;
	double valorTotal = 0;
	int qtdeTotalNota = 0;
	int qtdeNFE = 0;
	int qtdeSAT = 0;
	double valorTotalSAT = 0;
	double valorTotalNFE = 0;
	double valorTotalNFCe = 0;
	int qtdeNFCe = 0;
	String modelo = "";
	String dataEmissao = "";
	public static List<NotaFiscal> notas = new ArrayList<NotaFiscal>();
	String data = "";
	// ----------------------------------

	
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela() {
		setTitle("Consulta XML");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 153);
		setLocation(450, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		fabrir = new FileDialog(this, "Abrir", FileDialog.LOAD);
		fsalvar = new FileDialog(this, "Salvar", FileDialog.SAVE);

		valorCaminho = new JTextField();
		valorCaminho.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		valorCaminho.setDisabledTextColor(new Color(255, 255, 255));
		valorCaminho.setCaretColor(new Color(0, 0, 0));
		valorCaminho.setBackground(new Color(255, 255, 255));
		valorCaminho.setForeground(new Color(0, 0, 0));
		valorCaminho.setToolTipText("Insira o caminho da pasta ou clique em carregar pasta");
		valorCaminho.setBounds(43, 36, 325, 20);
		contentPane.add(valorCaminho);
		valorCaminho.setColumns(10);

		JButton btnCarregar = new JButton("Carregar pasta");
		btnCarregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCarregar.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnCarregar.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		btnCarregar.setForeground(new Color(0, 0, 0));
		btnCarregar.setBackground(new Color(51, 153, 255));
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fabrir.setVisible(true);
				if (fabrir.getDirectory() == null) {
					return;
				}
				caminhoArquivos = fabrir.getDirectory();
				valorCaminho.setText("");
				valorCaminho.setText(caminhoArquivos);
			}
		});
		btnCarregar.setBounds(10, 81, 130, 23);
		contentPane.add(btnCarregar);

		JButton btnGerar = new JButton("Gerar arquivo");
		btnGerar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGerar.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnGerar.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		btnGerar.setForeground(new Color(0, 0, 0));
		btnGerar.setBackground(new Color(51, 153, 255));
		btnGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
							
				
				verificaXML();
			}
		});
		btnGerar.setBounds(150, 81, 115, 23);
		contentPane.add(btnGerar);
		getRootPane().setDefaultButton(btnGerar);

		JLabel lblConsultaXml = new JLabel("Consulta XML");
		lblConsultaXml.setForeground(new Color(0, 102, 153));
		lblConsultaXml.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		lblConsultaXml.setBounds(139, 11, 212, 14);
		contentPane.add(lblConsultaXml);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnLimpar.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		btnLimpar.setForeground(new Color(0, 0, 0));
		btnLimpar.setBackground(new Color(51, 153, 255));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				valorCaminho.setText("");
			}
		});
		btnLimpar.setBounds(275, 81, 110, 23);
		contentPane.add(btnLimpar);
	}

	void verificaXML() {

		notas.clear();
		qtdeTotalNota = 0;
		qtdeNFCe = 0;
		qtdeNFE = 0;
		qtdeSAT = 0;
		valorTotalNFCe = 0;
		valorTotalNFE = 0;
		valorTotalSAT = 0;
		valorTotal = 0;

		String txt = "";
		File caminho = new File(valorCaminho.getText());
		if (!caminho.isDirectory()) {
			JOptionPane.showMessageDialog(null, "Caminho inválido");
			//valorCaminho.set
			return;
		}

		File[] arquivos = caminho.listFiles();
		fsalvar.setVisible(true);

		if (fsalvar.getDirectory() == null) {
			return;
		}

		txt = fsalvar.getDirectory() + fsalvar.getFile();

		FileWriter arq = null;
		try {
			arq = new FileWriter(txt);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		PrintWriter gravarArq = new PrintWriter(arq);
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		for (File s : arquivos) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			dest = false;
			if (s.toString().endsWith("procNFe.xml")) {

				File xml = new File(s.toString());
				try {
					doc = builder.build(xml);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Element root = doc.getRootElement();
				List<Element> lista = root.getChildren();
				Element NFe = lista.get(0);
				List<Element> filhosNFe = NFe.getChildren();
				Element infNFe = filhosNFe.get(0); // OK
				List<Element> filhosInfNFe = infNFe.getChildren();

				// ID DA NOTA
				NotaFiscal nota = new NotaFiscal();
				nota.setId(infNFe.getAttributeValue("Id"));

				// MODELO
				for (Element e : filhosInfNFe) {
					if (e.getName().equalsIgnoreCase("ide")) {
						List<Element> filhosIde = e.getChildren();
						for (Element x : filhosIde) {
							if (x.getName().equalsIgnoreCase("mod")) {
								if (x.getValue().equalsIgnoreCase("55")) {
									nota.setModelo("NFE");
									modelo = "nfe";
									qtdeNFE++;
									dest = true;
								} else if (x.getValue().equalsIgnoreCase("65")) {
									nota.setModelo("NFCe");
									qtdeNFCe++;
									modelo = "nfce";
								}

							}
						}
					}
				}

				// DATA EMISSÃO
				for (Element e : filhosInfNFe) {
					if (e.getName().equalsIgnoreCase("ide")) {
						List<Element> filhosIde = e.getChildren();
						for (Element x : filhosIde) {
							if (x.getName().equalsIgnoreCase("dEmi")) {
								dataEmissao = x.getValue();
								String ano = dataEmissao.substring(0, 4);
								String mes = dataEmissao.substring(5, 7);
								String dia = dataEmissao.substring(8, 10);
								data = dia + "/" + mes + "/" + ano;
								Date dataFormatada = null;
								try {
									dataFormatada = NotaFiscal.formataData(data);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								nota.setData(data);
								nota.setDate(dataFormatada);
								nota.setDataLong(Long.parseLong(ano + mes + dia));
							} else if (x.getName().equalsIgnoreCase("dhEmi")) {
								dataEmissao = x.getValue();
								String ano = dataEmissao.substring(0, 4);
								String mes = dataEmissao.substring(5, 7);
								String dia = dataEmissao.substring(8, 10);
								data = dia + "/" + mes + "/" + ano;
								nota.setData(data);
								Date dataFormatada = null;
								try {
									dataFormatada = NotaFiscal.formataData(data);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								nota.setDate(dataFormatada);
								nota.setDataLong(Long.parseLong(ano + mes + dia));
							}
						}
					}
				}

				// EMITENTE
				for (Element e : filhosInfNFe) {
					if (e.getName().equalsIgnoreCase("emit")) {
						List<Element> filhosEmit = e.getChildren();
						for (Element x : filhosEmit) {
							if (x.getName().equalsIgnoreCase("xNome")) {
								nota.setEmitente(x.getValue());
							}
						}
					}
				}

				// DESTINATARIO
				if (dest) {
					for (Element e : filhosInfNFe) {
						if (e.getName().equalsIgnoreCase("dest")) {
							List<Element> filhosDest = e.getChildren();
							for (Element x : filhosDest) {
								if (x.getName().equalsIgnoreCase("xNome")) {
									nota.setDestinatario(x.getValue());
								}
							}
						}
					}
				} else {
					nota.setDestinatario("CONSUMIDOR");
				}

				// VALOR DA NOTA
				for (Element e : filhosInfNFe) {
					if (e.getName().equalsIgnoreCase("total")) {
						List<Element> filhosTotal = e.getChildren();
						for (Element x : filhosTotal) {
							if (x.getName().equalsIgnoreCase("ICMSTot")) {
								List<Element> filhosICMSTot = x.getChildren();
								for (Element k : filhosICMSTot) {
									if (k.getName().equals("vNF")) {
										valorTotal = valorTotal + Double.parseDouble(k.getValue());
										nota.setValorFinal(Double.parseDouble(k.getValue()));
										if (modelo.equalsIgnoreCase("nfe")) {
											valorTotalNFE = valorTotalNFE + Double.parseDouble(k.getValue());
										} else if (modelo.equalsIgnoreCase("nfce")) {
											valorTotalNFCe = valorTotalNFCe + Double.parseDouble(k.getValue());
										}
									}
								}
							}
						}
					}
				}
				notas.add(nota);
				qtdeTotalNota++;

				// SAT

			} else if (s.toString().contains("AD")) {

				NotaFiscal nota = new NotaFiscal();

				try {
					doc = builder.build(s.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}

				Element root = doc.getRootElement();
				List<Element> elementos = root.getChildren();
				Element infCFe = elementos.get(0);
				List<Element> filhosInfCFe = infCFe.getChildren();

				// ID ---- SAT
				nota.setId(infCFe.getAttributeValue("Id"));

				// Emitente ---- SAT
				for (Element e : filhosInfCFe) {
					if (e.getName().equalsIgnoreCase("emit")) {
						List<Element> filhosEmit = e.getChildren();
						for (Element x : filhosEmit) {
							if (x.getName().equalsIgnoreCase("xNome")) {
								nota.setModelo("SAT");
								nota.setEmitente(x.getValue());
								nota.setDestinatario("CONSUMIDOR");
							}
						}
					}
				}

				// DATA E HORA ---- SAT
				for (Element e : filhosInfCFe) {
					if (e.getName().equalsIgnoreCase("ide")) {
						List<Element> filhosEmit = e.getChildren();
						for (Element x : filhosEmit) {
							if (x.getName().equalsIgnoreCase("dEmi")) {
								data = x.getValue().substring(6, 8) + "/" + x.getValue().substring(4, 6) + "/"
										+ x.getValue().substring(0, 4);
							} else if (x.getName().equalsIgnoreCase("hEmi")) {
								String hora = x.getValue().substring(0, 2) + ":" + x.getValue().substring(2, 4);
								nota.setData(data + " " + hora);
							}
						}
					}
				}

				// VALOR VENDA ---- SAT
				for (Element e : filhosInfCFe) {
					if (e.getName().equalsIgnoreCase("total")) {
						List<Element> filhosTotal = e.getChildren();
						for (Element x : filhosTotal) {
							if (x.getName().equalsIgnoreCase("vCFe")) {
								nota.setValorFinal(Double.parseDouble(x.getValue()));
								valorTotalSAT = valorTotalSAT + Double.parseDouble(x.getValue());
								valorTotal = valorTotal + Double.parseDouble(x.getValue());
							}
						}
					}
				}
				notas.add(nota);
				qtdeTotalNota++;
				qtdeSAT++;
			}

		}
		Collections.sort(notas);
		for (NotaFiscal e : notas) {
			gravarArq.printf("ID: " + e.getId() + System.getProperty("line.separator"));
			gravarArq.printf("Modelo: " + e.getModelo() + System.getProperty("line.separator"));
			gravarArq.printf("Data: " + e.getData() + System.getProperty("line.separator"));
			gravarArq.printf("Emitente: " + e.getEmitente() + System.getProperty("line.separator"));
			gravarArq.printf("Destinatário: " + e.getDestinatario() + System.getProperty("line.separator"));
			gravarArq.printf("Valor final: " + e.getValorFinal() + System.getProperty("line.separator"));
			gravarArq.printf("=========================================================\n"
					+ System.getProperty("line.separator"));
		}

		if (qtdeTotalNota != 0) {
			DecimalFormat df = new DecimalFormat("00.00");
			gravarArq.printf("Total NFE: " + qtdeNFE + System.getProperty("line.separator"));
			gravarArq.printf("Valor: R$" + df.format(valorTotalNFE) + System.getProperty("line.separator"));
			gravarArq.printf(System.getProperty("line.separator"));
			gravarArq.printf("Total NFCe: " + qtdeNFCe + System.getProperty("line.separator"));
			gravarArq.printf("Valor: R$" + df.format(valorTotalNFCe) + System.getProperty("line.separator"));
			gravarArq.printf(System.getProperty("line.separator"));
			gravarArq.printf("Total SAT: " + qtdeSAT + System.getProperty("line.separator"));
			gravarArq.printf("Valor: R$" + df.format(valorTotalSAT) + System.getProperty("line.separator"));
			gravarArq.printf(System.getProperty("line.separator"));
			gravarArq.printf("QUANTIDADE TOTAL DE NOTAS: " + qtdeTotalNota + System.getProperty("line.separator"));
			gravarArq.printf("VALOR TOTAL: R$" + df.format(valorTotal) + System.getProperty("line.separator"));
			try {
				arq.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Arquivo " + fsalvar.getFile() + " gerado com sucesso");
		} else {
			JOptionPane.showMessageDialog(null, "Nenhuma nota encontrada.");
		}

	}

}
